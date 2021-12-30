package bom_it.Enum;

import bom_it.engine.GameWorld;

public enum StatusGame {
    LOSS, WIN, PASS_LEVEL, PLAY, PAUSE;

    public static void setPause(GameWorld game) {
        game.setStatus(PAUSE.ordinal());
    }

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
