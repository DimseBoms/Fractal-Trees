package com.example.fractaltrees;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    // instansierer knapper som skal benyttes på venstre side
    Label grenLengdeLbl = new Label("Lengde på gren:");
    Slider grenLengdeSlider = new Slider(0.0, 300, 150.0);
    Label vinkelLbl = new Label("Startvinkel:");
    Slider vinkelSlider = new Slider(0.0, 120.0, 30.0);
    Label stammeLengdeLbl = new Label("Lengde på stamme:");
    Slider stammeLengdeSlider = new Slider(0.0, 300.0, 150.0);
    Label tykkelseLbl = new Label("Tykkelse:");
    Slider tykkelseSlider = new Slider(0.0, 30.0, 15.0);
    Label grenProsentLbl = new Label("Sjangse for ny gren:");
    Slider grenProsentSlider = new Slider(75.0, 100.0, 95.0);
    Label vinkelTilfeldighetsLbl = new Label("Tilfeldighet på vinkel:");
    Slider vinkelTilfeldighetsSlider = new Slider(0.0, 80.0, 25.0);
    Label grenTilfeldigLbl = new Label("Tilfeldighet på grenlengde:");
    Slider grenTilfeldigSlider = new Slider(0.0, 30.0, 5.0);
    Label antGrenerLbl = new Label("Antall grener på tre");
    TextField antGrenTxt = new TextField("2");
    Label antRekLbl = new Label("Antall nivå på fraktal:");
    TextField antRekTxt = new TextField("5");
    ColorPicker fargeVelger = new ColorPicker();
    ToggleGroup tg = new ToggleGroup();
    RadioButton r1 = new RadioButton("Farge fra fargevelger");
    RadioButton r2 = new RadioButton("Tilfeldig farge");
    RadioButton r3 = new RadioButton("Partytime");
    Button tilfeldigKnapp = new Button("Lag tilfeldig tre");
    Button genererTreKnapp = new Button("Lag tre");
    // initialiserer tegnerute
    TreKanvas tegnerute = new TreKanvas();
    BorderPane borderpane = new BorderPane();

    @Override
    public void start(Stage stage) throws IOException {
        // bygger opp BorderPane med noder
        HBox radioHB = new HBox();
        radioHB.setSpacing(10);
        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);
        tg.selectToggle(r2);
        radioHB.getChildren().addAll(r1, r2);
        HBox knappBox = new HBox();
        knappBox.setSpacing(10);
        knappBox.getChildren().addAll(tilfeldigKnapp, genererTreKnapp);
        knappBox.setAlignment(Pos.CENTER);
        VBox venstreBox = new VBox();
        venstreBox.setAlignment(Pos.CENTER);
        venstreBox.setSpacing(10);
        venstreBox.setPadding(new Insets(20));
        venstreBox.getChildren().addAll(
                grenLengdeLbl,
                grenLengdeSlider,
                vinkelLbl,
                vinkelSlider,
                stammeLengdeLbl,
                stammeLengdeSlider,
                tykkelseLbl,
                tykkelseSlider,
                grenProsentLbl,
                grenProsentSlider,
                vinkelTilfeldighetsLbl,
                vinkelTilfeldighetsSlider,
                grenTilfeldigLbl,
                grenTilfeldigSlider,
                antRekLbl,
                antRekTxt,
                fargeVelger,
                radioHB,
                knappBox
        );

        borderpane.setLeft(venstreBox);
        borderpane.setCenter(tegnerute);

        // sender verdier til lagTre metode i TreKanvas.java
        genererTreKnapp.setOnAction(e -> {
            Color tempFarge = Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255),  (int)(Math.random() * 255));
            if (tg.getSelectedToggle() == r1) tempFarge = fargeVelger.getValue();
            tegnerute.lagTre(
                    grenLengdeSlider.getValue(),
                    vinkelSlider.getValue(),
                    stammeLengdeSlider.getValue(),
                    tykkelseSlider.getValue(),
                    grenProsentSlider.getValue(),
                    vinkelTilfeldighetsSlider.getValue(),
                    grenTilfeldigSlider.getValue(),
                    Integer.parseInt(antGrenTxt.getText()),
                    Integer.parseInt(antRekTxt.getText()),
                    tempFarge
            );
        });
        // tilfeldig generering
        tilfeldigKnapp.setOnAction(e -> {
            grenLengdeSlider.setValue(Math.random() * grenLengdeSlider.getMax());
            vinkelSlider.setValue(Math.random() * vinkelSlider.getMax());
            stammeLengdeSlider.setValue(Math.random() * stammeLengdeSlider.getMax());
            tykkelseSlider.setValue(Math.random() * tykkelseSlider.getMax());
            grenProsentSlider.setValue(
                    grenProsentSlider.getMin() +
                            (Math.random() *
                                    ((grenProsentSlider.getMax() - grenProsentSlider.getMin()) + 1)));
            vinkelTilfeldighetsSlider.setValue(Math.random() * vinkelTilfeldighetsSlider.getMax());
            antGrenTxt.setText( Integer.toString( (int) (Math.random() * 5 + 0.5)) );
            antRekTxt.setText( Integer.toString( (int) (Math.random() * 15 + 0.5)) );
            Color tempFarge = Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255),  (int)(Math.random() * 255));
            if (tg.getSelectedToggle() == r1) tempFarge = fargeVelger.getValue();
            tegnerute.lagTre(
                    grenLengdeSlider.getValue(),
                    vinkelSlider.getValue(),
                    stammeLengdeSlider.getValue(),
                    tykkelseSlider.getValue(),
                    grenProsentSlider.getValue(),
                    vinkelTilfeldighetsSlider.getValue(),
                    grenTilfeldigSlider.getValue(),
                    Integer.parseInt(antGrenTxt.getText()),
                    Integer.parseInt(antRekTxt.getText()),
                    tempFarge
            );
        });

        // viser vinduet
        Scene scene = new Scene(borderpane, 1200, 1000);
        stage.setTitle("Lag fraktaltrær!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
