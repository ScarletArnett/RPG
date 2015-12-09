package com.example.Chauviste_Game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;


public class FightScene extends Activity {

    private Button attackButton, magicButton, leaveButton, autoAttackButton, attackSpellOne, attackSpellTwo, backButton, magicButtonOne, magicButtonTwo, magicButtonThree;
    private LinearLayout menuBattleLayout, menuAttackLayout, menuMagicBattle;
    private ImageView enemy, heroLeft;
    private ArrayList<String[]> attackSpell;
    private ArrayList<String[]> magicSpell;
    private int heroImage;
    private ProgressBar hpBar, manaBar;
    private Hero saitama;
    private Monster monster = new Wolf();
    private int monsterHP = monster.getHpCurrent();
    public boolean isHeroTurn = true, isMonsterTurn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fight);

        hpBar = (ProgressBar) findViewById(R.id.hpBar);
        manaBar = (ProgressBar) findViewById(R.id.manaBar);
        enemy = (ImageView) findViewById(R.id.enemy);
        heroLeft = (ImageView) findViewById(R.id.heroLeft);
        attackButton = (Button) findViewById(R.id.attackButton);
        magicButton  = (Button) findViewById(R.id.magicButton);
        leaveButton  = (Button) findViewById(R.id.runButton);
        backButton   = (Button) findViewById(R.id.backArrow);
        autoAttackButton = (Button) findViewById(R.id.autoAttack);
        attackSpellOne = (Button) findViewById(R.id.attackSpellOne);
        attackSpellTwo = (Button) findViewById(R.id.attackSpellTwo);
        magicButtonOne = (Button) findViewById(R.id.magicSpellOne);
        magicButtonTwo = (Button) findViewById(R.id.magicSpellTwo);
        magicButtonThree = (Button) findViewById(R.id.magicSpellThree);
        menuAttackLayout = (LinearLayout) findViewById(R.id.menuAttackBattle);
        menuBattleLayout = (LinearLayout) findViewById(R.id.menuBattleLayout);
        menuMagicBattle  = (LinearLayout) findViewById(R.id.menuMagicBattle);

        hpBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        manaBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

        receiveIntent();
        setHeroSprite();
        setMonsterSprite(monster);
        setSpell();
        setHpBar(saitama.getHpCurrent(),saitama.getHpMax());
        setManaBar(saitama.getManaCurrent(),saitama.getManaMax());

        Typeface type = Typeface.createFromAsset(getAssets(), "font/RPGSystem.ttf");
        attackButton.setTypeface(type);
        magicButton.setTypeface(type);
        leaveButton.setTypeface(type);
        attackSpellOne.setTypeface(type);
        attackSpellTwo.setTypeface(type);
        autoAttackButton.setTypeface(type);
        backButton.setTypeface(type);
        magicButtonOne.setTypeface(type);
        magicButtonTwo.setTypeface(type);
        magicButtonThree.setTypeface(type);

        layoutOrganisation();
        setAttackButtonListeners();
        setMagicButtonListeners();
        run();
    }

    @SuppressWarnings("unchecked")
    public void receiveIntent(){
        Intent intent = getIntent();
        attackSpell = (ArrayList<String[]>) intent.getSerializableExtra("attackSpell");
        magicSpell  = (ArrayList<String[]>) intent.getSerializableExtra("magicSpell");
        heroImage   = intent.getIntExtra("heroImage",R.drawable.hero_cowboy);
        saitama     = (Hero) intent.getSerializableExtra("saitama");
    }

    public void setSpell(){
        if(attackSpell.isEmpty()){
            attackSpellOne.setText("------");
            attackSpellTwo.setText("------");
        } else {
            attackSpellOne.setText(attackSpell.get(0)[0]);
            attackSpellTwo.setText(attackSpell.get(1)[0]);
        }
        if(!magicSpell.isEmpty()){
            magicButtonOne.setText(magicSpell.get(0)[0]);
            magicButtonTwo.setText(magicSpell.get(1)[0]);
            magicButtonThree.setText(magicSpell.get(2)[0]);
        }
    }

    public void setHeroSprite(){
        heroLeft.setBackgroundResource(heroImage);
    }

    public void setMonsterSprite(Monster monster){
        enemy.setBackgroundResource(monster.getDrawableID());
    }

    public void setHpBar(int currentHP, int maxHP){
        hpBar.setMax(maxHP);
        hpBar.setProgress(currentHP);
    }

    public void setHpBar(int currentHP){
        hpBar.setProgress(currentHP);
    }

    public void setManaBar(int mana){
        manaBar.setProgress(mana);
    }

    public void setManaBar(int currentMana, int maxMana){
        manaBar.setMax(maxMana);
        manaBar.setProgress(currentMana);
    }

    public void monsterAttack(){
        saitama.setHpCurrent(saitama.getHpCurrent()-40);
        monsterAttackAnimation();
        setHpBar(saitama.getHpCurrent());
    }

    public void monsterAttackAnimation() {
        enemy.setX(enemy.getX()-100);
        enemy.setX(enemy.getX()+100);
    }

    public void saitamaAttack(String manaString,String damageString, String healString){
        if(damageString != null){
            saitama.setManaCurrent(saitama.getManaCurrent()-Integer.parseInt(manaString));
            setManaBar(saitama.getManaCurrent());
            monsterHP = monsterHP - Integer.parseInt(damageString);
        } else {
            saitama.setHpCurrent(saitama.getHpCurrent()+Integer.parseInt(healString));
            setHpBar(saitama.getHpCurrent());
            saitama.setManaCurrent(saitama.getManaCurrent()-Integer.parseInt(manaString));
            setManaBar(saitama.getManaCurrent());
        }
    }

    public void setAttackButtonListeners(){
        autoAttackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHeroTurn){
                    monsterHP = monsterHP - 10;
                    Toast.makeText(getApplicationContext(),Integer.toString(monsterHP),Toast.LENGTH_LONG).show();
                    isHeroTurn = false;
                    isMonsterTurn = true;
                    monsterTurn();
                }
            }
        });
        attackSpellOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHeroTurn){
                    saitamaAttack(attackSpell.get(0)[2],attackSpell.get(0)[1],attackSpell.get(0)[3]);
                    isHeroTurn = false;
                    isMonsterTurn = true;
                    monsterTurn();
                }

            }
        });
        attackSpellTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHeroTurn){
                    saitamaAttack(attackSpell.get(1)[2],attackSpell.get(1)[1],attackSpell.get(1)[3]);
                    isHeroTurn = false;
                    isMonsterTurn = true;
                    monsterTurn();
                }
            }
        });
    }

    public void setMagicButtonListeners(){
        magicButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saitamaAttack(magicSpell.get(0)[2],magicSpell.get(0)[1],magicSpell.get(0)[3]);
            }
        });
        magicButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saitamaAttack(magicSpell.get(1)[2],magicSpell.get(0)[1],magicSpell.get(1)[3]);
            }
        });
        magicButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saitamaAttack(magicSpell.get(2)[2],magicSpell.get(2)[1],magicSpell.get(2)[3]);
            }
        });
    }

    public void layoutOrganisation(){
        backButton.setX(leaveButton.getX()-50);
        backButton.setY(leaveButton.getY()-50);

        attackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuAttackLayout.setVisibility(View.VISIBLE);
                menuBattleLayout.setVisibility(View.INVISIBLE);
                backButton.setVisibility(View.VISIBLE);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuAttackLayout.setVisibility(View.INVISIBLE);
                menuMagicBattle.setVisibility(View.INVISIBLE);
                menuBattleLayout.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.INVISIBLE);
            }
        });
        magicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(magicSpell.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Vous ne possédez pas d'attaques magiques!",Toast.LENGTH_LONG).show();
                } else {
                    menuMagicBattle.setVisibility(View.VISIBLE);
                    menuBattleLayout.setVisibility(View.INVISIBLE);
                    backButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void monsterTurn(){
        if(isMonsterTurn){
            monsterAttack();
            isHeroTurn = true;
            isMonsterTurn = false;
            Toast.makeText(getApplicationContext(),"LE MONSTRE A ATTAQUE",Toast.LENGTH_SHORT).show();
        }

    }

    public void isFinished(){
        if(saitama.getHpCurrent() <= 0 || monster.getHpCurrent() <= 0){
            Toast.makeText(getApplicationContext(),"COMBAT TERMINE",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MyActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("hero",saitama);
            intent.putExtra("heroImage",heroImage);
            getApplicationContext().startActivity(intent);
        }
    }

    public void run(){
        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFinished();
            }
        });
    }
}
