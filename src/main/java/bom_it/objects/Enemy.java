package bom_it.objects;

import bom_it.controller.*;
import bom_it.engine.Images;
import bom_it.engine.Sprite;
import bom_it.game.App;
import bom_it.game.Enum;

import java.util.List;
import java.util.stream.Collectors;

import static bom_it.game.Enum.STATUS_CHARACTER.IMMORTAL;
import static bom_it.game.Enum.TYPE_SPRITE.ENEMY;

public class Enemy extends Character {
    private EnemyController controller;

    public Enemy(int xInMap, int yInMap) {
        super(Images.enemy_down[App.gameWorld.getLevel() - 1][0].getImage(), xInMap, yInMap, ENEMY,
                Images.enemy_up[App.gameWorld.getLevel() - 1], Images.enemy_down[App.gameWorld.getLevel() - 1],
                Images.enemy_right[App.gameWorld.getLevel() - 1], Images.enemy_left[App.gameWorld.getLevel() - 1]);
        switch (App.gameWorld.getLevel()) {
            case 1 -> controller = new EnemyLevel1Controller(this);
            case 2 -> controller = new EnemyLevel2Controller(this);
            case 3 -> controller = new EnemyLevel3Controller(this);
            case 4 -> controller = new EnemyLevel4Controller(this);
            case 5 -> controller = new EnemyLevel5Controller(this);
        }
    }

    @Override
    public void update() {
        controller.control();
        super.update();
    }

    @Override
    protected void collisionItem(Item item) {
        switch (item.getType()) {
            case BOMB_ITEM -> increaseNumBomb();
            case FLAME_ITEM -> powerBomb.setValue(Math.min(powerBomb.getValue() + 1, Item.MAX_POWER_BOM));
            case SPEED_ITEM -> powerSpeed.setValue(Math.min(powerSpeed.getValue() + 1, Item.MAX_POWER_SPEED));
            case HEART_ITEM -> {
                if (App.gameWorld.getLevel() > 2) lives.setValue(lives.getValue() + 1);
            }
        }
    }

    @Override
    public void executeCollision() {
        super.executeCollision();
        if (App.gameWorld.getLevel() == 1) {
            for (Sprite sprite : App.gameWorld.sprites()) {
                if (this.checkCollision(sprite) && sprite instanceof Player && ((Player) sprite).status != IMMORTAL) {
                    Enum.STATUS_CHARACTER.setStunned((Character) sprite);
                }
            }
        }
    }

    @Override
    protected void collisionExplode() {
        handleDeath();
    }

    @Override
    protected void collisionWall() {
        backStep();
    }

    @Override
    protected void collisionBox() {
        switch (App.gameWorld.getLevel()) {
            case 1, 3, 4, 5 -> backStep();
        }
    }

    @Override
    public void handleDeath() {
        super.handleDeath();
        List<Sprite> enemies = App.gameWorld.sprites().stream().filter(sprite -> sprite instanceof Enemy).collect(Collectors.toList());
        if (enemies.isEmpty()) {
            Pair position = App.gameWorld.getSpritesMap().findEmptySquare();
            App.gameWorld.spawn(new Portal(position.getX(), position.getY()));
        }
    }
}
