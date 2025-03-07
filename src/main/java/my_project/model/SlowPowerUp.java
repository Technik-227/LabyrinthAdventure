package my_project.model;

import KAGO_framework.view.DrawTool;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SlowPowerUp extends PowerUp {

    int saveSpeed;
    int newSpeed;

    public SlowPowerUp(double x, double y, int dauer, Player player) {
        super(x, y, dauer, player);
        saveSpeed = player.getSpeed();
        newSpeed = saveSpeed - 50;
        try {
            File imagefileSlowOrb = new File("images/slowOrb.png");
            powerUpImage = ImageIO.read(imagefileSlowOrb);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
    public void zeichnePowerUpAnzeige(DrawTool drawTool) {
        drawTool.setCurrentColor(255, 255, 255, 255);
        drawTool.drawText(1720,990,"Speed für: " + Math.floor(dauer) + " sek. ");
    }

    public void aktivierePowerUp() {
        player.setSpeed(newSpeed);
    }

    public void deaktivierePowerUp() {
        player.setSpeed(saveSpeed);
    }

}//
