package com.example.Chauviste_MapEditor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Antoine Chauvin
 */
public class Tileset {
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

    public Tile getById(int id) {
        return at((id >> 16) & 0xFFFF, id & 0xFFFF);
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

    public Tileset withDimensions(int w, int h) {
        return new Tileset(image, w, h);
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

        public int getId() {
            return (i & 0xFFFF) << 16 | j & 0xFFFF;
        }
    }
}
