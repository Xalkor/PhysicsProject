package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Physics Simulation");
        primaryStage.setMinHeight(root.getMinHeight()+35);
        primaryStage.setMinWidth(root.getMinWidth()+15);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
