package bom_it.objects;

import bom_it.engine.Images;
import bom_it.game.App;
import bom_it.game.Enum;

import static bom_it.game.Enum.DIRECTION.DOWN;
import static bom_it.game.Enum.TYPE_PLAYER.BLUE;
import static bom_it.game.Enum.TYPE_SPRITE.PLAYER;

public class Player extends Character {
    public static Enum.TYPE_PLAYER type = BLUE;

    public static Player createPlayer(int xInMap, int yInMap) {
        Player player = new Player(xInMap, yInMap);
        App.gameWorld.spawn(player);
        return player;
    }

    public Player(int xInMap, int yInMap) {
        super(Images.boomer[type.ordinal()][DOWN.ordinal()][0].getImage(),
                xInMap, yInMap, PLAYER, Images.boomer[type.ordinal()]);
    }

    @Override
    public void update() {
        super.update();
    }
}
