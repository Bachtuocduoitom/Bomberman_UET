package entities.DynamicObject;

import entities.Bomb.Bomb;
import entities.Entity;
import entities.StaticObject.Brick;
import entities.StaticObject.Wall;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import static bombermanGame.BombermanGame.stillObjects;


public abstract class DynamicObject extends Entity {
    protected double vel;
    protected String _direction = null;
    protected boolean _moving = true;
    protected int _animate = 0;
    protected final int MAX_ANIMATE = 7500;
    protected void animate() {
        if(_animate < MAX_ANIMATE) _animate++; else _animate = 0;
    }

    public DynamicObject(double x, double y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    public boolean canMove(String direction) {
        double space = 1.0;
        if (direction.equals("up")) {
            for (int i = 0; i < stillObjects.size(); i++) {
                Entity obstacle = stillObjects.get(i);
                if (Math.abs(obstacle.getX() - round1(getX())) < space && round1(getY()) == round1(obstacle.getY() + space)) {
                    if (obstacle instanceof Bomb) {
                        return false;
                    }
                    else if (obstacle instanceof Brick) {
                        return false;

                    } else if (obstacle instanceof Wall) {
                            return false;
                    }
                }
            }
        } else if (direction.equals("down")) {
            for (int i = 0; i < stillObjects.size(); i++) {
                Entity obstacle = stillObjects.get(i);
                if (Math.abs(obstacle.getX() - round1(getX())) < space && round1(getY()) == round1(obstacle.getY() - space)) {
                    if (obstacle instanceof Bomb) {
                        return false;
                    }
                    else if (obstacle instanceof Brick) {
                        return false;

                    }else if (obstacle instanceof Wall) {
                        return false;
                    }
                }
            }
        } else if (direction.equals("left")) {
            for (int i = 0; i < stillObjects.size(); i++) {
                Entity obstacle = stillObjects.get(i);
                if (Math.abs(obstacle.getY() - round1(getY())) < space && round1(getX()) == round1(obstacle.getX() + space)) {
                    if (obstacle instanceof Bomb) {
                        return false;
                    } else if (obstacle instanceof Brick) {
                            return false;
                    } else if (obstacle instanceof Wall) {
                        return false;
                    }
                }
            }
        } else if (direction.equals("right")) {
            for (int i = 0; i < stillObjects.size(); i++) {
                Entity obstacle = stillObjects.get(i);
                if (Math.abs(obstacle.getY() - round1(getY())) < space && round1(getX()) == round1(obstacle.getX() - space)) {
                    if (obstacle instanceof Bomb) {
                        return false;
                    }
                    else if (obstacle instanceof Brick) {
                        return false;
                    }else if (obstacle instanceof Wall) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public double round1(double n) {
        return (double) Math.round(n * 10) / 10;
    }

}
