package com.example.Chauviste_Game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.*;

import java.util.HashMap;

public class HeroCreation extends Activity {

    private ImageView heroSelection;
    private EditText heroNameEdit;
    private AbsoluteLayout absoluteLayout;
    private HashMap<Integer, Integer> heroMap = new HashMap<Integer, Integer>();
    int currentIntDrawable = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.creation);

        heroSelection = (ImageView) findViewById(R.id.hero_selection);
        Button leftArrow = (Button) findViewById(R.id.left_arrow);
        Button rightArrow = (Button) findViewById(R.id.right_arrow);
        heroNameEdit = (EditText) findViewById(R.id.hero_name);
        Button select = (Button) findViewById(R.id.select_hero);
        absoluteLayout = (AbsoluteLayout) findViewById(R.id.layout_creation);

        heroMap.put(1, R.drawable.hero_cowboy);
        heroMap.put(2, R.drawable.hero_angel);
        heroMap.put(3, R.drawable.hero_grill);
        heroMap.put(4, R.drawable.hero_overwatch);
        heroMap.put(5, R.drawable.hero_robo);
        heroSelection.setBackgroundResource(heroMap.get(currentIntDrawable));


        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIntDrawable == 1) {
                    currentIntDrawable = 5;
                } else {
                    currentIntDrawable--;
                }
                heroSelection.setBackgroundResource(heroMap.get(currentIntDrawable));
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIntDrawable == 5) {
                    currentIntDrawable = 1;
                } else {
                    currentIntDrawable++;
                }
                heroSelection.setBackgroundResource(heroMap.get(currentIntDrawable));
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HeroStory.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("heroResource", heroMap.get(currentIntDrawable));
                intent.putExtra("heroName", getHeroName());
                getApplicationContext().startActivity(intent);
                finish();
            }
        });




    }

    public String getHeroName() {
        return heroNameEdit.getText().toString();
    }

    public static String colorDecToHex(int p_red, int p_green, int p_blue) {
        String red = Integer.toHexString(p_red);
        String green = Integer.toHexString(p_green);
        String blue = Integer.toHexString(p_blue);

        if (red.length() == 1) {
            red = "0" + red;
        }
        if (green.length() == 1) {
            green = "0" + green;
        }
        if (blue.length() == 1) {
            blue = "0" + blue;
        }

        String colorHex = "#" + red + green + blue;
        return colorHex;
    }

    public void createProgressBar(){
        ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        String color = colorDecToHex(75, 150, 160);

        // Define a shape with rounded corners
        final float[] roundedCorners = new float[]{5, 5, 5, 5, 5, 5, 5, 5};
        ShapeDrawable pgDrawable = new ShapeDrawable(new RoundRectShape(roundedCorners, null, null));

        pgDrawable.getPaint().setColor(Color.parseColor(color));
        ClipDrawable progress = new ClipDrawable(pgDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        progressBar.setProgressDrawable(progress);

        // Sets a background to have the 3D effect
        progressBar.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.progress_horizontal));
        progressBar.setProgress(50);
        // Adds your progressBar to your layout
        absoluteLayout.addView(progressBar);
    }
}

