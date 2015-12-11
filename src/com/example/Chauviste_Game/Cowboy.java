package com.example.Chauviste_Game;

/**
 * Created by BAPTISTE Alexandre on 08/12/2015.
 */
public class Cowboy extends Hero {
    private String[] spellOne = {"LAME D'ACIER", "50","10",null};
    private String[] spellTwo = {"DERNIER SOUPIR", "100","100",null};
    public Cowboy() {
        super(50, 10, 300, 100, true);
        super.addSpell(super.getAttackSpell(),spellOne);
        super.addSpell(super.getAttackSpell(),spellTwo);
        setSpriteId(R.drawable.hero_cowboy);
    }
}
