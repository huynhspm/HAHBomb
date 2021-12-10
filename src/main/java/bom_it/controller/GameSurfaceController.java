package bom_it.controller;

import bom_it.engine.Images;
import bom_it.game.App;
import bom_it.objects.Character;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import static bom_it.game.Enum.STATUS_GAME.PLAY;

public class GameSurfaceController implements Initializable {
    private final Images information = Images.information;
    private final Images border = Images.border;
    private final Images[] imageLWP = Images.imageLWP;

    @FXML
    AnchorPane anchorPane;

    private void setInfo(Character character, double coordinateX, double coordinateY) {
        ImageView imageInfo, imageCharacter;
        Label lives, powerBomb, numBomb, powerSpeed;

        imageInfo = new ImageView(information.getImage());
        imageInfo.setTranslateX(coordinateX);
        imageInfo.setTranslateY(coordinateY);

        imageCharacter = new ImageView(character.getImageInfo());
        imageCharacter.setFitHeight(72);
        imageCharacter.setFitWidth(48);
        imageCharacter.setTranslateX(coordinateX + 26);
        imageCharacter.setTranslateY(coordinateY + 21);

        lives = new Label(String.valueOf(character.livesProperty().getValue()));
        character.livesProperty().addListener((observableValue, oldValue, newValue) -> lives.setText(String.valueOf(newValue)));
        lives.setTranslateY(coordinateY + 15);

        powerBomb = new Label(String.valueOf(character.powerBombProperty().getValue()));
        character.powerBombProperty().addListener((observableValue, oldValue, newValue) -> powerBomb.setText(String.valueOf(newValue)));
        powerBomb.setTranslateY(coordinateY + 39);

        numBomb = new Label(String.valueOf(character.numBombProperty().getValue()));
        character.numBombProperty().addListener((observableValue, oldValue, newValue) -> numBomb.setText(String.valueOf(newValue)));
        numBomb.setTranslateY(coordinateY + 65);

        powerSpeed = new Label(String.valueOf(App.gameWorld.getPlayer().powerSpeedProperty().getValue()));
        character.powerSpeedProperty().addListener((observableValue, oldValue, newValue) -> powerSpeed.setText(String.valueOf(newValue)));
        powerSpeed.setTranslateY(coordinateY + 89);

        Group group = new Group(lives, powerBomb, numBomb, powerSpeed);
        group.setStyle("-fx-font-weight: Bold");
        group.setTranslateX(coordinateX + 132);

        anchorPane.getChildren().addAll(imageInfo, imageCharacter, group);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.getChildren().add(App.gameWorld.getSceneSprites());
        anchorPane.getChildren().add(new ImageView(border.getImage()));

        App.gameWorld.statusProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.intValue() != PLAY.ordinal()) {
                ImageView imageView = new ImageView(imageLWP[newValue.intValue()].getImage());
                imageView.setTranslateX(190);
                imageView.setTranslateY(150);
                anchorPane.getChildren().add(imageView);
            }
        });

        setInfo(App.gameWorld.getPlayer(), 680, 30);

        setIcon(Images.iconHome, 705, 200).setOnMouseClicked(mouseEvent -> {
            App.gameWorld.shutdown();
            App.setRoot("Menu");
        });

        ImageView iconContinue = setIcon(Images.iconContinue, 780, 200);
        ImageView iconPause = setIcon(Images.iconPause, 780, 200);
        ImageView iconOnSoundEffect = setIcon(Images.iconOnSoundEffect, 705, 300);
        ImageView iconOffSoundEffect = setIcon(Images.iconOffSoundEffect, 705, 300);
        ImageView iconOnMusic = setIcon(Images.iconOnMusic, 780, 300);
        ImageView iconOffMusic = setIcon(Images.iconOffMusic, 780, 300);

        iconContinue.setVisible(false);
        iconContinue.setOnMouseClicked(mouseEvent -> {
            iconContinue.setVisible(false);
            iconPause.setVisible(true);
            App.gameWorld.begin();
        });
        iconPause.setOnMouseClicked(mouseEvent -> {
            iconPause.setVisible(false);
            iconContinue.setVisible(true);
            App.gameWorld.pause();
        });


        iconOffMusic.setVisible(false);
        iconOffMusic.setOnMouseClicked(mouseEvent -> {
            iconOffMusic.setVisible(false);
            iconOnMusic.setVisible(true);
            App.gameWorld.getMusicGame().play();
        });
        iconOnMusic.setOnMouseClicked(mouseEvent -> {
            iconOnMusic.setVisible(false);
            iconOffMusic.setVisible(true);
            App.gameWorld.getMusicGame().pause();
        });


        iconOffSoundEffect.setVisible(false);
        iconOffSoundEffect.setOnMouseClicked(mouseEvent -> {
            iconOffSoundEffect.setVisible(false);
            iconOnSoundEffect.setVisible(true);
            App.gameWorld.getSoundEffectGame().setStatus(true);
        });
        iconOnSoundEffect.setOnMouseClicked(mouseEvent -> {
            iconOnSoundEffect.setVisible(false);
            iconOffSoundEffect.setVisible(true);
            App.gameWorld.getSoundEffectGame().setStatus(false);
        });

        Label gameLevel = new Label("LEVEL: " + App.gameWorld.getLevel());
        gameLevel.setTranslateX(705);
        gameLevel.setTranslateY(400);
        gameLevel.setStyle("-fx-font-size: 30; -fx-text-fill: green; -fx-font-weight: bold");
        anchorPane.getChildren().add(gameLevel);

        Label timeLeft = new Label("TIME LEFT: 300");
        timeLeft.setTranslateX(700);
        timeLeft.setTranslateY(480);
        timeLeft.setStyle("-fx-font-size: 20; -fx-text-fill: purple; -fx-font-weight: bold");
        anchorPane.getChildren().add(timeLeft);
        App.gameWorld.timeLeftProperty().addListener((observableValue, oldValue, newValue) -> timeLeft.setText("TIME LEFT: " + (int) newValue / App.gameWorld.getFramesPerSecond()));

    }

    private ImageView setIcon(Images[] imageIcon, double coordinateX, double coordinateY) {
        ImageView icon = new ImageView(imageIcon[0].getImage());
        icon.setOnMouseEntered(mouseEvent -> icon.setImage(imageIcon[1].getImage()));
        icon.setOnMouseExited(mouseEvent -> icon.setImage(imageIcon[0].getImage()));

        icon.setTranslateX(coordinateX);
        icon.setTranslateY(coordinateY);
        anchorPane.getChildren().add(icon);
        return icon;
    }
}
