package bom_it.objects;

import bom_it.engine.Sprite;
import bom_it.game.App;

import static bom_it.game.Enum.TYPE_SPRITE.WALL;

public class Wall extends Sprite {
    public Wall(int xInMap, int yInMap, int index) {
        super(App.gameWorld.getSpritesMap().getWall()[index].getImage(), xInMap, yInMap, WALL);
    }
}
