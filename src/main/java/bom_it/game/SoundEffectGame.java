package bom_it.game;

import bom_it.engine.AudioGame;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class SoundEffectGame extends AudioGame {
    public SoundEffectGame(String path) {
        super(path);
    }

    @Override
    public void play() {
        mediaPlayer = new MediaPlayer(Objects.requireNonNull(media));
        mediaPlayer.setVolume(0.3);
        super.play();
    }
}
