package bom_it.controller;

import bom_it.engine.Images;
import bom_it.game.App;
import bom_it.game.Enum;
import bom_it.game.Map;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooseMapController implements Initializable {
    private final Images[] listImageMap = Images.chooseMap;
    private final Images[] buttonContinue = Images.buttonContinue;
    private final Images[] buttonNewGame = Images.buttonNewGame;
    private final Images[] buttonBehind = Images.buttonBehind;
    private final Images[] buttonFront = Images.buttonFront;

    @FXML
    private ImageView imageMap, imageContinue, imageNewGame, imageBehind, imageFront;

    @FXML
    private void enterImageBehind() {
        imageBehind.setImage(buttonBehind[1].getImage());
    }

    @FXML
    private void exitImageBehind() {
        imageBehind.setImage(buttonBehind[0].getImage());
    }

    @FXML
    private void clickImageBehind() {
        Map.type = Enum.TYPE_MAP.values()[(Map.type.ordinal() + 1) % Enum.TYPE_MAP.values().length];
        imageMap.setImage(listImageMap[Map.type.ordinal()].getImage());
    }

    @FXML
    private void enterImageFront() {
        imageFront.setImage(buttonFront[1].getImage());
    }

    @FXML
    private void exitImageFront() {
        imageFront.setImage(buttonFront[0].getImage());
    }

    @FXML
    private void clickImageFront() {
        Map.type = Enum.TYPE_MAP.values()[(Map.type.ordinal() - 1 + Enum.TYPE_MAP.values().length) % Enum.TYPE_MAP.values().length];
        imageMap.setImage(listImageMap[Map.type.ordinal()].getImage());
    }

    @FXML
    private void enterImageContinue() {
        imageContinue.setImage(buttonContinue[1].getImage());
    }

    @FXML
    private void exitImageContinue() {
        imageContinue.setImage(buttonContinue[0].getImage());
    }

    @FXML
    private void clickImageContinue() {
        App.setRoot("ChooseCharacter");
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageMap.setImage(listImageMap[Map.type.ordinal()].getImage());
        imageContinue.setImage(buttonContinue[0].getImage());
        imageNewGame.setImage(buttonNewGame[0].getImage());
        imageFront.setImage(buttonFront[0].getImage());
        imageBehind.setImage(buttonBehind[0].getImage());
    }
}