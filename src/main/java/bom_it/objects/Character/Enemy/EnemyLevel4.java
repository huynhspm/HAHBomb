package bom_it.objects.Character.Enemy;

import bom_it.controller.EnemyLevel4Controller;

public class EnemyLevel4 extends Enemy {
    public EnemyLevel4(int xInMap, int yInMap) {
        super(xInMap, yInMap);
        controller = new EnemyLevel4Controller(this);
    }
}
