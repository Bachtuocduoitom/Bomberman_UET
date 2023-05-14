package entities.Bomb;

import bombermanGame.BombermanGame;
import entities.DynamicObject.DynamicObject;
import entities.Entity;
import graphic.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static bombermanGame.BombermanGame.*;

public class Bomb extends DynamicObject {
    protected boolean checkFlame = true;
    protected double timeToExplode = 120;
    public int timeAfter = 20;
    protected boolean exploded = false;
    protected Flame[] flames;
    public Bomb(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        checkInstanceExplode();
        if (timeToExplode > 0)
            timeToExplode--;
        else {
            if (!exploded) {
                explode();
            } else {
                updateFlames();
            }
            if (timeAfter > 0)
                timeAfter--;
            else {
                listFlame.clear();
                listFlameSegment.clear();
                stillObjects.remove(this);
                checkFlame = false;
            }
        }
        animate();
    }

    @Override
    public void render(GraphicsContext gc) {
        if (exploded) {
            if (checkFlame) img = Sprite.bomb_exploded2.getFxImage();
            renderFlames(gc);
        } else {
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 50).getFxImage();
        }
        super.render(gc);
    }

    public void renderFlames(GraphicsContext gc) {
        if (checkFlame) {
            for (int i = 0; i < flames.length; i++) {
                flames[i].render(gc);
            }
        }
    }

    protected void updateFlames() {
        for (int i = 0; i < flames.length; i++) {
            flames[i].update();
        }
    }

    protected void explode() {
        exploded = true;
        int radius = RADIUS;
        Flame flame0 = new Flame((int) x, (int) y, "up", radius);
        Flame flame1 = new Flame((int) x, (int) y, "right", radius);
        Flame flame2 = new Flame((int) x, (int) y, "down", radius);
        Flame flame3 = new Flame((int) x, (int) y, "left", radius);
        flames = new Flame[]{flame0, flame1, flame2, flame3};
        listFlame.add(flame0);
        listFlame.add(flame1);
        listFlame.add(flame2);
        listFlame.add(flame3);
        numberOfBombs--;
    }

    public void checkInstanceExplode() {
        for (Entity flameSegment : listFlameSegment) {
            if (Math.abs(flameSegment.getX() - x) < 0.8 && Math.abs(flameSegment.getY() - y) < 0.8) {
                timeToExplode = -1;
                break;
            }
        }
    }
}
