package bom_it.objects;

import bom_it.engine.Images;
import bom_it.engine.Sprite;
import bom_it.game.App;

import java.util.Date;

import static bom_it.game.Enum.TYPE_SPRITE.BOMB;

public class Bomb extends Sprite {
    private static final Images[] BOMB_IMAGE = Images.bomb;
    private static final double DEATH_TIME = 3;

    private final Character character;
    private double deathTime = DEATH_TIME;
    private final int power;

    // constructor
    public Bomb(int xInMap, int yInMap, int power, Character character) {
        super(BOMB_IMAGE[0].getImage(), xInMap, yInMap, BOMB);
        this.power = power;
        this.character = character;

        for (Sprite sprite : App.gameWorld.sprites()) {
            if (sprite instanceof Character && sprite.checkCollision(this)) {
                ((Character) sprite).setOnBomb(((Character) sprite).getOnBomb(), this);

            }
        }
    }

    public int getPower() {
        return power;
    }

    @Override
    public void update() {
        setImage(BOMB_IMAGE[(int) (new Date().getTime() / 100) % 2 + 1].getImage());
        deathTime -= 1.0 / App.gameWorld.getFramesPerSecond();
        if (deathTime < 0) {
            handleDeath();
        }
    }

    @Override
    public void executeCollision() {
        for (Sprite sprite : App.gameWorld.sprites()) {
            if (this.checkCollision(sprite) && sprite instanceof Explode) {
                handleDeath();
            }
        }

    }

    @Override
    public void handleDeath() {
        if (App.gameWorld.getSoundEffectGame().isStatus()) {
            App.gameWorld.getSoundEffectGame().play();
        }
        super.handleDeath();
        character.increaseNumBomb();
        App.gameWorld.spawn(new Explode(xInMap, yInMap, 6));

        boolean up = false;
        boolean left = false;
        boolean down = false;
        boolean right = false;
        Explode explode;

        for (int i = 1; i <= power; ++i) {
            if (!up) {
                explode = new Explode(xInMap, yInMap - i, (i == power) ? 2 : 0);
                up = spawnExplode(explode);
            }

            if (!down) {
                explode = new Explode(xInMap, yInMap + i, (i == power) ? 4 : 0);
                down = spawnExplode(explode);
            }

            if (!left) {
                explode = new Explode(xInMap - i, yInMap, (i == power) ? 5 : 1);
                left = spawnExplode(explode);
            }

            if (!right) {
                explode = new Explode(xInMap + i, yInMap, (i == power) ? 3 : 1);
                right = spawnExplode(explode);
            }
        }
    }

    // check explode can spread out
    private boolean spawnExplode(Explode explode) {
        if (explode.collisionBox()) {
            App.gameWorld.spawn(explode);
            return true;
        } else if (!explode.collisionWall()) {
            App.gameWorld.spawn(explode);
            return false;
        }
        return true;
    }
}
