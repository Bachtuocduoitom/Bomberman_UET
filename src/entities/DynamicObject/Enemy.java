package entities.DynamicObject;

import bombermanGame.BombermanGame;
import entities.Entity;
import graphic.Sprite;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import static bombermanGame.BombermanGame.listFlameSegment;

public abstract class Enemy extends DynamicObject {
    protected int timeToDead = 0;

    public Enemy(double x, double y, Image img) {
        super(x, y, img);

    }

    public Enemy(double x, double y, Image img, double vel) {
        super(x, y, img);
        this.vel = vel;
    }

    @Override
    public void update() {
        if (!_alive) timeToDead++;
        else {
            checkDistanceToFlame();
            animate();
            calculateMove();
        }

    }

    @Override
    public void render(GraphicsContext gc) {
        if (_alive)
            chooseSprite();
        else {
            if (timeToDead < 40) {
                _animate = 0;
                img = Sprite.movingSprite(Sprite.mob_dead1,
                        Sprite.mob_dead2,
                        Sprite.mob_dead3, _animate, 40).getFxImage();
            } else {
                BombermanGame.entities.remove(this);
            }
        }
        super.render(gc);
    }

    public void calculateMove() {
        double xa = 0, ya = 0;

        if (_direction == "up") ya--;
        if (_direction == "down") ya++;
        if (_direction == "left") xa--;
        if (_direction == "right") xa++;

        if (canMove(_direction)) {
            x += xa * vel;
            y += ya * vel;
            if (canTurn() > 2 && round1(x) == Math.round(x) && round1(y) == Math.round(y)) {
                _direction = calculateDirection();
            }
        } else {
            _direction = calculateDirection() ;
        }
    }

    public int canTurn() {
        int count = 0;
        String direct = "";
        for (int i = 0; i < 4 ; i++) {
            if (i == 0) {
                direct = "up";
            } else if (i == 1) {
                direct = "right";
            } else if (i == 2) {
                direct = "down";
            } else {
                direct = "left";
            }
            if(canMove(direct)) {
                count++;
            }
        }
        return count;
    }

    public void checkDistanceToFlame() {
        for (Entity obstacle : listFlameSegment) {
            if (Math.abs(obstacle.getX() - x) < 0.8 && Math.abs(obstacle.getY() - y) < 0.8) {
                this.die();
            }
        }
    }

    public void die() {
        _alive = false;
        BombermanGame.countEnemy--;
    }

    protected abstract void chooseSprite();
    public abstract String calculateDirection();
}
