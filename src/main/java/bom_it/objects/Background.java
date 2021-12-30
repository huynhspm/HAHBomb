package bom_it.objects;

import bom_it.engine.Sprite;
import bom_it.game.App;

import static bom_it.Enum.TypeSprite.BACKGROUND;

public class Background extends Sprite {
    public static void createBackground(int xInMap, int yInMap){
        App.gameWorld.spawn(new Background(xInMap, yInMap));
    }

    public Background(int xInMap, int yInMap) {
        super(App.gameWorld.getSpritesMap().getBackground().getImage(), xInMap, yInMap, BACKGROUND);
    }
}

