package com.example.Chauviste_Game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author Antoine Chauvin
 */
public class GameMap {
    private final Tileset tileset;
    private final Tileset.Tile[][] tiles;
    private final int width, height;

    public GameMap(Tileset tileset, Tileset.Tile[][] tiles, int width, int height) {
        this.tileset = tileset;
        this.tiles = tiles;
        this.width = width;
        this.height = height;
    }

    public Tileset getTileset() {
        return tileset;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tileset.Tile at(int x, int y) {
        return tiles[y][x];
    }

    public static GameMap readFrom(Resources res, int id) throws IOException {
        try (DataInputStream in = new DataInputStream(res.openRawResource(id))) {
            int tileWidth = in.readInt();
            int tileHeight = in.readInt();

            int imgLen = in.readInt();
            byte[] imgBytes = new byte[imgLen];
            //noinspection ResultOfMethodCallIgnored
            in.read(imgBytes);

            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgLen);
            Tileset tileset = new Tileset(bitmap, tileWidth, tileHeight);

            int width = in.readInt();
            int height = in.readInt();
            Tileset.Tile[][] tiles = new Tileset.Tile[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    tiles[y][x] = tileset.getById(in.readInt());
                }
            }

            return new GameMap(tileset, tiles, width, height);
        }
    }
}
