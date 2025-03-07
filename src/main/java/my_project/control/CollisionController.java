package my_project.control;

import my_project.model.Labyrinth;
import my_project.model.LabyrinthWegzelle;
import my_project.model.Player;

public class CollisionController {
    private Player player;
    private Labyrinth dasLabyrinth;
    private LabyrinthWegzelle currentZelle, upperZelle, lowerZelle, rightZelle, leftZelle;
    private double imageOffset = 10;

    public CollisionController(Player player, Labyrinth dasLabyrinth){
        this.player = player;
        this.dasLabyrinth = dasLabyrinth;
    }

    public void update(){
        changeBounds();
    }

    private void changeBounds() {

        updateCurrentZelle();

        if(currentZelle == null){
            return;
        }

        player.setUpperBound(currentZelle.getY() - 80);
        player.setLowerBound(currentZelle.getY() + 160);
        player.setRightBound(currentZelle.getX() + 160);
        player.setLeftBound(currentZelle.getX() - 80);

        if(!(currentZelle.getRichtung() == 3 || upperZelle.getRichtung() == 1)){
            player.setUpperBound(currentZelle.getY());
        }
        if(!(currentZelle.getRichtung() == 1 || lowerZelle.getRichtung() == 3)){
            player.setLowerBound(currentZelle.getY() + 80 - imageOffset);
        }
        if(!(currentZelle.getRichtung() == 4 || rightZelle.getRichtung() == 2)){
            player.setRightBound(currentZelle.getX() + 80 - imageOffset);
        }
        if(!(currentZelle.getRichtung() == 2 || leftZelle.getRichtung() == 4)){
            player.setLeftBound(currentZelle.getX() + imageOffset);
        }
    }

    private void updateCurrentZelle(){
        LabyrinthWegzelle[] hintergrundZellen = dasLabyrinth.getHintergrundZellen();

        for (int i = 0; i < hintergrundZellen.length; i++) {
                LabyrinthWegzelle zelle = hintergrundZellen[i];

                if(player.getX() > zelle.getX() && player.getX() < zelle.getX() + 80 && player.getY() > zelle.getY() && player.getY() < zelle.getY() + 80){
                    currentZelle = zelle;
                }
                if(player.getX() > zelle.getX() && player.getX() < zelle.getX() + 80 && player.getY() - 80 > zelle.getY() && player.getY() - 80 < zelle.getY() + 80){
                    upperZelle = zelle;
                }
                if(player.getX() > zelle.getX() && player.getX() < zelle.getX() + 80 && player.getY() + 80 > zelle.getY() && player.getY() + 80 < zelle.getY() + 80){
                    lowerZelle = zelle;
                }
                if(player.getX() + 80 > zelle.getX() && player.getX() + 80 < zelle.getX() + 80 && player.getY() > zelle.getY() && player.getY() < zelle.getY() + 80){
                    rightZelle = zelle;
                }
                if(player.getX() - 80 > zelle.getX() && player.getX() - 80 < zelle.getX() + 80 && player.getY() > zelle.getY() && player.getY() < zelle.getY() + 80){
                    leftZelle = zelle;
                }
            }

    }
}//
