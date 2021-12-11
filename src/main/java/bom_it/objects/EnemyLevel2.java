package bom_it.objects;

import bom_it.controller.EnemyController;
import bom_it.controller.EnemyLevel2Controller;

public class EnemyLevel2 extends Enemy{
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
