package bom_it.objects.Character;

import bom_it.Enum.TypePlayer;
import bom_it.game.Images;
import bom_it.game.App;

import static bom_it.Enum.Direction.DOWN;
import static bom_it.Enum.TypePlayer.BLUE;
import static bom_it.Enum.TypeSprite.PLAYER;

public class Player extends Character {
    public static TypePlayer type = BLUE;

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
