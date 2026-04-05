package runners;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Finder;

import java.io.IOException;

public class ApplicationLauncher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationLauncher.class.getResource("/FXML/MainMenu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 737, 482);
        stage.setTitle("Главное меню");
        stage.setScene(scene);
        stage.getIcons().add(Finder.findIcon("/images/Tree.png"));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}