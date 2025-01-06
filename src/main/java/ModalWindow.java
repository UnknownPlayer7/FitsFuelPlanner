import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ModalWindow {


    public void newWindow(int width, int height, String title, String resource, String pathIcon) {

        Stage stage = new Stage();
        Image icon = Finder.findIcon(pathIcon);

        stage.initModality(Modality.APPLICATION_MODAL);
        try {

            Scene scene = makeScene(title,width,height,resource);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.show();
        } catch (Exception exc) {
            System.out.println("Не удалось загрузить сцену " + exc);
        }
    }
    private Scene makeScene(String string, int width, int height, String resource) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(resource));
        Scene scene = new Scene(loader.load(), width, height);
        if (string.equals("Справка")) {
            HelpController helpController = loader.getController();
            helpController.printHelpInfo();
        }
        if (string.equals("Хранитель")){

            HelpController helpController = loader.getController();
            helpController.printInfo("Сохранено!");

        }
        return scene;
    }

    public static void getSaveSuccess(){
        ModalWindow infoWindow = new ModalWindow();
        infoWindow.newWindow(201,63,"Хранитель","/FXML/Information-view.fxml",
                "/images/iconInfo.png");
    }

    public static void getNameWriter(){
        ModalWindow infoWindow = new ModalWindow();
        infoWindow.newWindow(201,63,"Имя клиента","/FXML/EnteringNameController-view.fxml",
                "/images/iconInfo.png");
    }
}
