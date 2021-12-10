package bom_it.objects;

import bom_it.engine.Sprite;
import bom_it.game.App;

import static bom_it.game.Enum.TYPE_SPRITE.BOX;

public class Box extends Sprite {
    public Box(int xInMap, int yInMap, int index) {
        super(App.gameWorld.getSpritesMap().getBox()[index].getImage(), xInMap, yInMap, BOX);
    }
}
