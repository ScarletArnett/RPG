package com.example.Chauviste_MapEditor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author Antoine Chauvin
 */
public final class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Tileset tileset = new Tileset(Paths.get("res", "drawable", "tileset.png"), 32, 32);

        TextField layerField = new TextField("0");

        TileChooser tileChooser = new TileChooser(tileset);
        ScrollPane tileChooserPane = new ScrollPane(tileChooser);
        tileChooserPane.setFitToWidth(true);
        tileChooserPane.setFitToHeight(true);

        MapView mapView = new MapView(Map.newEmptyMap(tileset, 23, 17, 3), tileChooser::getSelected, () -> Integer.parseInt(layerField.getText()));
        ScrollPane mapPane = new ScrollPane(mapView);
        mapPane.setFitToWidth(true);
        mapPane.setFitToHeight(true);

        Button saveBtn = new Button("Save");
        saveBtn.setOnMouseClicked(evt -> {
            FileChooser fileChooser = newFileChooser("Chauviste RPG map file", "*.map");

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
            FileChooser fileChooser = newFileChooser("Chauviste RPG map file", "*.map");

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

        Button openTilesetBtn = new Button("Open Tileset");
        openTilesetBtn.setOnMouseClicked(evt -> {
            FileChooser fileChooser = newFileChooser("Image file", "*.png", "*.bmp");

            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    Tileset newTileset = new Tileset(file.toPath(), 32, 32);
                    tileChooser.setTileset(newTileset);
                    mapView.setMap(mapView.getMap().withSameDimensions(newTileset));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button setTileDimBtn = new Button("Set Tile Dimensions");
        setTileDimBtn.setOnMouseClicked(evt -> {
            Tileset theTileset = tileChooser.getTileset();
            TextField widthField = new TextField(Integer.toString(theTileset.getWidth()));
            TextField heightField = new TextField(Integer.toString(theTileset.getHeight()));
            Button confirmBtn = new Button("Confirm");

            GridPane grid = new GridPane();
            grid.addRow(0, new Text("Width"), widthField);
            grid.addRow(1, new Text("Height"), heightField);

            Stage stage = new Stage();
            stage.setScene(new Scene(new VBox(20.0,
                    new Text("Careful as it will erase your current map!"),
                    grid,
                    confirmBtn)));

            confirmBtn.setOnMouseClicked(evt1 -> {
                int w = Integer.parseInt(widthField.getText());
                int h = Integer.parseInt(heightField.getText());

                Tileset newTileset = theTileset.withDimensions(w, h);
                Map newMap = mapView.getMap().withSameDimensions(newTileset);

                tileChooser.setTileset(newTileset);
                mapView.setMap(newMap);

                stage.close();
            });

            stage.showAndWait();
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
                                new VBox(5.0,
                                        new HBox(20.0,
                                                openBtn,
                                                saveBtn,
                                                closeBtn,
                                                toggleGridBtn
                                        ),
                                        new HBox(20.0,
                                                clearBtn,
                                                openTilesetBtn,
                                                setTileDimBtn,
                                                layerField
                                        )
                                )
                        ),
                        tileChooserPane
                )
        ));
        primaryStage.show();
    }

    private static FileChooser newFileChooser(String description, String... extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, extensions));
        return fileChooser;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
