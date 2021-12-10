package bom_it.objects;

import bom_it.engine.Images;
import bom_it.engine.Sprite;
import bom_it.game.App;
import bom_it.game.Enum;

import java.util.Date;
import java.util.Random;

import static bom_it.game.Enum.TYPE_SPRITE.ITEM;

public class Item extends Sprite {
    public static final int MAX_POWER_SPEED = 8;
    public static final int MAX_POWER_BOM = 4;
    private static final Images[] IMAGE_ITEM = Images.items;

    private final Enum.TYPE_ITEM type;

    public static void createItem(int xInMap, int yInMap) {
        int random = Math.abs(new Random().nextInt()) % 20;
        int index = (random < 1) ? 1 : (random < 5) ? 0 : (random < 9) ? 2 : (random < 13) ? 3 : 4;
        if (index < 4) {
            App.gameWorld.spawn(new Item(xInMap, yInMap, index));
        }
    }

    // constructor
    public Item(int xInMap, int yInMap, int index) {
        super(IMAGE_ITEM[index].getImage(), xInMap, yInMap, ITEM);
        type = Enum.TYPE_ITEM.values()[index];
    }

    public Enum.TYPE_ITEM getType() {
        return type;
    }

    @Override
    public void update() {
        int index = (int) (new Date().getTime() / 400) % 2 + 1;
        index = (index == 1) ? type.ordinal() + 4 : type.ordinal();
        setImage(IMAGE_ITEM[index].getImage());
    }

    @Override
    public void handleDeath() {
        super.handleDeath();
    }
}
