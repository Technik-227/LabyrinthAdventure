package my_project.model;

import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

public class LabyrinthWegzelle extends GraphicalObject {

    int x, y, laenge, breite; //nur für grafische Darstellung
    boolean belegt;
    int richtung;
    int reihenfolge;
    boolean checkedN, checkedO, checkedS, checkedW;

    boolean showStructure;



    public LabyrinthWegzelle(int px, int py,int pLaenge, int pBreite){
        this.x = px;
        this.y = py;
        this.laenge = pLaenge;
        this.breite = pBreite;
        this.belegt = false;
        this.richtung = 0;
        this.reihenfolge = 0;
        this.checkedN = false;
        this.checkedO = false;
        this.checkedS = false;
        this.checkedW = false;

        showStructure = false;
    }

    public void draw(DrawTool drawTool){
        if(!showStructure) {
        drawTool.setCurrentColor(100, 100, 100, 255);


        switch (this.richtung) {// Weg zeigt immer in entgegengesetzte Richtung bzw. zurück auf die Richtung aus der er kam
            case 1: //Norden -> nach Süden zeigend
                drawTool.drawFilledRectangle(this.x + 15, this.y + 15, 50, 130);
                break;
            case 2: // Osten -> nach Westen zeigend
                drawTool.drawFilledRectangle(this.x - 65, this.y + 15, 130, 50);
                break;
            case 3: // Süden -> nach Norden zeigend
                drawTool.drawFilledRectangle(this.x + 15, this.y - 65, 50, 130);
                break;
            case 4: // Westen -> nach Osten zeigend
                drawTool.drawFilledRectangle(this.x + 15, this.y + 15, 130, 50);
                break;
        }
        }else{
        if(this.belegt) { //nur für grafische Darstellung
            drawTool.setCurrentColor(255, 215, 0, 255);
        }else{
            drawTool.setCurrentColor(175, 175, 175, 255);
        }
        drawTool.drawRectangle(this.x, this.y, this.laenge,this.breite);
        switch (this.richtung) {
            case 1: //Norden -> nach Süden zeigend
                drawTool.drawLine(this.x + 40,this.y + 10, this.x + 40,this.y + 70);
                drawTool.drawFilledPolygon(this.x + 40,this.y + 75, this.x + 45,this.y + 70, this.x + 35,this.y + 70);
                break;
            case 2: // Osten -> nach Westen zeigend
                drawTool.drawLine(this.x + 10,this.y + 40, this.x + 70,this.y + 40);
                drawTool.drawFilledPolygon(this.x + 5,this.y + 40, this.x + 10,this.y + 45, this.x + 10,this.y + 35);
                break;
            case 3: // Süden -> nach Norden zeigend
                drawTool.drawLine(this.x + 40,this.y + 10, this.x + 40,this.y + 70);
                drawTool.drawFilledPolygon(this.x + 40,this.y + 5, this.x + 45,this.y + 10, this.x + 35,this.y + 10);
                break;
            case 4: // Westen -> nach Osten zeigend
                drawTool.drawLine(this.x + 10,this.y + 40, this.x + 70,this.y + 40);
                drawTool.drawFilledPolygon(this.x + 75,this.y + 40, this.x + 70,this.y + 45, this.x + 70,this.y + 35);
                break;
        }

        drawTool.drawText(this.x +5,this.y+15, " Nr." + this.reihenfolge);
        }
    }
    public void update(double dt){

    }

    public int getRichtung() {
        return richtung;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}