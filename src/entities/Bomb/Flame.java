package entities.Bomb;

import entities.Entity;
import entities.StaticObject.Brick;
import entities.StaticObject.Wall;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static bombermanGame.BombermanGame.listFlameSegment;
import static bombermanGame.BombermanGame.stillObjects;

public class Flame extends Entity {
    private int radius;
    protected String direction;
    protected FlameSegment[] flameSegments;
    public Flame(double x, double y) {
        super(x, y);
    }

    public Flame(int x, int y, String direction, int radius) {
        super(x, y);
        this.direction = direction;
        this.radius = radius;
        createFlameSegments();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < flameSegments.length; i++) {
            flameSegments[i].render(gc);
        }
        super.render(gc);
    }

    private void createFlameSegments() {
        flameSegments = new FlameSegment[sizeOfFlame()];
        int xa = 0;
        int ya = 0;
        if (direction.equals("up")) ya = -1;
        if (direction.equals("right")) xa = 1;
        if (direction.equals("down")) ya = 1;
        if (direction.equals("left")) xa = -1;
        for (int i = 0; i < flameSegments.length; i++) {
            int xb = (int) (x + xa * (i + 1));
            int yb = (int) (y + ya * (i + 1));
            if (i == flameSegments.length - 1) {
                flameSegments[i] = new FlameSegment(xb, yb, direction, true);
                listFlameSegment.add(flameSegments[i]);
            } else {
                flameSegments[i] = new FlameSegment(xb, yb, direction, false);
                listFlameSegment.add(flameSegments[i]);
            }
        }
    }

    private int sizeOfFlame() {
        int xa = 0;
        int ya = 0;
        if (direction.equals("up")) ya = -1;
        if (direction.equals("right")) xa = 1;
        if (direction.equals("down")) ya = 1;
        if (direction.equals("left")) xa = -1;

        for (int i = 0; i < radius; i++) {
            int xb = (int) (x + xa * (i + 1));
            int yb = (int) (y + ya * (i + 1));
            Entity entity = null;
            for (Entity e : stillObjects) {
                if ((int)e.getX() == xb && (int)e.getY() == yb) {
                    entity = e;
                    break;
                }
            }
            if (entity instanceof Wall) return i;
            if (entity instanceof Brick) {
                ((Brick) entity)._destroyed = true;
                return i;
            }
        }
        return radius;
    }
}
