package bom_it.objects.Character.Enemy;

import bom_it.controller.EnemyLevel5Controller;

public class EnemyLevel5 extends Enemy {
    public EnemyLevel5(int xInMap, int yInMap) {
        super(xInMap, yInMap);
        controller = new EnemyLevel5Controller(this);
    }
}
