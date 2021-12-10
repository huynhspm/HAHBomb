package bom_it.game;

import bom_it.engine.AudioGame;
import javafx.scene.media.MediaPlayer;

public class MusicGame extends AudioGame {
    public MusicGame(String path) {
        super(path);
    }

    @Override
    public void play() {
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.2);
        super.play();
    }
}
