package bom_it.engine;

import bom_it.Enum.TypeSprite;
import bom_it.game.App;
import bom_it.game.TheGame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import static bom_it.Enum.TypeSprite.*;

public class Sprite extends ImageView implements Comparable<Sprite> {
    protected final int SIZE = TheGame.SIZE_A_SQUARE;

    // type of sprite.
    private final TypeSprite typeSprite;

    protected int xInMap, yInMap;

    // Bound to check collision.
    public final Rectangle collisionBound;

    // constructor
    protected Sprite(Image image, int xInMap, int yInMap, TypeSprite typeSprite) {
        this.xInMap = xInMap;
        this.yInMap = yInMap;
        double coordinateX = xInMap * SIZE;
        double coordinateY = yInMap * SIZE;

        if (typeSprite == ENEMY || typeSprite == PLAYER) {
            double approximateUp = 14;
            double approximateLeft = 10;
            double approximateDown = 10;
            double approximateRight = 10;
            collisionBound = new Rectangle(coordinateX + approximateLeft, coordinateY + approximateUp,
                    SIZE - approximateLeft - approximateRight, SIZE - approximateUp - approximateDown);
        } else {
            collisionBound = new Rectangle(coordinateX, coordinateY, SIZE, SIZE);
        }

        this.typeSprite = typeSprite;
        setImage(image);
        setTranslateX(coordinateX + SIZE - image.getWidth());
        setTranslateY(coordinateY + SIZE - image.getHeight());
    }

    // getter
    public TypeSprite getTypeSprite() {
        return typeSprite;
    }

    // Check collision of two sprites
    public boolean checkCollision(Sprite other) {
//        return collisionBound.getBoundsInLocal().intersects(other.collisionBound.getBoundsInLocal());
        return Math.max(collisionBound.getX(), other.collisionBound.getX())
                < Math.min(collisionBound.getX() + collisionBound.getWidth(), other.collisionBound.getX() + other.collisionBound.getWidth())
                && Math.max(collisionBound.getY(), other.collisionBound.getY())
                < Math.min(collisionBound.getY() + collisionBound.getHeight(), other.collisionBound.getY() + other.collisionBound.getHeight());
    }

    public int getXInMap() {
        return xInMap;
    }

    public int getYInMap() {
        return yInMap;
    }

    public void update() {
    }

    public void executeCollision() {
    }

    public void handleDeath() {
        App.gameWorld.destroy(this);
    }

    @Override
    public int compareTo(Sprite other) {
        return (getYInMap() < other.getYInMap()) ? -1 : (getYInMap() > other.getYInMap()) ? 1
                : (typeSprite == PLAYER) ? 1 : (other.typeSprite == PLAYER) ? -1
                : (typeSprite == ENEMY) ? 1 : (other.typeSprite == ENEMY) ? -1 : 0;
    }
}

