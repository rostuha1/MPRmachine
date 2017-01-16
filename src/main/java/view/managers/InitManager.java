package view.managers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jfxtras.labs.scene.control.window.Window;
import main.Main;
import model.Register;
import view.components.ComponentBuilder;

import java.util.HashMap;
import java.util.Map;

public class InitManager extends Window {

    private static InitManager instance = new InitManager();
    private static boolean isShow;
    private final ListView<Register> listView = new ListView<>();

    private final Insets insets = new Insets(10);

    private TextField field = new TextField();

    private Region addBtn = ComponentBuilder.getButton("Додати", 16, 100, 40, 0.7, Color.LIGHTGREEN, Color.web("0x2A7A2A"));
    private Region removeBtn = ComponentBuilder.getButton("Видалити", 16, 200, 40, 0.7, Color.LIGHTGREEN, Color.web("0x2A7A2A"));
    private Region removeAllBtn = ComponentBuilder.getButton("Видалити все", 16, 200, 40, 0.7, Color.LIGHTGREEN, Color.web("0x2A7A2A"));

    private InitManager() {
        setTitle("Ініціалізація регістрів");

        setLayoutX(100);
        setLayoutY(30);

        setTitleBarStyleClass("window-title");
        setStyle("-fx-background-color: transparent");

        setPrefSize(400, 200);

        addBtn.setOnMouseClicked(event -> {
            Register r = Register.parse(field.getText());
            if (r != null && !listView.getItems().contains(r)) listView.getItems().add(r);
        });

        removeBtn.setOnMouseClicked(event -> {
            int index = instance.listView.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                instance.listView.getItems().remove(index);
            }
        });

        removeAllBtn.setOnMouseClicked(event -> listView.getItems().clear());

        listView.setMinSize(150, 0);
        field.setMinWidth(95);
        field.setAlignment(Pos.CENTER);

        HBox addBox = new HBox(field, addBtn);
        addBtn.setPadding(new Insets(0, 0, 0, 5));

        VBox controls = new VBox(addBox, removeBtn, removeAllBtn);

        VBox.setMargin(addBox, insets);
        VBox.setMargin(removeBtn, insets);
        VBox.setMargin(removeAllBtn, insets);

        HBox hBox = new HBox(controls, listView);

        HBox.setMargin(controls, insets);
        HBox.setMargin(listView, insets);

        hBox.setAlignment(Pos.CENTER);

        addBox.setMaxSize(0, 0);
        controls.setMaxSize(0, 0);
        hBox.setMaxSize(0, 0);

        getContentPane().getChildren().add(hBox);
    }

    public static void requestWindow() {
        if (isShow) {
            isShow = false;
            hide();
            return;
        }

        isShow = true;


        show();
    }

    private static void hide() {
        Main.getWrapper().getChildren().remove(instance);
    }
    private static void show() {
        Main.getWrapper().getChildren().add(instance);
    }

    public static Map<Integer, Integer> getInitMap () {
        Map<Integer, Integer> initMap = new HashMap<>();
        instance.listView.getItems().forEach(item -> initMap.put(item.getIndex(), item.getValue()));

        return initMap;
    }

}
