package bom_it.game;

import bom_it.engine.GameWorld;
import bom_it.engine.Sprite;
import bom_it.objects.*;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static bom_it.game.App.setRoot;
import static bom_it.game.Enum.STATUS_GAME.*;
import static bom_it.game.Enum.TYPE_SPRITE.*;

public class TheGame extends GameWorld {
    public static final int HEIGHT = 600;
    public static final int WIDTH = 680;
    public static final int SIZE_A_SQUARE = 40;
    private final int MAX_LEVEL = 5;

    public TheGame(String title, int framesPerSecond) {
        super(framesPerSecond);
        musicGame = new MusicGame("src/main/resources/bom_it/Music/music.mp3");
        soundEffectGame = new SoundEffectGame("src/main/resources/bom_it/Music/sound_effect.wav");
        musicGame.play();
    }

    @Override
    protected void showSprites() {
        List<Sprite> sprites = sprites().stream().filter(sprite -> !(sprite instanceof Background)).collect(Collectors.toList());
        sceneSprites.getChildren().removeAll(sprites);
        Collections.sort(sprites);
        sceneSprites.getChildren().addAll(sprites);
    }

    @Override
    protected void nextLevel() {
        if (isNextLevel) {
            sceneSprites.getChildren().clear();
            isNextLevel = false;
            level++;
            Enum.STATUS_GAME.setPlay(this);
            timeLeft.setValue(18000);
            generateMap();
            setRoot("GameSurface");
        }
    }

    @Override
    protected void checkEndGame() {
        if (status.getValue() == LOSS.ordinal() && timeLWP < 0) {
            shutdown();
            sleep();
            App.setRoot("Menu");
            return;
        }

        if ((player != null && player.livesProperty().getValue() <= 0) || timeLeft.getValue() < 0) {
            Enum.STATUS_GAME.setLoss(this);
        }
    }

    public boolean executeWin() {
        if (status.getValue() == WIN.ordinal() && timeLWP < 0) {
            shutdown();
            sleep();
            App.setRoot("Menu");
            return true;
        }

        if (level >= MAX_LEVEL) {
            Enum.STATUS_GAME.setWin(this);
            return true;
        }
        return false;
    }

    @Override
    protected void checkNextLevel() {
        if (status.getValue() == WIN.ordinal()) {
            executeWin();
            return;
        }

        if (status.getValue() == PASS_LEVEL.ordinal() && timeLWP < 0) {
            sleep();
            isNextLevel = true;
            pause();
            setRoot("ChooseMap");
            return;
        }

        if (spritesMap == null || spritesMap.getMap()[player.getYInMap()][player.getXInMap()].getTypeSprite(PORTAL)) {
            if (!executeWin()) {
                Enum.STATUS_GAME.setPassLevel(this);
            }
        }
    }

    private void generateMap() {
        spritesMap = new Map(WIDTH / SIZE_A_SQUARE, HEIGHT / SIZE_A_SQUARE, level);
        for (int h = 0; h < HEIGHT / SIZE_A_SQUARE; ++h) {
            for (int w = 0; w < WIDTH / SIZE_A_SQUARE; ++w) {
                spawn(new Background(w, h));
            }
        }

        for (int h = 0; h < HEIGHT / SIZE_A_SQUARE; ++h) {
            for (int w = 0; w < WIDTH / SIZE_A_SQUARE; ++w) {
                if (spritesMap.getMap()[h][w].getTypeSprite(BOX)) {
                    int index = (w + h) % 3;
                    spawn(new Box(w, h, index));
                } else if (spritesMap.getMap()[h][w].getTypeSprite(WALL)) {
                    int index;
                    if ((h == 0 || w == 0 || h + 1 == HEIGHT / SIZE_A_SQUARE || w + 1 == WIDTH / SIZE_A_SQUARE)) {
                        index = 0;
                    } else {
                        int random = Math.abs(new Random().nextInt()) % 10;
                        index = (random < 3) ? 0 : 1;
                    }
                    spawn(new Wall(w, h, index));
                } else if (spritesMap.getMap()[h][w].getTypeSprite(ENEMY)) {
                    spawn(new Enemy(w, h));
                } else if (spritesMap.getMap()[h][w].getTypeSprite(PLAYER)) {
                    player = new Player(w, h);
                    spawn(player);
                    setInput();
                }
            }
        }
    }

    @Override
    public void spawn(Sprite sprite) {
        super.spawn(sprite);
        spritesMap.addSprite(sprite);
    }

    @Override
    public void destroy(Sprite sprite) {
        super.destroy(sprite);
        spritesMap.removeSprite(sprite);
    }

    private void setInput() {
        EventHandler<KeyEvent> playerMovePress = keyEvent -> {
            if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) {
                player.setMoveLeft(true);
            } else if (keyEvent.getCode() == KeyCode.S || keyEvent.getCode() == KeyCode.DOWN) {
                player.setMoveDown(true);
            } else if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) {
                player.setMoveRight(true);
            } else if (keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.UP) {
                player.setMoveUp(true);
            }
            if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
                player.storeBomb();
            }
        };
        App.scene.setOnKeyPressed(playerMovePress);

        EventHandler<KeyEvent> playerMoveRelease = keyEvent -> {
            if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) {
                player.setMoveLeft(false);
            } else if (keyEvent.getCode() == KeyCode.S || keyEvent.getCode() == KeyCode.DOWN) {
                player.setMoveDown(false);
            } else if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) {
                player.setMoveRight(false);
            } else if (keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.UP) {
                player.setMoveUp(false);
            }
        };
        App.scene.setOnKeyReleased(playerMoveRelease);
    }

    private void sleep() {
        if (spritesMap != null) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}