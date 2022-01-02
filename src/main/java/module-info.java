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
    exports bom_it.objects.Character.Enemy;
    opens bom_it.objects.Character.Enemy to javafx.fxml;
    exports bom_it.objects.Item;
    opens bom_it.objects.Item to javafx.fxml;
    exports bom_it.objects.Character;
    opens bom_it.objects.Character to javafx.fxml;
}