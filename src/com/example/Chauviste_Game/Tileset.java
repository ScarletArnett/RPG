package com.example.Chauviste_Game;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * @author Antoine Chauvin
 */
public class Tileset {
    private final Bitmap image;
    private final int width, height;

    public Tileset(Bitmap image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public Tile at(int i, int j) {
        return new Tile(i, j);
    }

    public Tile getById(int id) {
        return at((id >> 16) & 0xFFFF, id & 0xFFFF);
    }

    public Bitmap getImage() {
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

        public int getId() {
            return (i & 0xFFFF) << 16 | j & 0xFFFF;
        }

        public Rect getRect() {
            return new Rect(i * width, j * width, (i+1) * width, (j+1) * height);
        }
    }
}
