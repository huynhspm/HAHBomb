package bom_it.objects;

import bom_it.game.Images;
import bom_it.engine.Sprite;
import bom_it.game.App;
import bom_it.objects.Character.Character;

import java.util.Date;

import static bom_it.Enum.TypeSprite.*;

public class Bomb extends Sprite {
    private static final Images[] BOMB_IMAGE = Images.bomb;
    private static final double DEATH_TIME = 3;

    private final Character character;
    private double deathTime = DEATH_TIME;
    private final int power;

    public static void createBomb(int xInMap, int yInMap, Character character){
        App.gameWorld.spawn(new Bomb(xInMap, yInMap, character));
    }

    // constructor
    public Bomb(int xInMap, int yInMap, Character character) {
        super(BOMB_IMAGE[0].getImage(), xInMap, yInMap, BOMB);
        this.character = character;
        this.power = character.powerBombProperty().getValue();

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
        Explode.createExplode(xInMap, yInMap, power);
    }
}
