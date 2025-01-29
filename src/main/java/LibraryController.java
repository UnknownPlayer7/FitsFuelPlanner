import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class LibraryController implements Initializable {

    private List<Goods> products;

    @FXML
    private FlowPane flowPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField textField;


    @FXML
    void handleKeyPress(KeyEvent event) {
        String filter = textField.getText();
        Stream<Goods> stream = products.stream().filter(product -> {
            String name = product.getName();
            String key = product.getKey();
            return name.startsWith(filter) || key.startsWith(filter);
        });
        List<Goods> fProducts =stream.collect(Collectors.toList());
        flowPane.getChildren().remove(1,flowPane.getChildren().size());
        fillLibrary(fProducts);
    }

    @FXML
    void addElement(ActionEvent event) {
        ModalWindow addWindow = new ModalWindow();
        addWindow.newWindow(520,337,"Добавление продукта","/FXML/AddProduct-view.fxml",
                "/images/icoBag.png");
    }


    public void putInLib(Goods product){
        products.add(product);
        Button button = new Button(product.getName());
        button.setOnMouseClicked(x -> {
            ControllersArchive.getCurrentTab().addProduct(product);
        });
        button.setPrefWidth(135);
        button.setPrefHeight(80);
        flowPane.getChildren().add(button);
    }

    private void setPropertyScrollPane(){
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }

    private void fillLibrary(List<Goods> products){
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
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllersArchive.setLibraryController(this);
        Reader reader = new Reader("/Goods/Product.txt");
        products = reader.readAllObject();
        fillLibrary(products);
        textField.setOnKeyReleased(this::handleKeyPress);
        setPropertyScrollPane();

    }
}
