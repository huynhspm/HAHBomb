package bom_it.objects;

import bom_it.controller.EnemyLevel1Controller;
import bom_it.engine.Sprite;
import bom_it.game.App;
import bom_it.game.Enum;

import static bom_it.game.Enum.STATUS_CHARACTER.IMMORTAL;

public class EnemyLevel1 extends Enemy{
    public EnemyLevel1(int xInMap, int yInMap) {
        super(xInMap, yInMap);
        controller = new EnemyLevel1Controller(this);
    }

    @Override
    public void executeCollision() {
        super.executeCollision();
        for (Sprite sprite : App.gameWorld.sprites()) {
            if (this.checkCollision(sprite) && sprite instanceof Player && ((Player) sprite).status != IMMORTAL) {
                Enum.STATUS_CHARACTER.setStunned((Character) sprite);
            }
        }
    }

    @Override
    protected void collisionItem(Item item) {
        if(!(item instanceof HeartItem)){
            item.powerUp(this);
        }
        item.handleDeath();
    }
}
