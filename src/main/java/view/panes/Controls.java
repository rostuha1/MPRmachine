package view.panes;

import controller.Algorithm;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.rule.Rule;
import view.components.ComponentBuilder;
import view.managers.InitManager;
import view.managers.RuleManager;
import view.managers.RuleManagerRequestType;

import java.util.Map;

public class Controls extends VBox {

    private TextField out = new TextField();
    private Region initBtn = ComponentBuilder.getButton("Ініціалізація", 16, 200, 40, 0.7, Color.LIGHTGREEN, Color.web("0x2A7A2A"));
    private Region addRuleBtn = ComponentBuilder.getButton("Редагування правил", 16, 200, 40, 0.7, Color.LIGHTGREEN, Color.web("0x2A7A2A"));
    private Region removeBtn = ComponentBuilder.getButton("Видалити", 16, 200, 40, 0.7, Color.LIGHTGREEN, Color.web("0x2A7A2A"));
    private Region startBtn = ComponentBuilder.getButton("Обчислити", 16, 200, 40, 0.7, Color.LIGHTGREEN, Color.web("0x2A7A2A"));

    private final Insets insets = new Insets(10);
    public static final Controls instance = new Controls();

    private static ListView<Rule> mainListView;

    private Controls() {

        setMaxSize(0, 0);

        initBtn.setOnMouseClicked(event -> InitManager.requestWindow());
        addRuleBtn.setOnMouseClicked(event -> RuleManager.requestWindow(RuleManagerRequestType.ADDING, Controls.mainListView));
        removeBtn.setOnMouseClicked(event -> {
            int index = mainListView.getSelectionModel().getSelectedIndex();
            if (index != -1) mainListView.getItems().remove(index);
        });
        startBtn.setOnMouseClicked(event -> {
            Map<Integer, Integer> initMap = InitManager.getInitMap();
            Algorithm algorithm = new Algorithm(initMap);

            mainListView.getItems().forEach(algorithm::addRule);

            int result = algorithm.getResult();
            out.setText(String.valueOf(result));
        });

        out.setMinHeight(40);
        out.setPromptText("Відповідь");
        out.setFocusTraversable(false);

        out.setStyle("-fx-font-size: 16");
        out.setAlignment(Pos.CENTER);

        setMargin(initBtn, insets);
        setMargin(addRuleBtn, insets);
        setMargin(removeBtn, insets);
        setMargin(startBtn, new Insets(30, 10, 10, 10));
        setMargin(out, insets);

        setAlignment(Pos.CENTER);

        getChildren().add(addRuleBtn);
        getChildren().add(initBtn);
        getChildren().add(removeBtn);

        getChildren().add(startBtn);
        getChildren().add(out);

    }

    public static void setListView(ListView<Rule> mainListView) {
        Controls.mainListView = mainListView;
    }

    public static ListView<Rule> getMainListView() {
        return mainListView;
    }
}