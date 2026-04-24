package components;

import controllers.HelpController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import runners.ApplicationLauncher;
import utils.ResourceSupplier;

import java.io.IOException;

public class ModalWindow {

    private final int width;
    private final int height;
    private final String title;
    private final String resource;
    private final Image icon;

    public ModalWindow(int width, int height, String title, String resource, String pathIcon) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.resource = resource;
        this.icon = ResourceSupplier.findIcon(pathIcon);
    }

    public void invokeWindow() {
        makeStage().show();
    }

    private Stage makeStage() {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.getIcons().add(icon);
        stage.setResizable(false);

        try {
            stage.setScene(makeScene());
        } catch (IOException e) {
            System.out.printf("Couldn't make a scene related to %s. Cause: %s%n", title, e);
        }
        return stage;
    }

    private Scene makeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationLauncher.class.getResource(resource));
        Scene scene = new Scene(loader.load(), width, height);

        if (title.equals("Справка")) {
            HelpController helpController = loader.getController();
            helpController.printHelpInfo();
        }
        return scene;
    }
}
