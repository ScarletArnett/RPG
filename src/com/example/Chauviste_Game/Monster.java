package com.example.Chauviste_Game;

import java.io.Serializable;

public class Monster implements Serializable{
    public int hpMax, hpCurrent, drawableID;

    public int getHpMax() {
        return hpMax;
    }

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }

    public int getHpCurrent() {
        return hpCurrent;
    }

    public void setHpCurrent(int hpCurrent) {
        this.hpCurrent = hpCurrent;
    }

    public int getDrawableID() {
        return drawableID;
    }

    public Monster(int hpMax, int id) {
        this.hpMax = hpCurrent = hpMax;
        this.drawableID = id;

    }
}
