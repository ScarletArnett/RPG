package com.example.Chauviste_Game;

public class Overwatch extends Hero {

    private String[] spellOne = {"RETROBANG", "50","10",null};
    private String[] spellTwo = {"TIME JUMP", null,"50","50"};
    private String[] spellThree = {"CHRONO-FRACTURE", "100","100",null};

    public Overwatch() {
        super(10, 50, 250, 150, true);
        super.addSpell(super.getMagicSpell(),spellOne);
        super.addSpell(super.getMagicSpell(),spellTwo);
        super.addSpell(super.getMagicSpell(),spellThree);
    }
}
