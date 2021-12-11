package bom_it.objects;

import bom_it.engine.Images;
import bom_it.engine.Sprite;
import bom_it.game.App;
import bom_it.game.Enum;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

import java.util.Date;

import static bom_it.game.Enum.DIRECTION.*;
import static bom_it.game.Enum.STATUS_CHARACTER.*;
import static bom_it.game.Enum.TYPE_SPRITE.*;

public abstract class Character extends Sprite {
    public static final double IMMORTAL_TIME = 3;
    public static final double STUNNED_TIME = 2;
    private final Images[][] imageCharacter;

    private final double VELOCITY = 0.25;

    private final Bomb[] onBomb;
    protected final IntegerProperty lives = new SimpleIntegerProperty(1);
    protected final IntegerProperty numBomb = new SimpleIntegerProperty(1);
    protected final IntegerProperty powerBomb = new SimpleIntegerProperty(1);
    protected final IntegerProperty powerSpeed = new SimpleIntegerProperty(4);

    private boolean moveUp = false;
    private boolean moveLeft = false;
    private boolean moveDown = false;
    private boolean moveRight = false;

    private double immortalTime;
    private double stunnedTime;
    protected Enum.STATUS_CHARACTER status = MOVE;

    // getter and setter
    protected void increaseNumBomb() {
        numBomb.setValue(numBomb.getValue() + 1);
    }

    protected Bomb[] getOnBomb() {
        return onBomb;
    }

    protected void setOnBomb(Bomb[] onBomb, Bomb bomb) {
        if (onBomb[0] == null) {
            onBomb[0] = bomb;
        } else if (onBomb[0] != bomb) {
            onBomb[1] = bomb;
        }
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public IntegerProperty livesProperty() {
        return lives;
    }

    public IntegerProperty numBombProperty() {
        return numBomb;
    }

    public IntegerProperty powerBombProperty() {
        return powerBomb;
    }

    public IntegerProperty powerSpeedProperty() {
        return powerSpeed;
    }

    public int getLives() {
        return lives.get();
    }

    public int getNumBomb() {
        return numBomb.get();
    }

    public int getPowerBomb() {
        return powerBomb.get();
    }

    public int getPowerSpeed() {
        return powerSpeed.get();
    }

    public Image getImageInfo() {
        return imageCharacter[DOWN.ordinal()][0].getImage();
    }

    public void setStunnedTime(double stunnedTime) {
        this.stunnedTime = stunnedTime;
    }

    public void setImmortalTime(double immortalTime) {
        this.immortalTime = immortalTime;
    }

    public void setStatus(Enum.STATUS_CHARACTER status) {
        this.status = status;
    }

    // Constructor
    public Character(Image image, int xInMap, int yInMap, Enum.TYPE_SPRITE typeSprite, Images[][] imageCharacter) {
        super(image, xInMap, yInMap, typeSprite);
        onBomb = new Bomb[]{null, null};
        this.imageCharacter = imageCharacter;
    }

    // Move
    private void move(double dx, double dy) {
        collisionBound.setX(collisionBound.getX() + dx);
        collisionBound.setY(collisionBound.getY() + dy);
        setTranslateX(getTranslateX() + dx);
        setTranslateY(getTranslateY() + dy);
        updateCoordinateInMap();
    }

    private void updateCoordinateInMap() {
        int x = (int) collisionBound.getX() / SIZE;
        int y = (int) collisionBound.getY() / SIZE;

        if (checkCovered(x, y)) {
            xInMap = x;
            yInMap = y;
        }
    }

    // Check entirety stand in this square
    public boolean checkCovered(int xInMap, int yInMap) {
        return (xInMap * SIZE < collisionBound.getX()
                && collisionBound.getX() + collisionBound.getWidth() < (xInMap + 1) * SIZE
                && yInMap * SIZE < collisionBound.getY()
                && collisionBound.getY() + collisionBound.getHeight() < (yInMap + 1) * SIZE);
    }

    @Override
    public void update() {
        int index = (int) (new Date().getTime() / 100);
        if (status == IMMORTAL) {
            immortalTime -= 1.0 / App.gameWorld.getFramesPerSecond();
            if (immortalTime < 0) {
                Enum.STATUS_CHARACTER.setMove(this);
            }
            setVisible(index % 2 + 2 == 1);
        } else {
            setVisible(true);
        }

        if (status == STUNNED) {
            stunnedTime -= 1.0 / App.gameWorld.getFramesPerSecond();
            if (stunnedTime <= 0) {
                Enum.STATUS_CHARACTER.setMove(this);
            }
            return;
        }

        if (moveUp || moveLeft || moveDown || moveRight) {
            App.gameWorld.getSpritesMap().removeSprite(this);
        }


        if (moveUp) {
            setImage(imageCharacter[UP.ordinal()][index % 3 + 2].getImage());
            move(0, -VELOCITY * powerSpeed.getValue());
        }

        if (moveLeft) {
            setImage(imageCharacter[LEFT.ordinal()][index % 3 + 2].getImage());
            move(-VELOCITY * powerSpeed.getValue(), 0);
        }

        if (moveDown) {
            setImage(imageCharacter[DOWN.ordinal()][index % 3 + 2].getImage());
            move(0, VELOCITY * powerSpeed.getValue());
        }

        if (moveRight) {
            setImage(imageCharacter[RIGHT.ordinal()][index % 3 + 2].getImage());
            move(VELOCITY * powerSpeed.getValue(), 0);
        }

        if (moveUp || moveLeft || moveDown || moveRight) {
            App.gameWorld.getSpritesMap().addSprite(this);
        }
    }

    @Override
    public void executeCollision() {
        boolean[] collisionBomb = new boolean[2];

        for (Sprite sprite : App.gameWorld.sprites()) {
            if (this.checkCollision(sprite)) {
                if (sprite instanceof Wall) {
                    collisionWall();
                } else if (sprite instanceof Box) {
                    collisionBox();
                } else if (sprite instanceof Explode && status != IMMORTAL) {
                    collisionExplode();
                } else if (sprite instanceof Bomb) {
                    if (sprite != onBomb[0] && sprite != onBomb[1]) {
                        backStep();
                    } else {
                        if (sprite == onBomb[0]) {
                            collisionBomb[0] = true;
                        } else {
                            collisionBomb[1] = true;
                        }
                    }
                } else if (sprite instanceof Item) {
                    collisionItem((Item) sprite);
                }
            }
        }

        if (onBomb[0] != null && !collisionBomb[0]) {
            onBomb[0] = null;
        }

        if (onBomb[1] != null && !collisionBomb[1]) {
            onBomb[1] = null;
        }

        if (onBomb[1] != null && onBomb[0] == null) {
            onBomb[0] = onBomb[1];
            onBomb[1] = null;
        }
    }

    protected void collisionItem(Item item) {
        item.powerUp(this);
        item.handleDeath();
    }

    protected void collisionExplode() {
        handleDeath();
    }

    protected void collisionWall() {
        backStep();
    }

    protected void collisionBox() {
        backStep();
    }

    @Override
    public void handleDeath() {
        lives.setValue(lives.getValue() - 1);
        Enum.STATUS_CHARACTER.setImmortal(this);
        if (lives.getValue() <= 0) {
            super.handleDeath();
        }
    }

    private int getXSetBomb() {
        return (int) (collisionBound.getX() + collisionBound.getWidth() / 2) / SIZE;
    }

    private int getYSetBomb() {
        return (int) (collisionBound.getY() + collisionBound.getHeight() / 2) / SIZE;
    }

    // set up bomb
    public void storeBomb() {
        if (numBomb.getValue() > 0) {
            numBomb.setValue(numBomb.getValue() - 1);
            int x = getXSetBomb();
            int y = getYSetBomb();
            if (!App.gameWorld.getSpritesMap().getMap()[y][x].getTypeSprite(BOMB)) {
                App.gameWorld.spawn(new Bomb(x, y, this));
            }
        }
    }

    // back the previous step.
    protected void backStep() {
        if (moveUp) {
            move(0, VELOCITY * powerSpeed.getValue());
        }

        if (moveLeft) {
            move(VELOCITY * powerSpeed.getValue(), 0);
        }

        if (moveDown) {
            move(0, -VELOCITY * powerSpeed.getValue());
        }

        if (moveRight) {
            move(-VELOCITY * powerSpeed.getValue(), 0);
        }
    }
}
