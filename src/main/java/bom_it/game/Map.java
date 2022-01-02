package bom_it.game;

import bom_it.Enum.Direction;
import bom_it.Enum.TypeMap;
import bom_it.Enum.TypeSprite;
import bom_it.controller.Pair;
import bom_it.engine.Sprite;
import bom_it.objects.Bomb;
import bom_it.objects.Item.FlameItem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static bom_it.Enum.Direction.*;
import static bom_it.Enum.TypeMap.CLASSROOM;
import static bom_it.Enum.TypeSprite.*;

public class Map {
    public static TypeMap type = CLASSROOM;
    private Images background;
    private Images[] wall;
    private Images[] box;

    public static final int[] dx = {-1, 1, 0, 0};
    public static final int[] dy = {0, 0, 1, -1};
    public static final int[] NUM_BOX = {30, 40, 50, 60, 70};

    private final Square[][] map;
    private final int width;
    private final int height;
    private int num_box;

    // constructor
    public Map(int width, int height, int level) {
        map = new Square[height][width];
        this.width = width;
        this.height = height;
        num_box = NUM_BOX[level - 1];
        setImage();
        randomMap();
    }

    // getter and setter
    public void addSprite(Sprite sprite) {
        map[sprite.getYInMap()][sprite.getXInMap()].add(sprite.getTypeSprite());
        if (sprite instanceof Bomb) {
            map[sprite.getYInMap()][sprite.getXInMap()].setPowerBomb(((Bomb) sprite).getPower());
        }
    }

    public void removeSprite(Sprite sprite) {
        map[sprite.getYInMap()][sprite.getXInMap()].remove(sprite.getTypeSprite());
        if (sprite instanceof Bomb) {
            map[sprite.getYInMap()][sprite.getXInMap()].setPowerBomb(((Bomb) sprite).getPower());
        }
    }

    public Square[][] getMap() {
        return map;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void setImage() {
        background = Images.map[type.ordinal()][0];
        wall = new Images[]{
                Images.map[type.ordinal()][1],
                Images.map[type.ordinal()][2]
        };
        box = new Images[]{
                Images.map[type.ordinal()][3],
                Images.map[type.ordinal()][4],
                Images.map[type.ordinal()][5],
        };
    }

    public Images getBackground() {
        return background;
    }

    public Images[] getWall() {
        return wall;
    }

    public Images[] getBox() {
        return box;
    }

    // create map
    public void randomMap() {
        // reset map
        for (int h = 0; h < height; ++h) {
            for (int w = 0; w < width; ++w) {
                map[h][w] = new Square();
            }
        }

        // create wall of border
        for (int h = 0; h < height; h++) {
            map[h][0].add(WALL);
            map[h][width - 1].add(WALL);
        }

        for (int w = 0; w < width; w++) {
            map[0][w].add(WALL);
            map[height - 1][w].add(WALL);
        }

        // create character
        map[1][1].add(PLAYER);
        map[height - 2][1].add(ENEMY);
        map[1][width - 2].add(ENEMY);
        map[height - 2][width - 2].add(ENEMY);

        map[8][1].add(WALL);
        map[1][8].add(WALL);
        map[13][8].add(WALL);
        map[6][15].add(WALL);

        // create Wall
        Random random = new Random();
        for (int h = 2; h < height - 2; h += 2) {
            for (int w = 2; w < width - 2; w += 2) {
                map[h][w].add(WALL);
            }
        }

        for (int h = 2; h < height - 2; h += 2) {
            int numWall = Math.abs(random.nextInt()) % (2);
            numWall += 2;
            if (numWall == 2) {
                int w1 = Math.abs(random.nextInt()) % (4);
                w1++;
                int w2 = Math.abs(random.nextInt()) % (6 - w1 - 1);
                w2 += w1 + 2;
                map[h][2 * w1 + 1].add(WALL);
                map[h][2 * w2 + 1].add(WALL);
            } else {
                int w1 = Math.abs(random.nextInt()) % (2);
                w1++;
                int w2;
                int w3;
                if (w1 == 2) {
                    w2 = 4;
                    w3 = 6;
                } else {
                    w2 = Math.abs(random.nextInt()) % (2);
                    w2 += w1 + 2;
                    w3 = Math.abs(random.nextInt()) % (6 - w2 - 1);
                    w3 += w2 + 2;
                }
                map[h][2 * w1 + 1].add(WALL);
                map[h][2 * w2 + 1].add(WALL);
                map[h][2 * w3 + 1].add(WALL);
            }
        }

        for (int w = 2; w < width - 2; w += 2) {
            ArrayList<Integer> vtWall = new ArrayList<>();
            for (int h = 3; h < height - 2; h += 2) {
                if (checkWall(h, w)) vtWall.add(h);
            }
            int h = vtWall.get(Math.abs(random.nextInt()) % (vtWall.size()));
            map[h][w].add(WALL);
        }

        while (num_box > 0) {
            int w = Math.abs(random.nextInt()) % width;
            int h = Math.abs(random.nextInt()) % height;

            Pair pair = new Pair(w, h);
            if (pair.equals(new Pair(1, 2)) || pair.equals(new Pair(2, 1))
                    || pair.equals(new Pair(width - 3, 1)) || pair.equals(new Pair(width - 2, 2))
                    || pair.equals(new Pair(1, height - 3)) || pair.equals(new Pair(2, height - 2))
                    || pair.equals(new Pair(width - 2, height - 3)) || pair.equals(new Pair(width - 3, height - 2))) {
                continue;
            }


            if (map[h][w].checkEmpty() && checkConsecutiveBox(h, w, false) && checkConsecutiveBox(h, w, true)) {
                map[h][w].add(BOX);
                num_box--;
            }
        }

        try {
            exportMapToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportMapToFile() throws IOException {
        FileOutputStream outputStream = new FileOutputStream("src/main/resources/bom_it/map/level-map.txt");

        String level = "level: " + App.gameWorld.getLevel() + "\n";
        outputStream.write(level.getBytes());

        for(int i = 0; i < height; ++ i){
            for(int j = 0; j < width; ++ j){
                if(map[i][j].getTypeSprite(WALL)){
                    outputStream.write('W');
                }
                else if(map[i][j].getTypeSprite(PLAYER)){
                    outputStream.write('P');
                }
                else if(map[i][j].getTypeSprite(ENEMY)){
                    outputStream.write('E');
                }
                else if(map[i][j].getTypeSprite(BOX)){
                    outputStream.write('B');
                }
                else{
                    outputStream.write(' ');
                }
            }
            outputStream.write('\n');
        }
        outputStream.close();
    }

    private void dfs(int h, int w, boolean[][] visited) {
        visited[h][w] = true;
        for (int i = 0; i < 4; ++i) {
            int _h = h + dy[i];
            int _w = w + dx[i];
            if (0 <= _h && _h < height && 0 <= _w && _w < width
                    && !visited[_h][_w] && !map[_h][_w].getTypeSprite(WALL)) {
                dfs(_h, _w, visited);
            }
        }
    }

    private boolean checkConsecutiveBox(int h, int w, boolean vertical) {
        int numBox = 0;
        int start = (vertical) ? h : w;

        for (int i = start - 1; ; --i) {
            if (vertical) {
                if (map[i][w].getTypeSprite(BOX)) {
                    numBox++;
                } else {
                    break;
                }
            } else {
                if (map[h][i].getTypeSprite(BOX)) {
                    numBox++;
                } else {
                    break;
                }
            }
        }

        for (int i = start + 1; ; ++i) {
            if (vertical) {
                if (map[i][w].getTypeSprite(BOX)) {
                    numBox++;
                } else {
                    break;
                }
            } else {
                if (map[h][i].getTypeSprite(BOX)) {
                    numBox++;
                } else {
                    break;
                }
            }
        }

        return numBox < 2;
    }

    private boolean checkWall(int h, int w) {
        boolean wall = true;
        map[h][w].add(WALL);
        boolean[][] visited = new boolean[height][width];
        dfs(1, 1, visited);
        for (int _h = 0; _h < height; ++_h) {
            for (int _w = 0; _w < width; ++_w) {
                if (!visited[_h][_w] && !map[_h][_w].getTypeSprite(WALL)) {
                    wall = false;
                    _h = 100;
                    break;
                }
            }
        }
        map[h][w].remove(WALL);
        return wall;
    }

    public Pair findEmptySquare() {
        Random random = new Random();
        int w, h;
        do {
            w = Math.abs(random.nextInt()) % width;
            h = Math.abs(random.nextInt()) % height;
        }
        while (!map[h][w].checkEmpty());
        return new Pair(w, h);
    }

    // check: this square exists in map ?
    public boolean checkSquareInMap(int x, int y) {
        return (0 <= x && x < width && 0 < y && y < height);
    }

    private boolean checkDangerInDirection(int x, int y, Direction direct) {
        for (int i = 1; i <= FlameItem.MAX_POWER_BOMB; ++i) {
            x += dx[direct.ordinal()];
            y += dy[direct.ordinal()];

            if (!checkSquareInMap(x, y)) {
                break;
            }

            if (!map[y][x].checkNotExist(new TypeSprite[]{BOX, BOMB, WALL})) {
                return (map[y][x].getPowerBomb() >= i);
            }
        }
        return false;
    }

    // check this square in map is dangerous ?
    public boolean checkDanger(int x, int y) {
        if (map[y][x].getTypeSprite(BOMB)) {
            return true;
        }

        return checkDangerInDirection(x, y, LEFT)
                || checkDangerInDirection(x, y, RIGHT)
                || checkDangerInDirection(x, y, UP)
                || checkDangerInDirection(x, y, DOWN);
    }
}


