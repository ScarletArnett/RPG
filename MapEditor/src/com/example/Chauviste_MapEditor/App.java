package com.example.Chauviste_MapEditor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Paths;

/**
 * @author Antoine Chauvin
 */
public final class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Tileset tileset = new Tileset(Paths.get("res", "drawable", "tileset.png"), 32, 32);

        TileChooser tileChooser = new TileChooser(tileset);
        ScrollPane tileChooserPane = new ScrollPane(tileChooser);
        tileChooserPane.setFitToWidth(true);
        tileChooserPane.setFitToHeight(true);

        MapView mapView = new MapView(Map.newEmptyMap(tileset, 23, 17), tileChooser::getSelected);
        ScrollPane mapPane = new ScrollPane(mapView);
        mapPane.setFitToWidth(true);
        mapPane.setFitToHeight(true);

        Button saveBtn = new Button("Save");
        saveBtn.setOnMouseClicked(evt -> {
            FileChooser fileChooser = newFileChooser();

            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    mapView.getMap().saveTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button openBtn = new Button("Open");
        openBtn.setOnMouseClicked(evt -> {
            FileChooser fileChooser = newFileChooser();

            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    Map map = Map.readFrom(file);
                    mapView.setMap(map);
                    tileChooser.setTileset(map.getTileset());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button closeBtn = new Button("Close");
        closeBtn.setOnMouseClicked(evt -> Platform.exit());

        Button clearBtn = new Button("Clear");
        clearBtn.setOnMouseClicked(evt -> tileChooser.getSelected().ifPresent(mapView::clearWith));

        Button toggleGridBtn = new Button("Toggle Grid");
        toggleGridBtn.setOnMouseClicked(evt -> mapView.toggleGrid());

        primaryStage.setScene(new Scene(
                new HBox(10.0,
                        new VBox(10.0,
                                mapPane,
                                new HBox(20.0,
                                        openBtn,
                                        saveBtn,
                                        closeBtn,
                                        clearBtn,
                                        toggleGridBtn
                                )
                        ),
                        tileChooserPane
                )
        ));
        primaryStage.show();
    }

    private static FileChooser newFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Chauviste RPG map file", "*.map"));
        return fileChooser;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
