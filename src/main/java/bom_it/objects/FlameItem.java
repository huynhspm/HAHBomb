package bom_it.objects;

import bom_it.engine.Images;

import static bom_it.game.Enum.TYPE_ITEM.FLAME_ITEM;

public class FlameItem extends Item {
    public static final int MAX_POWER_BOM = 4;
    protected FlameItem(int xInMap, int yInMap) {
        super(Images.items[FLAME_ITEM.ordinal()][0].getImage(), xInMap, yInMap, Images.items[FLAME_ITEM.ordinal()]);
    }

    @Override
    protected void powerUp(Character character) {
        character.powerBombProperty().setValue(Math.min(character.getPowerBomb() + 1, MAX_POWER_BOM));
    }
}
