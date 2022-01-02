package bom_it.objects.Character.Enemy;

import bom_it.controller.EnemyLevel2Controller;
import bom_it.objects.Item.HeartItem;
import bom_it.objects.Item.Item;

public class EnemyLevel2 extends Enemy {
    public EnemyLevel2(int xInMap, int yInMap) {
        super(xInMap, yInMap);
        controller = new EnemyLevel2Controller(this);
    }

    @Override
    protected void collisionBox() {
    }

    @Override
    protected void collisionItem(Item item) {
        if(!(item instanceof HeartItem)){
            item.powerUp(this);
        }
        item.handleDeath();
    }
}
