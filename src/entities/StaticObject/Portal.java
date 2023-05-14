package entities.StaticObject;

import javafx.scene.image.Image;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import static bombermanGame.BombermanGame.countEnemy;

public class Portal extends StaticObject {
    private boolean isOpen = false;

    public Portal(double x, double y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if(countEnemy == 0){
            isOpen = true;
            try {
                URL url = Paths.get("res/sprites/portal_open.png").toUri().toURL();
                this.img = new Image(String.valueOf(url));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
    }

    public boolean getIsOpen() {
        return isOpen;
    }
}
