package bom_it.objects;

import bom_it.engine.Images;

import static bom_it.game.Enum.TYPE_ITEM.SPEED_ITEM;

public class SpeedItem extends Item {
    private static final int MAX_POWER_SPEED = 10;
    protected SpeedItem(int xInMap, int yInMap) {
        super(Images.items[SPEED_ITEM.ordinal()][0].getImage(), xInMap, yInMap, Images.items[SPEED_ITEM.ordinal()]);
    }

    @Override
    protected void powerUp(Character character) {
        character.powerSpeedProperty().setValue(Math.min(character.getPowerSpeed() + 1, MAX_POWER_SPEED));
    }
}
