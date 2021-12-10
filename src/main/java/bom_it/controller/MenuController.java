package bom_it.controller;

import bom_it.engine.Images;
import bom_it.game.App;
import bom_it.game.TheGame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    private final Images[] buttonStart = Images.buttonStart;
    private final Images[] buttonInstruct = Images.buttonInstruct;

    @FXML
    private ImageView imageStart, imageInstruct;

    @FXML
    private void enterImageInstruct() {
        imageInstruct.setImage(buttonInstruct[1].getImage());
    }

    @FXML
    private void exitImageInstruct() {
        imageInstruct.setImage(buttonInstruct[0].getImage());
    }

    @FXML
    private void clickImageInstruct() {
        App.setRoot("Instruction");
    }

    @FXML
    private void enterImageStart() {
        imageStart.setImage(buttonStart[1].getImage());
    }

    @FXML
    private void exitImageStart() {
        imageStart.setImage(buttonStart[0].getImage());
    }

    @FXML
    private void clickImageStart() {
        App.gameWorld = new TheGame("HAH BOM", 60);
        App.gameWorld.begin();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageStart.setImage(buttonStart[0].getImage());
        imageInstruct.setImage(buttonInstruct[0].getImage());
    }
}