package bom_it.objects.Character.Enemy;

import bom_it.Enum.StatusCharacter;
import bom_it.controller.EnemyLevel1Controller;
import bom_it.engine.Sprite;
import bom_it.game.App;
import bom_it.objects.Character.Character;
import bom_it.objects.Item.HeartItem;
import bom_it.objects.Item.Item;
import bom_it.objects.Character.Player;

import static bom_it.Enum.StatusCharacter.IMMORTAL;

public class EnemyLevel1 extends Enemy {
    public EnemyLevel1(int xInMap, int yInMap) {
        super(xInMap, yInMap);
        controller = new EnemyLevel1Controller(this);
    }

    @Override
    public void executeCollision() {
        super.executeCollision();
        for (Sprite sprite : App.gameWorld.sprites()) {
            if (this.checkCollision(sprite) && sprite instanceof Player && ((Player) sprite).getStatus() != IMMORTAL) {
                StatusCharacter.setStunned((Character) sprite);
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
