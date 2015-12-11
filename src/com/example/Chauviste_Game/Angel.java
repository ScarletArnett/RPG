package com.example.Chauviste_Game;

public class Angel extends Hero {

    //                              NOM,           DEGATS, MANA, HEAL
    private String[] spellOne = {"LUMIERE CELESTE", "40","50",null};
    private String[] spellTwo = {"CHATIMENT", "30","30",null};
    private String[] spellThree = {"PURIFICATION", null,"200", Integer.toString(getHpMax())};

    public Angel() {
        super(10, 50, 200, 200, true);
        super.addSpell(super.getMagicSpell(),spellOne);
        super.addSpell(super.getMagicSpell(),spellTwo);
        super.addSpell(super.getMagicSpell(),spellThree);
        setSpriteId(R.drawable.hero_angel);
    }
}
