package com.example.Chauviste_Game;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Antoine Chauvin
 */
public class GameMap extends View {
    public static final String[] MAP = {
            "                                                                    ",
            "                                                                    ",
            "                                                                    ",
            "                                                                    ",
            "                                                                    ",
            "                                                                    ",
            "                         $                                          ",
            "                             X                                      ",
            "                            .                                       ",
            "                                                                    ",
            "                                                                    ",
            "                                                                    ",
            "                                                                    ",
            "                                                                    ",
            "                                                                    ",
    };

    // density of pixel
    public static final int DP = 3;

    public static final int TILE_WIDTH = 32 * DP, TILE_HEIGHT = 32 * DP;
    public static final int HERO_WIDTH = 32 * DP, HERO_HEIGHT = 48 * DP;
//    public static final int TILE_WIDTH = 40 * DP, TILE_HEIGHT = 40 * DP;

    public static Rect getTile(int x, int y) {
        return new Rect(x*TILE_WIDTH, y*TILE_HEIGHT, (x+1) * TILE_WIDTH, (y+1) * TILE_HEIGHT);
    }

    public static final Rect GRASS_TILE = getTile(0, 0);
    public static final Rect PAVEMENT_TILE = getTile(1, 0);
    public static final Rect TREE_TILE = getTile(0, 1);
//    public static final Rect GRASS_TILE = getTile(6, 3);

    private Paint black;

    private Bitmap heroSprite;
    private Paint heroPaint;

    private Bitmap tilesetSprite;
    private Paint tilesetPaint;

    private Rect rect = new Rect(0, 0, TILE_WIDTH, TILE_HEIGHT);

    private int startx, starty;
    private Hero hero;

    public GameMap(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.black = new Paint();
        this.black.setColor(Color.BLACK);

        this.heroPaint = new Paint();

        this.tilesetSprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.tileset);
        this.tilesetPaint = new Paint();

        outer: for (int y = 0; y < MAP.length; y++) {
            String line = MAP[y];
            for (int x = 0; x < line.length(); x++) {
                char tile = line.charAt(x);
                if (tile == 'X') {
                    this.startx = x;
                    this.starty = y;
                    break outer;
                }
            }
        }

        setHero(new Cowboy(), true);
    }

    public void setHero(Hero hero, boolean starting) {
        this.hero = hero;
        if (starting) {
            this.hero.setPosx(startx);
            this.hero.setPosy(starty);
        }
        this.heroSprite = BitmapFactory.decodeResource(getResources(), this.hero.getSpriteId());
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // bounds of the screen in term of tiles
        // in other words, how many tiles can we fit on the screen?
        int w = (int) Math.ceil((double) getWidth() / TILE_WIDTH),
            h = (int) Math.ceil((double) getHeight() / TILE_HEIGHT);

        // find the origin of the screen, ie. where the player will be centered
        int originx = (w-1)/2, originy = (h-1)/2;

        // draw the map on the screen
        for (int y = 0; y < w; y++) {
            for (int x = 0; x < h; x++) {
                // we got the tile coordinates, let's get them in term of pixels
                rect.set(x * TILE_WIDTH, y * TILE_HEIGHT, (x+1) * TILE_WIDTH, (y+1) * TILE_HEIGHT);

                // get the relative coordinates
                int rx = x - originx, ry = y - originy;

                // apply the relative coordinates to the current position on the map
                int mapx = hero.getPosx() + rx, mapy = hero.getPosy() + ry;

                if (mapy < 0 || mapy >= MAP.length || mapx < 0 || mapx >= MAP[mapy].length()) {
                    // draw nothing if we are getting close to the map boundaries
                    canvas.drawRect(rect, black);
                } else {
                    char tile = MAP[mapy].charAt(mapx);
                    switch (tile) {
                        case 'X':
                        case ' ':
                            canvas.drawBitmap(tilesetSprite, GRASS_TILE, rect, tilesetPaint);
                            break;

                        case '.':
                            canvas.drawBitmap(tilesetSprite, PAVEMENT_TILE, rect, tilesetPaint);
                            break;

                        case '$':
                            canvas.drawBitmap(tilesetSprite, TREE_TILE, rect, tilesetPaint);
                            break;

                        default:
                            // we found an unsupported tile, just draw nothing and ignore the fact
                            canvas.drawRect(rect, black);
                            break;
                    }
                }
            }
        }

        // the hero will correctly be drawn such as its feet touch the ground
        // and its head overflows on top of its tile,
        // and its body its correctly centered on the tile
        //noinspection PointlessArithmeticExpression
        canvas.drawBitmap(heroSprite,
                // center the body
                originx * TILE_WIDTH + (TILE_WIDTH - HERO_WIDTH) / 2,
                // place the feet on the tile and let the head overflow on top
                originy * TILE_HEIGHT - HERO_HEIGHT + TILE_HEIGHT,
                heroPaint);
    }
}
