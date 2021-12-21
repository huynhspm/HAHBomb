package bom_it.controller;

import bom_it.engine.Images;
import bom_it.game.App;
import bom_it.game.TheGame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class EndGameController implements Initializable {
    private final Images[] buttonNewGame = Images.buttonNewGame;

    @FXML
    ImageView imageNewGame;

    @FXML
    private void enterImageNewGame() {
        imageNewGame.setImage(buttonNewGame[1].getImage());
    }

    @FXML
    private void exitImageNewGame() {
        imageNewGame.setImage(buttonNewGame[0].getImage());
    }

    @FXML
    private void clickImageNewGame() {
        App.gameWorld.shutdown();
        App.setRoot("Menu");
    }

    @FXML
    private void clickButtonReplay(){
        TheGame.init();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageNewGame.setImage(buttonNewGame[0].getImage());
    }
}
