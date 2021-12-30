package bom_it.objects;

import bom_it.engine.Images;

import static bom_it.Enum.TypeItem.BOMB_ITEM;

public class BombItem extends Item {
    protected BombItem(int xInMap, int yInMap) {
        super(Images.items[BOMB_ITEM.ordinal()][0].getImage(), xInMap, yInMap, Images.items[BOMB_ITEM.ordinal()]);
    }

    @Override
    protected void powerUp(Character character) {
        character.increaseNumBomb();
    }
}
