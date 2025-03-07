package my_project.model;

import KAGO_framework.control.ViewController;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;
import static my_project.Config.WINDOW_WIDTH;
import static my_project.Config.WINDOW_HEIGHT;
import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Repräsentiert eine Kugel (einen Kreis), der in eine Schlange eingefügt werden soll. Dabei muss jeder QueueBall immer
 * seinen Vorgänger kennen, damit er zu ihm Abstand halten kann.
 */
public class Player extends GraphicalObject {

    private int hp;
    private int speed;

    private String direction;

    private BufferedImage standingImage, northImage, southImage, westImage, eastImage, northStandingImage, southStandingImage, westStandingImage, eastStandingImage;

    private double upperBound = -50, lowerBound = 1000000, rightBound = 100000,leftBound = -50;


    //Referenzen
    private ViewController viewController;

    /**
     * Erzeugt einen neuen QueueBall
     * @param x Startposition x
     * @param y Startposition y
     */
    public Player(double x, double y, ViewController viewController) {
        super("images/spielerStehendN.png"); // Standing image als default
        this.viewController = viewController;
        this.x = x;
        this.y = y;
        this.radius = 20;
        this.speed = 150;
        this.hp = 100;
        this.direction = "north";
        loadImages(); // Load all images

        //BufferedImage image = null;

        //image = ImageIO.read(imagefile);
    }
    private void loadImages() {
        try {
            File imagefileN = new File("images/spielerN.png");
            File imagefileS = new File("images/spielerS.png");
            File imagefileW = new File("images/spielerW.png");
            File imagefileE = new File("images/spielerO.png");

            File imagefileStayingN = new File("images/spielerStehendN.png");
            File imagefileStayingS = new File("images/spielerStehendS.png");
            File imagefileStayingW = new File("images/spielerStehendW.png");
            File imagefileStayingE = new File("images/spielerStehendO.png");

            standingImage = ImageIO.read(new File("images/spielerStehendN.png"));
            northImage = ImageIO.read(imagefileN);
            southImage = ImageIO.read(imagefileS);
            westImage = ImageIO.read(imagefileW);
            eastImage = ImageIO.read(imagefileE);

            northStandingImage = ImageIO.read(imagefileStayingN);
            southStandingImage = ImageIO.read(imagefileStayingS);
            westStandingImage = ImageIO.read(imagefileStayingW);
            eastStandingImage = ImageIO.read(imagefileStayingE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        BufferedImage currentImage;

        // Prüft ob der Spieler sich bewegt
        if (direction.endsWith("Running")) {
            // Get the corresponding running image based on the direction
            switch (direction) {
                case "northRunning":
                    currentImage = northImage;
                    break;
                case "southRunning":
                    currentImage = southImage;
                    break;
                case "westRunning":
                    currentImage = westImage;
                    break;
                case "eastRunning":
                    currentImage = eastImage;
                    break;
                default:
                    currentImage = standingImage; // Default für standing image
            }
        } else {
            // Der Spieler wird mit einem standing image in Bezug auf Bewegungsrichtung gezeigt
            switch (direction) {
                case "northStanding":
                    currentImage = northStandingImage;
                    break;
                case "southStanding":
                    currentImage = southStandingImage;
                    break;
                case "westStanding":
                    currentImage = westStandingImage;
                    break;
                case "eastStanding":
                    currentImage = eastStandingImage;
                    break;
                default:
                    currentImage = standingImage; // Default für standing image
            }
        }

        if (currentImage == null) {
            System.err.println("Error: Current image is null!");
            return;
        }

        // Das Bild wird gezeigt
        drawTool.drawTransformedImage(currentImage, x - currentImage.getWidth() * 0.4, y - currentImage.getHeight() * 0.4, 0.0, 1);
    }


    /**
     * Wird mit jedem Frame vom Framework aufgerufen und dient zur Manipulation des Objekts im Verlauf
     * der Zeit.
     * @param dt die Sekunden, die seit dem letzten Aufruf von update vergangen sind
     */
    @Override
    public void update(double dt){
        move(dt);
        checkBounds();
    }

    public void move(double dt){
        boolean isMoving = false;

        if(viewController.isKeyDown(KeyEvent.VK_W)){
            y=y-speed*dt;
            direction = "northRunning";
            isMoving = true;
        } else if(viewController.isKeyDown(KeyEvent.VK_S)){
            y=y+speed*dt;
            direction = "southRunning";
            isMoving = true;
        } else if(viewController.isKeyDown(KeyEvent.VK_A)){
            x=x-speed*dt;
            direction = "westRunning";
            isMoving = true;
        } else if(viewController.isKeyDown(KeyEvent.VK_D)){
            x=x+speed*dt;
            direction = "eastRunning";
            isMoving = true;
        }

        if (!isMoving) {
            // Wenn man sich nicht bewegt, wird dann die Bewegungsrichtung geändert
            switch (direction) {
                case "northRunning":
                    direction = "northStanding";
                    break;
                case "southRunning":
                    direction = "southStanding";
                    break;
                case "westRunning":
                    direction = "westStanding";
                    break;
                case "eastRunning":
                    direction = "eastStanding";
                    break;
            }
        }
    }



    /**
     * Methode wird von update(double dt) aufgerufen
     * Stellt sicher, dass der Spieler das Fenster nicht verlassen kann.
     */
    public void checkBounds(){
        if(x<leftBound){
            x=leftBound+20;
        }

        if(x>rightBound){
            x=rightBound-20;
        }
        if(y<upperBound){
            y=upperBound+20;
        }
        if(y>lowerBound){
            y=lowerBound-20;
        }
    }

    //get- und set-Methoden für Geschwindigkeit, mit welcher der Spieler sich bewegt
    public void setSpeed(int amount){
        //TODO 03: Implementiere die Methode, wie im Kommentar beschrieben.
        speed = amount;
    }
    public int getSpeed(){
        return speed;
    }

    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }

    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }

    public void setRightBound(double rightBound) {
        this.rightBound = rightBound;
    }

    public void setLeftBound(double leftBound) {
        this.leftBound = leftBound;
    }
}