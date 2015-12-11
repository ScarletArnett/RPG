package com.example.Chauviste_Game;

/**
 * Created by BAPTISTE Alexandre on 08/12/2015.
 */
public class Assassin extends Hero {

    private String[] spellOne = {"LOTUS MORTEL", "50","10",null};
    private String[] spellTwo = {"SHUNPO", "10","20","10"};
    public Assassin() {
        super(50, 10, 300, 100, true);
        super.addSpell(super.getAttackSpell(),spellOne);
        super.addSpell(super.getAttackSpell(),spellTwo);
        setSpriteId(R.drawable.hero_grill);
    }
}
