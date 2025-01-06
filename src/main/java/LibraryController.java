import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class LibraryController implements Initializable {

    @FXML
    private FlowPane flowPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    void addElement(ActionEvent event) {
        ModalWindow addWindow = new ModalWindow();
        addWindow.newWindow(520,337,"Добавление продукта","/FXML/AddProduct-view.fxml",
                "/images/icoBag.png");
    }

    public void putInLib(Goods product){
        Button button = new Button(product.getName());
        button.setPrefWidth(135);
        button.setPrefHeight(80);
        flowPane.getChildren().add(button);
    }

    private void setPropertyScrollPane(){
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllersArchive.setLibraryController(this);
        Reader reader = new Reader("/Goods/Product.txt");
        ArrayList<Goods> products = reader.readAllObject();
        if(products != null){
            for(Goods product:products){
                Button button = new Button(product.getName());
                button.setOnMouseClicked(x -> {
                        ControllersArchive.getCurrentTab().addProduct(product);
                });
                button.setPrefWidth(135);
                button.setPrefHeight(80);
                flowPane.getChildren().add(button);
            }
        }
        setPropertyScrollPane();

    }
}
