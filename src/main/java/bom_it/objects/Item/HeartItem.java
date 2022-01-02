package bom_it.objects.Item;

import bom_it.game.Images;
import bom_it.objects.Character.Character;

import static bom_it.Enum.TypeItem.HEART_ITEM;

public class HeartItem extends Item {

    public HeartItem(int xInMap, int yInMap) {
        super(Images.items[HEART_ITEM.ordinal()][0].getImage(), xInMap, yInMap, Images.items[HEART_ITEM.ordinal()]);
    }

    @Override
    public void powerUp(Character character) {
        character.increaseLives();
    }
}
