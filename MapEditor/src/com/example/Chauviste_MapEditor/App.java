package com.example.Chauviste_MapEditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Antoine Chauvin
 */
public final class App extends Application {
    public static class Tileset {
        private final Image image;
        private final int width, height;

        public Tileset(Image image, int width, int height) {
            this.image = image;
            this.width = width;
            this.height = height;
        }

        public Tileset(Path path, int height, int width) throws IOException {
            this(new Image(Files.newInputStream(path)), width, height);
        }

        public Tile at(int i, int j) {
            return new Tile(i, j);
        }

        public int getMaxTileX() {
            return (int) image.getWidth() / width;
        }

        public int getMaxTileY() {
            return (int) image.getHeight() / height;
        }

        public Image getImage() {
            return image;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public class Tile {
            private final int i, j;

            public Tile(int i, int j) {
                this.i = i;
                this.j = j;
            }

            public void draw(GraphicsContext g, int x, int y) {
                g.drawImage(image, i * width, j * width, width, height, x * width, y * height, width, height);
            }
        }
    }

    public static class Map extends Canvas {
        private final Tileset tileset;
        private final Supplier<Optional<Tileset.Tile>> tileSupplier;
        private final Tileset.Tile[][] tiles;

        public Map(Tileset tileset, int width, int height, Supplier<Optional<Tileset.Tile>> tileSupplier) {
            this.tileset = tileset;
            this.tileSupplier = tileSupplier;

            setWidth(tileset.getWidth() * width);
            setHeight(tileset.getHeight() * height);

            Tileset.Tile nil = tileset.at(0, 0);

            tiles = new Tileset.Tile[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    tiles[y][x] = nil;
                }
            }

            setOnMouseClicked(evt -> {
                Optional<Tileset.Tile> tile = tileSupplier.get();
                if (!tile.isPresent()) {
                    return;
                }

                int x = (int) evt.getX() / tileset.getWidth();
                int y = (int) evt.getY() / tileset.getHeight();

                tiles[y][x] = tile.get();
                draw();
            });

            draw();
        }

        private void draw() {
            GraphicsContext g = getGraphicsContext2D();

            g.setStroke(Color.BLACK);
            for (int y = 0; y < tiles.length; y++) {
                for (int x = 0; x < tiles[y].length; x++) {
                    Tileset.Tile tile = tiles[y][x];
                    tile.draw(g, x, y);
                    g.strokeRect(x * tileset.getWidth(), y * tileset.getHeight(), tileset.getWidth(), tileset.getHeight());
                }
            }
        }
    }

    public static class TileChooser extends Canvas {
        private final Tileset tileset;
        private int selectedx = -1, selectedy = -1;

        public TileChooser(Tileset tileset) {
            this.tileset = tileset;

            setWidth(this.tileset.getImage().getWidth());
            setHeight(this.tileset.getImage().getHeight());

            setOnMouseClicked(evt -> {
                selectedx = (int) evt.getX() / tileset.getWidth();
                selectedy = (int) evt.getY() / tileset.getHeight();

                draw();
            });

            draw();
        }

        public Optional<Tileset.Tile> getSelected() {
            return selectedx != -1 ?
                    selectedy != -1 ?
                            Optional.of(tileset.at(selectedx, selectedy)):
                            Optional.empty():
                            Optional.empty();
        }

        private void draw() {
            GraphicsContext g = getGraphicsContext2D();

            g.clearRect(0, 0, getWidth(), getHeight());

            g.drawImage(tileset.getImage(), 0, 0);

            g.setStroke(Color.RED);
            for (int y = 0; y < tileset.getMaxTileY(); y++) {
                for (int x = 0; x < tileset.getMaxTileX(); x++) {
                    g.strokeRect(x * tileset.getWidth(), y * tileset.getHeight(), tileset.getWidth(), tileset.getHeight());
                }
            }

            if (selectedx != -1 && selectedy != -1) {
                g.setStroke(Color.BLUE);
                g.strokeRect(selectedx * tileset.getWidth(), selectedy * tileset.getHeight(), tileset.getWidth(), tileset.getHeight());
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Tileset tileset = new Tileset(Paths.get("res", "drawable", "tileset.png"), 32, 32);

        TileChooser tileChooser = new TileChooser(tileset);

        primaryStage.setScene(new Scene(
                new HBox(10.0,
                        new VBox(10.0,
                                new Map(tileset, 23, 17, tileChooser::getSelected),
                                new HBox(20.0,
                                        new Button("Save"),
                                        new Button("Close")
                                )
                        ),
                        tileChooser
                )
        ));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
