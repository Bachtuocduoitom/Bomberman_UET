package entities.DynamicObject;

import entities.Bomb.Bomb;
import entities.Entity;
import entities.Items.BombItem;
import entities.Items.FlameItem;
import entities.Items.SpeedItem;
import entities.StaticObject.Grass;
import entities.StaticObject.Portal;
import graphic.Sprite;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import java.io.IOException;

import static bombermanGame.BombermanGame.*;

public class Bomber extends DynamicObject {
    public int WIDTH = 31;
    public int HEIGHT = 13;
    private KeyCode direction ;
    protected boolean space = false;

    public Bomber(double x, double y, Image img) {
        super( x, y, img);
        vel = 0.05;
    }

    @Override
    public void update() {
        checkDistanceToEnemy();
        checkDistanceToFlame();
        checkTouchPortal();
        detectPlaceBomb();
        checkTouchItem();
        if (_alive) {
            chooseAliveSprite();
        } else {
            chooseDeadSprite();
        }

    }

    @Override
    public void render(GraphicsContext gc) {

        super.render(gc);
    }


    public EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (direction == event.getCode()) {
                if (direction == KeyCode.LEFT) {
                    img = Sprite.player_left.getFxImage();
                }
                if (direction == KeyCode.RIGHT) {
                    img = Sprite.player_right.getFxImage();
                }
                if (direction == KeyCode.UP) {
                    img = Sprite.player_up.getFxImage();
                }
                if (direction == KeyCode.DOWN) {
                    img = Sprite.player_down.getFxImage();
                }
                if (direction == KeyCode.SPACE) {
                    space = false;
                }
                direction = null;
            }
        }
    };

    public EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.LEFT || keyCode == KeyCode.RIGHT|| keyCode == KeyCode.UP || keyCode == KeyCode.DOWN) {
                direction = keyCode;
            }
            if (keyCode == KeyCode.SPACE) {
                space = true;
            }
        }

    };

    private void chooseAliveSprite() {
        if (direction == KeyCode.LEFT) {
            img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, _animate, 15).getFxImage();
            animate();
            _direction = "left";
            if (canMove(String.valueOf(_direction))) {
                setX(getX() - vel);
                if (getX() < 1) setX(1);
            }
        }
        if (direction == KeyCode.RIGHT) {
            img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, _animate, 15).getFxImage();
            animate();
            _direction = "right";
            if (canMove(String.valueOf(_direction))) {
                setX(getX() + vel);
                if (getX() >= WIDTH - 2) setX(WIDTH - 2);
            }
        }
        if (direction == KeyCode.UP) {
            img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, _animate, 15).getFxImage();
            animate();
            _direction = "up";
            if (canMove(String.valueOf(_direction))) {
                setY(getY() - vel);
                if (getY() < 1) setY(1);
            }
        }
        if (direction == KeyCode.DOWN) {
            img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, _animate, 15).getFxImage();
            animate();
            _direction = "down";
            if (canMove(String.valueOf(_direction))) {
                setY(getY() + vel);
                if (getY() >= HEIGHT - 2) setY(HEIGHT - 2);
            }
        }


    }

    private void chooseDeadSprite() {
        _animate = 0;
        img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, _animate, 15).getFxImage();
    }

    public void checkDistanceToEnemy() {
        for (Entity obstacle : entities) {
            if (obstacle instanceof Enemy)
                if (Math.abs(obstacle.getX() - x) < 0.8 && Math.abs(obstacle.getY() - y) < 0.8) {
                    this.die();
                }
        }

    }

    public void detectPlaceBomb() {
        double min = 100;
        double _x = 1;
        double _y = 1;
        for (Entity object : stillObjects) {
            if (object instanceof Grass) {
                if (Math.abs(object.getX() - x) + Math.abs(object.getY() - y) < min) {
                    min = Math.abs(object.getX() - x) + Math.abs(object.getY() - y);
                    _x = object.getX();
                    _y = object.getY();
                }
            }
        }
        if (space) {
            placeBomb((int)_x, (int)_y);
            space = false;
        }
    }

    public void placeBomb(int x, int y) {
        Bomb bomb = new Bomb(x, y, Sprite.bomb.getFxImage());
        if (numberOfBombs < bomMax) {
            stillObjects.add(bomb);
            numberOfBombs++;
        }
    }

    public void checkDistanceToFlame() {
        for (Entity obstacle : listFlame) {
            if (Math.abs(obstacle.getX() - x) < 0.8 && Math.abs(obstacle.getY() - y) < 0.8) {
                this.die();
            }
        }
        for (Entity obstacle : listFlameSegment) {
            if (Math.abs(obstacle.getX() - x) < 0.8 && Math.abs(obstacle.getY() - y) < 0.8) {
                this.die();
            }
        }
    }

    public void checkTouchItem() {
        for (Entity item : stillObjects) {
            if (Math.abs(x - item.getX()) < 0.8 && Math.abs(y - item.getY()) < 0.8) {
                if (item instanceof FlameItem) {
                    RADIUS++;
                    stillObjects.remove(item);
                    break;
                } else if (item instanceof SpeedItem) {
                    stillObjects.remove(item);
                    vel += 0.03;
                    break;
                } else if (item instanceof BombItem) {
                    stillObjects.remove(item);
                    bomMax++;
                    break;
                }
            }
        }
    }

    public void checkTouchPortal() {
        for (Entity portal : stillObjects) {
            if (portal instanceof Portal) {
                if (((Portal) portal).getIsOpen() && Math.abs(x - portal.getX()) < 0.8 && Math.abs(y - portal.getY()) < 0.8) {
                    isWin = true;
                }
            }
        }
    }

    public Bomber getBomber() {
        return this;
    }

    public void setVelocity(int velocity) {
        this.vel = velocity;
    }

    public void die() {
        _alive = false;
    }

    public boolean isAlive() {
        return _alive;
    }

}
