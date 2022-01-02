package bom_it.objects;

import bom_it.game.Images;
import bom_it.engine.Sprite;
import bom_it.game.App;

import java.util.Date;

import static bom_it.Enum.TypeSprite.PORTAL;

public class Portal extends Sprite {
    private static final Images[] PORTAL_IMAGE = Images.portal;

    public static void createPortal(int xInMap, int yInMap){
        App.gameWorld.spawn(new Portal(xInMap, yInMap));
    }
    protected Portal(int xInMap, int yInMap) {
        super(Images.portal[0].getImage(), xInMap, yInMap, PORTAL);
    }

    @Override
    public void update() {
        setImage(PORTAL_IMAGE[(int) (new Date().getTime() / 100) % 3 + 2].getImage());
    }
}
