package bombermanGame;

import entities.Bomb.Flame;
import entities.Bomb.FlameSegment;
import entities.DynamicObject.Balloom;
import entities.DynamicObject.Bomber;
import entities.DynamicObject.Oneal;
import entities.Entity;
import entities.StaticObject.Brick;
import entities.StaticObject.Grass;
import entities.StaticObject.Portal;
import entities.StaticObject.Wall;
import graphic.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Entity> entities = new ArrayList<>();
    public static List<Flame> listFlame = new ArrayList<>();
    public static List<FlameSegment> listFlameSegment = new ArrayList<>();
    public static List<String> map = new ArrayList<>();
    public static int countEnemy = 0;
    public static int numberOfBombs = 0;
    public static int bomMax = 1;
    public static int RADIUS = 1;
    public static boolean isWin = false;

    private GraphicsContext gc;
    private Canvas canvas;
    private Group root;
    private Bomber bomberman;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("Bomberman Game");
        try {
            URL url = Paths.get("res/sprites/bomber_down.png").toUri().toURL();
            Image icon = new Image(String.valueOf(url));
            stage.getIcons().add(icon);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        stage.show();

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        bomberman.render(gc);

        scene.setOnKeyPressed(bomberman.keyPressed);
        scene.setOnKeyReleased(bomberman.keyReleased);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!bomberman.isAlive()) {
                    Pane pane = new Pane();
                    pane.setPrefSize(WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE);
                    try {
                        URL url = Paths.get("res/sprites/bombermangameover.jpg").toUri().toURL();
                        ImageView image = new ImageView(String.valueOf(url));
                        pane.getChildren().add(image);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    root.getChildren().add(pane);

                }
                if (isWin) {
                    Pane pane = new Pane();
                    pane.setPrefSize(WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE);
                    try {
                        URL url = Paths.get("res/sprites/bombermanwin.jpg").toUri().toURL();
                        ImageView image = new ImageView(String.valueOf(url));
                        pane.getChildren().add(image);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    root.getChildren().add(pane);
                }
                render();
                update();

            }
        };
        timer.start();
        createMap();
        entities.add(bomberman);

    }

    public void createMap() throws IOException {
        String url = "res/levels/Level1.txt";
        File fileMap = new File(String.valueOf(url));
        Scanner scanner = null;
        try {
        scanner = new Scanner(fileMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = scanner.nextLine();
        String parameter[] = line.split(" ");
        for (int i = 0; i < HEIGHT; i++) {
            line = scanner.nextLine().trim() + "\n";
            map.add(line);
            //System.out.println(line);
        }
        Entity object;
        for (int i = 0; i < WIDTH; i++)  {
            for (int j = 0; j < HEIGHT; j++) {
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                    stillObjects.add(object);
                } else {
                    switch (map.get(j).charAt(i)) {
                        case '*': case 'f' : case 's' : case 'b' :
                            object = new Brick(i, j, Sprite.brick.getFxImage());
                            break;
                        case '#':
                            object = new Wall(i, j, Sprite.wall.getFxImage());
                            break;
                        case 'x':
                            object = new Brick(i, j, Sprite.brick.getFxImage());
                            break;
                        case '1':
                            object = new Grass(i, j, Sprite.grass.getFxImage());
                            entities.add(new Balloom(i, j, Sprite.balloom_left1.getFxImage()));
                            countEnemy ++;
                            break;
                        case '2':
                            object = new Grass(i, j, Sprite.grass.getFxImage());
                            entities.add(new Oneal(i, j, Sprite.oneal_left1.getFxImage()));
                            countEnemy ++;
                            break;
                        default:
                            object = new Grass(i, j, Sprite.grass.getFxImage());
                            stillObjects.add(object);
                    }
                    stillObjects.add(object);
                }

            }
        }
    }

    public void update() {
        for(int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
        for(int i = 0; i < stillObjects.size(); i++) {
            stillObjects.get(i).update();
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(int i = 0; i < stillObjects.size(); i++) {
            stillObjects.get(i).render(gc);
        }
        for(int i = 0; i < entities.size(); i++) {
            entities.get(i).render(gc);
        }

    }

}