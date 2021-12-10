package bom_it.controller;

import bom_it.game.App;
import bom_it.game.Enum;
import bom_it.game.Map;
import bom_it.objects.Enemy;

import java.util.*;

import static bom_it.game.Enum.DIRECTION.*;
import static bom_it.game.Enum.TYPE_SPRITE.*;

public abstract class EnemyController {
    // this enemy to control
    protected final Enemy enemy;

    // map of game
    protected Map spritesMap;

    protected Pair nextStep;

    protected Enum.DIRECTION direction;

    public EnemyController(Enemy enemy) {
        this.enemy = enemy;
        // way to move
        nextStep = new Pair(enemy.getXInMap(), enemy.getYInMap());
        direction = UP;
    }

    //find random way
    protected void findRandomWay(boolean acrossBox) {
        boolean exitWay = false;
        boolean[] used = new boolean[Enum.DIRECTION.values().length];
        for (int i = 0; i < 3; ++i) {
            Enum.DIRECTION direct;
            while (true) {
                direct = Enum.DIRECTION.values()[Math.abs(new Random().nextInt() % 4)];
                if (direct != Enum.DIRECTION.oppositeDirect(direction) && !used[direct.ordinal()]) {
                    used[direct.ordinal()] = true;
                    break;
                }
            }

            nextStep = new Pair(enemy.getXInMap() + Map.dx[direct.ordinal()], enemy.getYInMap() + Map.dy[direct.ordinal()]);
            if (direct != Enum.DIRECTION.oppositeDirect(direction)) {
                if ((!acrossBox && spritesMap.getMap()[nextStep.getY()][nextStep.getX()].checkNotExist(new Enum.TYPE_SPRITE[]{BOMB, BOX, EXPLODE, WALL}))
                        || (acrossBox && spritesMap.getMap()[nextStep.getY()][nextStep.getX()].checkNotExist(new Enum.TYPE_SPRITE[]{BOMB, EXPLODE, WALL}))) {
                    direction = direct;
                    exitWay = true;
                    break;
                }
            }
        }

        if (!exitWay) {
            direction = Enum.DIRECTION.oppositeDirect(direction);
            nextStep = new Pair(enemy.getXInMap() + Map.dx[direction.ordinal()], enemy.getYInMap() + Map.dy[direction.ordinal()]);
            if (spritesMap.getMap()[nextStep.getY()][nextStep.getX()].getTypeSprite(BOMB)) {
                nextStep = new Pair(enemy.getXInMap(), enemy.getYInMap());
            }
        }
    }

    // find the shortest way from this enemy to player.
    protected void findShortestWay() {
        int[][] dis = new int[spritesMap.getHeight()][spritesMap.getWidth()];
        Pair[][] last = new Pair[spritesMap.getHeight()][spritesMap.getWidth()];
        for (int i = 0; i < spritesMap.getHeight(); i++) {
            for (int j = 0; j < spritesMap.getWidth(); j++) {
                dis[i][j] = Integer.MAX_VALUE;
            }
        }

        Deque<Pair> way = new ArrayDeque<>();
        way.addLast(new Pair(enemy.getXInMap(), enemy.getYInMap()));
        dis[enemy.getYInMap()][enemy.getXInMap()] = 0;

        while (!way.isEmpty()) {
            Pair cur = way.pop();
            if (spritesMap.getMap()[cur.getY()][cur.getX()].getTypeSprite(PLAYER)) {
                while (!cur.equals(new Pair(enemy.getXInMap(), enemy.getYInMap()))) {
                    nextStep = cur;
                    cur = last[cur.getY()][cur.getX()];
                }
                break;
            }

            for (Enum.DIRECTION direct : Enum.DIRECTION.values()) {
                int X = cur.getX() + Map.dx[direct.ordinal()];
                int Y = cur.getY() + Map.dy[direct.ordinal()];
                if (spritesMap.checkSquareInMap(X, Y) && dis[Y][X] == Integer.MAX_VALUE) {
                    dis[Y][X] = dis[cur.getY()][cur.getX()] + 1;
                    if (dis[Y][X] <= 1) {
                        if ((!spritesMap.checkDanger(X, Y) && spritesMap.getMap()[Y][X].checkNotExist(new Enum.TYPE_SPRITE[]{EXPLODE, WALL, BOX}))) {
                            way.addLast(new Pair(X, Y));
                            last[Y][X] = new Pair(cur.getX(), cur.getY());
                        }
                    } else {
                        if (!spritesMap.getMap()[Y][X].getTypeSprite(WALL)) {
                            way.addLast(new Pair(X, Y));
                            last[Y][X] = new Pair(cur.getX(), cur.getY());
                        }
                    }
                }
            }
        }
    }

    // find way to avoid bomb if this enemy is threatened
    protected boolean avoidBomb(int safe) {
        boolean[][] visited = new boolean[spritesMap.getHeight()][spritesMap.getWidth()];
        Pair[][] last = new Pair[spritesMap.getHeight()][spritesMap.getWidth()];
        Deque<Pair> way = new ArrayDeque<>();
        way.addLast(new Pair(enemy.getXInMap(), enemy.getYInMap()));

        while (!way.isEmpty()) {
            Pair cur = way.pop();
            boolean existWay = true;
            if (safe >= 1) {
                if ((cur.getX() == enemy.getXInMap() && Math.abs(cur.getY() - enemy.getYInMap()) <= enemy.powerBombProperty().getValue())
                        || (cur.getY() == enemy.getYInMap() && Math.abs(cur.getX() - enemy.getXInMap()) <= enemy.powerBombProperty().getValue())) {
                    existWay = false;
                }
            }

            if (spritesMap.checkDanger(cur.getX(), cur.getY())) {
                existWay = false;
            }

            if (existWay) {
                while (!cur.equals(new Pair(enemy.getXInMap(), enemy.getYInMap()))) {
                    nextStep = cur;
                    cur = last[cur.getY()][cur.getX()];
                }
                return true;
            }

            visited[cur.getY()][cur.getX()] = true;
            for (int i = 0; i < Enum.DIRECTION.values().length; ++i) {
                int X = cur.getX() + Map.dx[i];
                int Y = cur.getY() + Map.dy[i];
                if (spritesMap.checkSquareInMap(X, Y) && !visited[Y][X]
                        && spritesMap.getMap()[Y][X].checkNotExist(new Enum.TYPE_SPRITE[]{BOMB, BOX, EXPLODE, WALL})) {
                    if ((safe >= 1 && spritesMap.checkDanger(X, Y)) ||
                            (safe >= 2 && spritesMap.getMap()[Y][X].getTypeSprite(PLAYER))) {
                        continue;
                    }

                    way.addLast(new Pair(X, Y));
                    last[Y][X] = new Pair(cur.getX(), cur.getY());
                }
            }
        }
        return false;
    }

    private void storeBomb(int safe) {
        // player and enemy in a square, player set bomb -> enemy not set bomb
        if (spritesMap.getMap()[enemy.getYInMap()][enemy.getXInMap()].getTypeSprite(BOMB)) {
            return;
        }

        if (enemy.numBombProperty().getValue() > 0 && avoidBomb(safe)) {
            enemy.storeBomb();
        }
    }

    protected void checkStoreBomb(boolean destroyPlayer, Boolean destroyBox, int safe) {
        spritesMap = App.gameWorld.getSpritesMap();
        if (spritesMap.getMap()[enemy.getYInMap()][enemy.getXInMap()].getTypeSprite(BOMB)) {
            return;
        }

        boolean exist = false;
        for (Enum.DIRECTION direct : Enum.DIRECTION.values()) {
            int x = enemy.getXInMap() + Map.dx[direct.ordinal()];
            int y = enemy.getYInMap() + Map.dy[direct.ordinal()];
            if ((destroyPlayer && spritesMap.getMap()[y][x].getTypeSprite(PLAYER))
                    || destroyBox && spritesMap.getMap()[y][x].getTypeSprite(BOX)) {
                exist = true;
                break;
            }
        }

        if (exist) {
            storeBomb(safe);
        }
    }

    protected void setStatic() {
        enemy.setMoveLeft(false);
        enemy.setMoveUp(false);
        enemy.setMoveDown(false);
        enemy.setMoveRight(false);
    }

    protected void setMove() {
        setStatic();
        if (enemy.getYInMap() < nextStep.getY()) {
            enemy.setMoveDown(true);
        } else if (enemy.getYInMap() > nextStep.getY()) {
            enemy.setMoveUp(true);
        } else if (enemy.getXInMap() < nextStep.getX()) {
            enemy.setMoveRight(true);
        } else if (enemy.getXInMap() > nextStep.getX()) {
            enemy.setMoveLeft(true);
        }
    }

    // control this enemy
    public void control() {
        spritesMap = App.gameWorld.getSpritesMap();
    }
}