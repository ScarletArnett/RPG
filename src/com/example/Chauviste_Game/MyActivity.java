package com.example.Chauviste_Game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.*;
import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class MyActivity extends Activity {

    private ImageButton topArrow, leftArrow, botArrow, rightArrow, buttonA, buttonB, buttonSelect;
    private Button statButton, saveButton,quitButton,inventoryButton;
    private RelativeLayout statsPanel;
    private LinearLayout panelLayout;
    private Boolean isLoad;
    private ArrayList<String> resArray = new ArrayList<String>();
    private int heroDrawableId;
    private ProgressBar manaBar, hpBar;
    private TextView forceIntel;
    private float xLeftLimit = 92, yTopLimit = 48, yBotLimit = 1904, xRightLimit = 1192;
    Hero saitama;

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
        statsPanel   = (RelativeLayout) findViewById(R.id.statsPanel)     ;
        buttonSelect = (ImageButton) findViewById(R.id.select_button_panel)      ;
        panelLayout  = (LinearLayout) findViewById(R.id.panelLayout);
        statButton   = (Button) findViewById(R.id.stats_button);
        saveButton   = (Button) findViewById(R.id.save_button);
        quitButton   = (Button) findViewById(R.id.quitter_button);
        manaBar      = (ProgressBar) findViewById(R.id.statsManaBar);
        hpBar        = (ProgressBar) findViewById(R.id.statsHPBar);
        forceIntel   = (TextView) findViewById(R.id.forceIntelPanel);
        inventoryButton   = (Button) findViewById(R.id.inventaire_button);
        //statsPanel.setPadding(50,100,0,0);

        hpBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        manaBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

        receiveIntent();
        setFromLoad();
        createButtonListeners();

        saitama = createHero();
        receiveIntentAfterFight();

        setHpBar(saitama.getHpCurrent(),saitama.getHpMax());
        setManaBar(saitama.getManaCurrent(),saitama.getManaMax());

        createStatsString(saitama);

    }

    public void createButtonListeners(){
        topArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        botArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessFight();
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
                } else {
                    panelLayout.setVisibility(View.VISIBLE);
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

            }
        });

    }

    public void createStatsString(Hero hero){
        String panelString = "Force: " + hero.getForce()        + "\n" +
                             "Intel: " + hero.getIntel();
        forceIntel.setText(panelString);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createFile(){
        String save_struc = 0 + "\n" +
                            0 + "\n" +
                            heroDrawableId;
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
//        heroImage.setX(x);
//        heroImage.setY(y);
    }

    public void swapViewVisibility(View view){
        if(view.getVisibility() == View.VISIBLE){
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void receiveIntent(){
        /* Intent from hero creation*/
        Intent intent = getIntent();
        heroDrawableId = intent.getIntExtra("heroResource",R.drawable.hero_cowboy);
//        heroImage.setBackgroundResource(heroDrawableId);
        isLoad = intent.getBooleanExtra("load",false);
    }

    public void receiveIntentAfterFight(){
        Intent afterFight = getIntent();
        if((afterFight.getSerializableExtra("hero") != null)){
            saitama = (Hero)afterFight.getSerializableExtra("hero");
//            heroImage.setBackgroundResource(afterFight.getIntExtra("heroImage",R.drawable.hero_cowboy));
        }
    }

    public void setFromLoad(){
        if(isLoad){
            loadFile();
            float x = Float.parseFloat(resArray.get(0));
            float y = Float.parseFloat(resArray.get(1));
            heroDrawableId = Integer.parseInt(resArray.get(2));
            setHeroPosition(x,y);
//            heroImage.setBackgroundResource(heroDrawableId);
            Toast.makeText(getApplicationContext(),"Load completed",Toast.LENGTH_SHORT).show();
        } else {
            setHeroPosition(5,5);
            Toast.makeText(getApplicationContext(),"NOT LOADED",Toast.LENGTH_SHORT).show();
        }
    }

    public Hero createHero(){
        Hero hero;
        switch (heroDrawableId){
            case R.drawable.hero_angel:
                hero = new Angel();
                break;
            case R.drawable.hero_cowboy:
                hero = new Cowboy();
                break;
            case R.drawable.hero_grill:
                hero = new Assassin();
                break;
            case R.drawable.hero_robo:
                hero = new Robot();
                break;
            case R.drawable.hero_overwatch:
                hero = new Overwatch();
                break;
            default:
                hero = new Cowboy();
        }
        return hero;
    }

    public void accessFight(){
        Intent intent = new Intent(getApplicationContext(), FightScene.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("attackSpell",saitama.getAttackSpell());
        intent.putExtra("magicSpell",saitama.getMagicSpell());
        intent.putExtra("heroImage",heroDrawableId);
        intent.putExtra("saitama",saitama);
        getApplicationContext().startActivity(intent);
    }

    public void setHpBar(int currentHP, int maxHP){
        hpBar.setMax(maxHP);
        hpBar.setProgress(currentHP);
    }

    public void setManaBar(int currentMana, int maxMana){
        manaBar.setMax(maxMana);
        manaBar.setProgress(currentMana);
    }

}
