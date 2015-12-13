package com.example.Chauviste_MapEditor;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Antoine Chauvin
 */
public class MapView extends Canvas {
    private final Supplier<Optional<Tileset.Tile>> tileSupplier;
    private Map map;
    private Tileset tileset;
    private boolean grid = true;

    public MapView(Map map, Supplier<Optional<Tileset.Tile>> tileSupplier) {
        this.map = map;
        this.tileset = map.getTileset();
        this.tileSupplier = tileSupplier;

        setWidth(tileset.getWidth() * map.getWidth());
        setHeight(tileset.getHeight() * map.getHeight());

        setOnMouseClicked(this::placeTile);
        setOnMouseDragged(this::placeTile);

        clearWith(tileset.at(0, 0));
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
        this.tileset = map.getTileset();
        draw();
    }

    public void toggleGrid() {
        grid = !grid;
        draw();
    }

    public void placeTile(MouseEvent evt) {
        Optional<Tileset.Tile> tile = tileSupplier.get();
        if (!tile.isPresent()) {
            return;
        }

        int x = (int) evt.getX() / tileset.getWidth();
        int y = (int) evt.getY() / tileset.getHeight();

        map.set(x, y, tile.get());
        draw();
    }

    public void clearWith(Tileset.Tile tile) {
        map.clear(tile);
        draw();
    }

    private void draw() {
        GraphicsContext g = getGraphicsContext2D();

        g.setStroke(Color.BLACK);
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Tileset.Tile tile = map.at(x, y);
                tile.draw(g, x, y);
                if (grid) {
                    g.strokeRect(x * tileset.getWidth(), y * tileset.getHeight(), tileset.getWidth(), tileset.getHeight());
                }
            }
        }
    }
}
