package bom_it.objects;

import bom_it.engine.Sprite;
import bom_it.game.App;

import static bom_it.game.Enum.TYPE_SPRITE.BACKGROUND;

public class Background extends Sprite {
    public Background(int xInMap, int yInMap) {
        super(App.gameWorld.getSpritesMap().getBackground().getImage(), xInMap, yInMap, BACKGROUND);
    }
}

