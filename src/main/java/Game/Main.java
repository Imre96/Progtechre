package Game;

import Controller.Controller;
import View.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Controller cmd = new Controller();
        //cmd.init();
        View view= new View(cmd);
        Scene scene = new Scene(view.getView(), 300, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
