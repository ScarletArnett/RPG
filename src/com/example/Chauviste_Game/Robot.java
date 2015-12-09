package com.example.Chauviste_Game;

/**
 * Created by BAPTISTE Alexandre on 08/12/2015.
 */
public class Robot extends Hero {

    private String[] spellOne = {"POINT D'ACIER", "50","10",null};
    private String[] spellTwo = {"BOUCLIER RUNIQUE", null,"100","100"};

    public Robot() {
        super(25, 25, 400, 100, true);
        super.addSpell(super.getAttackSpell(),spellOne);
        super.addSpell(super.getAttackSpell(),spellTwo);
    }
}
