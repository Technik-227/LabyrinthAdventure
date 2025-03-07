package my_project.model;
import KAGO_framework.model.GraphicalObject;
import KAGO_framework.view.DrawTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static my_project.Config.WINDOW_WIDTH;
import static my_project.Config.WINDOW_HEIGHT;

public class Labyrinth extends GraphicalObject{
    private static LabyrinthWegzelle[] hintergrundZellen = new LabyrinthWegzelle[390];

    private BufferedImage hintergrund;
    public Labyrinth(){
        erstelleGitter();
        belegeWegrichtung();
        try {
            File imagefileHintergund = new File("images/hintergrund.jpg");
            hintergrund = ImageIO.read(imagefileHintergund);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(DrawTool drawTool){
        drawTool.drawTransformedImage(hintergrund, 0 , 0 , 0.0, 1);
        for (LabyrinthWegzelle labyrinthWegzelle : hintergrundZellen) {
            labyrinthWegzelle.draw(drawTool);
        }
    }

    public void update(double dt){
        belegeWegrichtung();
    }

    public void erstelleGitter(){
        int index = 0;
        for (int zahly = 1; zahly <= 15; zahly++) {
            for (int zahlx = 1; zahlx <= 26; zahlx++) {
                LabyrinthWegzelle neueZelle = new LabyrinthWegzelle((zahlx-2)*80, (zahly-2)*80, 80, 80);
                hintergrundZellen[index] = neueZelle;
                index++;
            }
        }
        for (LabyrinthWegzelle eineZelle : hintergrundZellen) {
            if (eineZelle.x == -80 || eineZelle.x == 1920 || eineZelle.y == -80 || eineZelle.y == 1040) {
                // Ränder werden "besucht" damit sie vom Bot ignoriert werden
                eineZelle.belegt = true;
            }
        }

        /*
        int index = 0;
        for (int zahly = 0; zahly < 13; zahly++) {
            for (int zahlx = 0; zahlx < 24; zahlx++) {
                LabyrinthWegzelle neueZelle = new LabyrinthWegzelle(zahlx * 80, zahly * 80, 80, 80);
                hintergrundZellen[index] = neueZelle;
                index++;
            }
        }
        for (int i = 0; i < hintergrundZellen.length; i++) {
            LabyrinthWegzelle eineZelle = hintergrundZellen[i];
            if (eineZelle.x == 0) {// Ränder werden "besucht" damit sie vom Bot ignoriert werden
                eineZelle.checkedW = true;
            }
            if (eineZelle.x == WINDOW_WIDTH-80) {
                eineZelle.checkedO = true;
            }
            if (eineZelle.y == 0) {// Ränder werden "besucht" damit sie vom Bot ignoriert werden
                eineZelle.checkedN = true;
            }
            if (eineZelle.y == WINDOW_HEIGHT-80) {
                eineZelle.checkedS = true;
            }
        }*/

    }


    /*public static void belegeWegrichtung(CheckDaten checkDaten) {
        boolean stepNotDone = true;

        for (int i = 0; i < hintergrundZellen.length; i++) {
            LabyrinthWegzelle aktuelleZelle = hintergrundZellen[i];
            if (aktuelleZelle.x == checkDaten.x && aktuelleZelle.y == checkDaten.y && stepNotDone) { // Zelle finden, auf der CheckBot ist

                if (checkDaten.richtung == 2) { // Bewegung nach Osten möglich und noch nicht bewegt?
                    LabyrinthWegzelle nachbarZelle = hintergrundZellen[i+1];
                    if (stepNotDone) { // Benachbarte Zelle, rechts von der aktuellen finden
                        if (!nachbarZelle.belegt) {
                            nachbarZelle.richtung = 2; // Richtung, aus der die Zelle kam (Für Zeichnung)
                            aktuelleZelle.belegt = true;
                            if (aktuelleZelle.reihenfolge == 0) { // Für Rückverfolgung nummerieren
                                checkDaten.reihenfolge++;
                                aktuelleZelle.reihenfolge = checkDaten.reihenfolge;
                            }
                            checkDaten.x = checkDaten.x + 80; // Checkbot weitersetzen
                            stepNotDone = false;
                        } else {
                            aktuelleZelle.checkedO = true;
                        }
                        checkDaten.richtung = (int) ((Math.random() * 4) + 1); // Checkbot neue Richtung zuweisen
                    }
                } else if (checkDaten.richtung == 4 ) { // Westen
                    LabyrinthWegzelle nachbarZelle = hintergrundZellen[i-1];
                    if ( stepNotDone) { // Benachbarte Zelle, links von der aktuellen
                        if (!nachbarZelle.belegt) {
                            nachbarZelle.richtung = 4; // Richtung, aus der die Zelle kam
                            aktuelleZelle.belegt = true;
                            if (aktuelleZelle.reihenfolge == 0) {
                                checkDaten.reihenfolge++;
                                aktuelleZelle.reihenfolge = checkDaten.reihenfolge;
                            }
                            checkDaten.x = checkDaten.x - 80;
                            stepNotDone = false;
                        } else {
                            aktuelleZelle.checkedW = true;
                        }
                        checkDaten.richtung = (int) ((Math.random() * 4) + 1); // Checkbot neue Richtung zuweisen
                    }
                } else if (checkDaten.richtung == 1) { // Norden
                    LabyrinthWegzelle nachbarZelle = hintergrundZellen[i-26];
                    if ( stepNotDone) { // Benachbarte Zelle, oberhalb der aktuellen
                        if (!nachbarZelle.belegt) {
                            nachbarZelle.richtung = 1; // Richtung, aus der die Zelle kam
                            aktuelleZelle.belegt = true;
                            if (aktuelleZelle.reihenfolge == 0) {
                                checkDaten.reihenfolge++;
                                aktuelleZelle.reihenfolge = checkDaten.reihenfolge;
                            }
                            checkDaten.y = checkDaten.y - 80;
                            stepNotDone = false;
                        } else {
                            aktuelleZelle.checkedN = true;
                        }
                        checkDaten.richtung = (int) ((Math.random() * 4) + 1); // Checkbot neue Richtung zuweisen
                    }
                } else if (checkDaten.richtung == 3) { // Süden
                        LabyrinthWegzelle nachbarZelle = hintergrundZellen[i+26];
                        if ( stepNotDone) { // Benachbarte Zelle, unterhalb der aktuellen
                            if (!nachbarZelle.belegt) {
                                nachbarZelle.richtung = 3; // Richtung, aus der die Zelle kam
                                aktuelleZelle.belegt = true;
                                if (aktuelleZelle.reihenfolge == 0) {
                                    checkDaten.reihenfolge++;
                                    aktuelleZelle.reihenfolge = checkDaten.reihenfolge;
                                }
                                checkDaten.y = checkDaten.y + 80;
                                stepNotDone = false;
                            } else {
                                aktuelleZelle.checkedS = true;
                            }
                            checkDaten.richtung = (int) ((Math.random() * 4) + 1); // Checkbot neue Richtung zuweisen
                        }

                }

                if (aktuelleZelle.checkedN && aktuelleZelle.checkedO && aktuelleZelle.checkedS && aktuelleZelle.checkedW) {
                    if (aktuelleZelle.reihenfolge == 0 && !aktuelleZelle.belegt) {
                        checkDaten.reihenfolge++;
                        aktuelleZelle.reihenfolge = checkDaten.reihenfolge;
                        aktuelleZelle.belegt = true;
                    }
                    for (LabyrinthWegzelle letzteZelle : hintergrundZellen) {
                        if (letzteZelle.reihenfolge == aktuelleZelle.reihenfolge - 1) {
                            checkDaten.x = letzteZelle.x;
                            checkDaten.y = letzteZelle.y;
                            stepNotDone = false;
                        }
                    }
                }
            }
        }*/

    public static void belegeWeg(LabyrinthWegzelle pAktuelleZelle, LabyrinthWegzelle pNachbar, int pRichtung, int pReihenfolge, int pI){
        if (!pNachbar.belegt) {
            pNachbar.richtung = pRichtung; // Richtung, aus der die Zelle kam (Für Zeichnung)
            pNachbar.belegt = true;
            if (pAktuelleZelle.reihenfolge == 0) { // Für Rückverfolgung nummerieren
                pReihenfolge++;
                pAktuelleZelle.reihenfolge = pReihenfolge;
            }
            pAktuelleZelle = pNachbar;
        } else {
            switch (pAktuelleZelle.richtung) {
                case 1: //Norden
                    pAktuelleZelle.checkedN = true;
                    break;
                case 2: // Osten
                    pAktuelleZelle.checkedO = true;
                    break;
                case 3: // Süden
                    pAktuelleZelle.checkedS = true;
                    break;
                case 4: // Westen
                    pAktuelleZelle.checkedO = true;
                    break;
            }
        }
    }


        public static void belegeWegrichtung3() {
            boolean stepNotDone = true;
            int i = 195; //Start
            int neuerIndex = i;
            int neueRichtung = (int) (Math.random() * 4) + 1;
            int reihenfolge = 0;
            LabyrinthWegzelle nachbarZelle = null;

            LabyrinthWegzelle aktuelleZelle = hintergrundZellen[i];
            for(int j = 0; j < hintergrundZellen.length; j++) {
                if (neueRichtung == 1) { // Norden
                    neuerIndex = i - 26;
                    nachbarZelle = hintergrundZellen[neuerIndex];
                    if (stepNotDone) { // Benachbarte Zelle, oberhalb der aktuellen
                        if (!nachbarZelle.belegt) {
                            nachbarZelle.richtung = neueRichtung; // Richtung, in der die nächste Zelle entsteht
                            aktuelleZelle.belegt = true;
                            if (aktuelleZelle.reihenfolge == 0) {
                                reihenfolge++;
                                aktuelleZelle.reihenfolge = reihenfolge;
                            }
                            aktuelleZelle = nachbarZelle;
                            i = neuerIndex;
                            stepNotDone = false;
                        } else {
                            aktuelleZelle.checkedN = true;
                        }
                        neueRichtung = (int) ((Math.random() * 4) + 1); //Neue Richtung zuweisen
                    }
                } else if (neueRichtung == 2) { // Bewegung nach Osten möglich und noch nicht bewegt?
                    neuerIndex = i +1;
                    nachbarZelle = hintergrundZellen[neuerIndex];
                    if (stepNotDone) {
                        if (!nachbarZelle.belegt) {
                            nachbarZelle.richtung = neueRichtung; // Richtung, aus der die Zelle kam (Für Zeichnung)
                            aktuelleZelle.belegt = true;
                            if (aktuelleZelle.reihenfolge == 0) { // Für Rückverfolgung nummerieren
                                reihenfolge++;
                                aktuelleZelle.reihenfolge = reihenfolge;
                            }
                            aktuelleZelle = nachbarZelle;
                            i = neuerIndex;
                            stepNotDone = false;
                        } else {
                            aktuelleZelle.checkedO = true;
                        }
                        neueRichtung = (int) ((Math.random() * 4) + 1); // Checkbot neue Richtung zuweisen
                    }
                }else if (neueRichtung == 3) { // Süden
                    neuerIndex = i + 26;
                    nachbarZelle = hintergrundZellen[neuerIndex];
                    if (stepNotDone) { // Benachbarte Zelle, unterhalb der aktuellen
                        if (!nachbarZelle.belegt) {
                            nachbarZelle.richtung = neueRichtung; // Richtung, aus der die Zelle kam
                            aktuelleZelle.belegt = true;
                            if (aktuelleZelle.reihenfolge == 0) {
                                reihenfolge++;
                                aktuelleZelle.reihenfolge = reihenfolge;
                            }
                            aktuelleZelle = nachbarZelle;
                            i = neuerIndex;
                            stepNotDone = false;
                        } else {
                            aktuelleZelle.checkedS = true;
                        }
                        neueRichtung = (int) ((Math.random() * 4) + 1); // Checkbot neue Richtung zuweisen
                    }
                }else if (neueRichtung == 4) { // Westen
                    neuerIndex = i -1;
                    nachbarZelle = hintergrundZellen[neuerIndex];
                    if (stepNotDone) { // Benachbarte Zelle, links von der aktuellen
                        if (!nachbarZelle.belegt) {
                            nachbarZelle.richtung = neueRichtung; // Richtung, aus der die Zelle kam
                            aktuelleZelle.belegt = true;
                            if (aktuelleZelle.reihenfolge == 0) {
                                reihenfolge++;
                                aktuelleZelle.reihenfolge = reihenfolge;
                            }
                            aktuelleZelle = nachbarZelle;
                            i = neuerIndex;
                            stepNotDone = false;
                        } else {
                            aktuelleZelle.checkedW = true;
                        }
                        neueRichtung = (int) ((Math.random() * 4) + 1); // Checkbot neue Richtung zuweisen
                    }
                }
                if (aktuelleZelle.checkedN && aktuelleZelle.checkedO && aktuelleZelle.checkedS && aktuelleZelle.checkedW) {
                    if (aktuelleZelle.reihenfolge == 0 && !aktuelleZelle.belegt) {
                        reihenfolge++;
                        aktuelleZelle.reihenfolge = reihenfolge;
                        aktuelleZelle.belegt = true;
                    }
                    for (LabyrinthWegzelle letzteZelle : hintergrundZellen) {
                        if (letzteZelle.reihenfolge == aktuelleZelle.reihenfolge - 1) {
                            aktuelleZelle = letzteZelle;
                            stepNotDone = false;
                        }
                    }
                }
            }
        }

    public static void belegeWegrichtung2() {
        boolean stepNotDone = true;
        int i = 195; // Startindex
        int reihenfolge = 0;
        LabyrinthWegzelle aktuelleZelle = hintergrundZellen[i];

        while (stepNotDone) {
            // Prüfen, ob alle Richtungen ausgecheckt sind
            if (aktuelleZelle.checkedN && aktuelleZelle.checkedO && aktuelleZelle.checkedS && aktuelleZelle.checkedW) {
                if (aktuelleZelle.reihenfolge == 0 && !aktuelleZelle.belegt) {
                    reihenfolge++;
                    aktuelleZelle.reihenfolge = reihenfolge;
                    aktuelleZelle.belegt = true;
                }
                // Suche nach der letzten Zelle in der Reihenfolge
                boolean gefunden = false;
                for (LabyrinthWegzelle letzteZelle : hintergrundZellen) {
                    if (letzteZelle.reihenfolge == aktuelleZelle.reihenfolge - 1) {
                        aktuelleZelle = letzteZelle;
                        gefunden = true;
                        break;
                    }
                }
                if (!gefunden) {
                    // Wenn keine vorherige Zelle gefunden wurde, beende die Schleife
                    stepNotDone = false;
                } else {
                    // Reset checked flags for the current cell
                    aktuelleZelle.checkedN = false;
                    aktuelleZelle.checkedO = false;
                    aktuelleZelle.checkedS = false;
                    aktuelleZelle.checkedW = false;
                }
                continue;
            }

            // Zufällige Richtung auswählen
            int neueRichtung = (int) (Math.random() * 4) + 1;
            LabyrinthWegzelle nachbarZelle = null;
            int neuerIndex = i;
            boolean richtungGefunden = false;

            for (int versuche = 0; versuche < 4; versuche++) {
                switch (neueRichtung) {
                    case 1: // Norden
                        if (i >= 26) { // Vermeidung von IndexOutOfBounds
                            neuerIndex = i - 26;
                            nachbarZelle = hintergrundZellen[neuerIndex];
                            if (!nachbarZelle.belegt) {
                                richtungGefunden = true;
                            } else {
                                aktuelleZelle.checkedN = true;
                            }
                        } else {
                            aktuelleZelle.checkedN = true;
                        }
                        break;
                    case 2: // Osten
                        if (i % 26 < 25) { // Vermeidung von IndexOutOfBounds
                            neuerIndex = i + 1;
                            nachbarZelle = hintergrundZellen[neuerIndex];
                            if (!nachbarZelle.belegt) {
                                richtungGefunden = true;
                            } else {
                                aktuelleZelle.checkedO = true;
                            }
                        } else {
                            aktuelleZelle.checkedO = true;
                        }
                        break;
                    case 3: // Süden
                        if (i < hintergrundZellen.length - 26) { // Vermeidung von IndexOutOfBounds
                            neuerIndex = i + 26;
                            nachbarZelle = hintergrundZellen[neuerIndex];
                            if (!nachbarZelle.belegt) {
                                richtungGefunden = true;
                            } else {
                                aktuelleZelle.checkedS = true;
                            }
                        } else {
                            aktuelleZelle.checkedS = true;
                        }
                        break;
                    case 4: // Westen
                        if (i % 26 > 0) { // Vermeidung von IndexOutOfBounds
                            neuerIndex = i - 1;
                            nachbarZelle = hintergrundZellen[neuerIndex];
                            if (!nachbarZelle.belegt) {
                                richtungGefunden = true;
                            } else {
                                aktuelleZelle.checkedW = true;
                            }
                        } else {
                            aktuelleZelle.checkedW = true;
                        }
                        break;
                }

                if (richtungGefunden) {
                    // Nachbarzelle belegen
                    nachbarZelle.richtung = neueRichtung;
                    aktuelleZelle.belegt = true;
                    if (aktuelleZelle.reihenfolge == 0) {
                        reihenfolge++;
                        aktuelleZelle.reihenfolge = reihenfolge;
                    }
                    aktuelleZelle = nachbarZelle;
                    i = neuerIndex; // Index aktualisieren

                    // Reset checked flags for the new cell
                    aktuelleZelle.checkedN = false;
                    aktuelleZelle.checkedO = false;
                    aktuelleZelle.checkedS = false;
                    aktuelleZelle.checkedW = false;
                    break;
                } else {
                    // Neue Richtung auswählen
                    neueRichtung = (neueRichtung % 4) + 1;
                }
            }
        }
    }
    public static void belegeWegrichtung() {
        boolean stepNotDone = true;
        int i = 195; // Startindex
        int reihenfolge = 0;
        LabyrinthWegzelle aktuelleZelle = hintergrundZellen[i];

        while (stepNotDone) {
            boolean richtungGefunden = false;

            for (int versuche = 0; versuche < 4; versuche++) {
                int neueRichtung = (int) (Math.random() * 4) + 1;
                LabyrinthWegzelle nachbarZelle = null;
                int neuerIndex = i;

                switch (neueRichtung) {
                    case 1: // Norden
                        if (i >= 26 && !aktuelleZelle.checkedN) { // Vermeidung von IndexOutOfBounds und überprüfte Richtung
                            neuerIndex = i - 26;
                            nachbarZelle = hintergrundZellen[neuerIndex];
                            if (!nachbarZelle.belegt) {
                                richtungGefunden = true;
                            } else {
                                aktuelleZelle.checkedN = true;
                            }
                        }
                        break;
                    case 2: // Osten
                        if (i % 26 < 25 && !aktuelleZelle.checkedO) { // Vermeidung von IndexOutOfBounds und überprüfte Richtung
                            neuerIndex = i + 1;
                            nachbarZelle = hintergrundZellen[neuerIndex];
                            if (!nachbarZelle.belegt) {
                                richtungGefunden = true;
                            } else {
                                aktuelleZelle.checkedO = true;
                            }
                        }
                        break;
                    case 3: // Süden
                        if (i < hintergrundZellen.length - 26 && !aktuelleZelle.checkedS) { // Vermeidung von IndexOutOfBounds und überprüfte Richtung
                            neuerIndex = i + 26;
                            nachbarZelle = hintergrundZellen[neuerIndex];
                            if (!nachbarZelle.belegt) {
                                richtungGefunden = true;
                            } else {
                                aktuelleZelle.checkedS = true;
                            }
                        }
                        break;
                    case 4: // Westen
                        if (i % 26 > 0 && !aktuelleZelle.checkedW) { // Vermeidung von IndexOutOfBounds und überprüfte Richtung
                            neuerIndex = i - 1;
                            nachbarZelle = hintergrundZellen[neuerIndex];
                            if (!nachbarZelle.belegt) {
                                richtungGefunden = true;
                            } else {
                                aktuelleZelle.checkedW = true;
                            }
                        }
                        break;
                }

                if (richtungGefunden) {// Nachbarzelle belegen
                    nachbarZelle.richtung = neueRichtung;
                    aktuelleZelle.belegt = true;
                    aktuelleZelle = nachbarZelle;
                    i = neuerIndex; // Index aktualisieren
                    if (aktuelleZelle.reihenfolge == 0) {
                        reihenfolge++;
                        aktuelleZelle.reihenfolge = reihenfolge;
                    }
                    break;
                }
            }

            if (!richtungGefunden) { // Wenn keine Richtung gefunden wurde, zurück zur vorherigen Zelle
                aktuelleZelle.belegt = true;
                if (aktuelleZelle.reihenfolge == 0) {
                    reihenfolge++;
                    aktuelleZelle.reihenfolge = reihenfolge;
                }
                boolean vorgaengerGefunden = false;
                for (int j = 0; j < hintergrundZellen.length; j++) {
                    if (hintergrundZellen[j].reihenfolge == aktuelleZelle.reihenfolge - 1) {
                        aktuelleZelle = hintergrundZellen[j];
                        i = j; // Index aktualisieren
                        vorgaengerGefunden = true;
                        break;
                    }
                }
                if (!vorgaengerGefunden) { // Wenn keine vorherige Zelle gefunden wurde, wir die Schleife beendet
                    stepNotDone = false;
                }
            }
        }
    }

    public LabyrinthWegzelle[] getHintergrundZellen() {
        return hintergrundZellen;
    }

}
