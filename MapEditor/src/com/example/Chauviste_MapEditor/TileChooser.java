package com.example.Chauviste_MapEditor;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Optional;

/**
 * @author Antoine Chauvin
 */
public class TileChooser extends Canvas {
    private Tileset tileset;
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

    public void setTileset(Tileset tileset) {
        this.tileset = tileset;
        this.selectedx = this.selectedy = -1;
        draw();
    }

    public Optional<Tileset.Tile> getSelected() {
        return selectedx != -1 ?
                selectedy != -1 ?
                        Optional.of(tileset.at(selectedx, selectedy)) :
                        Optional.empty() :
                Optional.empty();
    }

    private void draw() {
        GraphicsContext g = getGraphicsContext2D();

        g.drawImage(tileset.getImage(), 0, 0);

        if (selectedx != -1 && selectedy != -1) {
            g.setStroke(Color.RED);
            g.strokeRect(selectedx * tileset.getWidth(), selectedy * tileset.getHeight(), tileset.getWidth(), tileset.getHeight());
        }
    }
}
