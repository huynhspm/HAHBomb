package bom_it.objects.Item;

import bom_it.Enum.TypeItem;
import bom_it.game.Images;
import bom_it.engine.Sprite;
import bom_it.game.App;
import bom_it.objects.Character.Character;
import javafx.scene.image.Image;

import java.util.Date;
import java.util.Random;

import static bom_it.Enum.TypeSprite.*;

public abstract class Item extends Sprite {
    private final Images[] imageItem;

    public static void createItem(int xInMap, int yInMap) {
        int random = Math.abs(new Random().nextInt()) % 20;
        int index = (random < 1) ? 1 : (random < 5) ? 0 : (random < 9) ? 2 : (random < 13) ? 3 : 4;
        if(index < 4) {
            switch (TypeItem.values()[index]) {
                case HEART_ITEM -> App.gameWorld.spawn(new HeartItem(xInMap, yInMap));
                case FLAME_ITEM -> App.gameWorld.spawn(new FlameItem(xInMap, yInMap));
                case BOMB_ITEM -> App.gameWorld.spawn(new BombItem(xInMap, yInMap));
                case SPEED_ITEM -> App.gameWorld.spawn(new SpeedItem(xInMap, yInMap));
            }
        }
    }

    // constructor
    protected Item(Image image, int xInMap, int yInMap, Images[] imageItem) {
        super(image, xInMap, yInMap, ITEM);
        this.imageItem = imageItem;
    }

    @Override
    public void update() {
        setImage(imageItem[(int) (new Date().getTime() / 400) % 2 + 1].getImage());
    }

    public abstract void powerUp(Character character);
}
