package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Schluss extends GraphicalObject {

    private double finalerTimer;

    private Player player;
    private double zielX;
    private double zielY;
    private BufferedImage zielOrb;
    private BufferedImage gameOverScreen;
    private BufferedImage geschafftScreen;

    private BufferedImage endscreen;
    private boolean spielende;

    public Schluss(double spieldauerdauer, Player player){
        finalerTimer = spieldauerdauer;
        this.player = player;
        zielX = 20 + ((int)(Math.random() * 23) * 80);
        zielY = 20 + ((int)(Math.random() * 12) * 80);
        try {
            File imagefileZiel = new File("images/ziel.png");
            zielOrb = ImageIO.read(imagefileZiel);
            File imagefileGameOver = new File("images/gameOver.png");
            gameOverScreen = ImageIO.read(imagefileGameOver);
            File imagefileGeschafft = new File("images/geschafft.png");
            geschafftScreen = ImageIO.read(imagefileGeschafft);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(DrawTool drawTool){
        drawTool.drawTransformedImage(zielOrb, zielX , zielY , 0.0, 1);
        drawTool.setCurrentColor(255, 0, 0, 100);
        drawTool.drawFilledRectangle(1705,910,200,100);
        drawTool.setCurrentColor(255, 0, 0, 255);
        drawTool.drawRectangle(1705,910,200,100);

        drawTool.setCurrentColor(255, 255, 255, 255);
        drawTool.drawText(1720,930,"Verbleibende Zeit: " + Math.floor(finalerTimer) + " sek. ");

        if (spielende){
            drawTool.drawTransformedImage(endscreen, 0 , 0 , 0.0, 1);
        }
    }

    public void update(double dt){
        if (!spielende){
            finalerTimer = finalerTimer - dt;
        }

        if (finalerTimer <= 0){
            spielende = true;
            endscreen = gameOverScreen;
        }
        spielerImZiel();
    }

    public void spielerImZiel(){
        if (berechneAbstand(player.getX(),player.getY(),zielX,zielY) < player.getRadius()){
            spielende = true;
            endscreen = geschafftScreen;
        }
    }

    public double berechneAbstand(double x1, double y1,double x2,double y2) {
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }
}
