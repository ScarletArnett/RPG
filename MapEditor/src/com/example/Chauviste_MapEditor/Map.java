package com.example.Chauviste_MapEditor;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author Antoine Chauvin
 */
public class Map {
    private final Tileset tileset;
    private final Tileset.Tile[][][] tiles;
    private final int width, height, nlayer;

    public Map(Tileset tileset, Tileset.Tile[][][] tiles, int width, int height, int nlayer) {
        this.tileset = tileset;
        this.tiles = tiles;
        this.width = width;
        this.height = height;
        this.nlayer = nlayer;
    }

    public Map(Tileset tileset, int width, int height, int nlayer) {
        this(tileset, new Tileset.Tile[nlayer][height][width], width, height, nlayer);
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

    public int getNlayer() {
        return nlayer;
    }

    public Tileset.Tile at(int x, int y, int l) {
        return tiles[l][y][x];
    }

    public void set(int x, int y, Tileset.Tile tile, int layer) {
        tiles[layer][y][x] = tile;
    }

    public void clear(Tileset.Tile tile, int l) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[l][y][x] = tile;
            }
        }
    }

    public Map withSameDimensions(Tileset tileset) {
        return Map.newEmptyMap(tileset, width, height, nlayer);
    }

    public void saveTo(File file) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
            out.writeInt(tileset.getWidth());
            out.writeInt(tileset.getHeight());

            BufferedImage img = SwingFXUtils.fromFXImage(tileset.getImage(), null);
            ByteArrayOutputStream tmpout = new ByteArrayOutputStream();
            ImageIO.write(img, "bmp", tmpout);
            byte[] imgBytes = tmpout.toByteArray();
            out.writeInt(imgBytes.length);
            out.write(imgBytes);

            out.writeInt(nlayer);
            out.writeInt(width);
            out.writeInt(height);

            for (int l = 0; l < nlayer; l++) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        Tileset.Tile tile = tiles[l][y][x];
                        out.writeInt(tile.getId());
                    }
                }
            }
        }
    }

    public static Map readFrom(File file) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            int tileWidth = in.readInt();
            int tileHeight = in.readInt();

            int imgLen = in.readInt();
            byte[] imgBytes = new byte[imgLen];
            //noinspection ResultOfMethodCallIgnored
            in.read(imgBytes);

            Image img = SwingFXUtils.toFXImage(ImageIO.read(new ByteArrayInputStream(imgBytes)), null);
            Tileset tileset = new Tileset(img, tileWidth, tileHeight);

            int nlayer = in.readInt();
            int width = in.readInt();
            int height = in.readInt();
            Tileset.Tile[][][] tiles = new Tileset.Tile[nlayer][height][width];
            for (int l = 0; l < nlayer; l++) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        tiles[l][y][x] = tileset.getById(in.readInt());
                    }
                }
            }

            return new Map(tileset, tiles, width, height, nlayer);
        }
    }

    public static Map newEmptyMap(Tileset tileset, int w, int h, int l) {
        Map map = new Map(tileset, w, h, l);
        map.clear(tileset.at(0, 0), 0);
        return map;
    }
}
