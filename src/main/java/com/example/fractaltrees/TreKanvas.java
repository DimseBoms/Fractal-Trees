package com.example.fractaltrees;

import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class TreKanvas extends Pane {
    final Border GRENSEFARGE = Border.stroke(Color.BLACK);
    double grenLengde;
    double vinkel;
    double stammeLengde;
    double tykkelse;
    int antGrener;
    int antRek;
    int totRek;
    double vinkelTilfeldigFaktor;
    double grenLengdeTilfeldigFaktor;
    double grenProsent;
    Color farge;

    // konstruktør som initialiserer tegnerute
    TreKanvas() {
        super();
        this.setBorder(GRENSEFARGE);
        this.setStyle("-fx-background-color: black;");
    }

    // metode som iverksetter konstruksjon av et tre
    public void lagTre(
            double grenLengde,
            double vinkel,
            double stammeLengde,
            double tykkelse,
            double grenProsent,
            double vinkelTilfeldighet,
            double lengdeTilfeldighet,
            int antGrener,
            int antRek,
            Color farge
    ) {
        System.out.println("Pane-bredde: " + this.getWidth());
        System.out.println("Pane-høyde: " + this.getHeight());
        System.out.println("Forsøker å lage et nytt tre med disse egenskapene:");
        System.out.println("Grenlengde: " + grenLengde);
        System.out.println("Vinkel: " + vinkel);
        System.out.println("Stammelengde: " + stammeLengde);
        System.out.println("Tykkelse: " + tykkelse);
        System.out.println("Sannsynlighet for å produsere gren: " + grenProsent);
        System.out.println("Vinkeltilfeldighet: " + vinkelTilfeldigFaktor);
        System.out.println("Grentilfeldighet: " + grenLengdeTilfeldigFaktor);
        System.out.println("Antall grener: " + antGrener);
        System.out.println("Antall rekursjoner: " + antRek);
        System.out.println();

        this.getChildren().clear();
        this.grenLengde = grenLengde;
        this.vinkel = vinkel;
        this.stammeLengde = stammeLengde;
        this.tykkelse = tykkelse;
        this.grenProsent = grenProsent;
        this.vinkelTilfeldigFaktor = vinkelTilfeldighet;
        this.grenLengdeTilfeldigFaktor = lengdeTilfeldighet;
        this.antGrener = antGrener;
        this.antRek = antRek;
        this.farge = farge;
        this.lagStamme();
    }

    // metode som bygger stamme
    private void lagStamme() {
        Line stamme = new Line();
        stamme.setStroke(this.farge);
        // setter startpunkt på stamme
        stamme.setStartX(this.getWidth() / 2);
        stamme.setStartY(this.getHeight());
        // setter tykkelse på stamme
        stamme.setStrokeWidth(this.tykkelse);
        // setter sluttpunkt på stamme
        stamme.setEndX(this.getWidth() / 2);
        stamme.setEndY(this.getHeight() - this.stammeLengde);
        this.getChildren().add(stamme);
        // sentrerer midtpunktet til de to første grenene slik at treet blir symmetrisk
        int intVinkelA = (int) this.vinkel;
        int intVinkelB = intVinkelA + intVinkelA;
        while ((intVinkelA + intVinkelB) / 2 != 90) {
            if (intVinkelA == 360) intVinkelA = 1;
            else intVinkelA++;
            if (intVinkelB == 360) intVinkelB = 1;
            else intVinkelB++;
        }
        double vinkelA = intVinkelA;
        double vinkelB = intVinkelB - 1;
        this.lagForgrening( // første gren
                stamme.getEndX(), // startkordinater for gren
                stamme.getEndY(),
                this.grenLengde, // lengde for gren
                this.tykkelse  - (this.tykkelse / 3), // tykkelse for gren
                vinkelA,
                this.vinkel / 2,
                this.antRek
        );
        this.lagForgrening( // andre gren
                stamme.getEndX(), // startkordinater for gren
                stamme.getEndY(),
                this.grenLengde, // lengde for gren
                this.tykkelse  - (this.tykkelse / 3), // tykkelse for gren
                vinkelB,
                this.vinkel / 2,
                this.antRek
        );
        System.out.println("Totalt antall grener: " + this.totRek);
        System.out.println();
    }

    // assistent-metode som lager grener
    private void lagForgrening(
            double startX,
            double startY,
            double rekursivGrenLengde,
            double rekursivTykkelse,
            double nyVinkel,
            double spenn,
            int flerForgreninger
    ) {
        // sjekker om grenens lengde vil bli lenger enn 2 piksler, om den ikke er det stopper rekursjonen
        if (this.grenLengde > 2) {
            // mystisk formel som finner koordinater for grenens nye startpunkt
            // gitt kunn vinkel og distanse fra møtepunkt med stammen
            // kilde:
            // https://stackoverflow.com/questions/9871727/how-to-get-coordinates-of-a-point-in-a-coordinate-system-based-on-angle-and-dist
            double endX = startX - this.grenLengde * Math.cos(Math.toRadians(nyVinkel));
            double endY = startY - this.grenLengde * Math.sin(Math.toRadians(nyVinkel));

            // sjekker om treet vokser utenfor kanvas
            if (!(endX < 0 || endY < 0 || endX > this.getWidth() || endY > this.getHeight())) {
                if (Math.random() * 100 <= this.grenProsent) { // sannsynlighet for produksjon av ny gren
                    this.totRek++;
                    Line gren = new Line();
                    gren.setStartX(startX);
                    gren.setStartY(startY);
                    gren.setStrokeWidth(rekursivTykkelse);
                    gren.setEndX(endX);
                    gren.setEndY(endY);
                    gren.setStroke(this.farge);
                    this.getChildren().add(gren);
                    // lager nye grener
                    if (flerForgreninger > 0) {
                        lagForgrening(
                                gren.getEndX(),
                                gren.getEndY(),
                                rekursivGrenLengde - (rekursivGrenLengde / 3) + tilfeldig(1),
                                rekursivTykkelse - (rekursivTykkelse / 3),
                                nyVinkel - spenn,
                                spenn + tilfeldig(0),
                                flerForgreninger - 1
                        );
                        lagForgrening(
                                gren.getEndX(),
                                gren.getEndY(),
                                rekursivGrenLengde - (rekursivGrenLengde / 3) + tilfeldig(1),
                                rekursivTykkelse - (rekursivTykkelse / 3),
                                nyVinkel + spenn,
                                spenn + tilfeldig(0),
                                flerForgreninger - 1
                        );
                    }
                }
            }
        }
    }

    // metode som genererer tilfeldig tall på størrelse gitt tilfeldighetsfaktor, både minus og pluss
    private double tilfeldig(int valg) {
        Double t = 0.0;
        // hvis valg = grenVinkel
        if (valg == 0) {
            t = Math.random() * vinkelTilfeldigFaktor;
            if (Math.random() >= 0.5) t = t * -1;
        }
        else if (valg == 1) {
            t = Math.random() * grenLengdeTilfeldigFaktor;
            if (Math.random() >= 0.5) t = t * -1;
        }
        return t;
    }
}
