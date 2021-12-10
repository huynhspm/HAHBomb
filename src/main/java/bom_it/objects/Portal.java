package bom_it.objects;

import bom_it.engine.Images;
import bom_it.engine.Sprite;

import java.util.Date;

import static bom_it.game.Enum.TYPE_SPRITE.PORTAL;

public class Portal extends Sprite {
    private static final Images[] PORTAL_IMAGE = Images.portal;

    protected Portal(int xInMap, int yInMap) {
        super(Images.portal[0].getImage(), xInMap, yInMap, PORTAL);
    }

    @Override
    public void update() {
        setImage(PORTAL_IMAGE[(int) (new Date().getTime() / 100) % 3 + 2].getImage());
    }
}
