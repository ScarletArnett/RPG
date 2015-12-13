package com.example.Chauviste_Game;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;

/**
 * @author Antoine Chauvin
 */
public class GameMapView extends View {
    // density of pixel
    public static final int DP = 3;

    private Bitmap heroSprite;

    private Paint black;
    private Paint heroPaint;
    private Paint tilesetPaint;

    private int startx, starty;
    private Hero hero;
    private GameMap map;
    private Tileset tileset;

    private final Rect rect = new Rect();

    public GameMapView(Context context, AttributeSet attrs) throws IOException {
        super(context, attrs);

        this.black = new Paint();
        this.black.setColor(Color.BLACK);

        this.heroPaint = new Paint();
        this.tilesetPaint = new Paint();

        this.map = GameMap.readFrom(context.getResources(), R.raw.startMap);
        this.tileset = this.map.getTileset();

        startx = starty = 0;

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
        int tileWidth = tileset.getWidth() * DP;
        int tileHeight = tileset.getHeight() * DP;

        // bounds of the screen in term of tiles
        // in other words, how many tiles can we fit on the screen?
        int w = (int) Math.ceil((double) getWidth() / tileWidth),
            h = (int) Math.ceil((double) getHeight() / tileHeight);

        // find the origin of the screen, ie. where the player will be centered
        int originx = (w-1)/2, originy = (h-1)/2;

        // draw the map on the screen
        for (int y = 0; y < w; y++) {
            for (int x = 0; x < h; x++) {
                // we got the tile coordinates, let's get them in term of pixels
                rect.set(x * tileWidth,
                        y * tileHeight,
                        (x+1) * tileWidth,
                        (y+1) * tileHeight);

                // get the relative coordinates
                int rx = x - originx, ry = y - originy;

                // apply the relative coordinates to the current position on the map
                int mapx = hero.getPosx() + rx, mapy = hero.getPosy() + ry;

                if (mapy < 0 || mapy >= map.getHeight() || mapx < 0 || mapx >= map.getWidth()) {
                    // draw nothing if we are getting close to the map boundaries
                    canvas.drawRect(rect, black);
                } else {
                    Tileset.Tile tile = map.at(mapx, mapy);
                    canvas.drawBitmap(tileset.getImage(), tile.getRect(), rect, tilesetPaint);
                }
            }
        }

        // the hero will correctly be drawn such as its feet touch the ground
        // and its head overflows on top of its tile,
        // and its body its correctly centered on the tile
        //noinspection PointlessArithmeticExpression
        canvas.drawBitmap(heroSprite,
                // center the body
                originx * tileWidth + (tileWidth - heroSprite.getWidth()) / 2,
                // place the feet on the tile and let the head overflow on top
                (originy+1) * tileHeight - heroSprite.getHeight(),
                heroPaint);
    }
}
