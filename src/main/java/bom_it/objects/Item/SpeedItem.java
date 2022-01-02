package bom_it.objects.Item;

import bom_it.game.Images;
import bom_it.objects.Character.Character;

import static bom_it.Enum.TypeItem.SPEED_ITEM;


public class SpeedItem extends Item {
    private static final int MAX_POWER_SPEED = 10;
    protected SpeedItem(int xInMap, int yInMap) {
        super(Images.items[SPEED_ITEM.ordinal()][0].getImage(), xInMap, yInMap, Images.items[SPEED_ITEM.ordinal()]);
    }

    @Override
    public void powerUp(Character character) {
        if(character.powerSpeedProperty().getValue() < MAX_POWER_SPEED){
            character.increasePowerSpeed();
        }
    }
}
