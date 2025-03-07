package my_project.control;

import KAGO_framework.control.ViewController;
import my_project.model.*;

import java.awt.image.BufferedImage;

/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute
    private Player player;
    private Labyrinth dasLabyrith;

    private SpeedPowerUp speedP1,speedP2;
    private SlowPowerUp slowP1, slowP2;

    private Schluss schluss;
    // Referenzen
    private ViewController viewController;  // diese Referenz soll auf ein Objekt der Klasse viewController zeigen. Über dieses Objekt wird das Fenster gesteuert.

    private CollisionController collisionController;


    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse viewController. Diese wird als Parameter übergeben.
     * @param viewController das viewController-Objekt des Programms
     */
    public ProgramController(ViewController viewController){
        this.viewController = viewController;
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     * Sie erstellt die leeren Datenstrukturen, zu Beginn nur eine Queue
     */
    public void startProgram() {

        dasLabyrith = new Labyrinth();
        viewController.draw(dasLabyrith);

        player = new Player(20 + ((int)(Math.random() * 23) * 80), 20 + ((int)(Math.random() * 12) * 80), viewController);
        viewController.draw(player);

        schluss = new Schluss(300, player);
        viewController.draw(schluss);

        speedP1 = new SpeedPowerUp(20 + ((int)(Math.random() * 23) * 80), 20 + ((int)(Math.random() * 12) * 80), 10, player);
        viewController.draw(speedP1);

        slowP1 = new SlowPowerUp(20 + ((int)(Math.random() * 23) * 80), 20 + ((int)(Math.random() * 12) * 80), 10, player);
        viewController.draw(slowP1);

        speedP2 = new SpeedPowerUp(20 + ((int)(Math.random() * 23) * 80), 20 + ((int)(Math.random() * 12) * 80), 15, player);
        viewController.draw(speedP2);

        slowP2 = new SlowPowerUp(20 + ((int)(Math.random() * 23) * 80), 20 + ((int)(Math.random() * 12) * 80), 15, player);
        viewController.draw(slowP2);


        collisionController = new CollisionController(player,dasLabyrith);


    }

    /**
     * Aufruf mit jeder Frame
     * @param dt Zeit seit letzter Frame
     */
    public void updateProgram(double dt){
        collisionController.update();
    }
}//
