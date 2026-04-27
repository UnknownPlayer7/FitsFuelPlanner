package controllers;

import components.ModalWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import models.Goods;
import utils.ControllersArchive;
import utils.Reader;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


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

        List<Goods> productsToStore = products
                .stream()
                .filter(product -> {
                    String name = product.getName();
                    String key = product.getKey();
                    return name.startsWith(filter) || key.startsWith(filter);
                })
                .collect(Collectors.toList());

        flowPane.getChildren().remove(1,flowPane.getChildren().size());
        fillLibrary(productsToStore);
    }

    @FXML
    void addElement(ActionEvent event) {
        ModalWindow addWindow = new ModalWindow(520,337,"Добавление продукта","/FXML/AddProduct-view.fxml",
                "/images/icoBag.png");
        addWindow.invokeWindow();
    }


    public void putInLib(Goods product) {
        products.add(product);

        Button button = createProductButton(product);
        flowPane.getChildren().add(button);
    }

    private Button createProductButton(Goods product) {
        Button button = new Button(product.getName());

        button.setOnMouseClicked(x -> ControllersArchive.getCurrentTabController().addProduct(product));
        button.setPrefWidth(135);
        button.setPrefHeight(80);

        return button;
    }

    private void fillLibrary(List<Goods> products) {
        if (products != null) {
            for (Goods product:products) {
                Button button = createProductButton(product);
                flowPane.getChildren().add(button);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllersArchive.setLibraryController(this);
        Reader reader = new Reader("/models.Goods/Product.txt");

        products = reader.readAllObject();
        fillLibrary(products);
        textField.setOnKeyReleased(this::handleKeyPress);
        setPropertyScrollPane();
    }

    private void setPropertyScrollPane() {
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }
}
