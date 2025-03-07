package my_project.model;

import KAGO_framework.view.DrawTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpeedPowerUp extends PowerUp {

    int saveSpeed;
    int newSpeed;
    public SpeedPowerUp(double x, double y, double dauer, Player player) {
        super(x, y, dauer, player);
        saveSpeed = player.getSpeed();
        newSpeed = saveSpeed + 50;
        try {
            File imagefileSpeedOrb = new File("images/speedOrb.png");
            powerUpImage = ImageIO.read(imagefileSpeedOrb);
        } catch (
        IOException e) {
            e.printStackTrace();
        }
    }

    public void zeichnePowerUpAnzeige(DrawTool drawTool) {
        drawTool.setCurrentColor(255, 255, 255, 255);
        drawTool.drawText(1720,970,"Speed für: " + Math.floor(dauer) + " sek. ");
    }
    public void aktivierePowerUp() {
        player.setSpeed(newSpeed);
    }

    public void deaktivierePowerUp() {
        player.setSpeed(saveSpeed);
    }

}
