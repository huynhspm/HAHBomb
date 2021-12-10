package bom_it.objects;

import bom_it.engine.Sprite;
import bom_it.game.App;

import java.util.Random;

import static bom_it.game.Enum.TYPE_SPRITE.BOX;

public class Box extends Sprite {
    public static void createBox(int xInMap, int yInMap){
        int index = Math.abs(new Random().nextInt()) % 3;
        App.gameWorld.spawn(new Box(xInMap, yInMap, index));
    }

    public Box(int xInMap, int yInMap, int index) {
        super(App.gameWorld.getSpritesMap().getBox()[index].getImage(), xInMap, yInMap, BOX);
    }
}
