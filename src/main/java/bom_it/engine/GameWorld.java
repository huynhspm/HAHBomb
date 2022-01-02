package bom_it.engine;

import bom_it.game.Map;
import bom_it.objects.Character.Player;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

import static bom_it.Enum.StatusGame.PLAY;

public abstract class GameWorld {
    public static int TIME_LWP = 2;
    private Timeline gameLoop;
    private final int framesPerSecond;

    // where contain all objects
    protected final Group sceneSprites;

    protected Map spritesMap;
    protected Player player;
    protected int level;
    protected boolean isNextLevel;
    protected final IntegerProperty timeLeft = new SimpleIntegerProperty(18000);
    protected AudioGame musicGame, soundEffectGame;
    protected final IntegerProperty status = new SimpleIntegerProperty(PLAY.ordinal());
    protected int timeLWP;

    // constructor
    public GameWorld(int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
        sceneSprites = new Group();
        isNextLevel = false;
        buildGameLoop();
    }

    // getter and setter
    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public Map getSpritesMap() {
        return spritesMap;
    }

    public Group getSceneSprites() {
        return sceneSprites;
    }

    public int getLevel() {
        return level;
    }

    public Player getPlayer() {
        return player;
    }

    public IntegerProperty timeLeftProperty() {
        return timeLeft;
    }

    public AudioGame getSoundEffectGame() {
        return soundEffectGame;
    }

    public AudioGame getMusicGame() {
        return musicGame;
    }

    public IntegerProperty statusProperty() {
        return status;
    }

    public int getTimeLWP() {
        return timeLWP;
    }

    public void setTimeLWP(int timeLWP) {
        this.timeLWP = timeLWP;
    }

    public void setStatus(int status) {
        this.status.setValue(status);
    }

    // create GameLoop
    private void buildGameLoop() {
        final Duration oneFrameAmt = Duration.millis(1000 / (float) framesPerSecond);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, actionEvent -> {
            nextLevel();
            updateSprites();
            checkCollision();
            showSprites();
            checkEndGame();
            checkNextLevel();
            if (timeLWP < TIME_LWP) {
                timeLWP--;
            }
        });

        gameLoop = new Timeline();
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.getKeyFrames().add(oneFrame);
    }

    // update for all sprites in group sceneSprites
    protected void updateSprites() {
        sprites().forEach(Sprite::update);
    }

    // solve collision  for all sprites in group sceneSprites
    protected void checkCollision() {
        sprites().forEach(Sprite::executeCollision);
    }

    //abstract class
    protected abstract void checkEndGame();

    protected abstract void nextLevel();

    protected abstract void showSprites();

    protected abstract void checkNextLevel();

    // control game loop
    public void begin() {
        gameLoop.play();
    }

    public void pause() {
        gameLoop.pause();
    }

    public void shutdown() {
        musicGame.pause();
        gameLoop.stop();
    }

    // solve sprite when spawn and destroy
    public void spawn(Sprite sprite) {
        sceneSprites.getChildren().add(sprite);
    }

    public void destroy(Sprite sprite) {
        sceneSprites.getChildren().remove(sprite);
    }

    // list of sprites in scene
    public List<Sprite> sprites() {
        return sceneSprites.getChildren().stream().map(image -> (Sprite) image).collect(Collectors.toList());
    }
}