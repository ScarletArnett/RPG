package com.example.Chauviste_Game;

import java.io.Serializable;
import java.util.ArrayList;

abstract class Hero implements Serializable {
    public int force, intel, hpMax, manaMax, hpCurrent, manaCurrent;
    public boolean isWizard;
    public ArrayList<String[]> attackSpell = new ArrayList<String[]>();
    public ArrayList<String[]> magicSpell  = new ArrayList<String[]>();

    public Hero(int force, int intel, int hpMax, int manaMax, boolean isWizard) {
        this.force = force;
        this.intel = intel;
        this.hpMax = hpCurrent = hpMax;
        this.manaMax = manaCurrent = manaMax;
        this.isWizard = isWizard;
    }

    public void addSpell(ArrayList arrayList, String[] spell){
        arrayList.add(spell);
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

    public int getHpMax() {
        return hpMax;
    }

    public void setHpMax(int hhMax) {
        this.hpMax = hhMax;
    }

    public int getManaMax() {
        return manaMax;
    }

    public void setManaMax(int manaMax) {
        this.manaMax = manaMax;
    }

    public int getManaCurrent() {
        return manaCurrent;
    }

    public void setManaCurrent(int manaCurrent) {
        this.manaCurrent = manaCurrent;
    }

    public int getHpCurrent() {
        return hpCurrent;
    }

    public void setHpCurrent(int hpCurrent) {
        this.hpCurrent = hpCurrent;
    }

    public boolean isWizard() {
        return isWizard;
    }

    public void setWizard(boolean wizard) {
        isWizard = wizard;
    }

    public ArrayList<String[]> getAttackSpell() {
        return attackSpell;
    }

    public void setAttackSpell(ArrayList<String[]> attackSpell) {
        this.attackSpell = attackSpell;
    }

    public ArrayList<String[]> getMagicSpell() {
        return magicSpell;
    }

    public void setMagicSpell(ArrayList<String[]> magicSpell) {
        this.magicSpell = magicSpell;
    }
}
