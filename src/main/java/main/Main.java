package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.rule.Rule;
import view.animation.AnimatedCircles;
import view.managers.RuleManager;
import view.managers.RuleManagerRequestType;
import view.panes.Controls;

import java.nio.file.Paths;

public class Main extends Application {

    private static Pane wrapper = new Pane();
    private static BorderPane root = new BorderPane();
    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {

        wrapper.setPrefSize(WindowSettings.width, WindowSettings.height);
        root.setPrefSize(WindowSettings.width, WindowSettings.height);
        wrapper.getChildren().add(root);
        scene = new Scene(wrapper);

        primaryStage.setFullScreen(WindowSettings.fullscreen);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setScene(scene);

        appInit();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void appInit() {

        root.setStyle("-fx-background-color: rgb(35, 40, 30)");
        AnimatedCircles.createSpawnNodes(root);

        ListView<Rule> listView = new ListView<>();
        listView.setMinSize(200, 0);

        String css = Paths.get("src/main/resources/style.css").toAbsolutePath().toUri().normalize().toString();
        wrapper.getStylesheets().add(css);

        Controls.setListView(listView);
        listView.setOnMouseReleased(event -> RuleManager.requestWindow(RuleManagerRequestType.CHANGING, listView));

        HBox hBox = new HBox(Controls.instance, listView);
        hBox.setMaxSize(0, 0);

        root.setCenter(hBox);

    }

    public static Scene getScene() {
        return scene;
    }

    public static Pane getRoot() {
        return root;
    }

    public static Pane getWrapper() {
        return wrapper;
    }
}
