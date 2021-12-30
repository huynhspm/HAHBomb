package bom_it.game;

import bom_it.Enum.TypeSprite;

import static bom_it.Enum.TypeSprite.BACKGROUND;

public class Square {
    boolean[] typeSprite;
    private int powerBomb;

    // constructor
    public Square() {
        typeSprite = new boolean[TypeSprite.values().length];
    }

    // setter and getter
    public void setPowerBomb(int powerBomb) {
        this.powerBomb = powerBomb;
    }

    public int getPowerBomb() {
        return powerBomb;
    }

    public void add(TypeSprite type) {
        typeSprite[type.ordinal()] = true;
    }

    public void remove(TypeSprite type) {
        typeSprite[type.ordinal()] = false;
    }


    public boolean getTypeSprite(TypeSprite type) {
        return typeSprite[type.ordinal()];
    }

    // check empty of square (except background)
    public boolean checkEmpty() {
        for (int i = 1; i < TypeSprite.values().length; ++i) {
            if (i != BACKGROUND.ordinal() && typeSprite[i]) {
                return false;
            }
        }

        return true;
    }

    public boolean checkNotExist(TypeSprite[] typeSprites) {
        for (TypeSprite type : typeSprites) {
            if (typeSprite[type.ordinal()]) {
                return false;
            }
        }
        return true;
    }
}
