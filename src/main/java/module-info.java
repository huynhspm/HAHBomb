module bom_it.game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens bom_it.game to javafx.fxml;
    opens bom_it.engine to javafx.fxml;
    opens bom_it.controller to javafx.fxml;
    opens bom_it.objects to javafx.fxml;
    exports bom_it.game;
    exports bom_it.engine;
    exports bom_it.controller;
    exports bom_it.objects;
    exports bom_it.Enum;
}