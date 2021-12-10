package bom_it.game;

import static bom_it.game.Enum.TYPE_SPRITE.BACKGROUND;

public class Square {
    boolean[] typeSprite;
    private int powerBomb;

    // constructor
    public Square() {
        typeSprite = new boolean[Enum.TYPE_SPRITE.values().length];
    }

    // setter and getter
    public void setPowerBomb(int powerBomb) {
        this.powerBomb = powerBomb;
    }

    public int getPowerBomb() {
        return powerBomb;
    }

    public void add(Enum.TYPE_SPRITE type) {
        typeSprite[type.ordinal()] = true;
    }

    public void remove(Enum.TYPE_SPRITE type) {
        typeSprite[type.ordinal()] = false;
    }


    public boolean getTypeSprite(Enum.TYPE_SPRITE type) {
        return typeSprite[type.ordinal()];
    }

    // check empty of square (except background)
    public boolean checkEmpty() {
        for (int i = 1; i < Enum.TYPE_SPRITE.values().length; ++i) {
            if (i != BACKGROUND.ordinal() && typeSprite[i]) {
                return false;
            }
        }

        return true;
    }

    public boolean checkNotExist(Enum.TYPE_SPRITE[] typeSprites) {
        for (Enum.TYPE_SPRITE type : typeSprites) {
            if (typeSprite[type.ordinal()]) {
                return false;
            }
        }
        return true;
    }
}
