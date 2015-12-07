package com.example.Chauviste_Game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.HashMap;

public class HeroCreation extends Activity {

    private ImageView heroSelection;
    private HashMap<Integer, Integer> heroMap = new HashMap<Integer, Integer>();
    int currentIntDrawable = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation);

        heroSelection = (ImageView) findViewById(R.id.hero_selection);
        ImageButton leftArrow = (ImageButton) findViewById(R.id.left_arrow);
        ImageButton rightArrow = (ImageButton) findViewById(R.id.right_arrow);
        Button select = (Button) findViewById(R.id.select_hero);

        heroMap.put(1,R.drawable.hero_cowboy);
        heroMap.put(2,R.drawable.hero_angel);
        heroMap.put(3,R.drawable.hero_grill);
        heroMap.put(4,R.drawable.hero_overwatch);
        heroMap.put(5,R.drawable.hero_robo);
        heroSelection.setBackgroundResource(heroMap.get(currentIntDrawable));


        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIntDrawable == 1){
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
                if(currentIntDrawable == 5){
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
                Intent intent = new Intent(getApplicationContext(),MyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("heroResource",heroMap.get(currentIntDrawable));
                getApplicationContext().startActivity(intent);
                finish();
            }
        });
    }
}
