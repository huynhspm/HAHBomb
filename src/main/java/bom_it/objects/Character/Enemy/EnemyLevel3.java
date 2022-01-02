package bom_it.objects.Character.Enemy;

import bom_it.controller.EnemyLevel3Controller;

public class EnemyLevel3 extends Enemy {
    public EnemyLevel3(int xInMap, int yInMap) {
        super(xInMap, yInMap);
        controller = new EnemyLevel3Controller(this);
    }
}
