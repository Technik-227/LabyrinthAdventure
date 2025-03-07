package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public abstract class PowerUp extends GraphicalObject {

    protected boolean powerUpAktiviert;

    protected double dauer;

    protected Player player;

    protected BufferedImage powerUpImage;


    public PowerUp(double x, double y, double dauer, Player player) {
        this.x = x;
        this.y = y;
        this.dauer = dauer;
        this.player = player;
        this.powerUpAktiviert = false;
    }

    public void draw(DrawTool drawTool) {
        if (!powerUpAktiviert) {
            //drawTool.setCurrentColor(new Color(24, 32, 192));
            //drawTool.drawFilledCircle(x, y, 20);
            drawTool.drawTransformedImage(powerUpImage, x , y , 0.0, 1);
        }
        if (powerUpAktiviert) {
            if(dauer > 0){
                zeichnePowerUpAnzeige(drawTool);
            }
        }
    }

    public void update(double dt){
        pruefeKollision(player);
        if (powerUpAktiviert) {
            aktivierePowerUp();
            dauer = dauer - dt;
            if(dauer <= 0){
                deaktivierePowerUp();
           }

        }
    }

    protected abstract void zeichnePowerUpAnzeige(DrawTool drawTool);

    protected abstract void aktivierePowerUp();

    protected abstract void deaktivierePowerUp();

    public void pruefeKollision(Player player) {
        double distance = Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));
        if (distance < 20) {
            powerUpAktiviert = true;
        }
    }

}//
