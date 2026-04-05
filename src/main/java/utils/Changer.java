package utils;

import constants.OperationType;
import controllers.NewTabController;
import controllers.WorkspaceController;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import models.Goods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Changer {

    public static void changeLabels(ArrayList<Goods> products, WorkspaceController controller, OperationType operation){
        for(Goods product : products) {
            changeLabels(product, controller, operation);
        }
    }

    public static void changeLabels(Goods product, WorkspaceController controller, OperationType operation) {
        changeNumber(controller.getAmountOfEnergyLabelMain(),product.getAmountOfEnergy(), operation);
        changeNumber(controller.getFatLabelMain(),product.getFat(), operation);
        changeNumber(controller.getCarbLabelMain(),product.getCarb(), operation);
        changeNumber(controller.getProteinLabelMain(),product.getProtein(), operation);
    }

    private static void changeNumber(Label label, double number, OperationType operation) {
        String string = label.getText();
        string = Finder.changeNumber(string, number, operation);
        label.setText(string);
    }

    public static void changeComboBox(ArrayList<Goods> products, NewTabController controller, OperationType operation) {
        for(Goods product : products) {
            changeComboBox(product, controller, operation);
        }
    }

    public static void changeComboBox(Goods product, NewTabController controller, OperationType operation) {
        changeNumber(controller.getAmountOfEnergyLabel(),product.getAmountOfEnergy(), operation);

        changeNumber(controller.getFatBox(),product.getFat(), Finder.findElementType(product.getTypeOfFat()), operation);
        controller.getFatBox().getSelectionModel().select(0);

        changeNumber(controller.getCarbBox(),product.getCarb(), Finder.findElementType(product.getTypeOfCarb()), operation);
        controller.getCarbBox().getSelectionModel().select(0);

        changeNumber(controller.getProteinBox(),product.getProtein(), Finder.findElementType(product.getTypeOfProtein()), operation);
        controller.getProteinBox().getSelectionModel().select(0);
    }

    private static void changeNumber(Button button, double number, OperationType operation) {
        String string = button.getText();
        string = Finder.changeNumber(string, number, operation);
        button.setText(string);

    }

    private static void changeNumber(ComboBox<String> comboBox, double number, String type, OperationType operation) {
        List<String> keys = Arrays.asList("Белки:", "Углеводы:", "Жиры:", type);

        List<String> strings = comboBox.getItems().stream()
                .map(string -> keys.stream().anyMatch(string::contains)
                        ? Finder.changeNumber(string, number, operation)
                        : string)
                .collect(Collectors.toList());

        comboBox.setItems(FXCollections.observableArrayList(strings));
    }

    public  static void changeCells(Goods product) {
        double amountOfProduct = Double.parseDouble(product.getAmountOfProduct());
        double oldAmountOfProduct = Double.parseDouble(product.getOldAmountOfProduct());

        product.setCarb(product.getCarb() * amountOfProduct / oldAmountOfProduct);
        product.setFat(product.getFat() * amountOfProduct / oldAmountOfProduct);
        product.setProtein(product.getProtein() * amountOfProduct / oldAmountOfProduct);
        product.setAmountOfEnergy(product.getAmountOfEnergy() * amountOfProduct / oldAmountOfProduct);
    }
}
