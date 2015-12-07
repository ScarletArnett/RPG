package com.example.Chauviste_Game;

public class Hero {
    public int force, intel, hp, mana, vigueur;

    public Hero() {
        force   = 10 ;
        intel   = 10 ;
        hp      = 250;
        mana    = 100;
        vigueur = 100;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getIntel() {
        return intel;
    }

    public void setIntel(int intel) {
        this.intel = intel;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getVigueur() {
        return vigueur;
    }

    public void setVigueur(int vigueur) {
        this.vigueur = vigueur;
    }
}
