package bom_it.controller;

import bom_it.game.Images;
import bom_it.game.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class PowerUpController implements Initializable {
    private final Images[] buttonControl = Images.buttonControl;

    @FXML
    ImageView imageControl;

    @FXML
    private void enterImageControl() {
        imageControl.setImage(buttonControl[1].getImage());
    }

    @FXML
    private void exitImageControl() {
        imageControl.setImage(buttonControl[0].getImage());
    }

    @FXML
    private void clickImageControl() {
        App.setRoot("Instruction");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageControl.setImage(buttonControl[0].getImage());
    }
}
