package bom_it.controller;

import bom_it.objects.Enemy;

import static bom_it.Enum.TypeSprite.*;

public class EnemyLevel2Controller extends EnemyController {

    public EnemyLevel2Controller(Enemy enemy) {
        super(enemy);
    }

    @Override
    public void control() {
        super.control();
        if (nextStep.equals(enemy.getXInMap(), enemy.getYInMap()) || (enemy.checkCovered(enemy.getXInMap(), enemy.getYInMap())
                && spritesMap.getMap()[nextStep.getY()][nextStep.getX()].getTypeSprite(BOMB))) {
            checkStoreBomb(true, false, 0);
            if (spritesMap.checkDanger(enemy.getXInMap(), enemy.getYInMap())) {
                avoidBomb(0);
            } else {
                findRandomWay(true);
            }
            setMove();
        }
    }
}
