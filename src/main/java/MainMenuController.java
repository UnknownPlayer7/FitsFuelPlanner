import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    ImageView wallpaper;

    @FXML
    private ComboBox<Client> clientBox;

    @FXML
    void start(ActionEvent event) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Workspace-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Корзина");
            stage.getIcons().add(Finder.findIcon("/images/Tree.png"));
            stage.show();
        }
        catch(IOException exc){
            System.out.println(exc);
        }
    }

    public ComboBox<Client> getClientBox() {
        return clientBox;
    }

    private void setWallpaper(){
        String imagePath = "/images/WallPaperMainMenu.jpg";
        Image image = new Image(String.valueOf(MainMenuController.class.getResource(imagePath)));
        wallpaper.setImage(image);
        wallpaper.setFitHeight(wallpaper.getScene().getHeight());
        wallpaper.setFitWidth(wallpaper.getScene().getWidth());
        wallpaper.setPreserveRatio(false);
    }

    private void setComboBox(){
        Reader reader = new Reader("/Goods/Client.txt");
        ArrayList<Client> clients = reader.readAllClients();
        ObservableList list = FXCollections.observableArrayList(clients);
        clientBox.setItems(list);
        clientBox.setCellFactory(Cell.getCallBack());
        clientBox.setButtonCell(Cell.getListCell());
        list.add(0, NewClient.getInstance());
        clientBox.setValue(clientBox.getItems().get(0));
        clientBox.setStyle("-fx-font-size: 12px; -fx-font-family: 'System';");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            ControllersArchive.setMainMenuController(this);
            setWallpaper();
            setComboBox();
        });
    }
}
