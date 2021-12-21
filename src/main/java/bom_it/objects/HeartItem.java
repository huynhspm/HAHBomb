package bom_it.objects;

import bom_it.engine.Images;

import static bom_it.game.Enum.TYPE_ITEM.HEART_ITEM;

public class HeartItem extends Item {

    public HeartItem(int xInMap, int yInMap) {
        super(Images.items[HEART_ITEM.ordinal()][0].getImage(), xInMap, yInMap, Images.items[HEART_ITEM.ordinal()]);
    }

    @Override
    protected void powerUp(Character character) {
        character.increaseLives();
    }
}
