package bom_it.game;

import bom_it.engine.GameWorld;
import bom_it.objects.Character;

public class Enum {
    public enum DIRECTION {
        UP, RIGHT, DOWN, LEFT;

        public static DIRECTION oppositeDirect(DIRECTION direct) {
            DIRECTION oppositeDirect = UP;
            switch (direct) {
                case UP -> oppositeDirect = DOWN;
                case LEFT -> oppositeDirect = RIGHT;
                case RIGHT -> oppositeDirect = LEFT;
            }
            return oppositeDirect;
        }
    }

    public enum TYPE_SPRITE {
        BACKGROUND, WALL, BOX, PLAYER, ENEMY, EXPLODE, BOMB, ITEM, PORTAL
    }

    public enum TYPE_PLAYER {
        BLUE, PINK, YELLOW, LIGHTGREEN, PURPLE, BLACK, GREEN, RED
    }

    public enum TYPE_ITEM {
        BOMB_ITEM, HEART_ITEM, FLAME_ITEM, SPEED_ITEM
    }

    public enum TYPE_MAP {
        CLASSROOM, BASEBALL, FOREST, CANDY, FIELD, SHOP, TOY
    }

    public enum STATUS_CHARACTER {
        STUNNED, MOVE, IMMORTAL;

        public static void setStunned(Character character) {
            character.setStatus(STUNNED);
            character.setStunnedTime(Character.STUNNED_TIME);
        }

        public static void setMove(Character character) {
            character.setStatus(MOVE);
        }

        public static void setImmortal(Character character) {
            character.setStatus(IMMORTAL);
            character.setImmortalTime(Character.IMMORTAL_TIME);
        }
    }

    public enum STATUS_GAME {
        LOSS, WIN, PASS_LEVEL, PLAY;

        public static void setLoss(GameWorld game) {
            game.setStatus(LOSS.ordinal());
            game.setTimeLWP(game.getTimeLWP() - 1);
        }

        public static void setWin(GameWorld game) {
            game.setStatus(WIN.ordinal());
            game.setTimeLWP(game.getTimeLWP() - 1);
        }

        public static void setPassLevel(GameWorld game) {
            game.setStatus(PASS_LEVEL.ordinal());
            game.setTimeLWP(game.getTimeLWP() - 1);
        }

        public static void setPlay(GameWorld game) {
            game.setStatus(PLAY.ordinal());
            game.setTimeLWP(GameWorld.TIME_LWP);
        }
    }
}
