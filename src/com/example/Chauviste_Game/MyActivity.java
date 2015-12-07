package com.example.Chauviste_Game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class MyActivity extends Activity {

    private ImageButton topArrow, leftArrow, botArrow, rightArrow, buttonA, buttonB, buttonSelect, statButton, saveButton, quitButton, inventoryButton;
    private ImageView heroImage, panelShadow;
    private TextView statsPanel;
    private LinearLayout panelLayout;
    private ArrayList<String> resArray = new ArrayList<String>();

    private float xLeftLimit = 92, yTopLimit = 48, yBotLimit = 952, xRightLimit = 1092;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        topArrow     = (ImageButton)findViewById(R.id.top)          ;
        botArrow     = (ImageButton)findViewById(R.id.bot)          ;
        leftArrow    = (ImageButton)findViewById(R.id.left)         ;
        rightArrow   = (ImageButton)findViewById(R.id.right)        ;
        buttonA      = (ImageButton)findViewById(R.id.a_Button)     ;
        buttonB      = (ImageButton)findViewById(R.id.b_Button)     ;
        heroImage    = (ImageView) findViewById(R.id.heroImage);
        statsPanel   = (TextView) findViewById(R.id.panel)     ;
        panelShadow  = (ImageView) findViewById(R.id.shadow)   ;
        buttonSelect = (ImageButton) findViewById(R.id.select)      ;
        panelLayout  = (LinearLayout) findViewById(R.id.panelLayout);
        statButton   = (ImageButton) findViewById(R.id.stats_button);
        saveButton   = (ImageButton) findViewById(R.id.save_button);
        quitButton   = (ImageButton) findViewById(R.id.quitter_button);
        inventoryButton   = (ImageButton) findViewById(R.id.inventaire_button);
        statsPanel.setPadding(50,100,0,0);

        setHeroByIntent();
        createButtonListeners();

        Hero knight = new Hero();
        createStatsString(knight);

    }

    public void createButtonListeners(){
        topArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float heroY = heroImage.getY();
                if ( heroY < yTopLimit ){
                    heroImage.setY(heroY);
                } else {
                    heroImage.setY(heroImage.getY()-100);
                }
            }
        });
        botArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float heroY = heroImage.getY();
                if( heroY > yBotLimit ){
                    heroImage.setY(heroY);
                } else {
                    heroImage.setY(heroImage.getY()+100);
                }
            }
        });
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float heroX = heroImage.getX();
                if (heroX < xLeftLimit){
                    heroImage.setX(heroX);
                } else{
                    heroImage.setX(heroImage.getX()-100);
                }
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float heroX = heroImage.getX();
                if (heroX > xRightLimit){
                    heroImage.setX(heroX);
                } else{
                    heroImage.setX(heroImage.getX()+100);
                }
            }
        });
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"A PRESSED",Toast.LENGTH_LONG).show();
            }
        });
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statsPanel.getVisibility() == View.VISIBLE){
                    statsPanel.setVisibility(View.INVISIBLE);
                    panelLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(panelLayout.getVisibility() == View.VISIBLE){
                    panelLayout.setVisibility(View.INVISIBLE);
                    panelShadow.setVisibility(View.INVISIBLE);
                } else {
                    panelLayout.setVisibility(View.VISIBLE);
                    panelShadow.setVisibility(View.VISIBLE);

                }
            }
        });
        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statsPanel.setVisibility(View.VISIBLE);
                panelLayout.setVisibility(View.INVISIBLE);
            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFile();
            }
        });
        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFile();
                float x = Float.parseFloat(resArray.get(0));
                float y = Float.parseFloat(resArray.get(1));
                setHeroPosition(x,y);
                Toast.makeText(getApplicationContext(),"Load completed",Toast.LENGTH_SHORT).show();
                swapViewVisibility(panelLayout);
                swapViewVisibility(panelShadow);
            }
        });

    }

    public void createStatsString(Hero hero){
        String panelString = "Force: " + hero.getForce() + "\n" +
                             "Intel: " + hero.getIntel() + "\n" +
                             "HP   : " + hero.getHp()    + "\n" +
                             "Mana : " + hero.getMana();
        statsPanel.setText(panelString);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createFile(){
        String save_struc = heroImage.getX() + "\n" +
                            heroImage.getY();
        File save = new File(Environment.getExternalStorageDirectory().getPath()+"/chauviste_save.txt");
        if(save.exists()){
            save.delete();
        }
        try {
            save.createNewFile();
            FileOutputStream out = new FileOutputStream(save);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.append(save_struc);
            writer.close();
            out.close();
            Toast.makeText(getApplicationContext(),"Saved completed",Toast.LENGTH_SHORT).show();
            swapViewVisibility(panelLayout);
            swapViewVisibility(panelShadow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile(){
        File save = new File(Environment.getExternalStorageDirectory().getPath()+"/chauviste_save.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(save));
            String line;
            while((line = br.readLine()) != null){
                resArray.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHeroPosition(float x, float y){
        heroImage.setX(x);
        heroImage.setY(y);
    }

    public void swapViewVisibility(View view){
        if(view.getVisibility() == View.VISIBLE){
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void setHeroByIntent(){
        Intent intent = getIntent();
        heroImage.setBackgroundResource(intent.getIntExtra("heroResource",R.drawable.hero_cowboy));
    }
}
