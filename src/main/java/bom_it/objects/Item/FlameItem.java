package bom_it.objects.Item;

import bom_it.game.Images;
import bom_it.objects.Character.Character;

import static bom_it.Enum.TypeItem.FLAME_ITEM;

public class FlameItem extends Item {
    public static final int MAX_POWER_BOMB = 4;
    protected FlameItem(int xInMap, int yInMap) {
        super(Images.items[FLAME_ITEM.ordinal()][0].getImage(), xInMap, yInMap, Images.items[FLAME_ITEM.ordinal()]);
    }

    @Override
    public void powerUp(Character character) {
        if(character.powerBombProperty().getValue() < MAX_POWER_BOMB){
            character.increasePowerBomb();
        }
    }
}
