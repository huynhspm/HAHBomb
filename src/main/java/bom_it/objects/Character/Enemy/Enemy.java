package bom_it.objects.Character.Enemy;

import bom_it.controller.EnemyController;
import bom_it.controller.Pair;
import bom_it.game.Images;
import bom_it.engine.Sprite;
import bom_it.game.App;
import bom_it.objects.*;
import bom_it.objects.Character.Character;

import java.util.List;
import java.util.stream.Collectors;

import static bom_it.Enum.Direction.DOWN;
import static bom_it.Enum.TypeSprite.ENEMY;

public abstract class Enemy extends Character {
    protected EnemyController controller;

    public static void createEnemy(int xInMap, int yInMap) {
        switch (App.gameWorld.getLevel()) {
            case 1 -> App.gameWorld.spawn(new EnemyLevel1(xInMap, yInMap));
            case 2 -> App.gameWorld.spawn(new EnemyLevel2(xInMap, yInMap));
            case 3 -> App.gameWorld.spawn(new EnemyLevel3(xInMap, yInMap));
            case 4 -> App.gameWorld.spawn(new EnemyLevel4(xInMap, yInMap));
            case 5 -> App.gameWorld.spawn(new EnemyLevel5(xInMap, yInMap));
        }
    }

    public Enemy(int xInMap, int yInMap) {
        super(Images.enemy[App.gameWorld.getLevel() - 1][DOWN.ordinal()][0].getImage(),
                xInMap, yInMap, ENEMY, Images.enemy[App.gameWorld.getLevel() - 1]);

    }

    @Override
    public void update() {
        controller.control();
        super.update();
    }


    @Override
    public void handleDeath() {
        super.handleDeath();
        List<Sprite> enemies = App.gameWorld.sprites().stream().filter(sprite -> sprite instanceof Enemy).collect(Collectors.toList());
        if (enemies.isEmpty()) {
            Pair position = App.gameWorld.getSpritesMap().findEmptySquare();
            Portal.createPortal(position.getX(), position.getY());
        }
    }
}
