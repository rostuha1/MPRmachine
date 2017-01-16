package view.managers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jfxtras.labs.scene.control.window.Window;
import main.Main;
import model.rule.Rule;
import model.rule.RuleType;
import view.components.ComponentBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class RuleManager extends Window {

    private static RuleType type;
    private static final Insets insets = new Insets(10);
    private static final Effect switchEffect = new Shadow(BlurType.GAUSSIAN, Color.GREENYELLOW, USE_COMPUTED_SIZE);

    private static RuleManagerRequestType currentType;
    private static Region currentBtn;

    private static boolean isShow;
    private static Region selectedBtn;

    private static final Region JButton = ComponentBuilder.getButton("J", 30, 40, 40, 0.7, Color.LIGHTGREEN, Color.web("0x2A7A2A"));
    private static final Region SButton = ComponentBuilder.getButton("S", 30, 40, 40, 0.7, Color.LIGHTGREEN, Color.web("0x2A7A2A"));
    private static final Region TButton = ComponentBuilder.getButton("T", 30, 40, 40, 0.7, Color.LIGHTGREEN, Color.web("0x2A7A2A"));
    private static final Region ZButton = ComponentBuilder.getButton("Z", 30, 40, 40, 0.7, Color.LIGHTGREEN, Color.web("0x2A7A2A"));

    private static final Region addBtn = ComponentBuilder.getButton("Додати", 30, 200, 40, 0.7, Color.LIGHTGREEN, Color.web("0x2A7A2A"));
    private static final Region changeBtn = ComponentBuilder.getButton("Змінити", 30, 200, 40, 0.7, Color.LIGHTGREEN, Color.web("0x2A7A2A"));

    private static final VBox specificButtonBox = new VBox();
    private static ListView<Rule> listView;

    private TextField field = new TextField();
    private static RuleManager instance = new RuleManager();

    private RuleManager() {
        setTitle("Редагування правил");

        setLayoutX(550);
        setLayoutY(30);

        setTitleBarStyleClass("window-title");
        setStyle("-fx-background-color: transparent");

        field.setMinWidth(160);
        field.setAlignment(Pos.CENTER);

        field.setSkin(createDefaultSkin());

        JButton.setOnMouseClicked(event -> {
            if (selectedBtn == JButton) {
                selectedBtn.setEffect(null);
                selectedBtn = null;
                return;
            }

            if (selectedBtn != null) field.clear();

            if (selectedBtn != null) selectedBtn.setEffect(null);
            selectedBtn = JButton;
            selectedBtn.setEffect(switchEffect);

            type = RuleType.J;

        });

        SButton.setOnMouseClicked(event -> {
            if (selectedBtn == SButton) {
                selectedBtn.setEffect(null);
                selectedBtn = null;
                return;
            }

            if (selectedBtn != null) field.clear();

            if (selectedBtn != null) selectedBtn.setEffect(null);
            selectedBtn = SButton;
            selectedBtn.setEffect(switchEffect);

            type = RuleType.S;
        });

        TButton.setOnMouseClicked(event -> {
            if (selectedBtn == TButton) {
                selectedBtn.setEffect(null);
                selectedBtn = null;
                return;
            }

            if (selectedBtn != null) field.clear();

            if (selectedBtn != null) selectedBtn.setEffect(null);
            selectedBtn = TButton;
            selectedBtn.setEffect(switchEffect);

            type = RuleType.T;

        });

        ZButton.setOnMouseClicked(event -> {
            if (selectedBtn == ZButton) {
                selectedBtn.setEffect(null);
                selectedBtn = null;
                return;
            }

            if (selectedBtn != null) field.clear();

            if (selectedBtn != null) selectedBtn.setEffect(null);
            selectedBtn = ZButton;
            selectedBtn.setEffect(switchEffect);

            type = RuleType.Z;

        });

        JButton.setUserData(RuleType.J);
        SButton.setUserData(RuleType.S);
        TButton.setUserData(RuleType.T);
        ZButton.setUserData(RuleType.Z);

        addBtn.setOnMouseClicked(event -> addBtnClick());
        changeBtn.setOnMouseClicked(event -> changeBtnClick());

        HBox.setMargin(JButton, insets);
        HBox.setMargin(SButton, insets);
        HBox.setMargin(TButton, insets);
        HBox.setMargin(ZButton, insets);

        VBox.setMargin(field, insets);
        VBox.setMargin(addBtn, insets);
        VBox.setMargin(changeBtn, insets);

        HBox hBox = new HBox(JButton, SButton, TButton, ZButton);
        specificButtonBox.getChildren().addAll(hBox, field);
        getContentPane().getChildren().add(specificButtonBox);
    }

    private static void hide() {
        Main.getWrapper().getChildren().remove(instance);
    }

    public static void requestWindow(RuleManagerRequestType type, ListView<Rule> listView) {

        RuleManager.listView = listView;

        if (currentType == type && type == RuleManagerRequestType.CHANGING) {
            if (listView.getSelectionModel().getSelectedIndex() != -1) {
                changingInit();

                Rule selectedRule = listView.getSelectionModel().getSelectedItem();

                if (selectedBtn != null) selectedBtn.setEffect(null);

                RuleType ruleType = RuleType.getType(selectedRule.toString());
                if (ruleType != null) {
                    switch (ruleType) {
                        case J:
                            selectedBtn = JButton;
                            JButton.setEffect(switchEffect);
                            break;
                        case T:
                            selectedBtn = TButton;
                            TButton.setEffect(switchEffect);
                            break;
                        case S:
                            selectedBtn = SButton;
                            SButton.setEffect(switchEffect);
                            break;
                        case Z:
                            selectedBtn = ZButton;
                            ZButton.setEffect(switchEffect);
                            break;
                    }
                }

                String ruleText = selectedRule.toString().
                        replace("J", "").
                        replace("T", "").
                        replace("S", "").
                        replace("Z", "").
                        replace("(", "").
                        replace(")", "");
                instance.field.setText(ruleText);
                return;
            }
        }

        if (isShow) {
            isShow = false;
            if (currentBtn != null) {
                specificButtonBox.getChildren().remove(currentBtn);
                currentBtn = null;
            }
            hide();
            return;
        }

        isShow = true;

        switch (type) {
            case ADDING:
                addingInit();
                break;
            case CHANGING:
                if (listView.getSelectionModel().getSelectedIndex() == -1) {
                    isShow = false;
                    return;
                }
                changingInit();
                break;
        }
        Main.getWrapper().getChildren().add(instance);
    }

    private static void addingInit() {
        if (currentBtn != null) specificButtonBox.getChildren().remove(currentBtn);
        instance.field.clear();
        currentBtn = addBtn;
        currentType = RuleManagerRequestType.CHANGING;
        specificButtonBox.getChildren().add(addBtn);
    }

    private static void changingInit() {
        if (currentBtn != null) specificButtonBox.getChildren().remove(currentBtn);
        currentBtn = changeBtn;
        currentType = RuleManagerRequestType.CHANGING;
        specificButtonBox.getChildren().add(changeBtn);
    }

    private static void addBtnClick() {
        if (selectedBtn == null) return;
        RuleType type = (RuleType) selectedBtn.getUserData();

        String[] stringValues = instance.field.getText().replaceAll(" ", "").split(",");
        ArrayList<Integer> values = new ArrayList<>(stringValues.length);

        if (!type.checkValues(stringValues.length)) return;

        try {
            for (String sValue : stringValues) {
                values.add(Integer.parseInt(sValue));
            }
        } catch (Exception ignored) {
            return;
        }

        Rule rule = new Rule(type, values);
        listView.getItems().add(rule);
    }

    private static void changeBtnClick() {
        Rule selectedRule = listView.getSelectionModel().getSelectedItem();
        if (selectedBtn == null || selectedRule == null) return;

        RuleType type = (RuleType) selectedBtn.getUserData();

        String[] stringValues = instance.field.getText().replaceAll(" ", "").split(",");
        ArrayList<Integer> values = new ArrayList<>(stringValues.length);

        if (!type.checkValues(stringValues.length)) return;

        try {
            for (String sValue : stringValues) {
                values.add(Integer.parseInt(sValue));
            }
        } catch (Exception ignored) {
            return;
        }

        selectedRule.setType(type);
        selectedRule.setValues(values);

        listView.refresh();

    }

}
