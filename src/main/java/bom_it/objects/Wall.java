package bom_it.objects;

import bom_it.engine.Sprite;
import bom_it.game.App;

import java.util.Random;

import static bom_it.Enum.TypeSprite.WALL;

public class Wall extends Sprite {
    public static void createWall(int xInMap, int yInMap){
        int index;
        if ((yInMap == 0 || xInMap == 0 || yInMap + 1 == 15 || xInMap + 1 == 17)) {
            index = 0;
        } else {
            int random = Math.abs(new Random().nextInt()) % 10;
            index = (random < 3) ? 0 : 1;
        }
        App.gameWorld.spawn(new Wall(xInMap, yInMap, index));
    }

    public Wall(int xInMap, int yInMap, int index) {
        super(App.gameWorld.getSpritesMap().getWall()[index].getImage(), xInMap, yInMap, WALL);
    }
}
