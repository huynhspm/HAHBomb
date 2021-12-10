package bom_it.objects;

import bom_it.engine.Images;
import bom_it.engine.Sprite;
import bom_it.game.App;

import java.util.Random;

import static bom_it.game.Enum.TYPE_SPRITE.*;

public class Explode extends Sprite {
    private static final double DEATH_TIME = 0.3;
    private double deathTime = DEATH_TIME;
    private boolean createItem;

    // constructor
    public Explode(int xInMap, int yInMap, int index) {
        super(Images.explodes[index].getImage(), xInMap, yInMap, EXPLODE);
        createItem = false;
    }

    @Override
    public void update() {
        deathTime -= 1.0 / App.gameWorld.getFramesPerSecond();
        if (deathTime < 0) {
            handleDeath();
        }
    }

    @Override
    public void executeCollision() {
        for (Sprite sprite : App.gameWorld.sprites()) {
            if (this.checkCollision(sprite) && sprite instanceof Box) {
                sprite.handleDeath();
                createItem = true;
            } else if (this.checkCollision(sprite) && sprite instanceof Item) {
                sprite.handleDeath();
            }
        }
    }

    @Override
    public void handleDeath() {
        super.handleDeath();

        if (createItem) {
            int random = Math.abs(new Random().nextInt()) % 20;
            int index = (random < 1) ? 1 : (random < 5) ? 0 : (random < 9) ? 2 : (random < 13) ? 3 : 4;
            if (index < 4) {
                App.gameWorld.spawn(new Item(xInMap, yInMap, index));
            }
        }
    }

    // check collision with wall
    public boolean collisionWall() {
        for (Sprite sprite : App.gameWorld.sprites()) {
            if (sprite instanceof Wall && this.checkCollision(sprite)) {
                return true;
            }
        }
        return false;
    }

    // check collision with box
    public boolean collisionBox() {
        for (Sprite sprite : App.gameWorld.sprites()) {
            if (sprite instanceof Box && this.checkCollision(sprite)) {
                return true;
            }
        }
        return false;
    }
}
