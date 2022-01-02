package bom_it.objects.Item;

import bom_it.game.Images;
import bom_it.objects.Character.Character;

import static bom_it.Enum.TypeItem.BOMB_ITEM;

public class BombItem extends Item {
    protected BombItem(int xInMap, int yInMap) {
        super(Images.items[BOMB_ITEM.ordinal()][0].getImage(), xInMap, yInMap, Images.items[BOMB_ITEM.ordinal()]);
    }

    @Override
    public void powerUp(Character character) {
        character.increaseNumBomb();
    }
}
