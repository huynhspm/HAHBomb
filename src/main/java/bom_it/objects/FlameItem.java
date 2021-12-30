package bom_it.objects;

import bom_it.engine.Images;

import static bom_it.Enum.TypeItem.FLAME_ITEM;

public class FlameItem extends Item {
    public static final int MAX_POWER_BOMB = 4;
    protected FlameItem(int xInMap, int yInMap) {
        super(Images.items[FLAME_ITEM.ordinal()][0].getImage(), xInMap, yInMap, Images.items[FLAME_ITEM.ordinal()]);
    }

    @Override
    protected void powerUp(Character character) {
        if(character.powerBombProperty().getValue() < MAX_POWER_BOMB){
            character.increasePowerBomb();
        }
    }
}
