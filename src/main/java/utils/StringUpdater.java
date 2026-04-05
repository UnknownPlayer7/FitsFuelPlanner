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

import static utils.StringFinder.findNumber;

public class StringUpdater {

    public static void updateLabels(ArrayList<Goods> products, WorkspaceController controller, OperationType operation){
        for(Goods product : products) {
            updateLabels(product, controller, operation);
        }
    }

    public static void updateLabels(Goods product, WorkspaceController controller, OperationType operation) {
        updateLabelNumber(controller.getAmountOfEnergyLabelMain(),product.getAmountOfEnergy(), operation);
        updateLabelNumber(controller.getFatLabelMain(),product.getFat(), operation);
        updateLabelNumber(controller.getCarbLabelMain(),product.getCarb(), operation);
        updateLabelNumber(controller.getProteinLabelMain(),product.getProtein(), operation);
    }

    private static void updateLabelNumber(Label label, double number, OperationType operation) {
        String string = label.getText();
        string = updateNumber(string, number, operation);
        label.setText(string);
    }

    public static String updateNumber(String string, double number, OperationType operation){
        String target = findNumber(string);
        double targetDouble = Double.parseDouble(target.replace(",","."));

        switch (operation) {
            case SUM:
                targetDouble += number;
                break;
            case SUB:
                targetDouble -=number;
        }
        String targetString = String.format("%.1f",targetDouble);

        return string.replace(target,targetString.replace(".",","));
    }

    public static void updateComboBox(ArrayList<Goods> products, NewTabController controller, OperationType operation) {
        for(Goods product : products) {
            updateComboBox(product, controller, operation);
        }
    }

    public static void updateComboBox(Goods product, NewTabController controller, OperationType operation) {
        updateButtonNumber(controller.getAmountOfEnergyLabel(),product.getAmountOfEnergy(), operation);

        updateNumbersInComboBox(controller.getFatBox(),product.getFat(), StringFinder.findElementType(product.getTypeOfFat()), operation);
        controller.getFatBox().getSelectionModel().select(0);

        updateNumbersInComboBox(controller.getCarbBox(),product.getCarb(), StringFinder.findElementType(product.getTypeOfCarb()), operation);
        controller.getCarbBox().getSelectionModel().select(0);

        updateNumbersInComboBox(controller.getProteinBox(),product.getProtein(), StringFinder.findElementType(product.getTypeOfProtein()), operation);
        controller.getProteinBox().getSelectionModel().select(0);
    }

    private static void updateButtonNumber(Button button, double number, OperationType operation) {
        String string = button.getText();
        string = updateNumber(string, number, operation);
        button.setText(string);

    }

    private static void updateNumbersInComboBox(ComboBox<String> comboBox, double number, String type, OperationType operation) {
        List<String> keys = Arrays.asList("Белки:", "Углеводы:", "Жиры:", type);

        List<String> strings = comboBox.getItems().stream()
                .map(string -> keys.stream().anyMatch(string::contains)
                        ? updateNumber(string, number, operation)
                        : string)
                .collect(Collectors.toList());

        comboBox.setItems(FXCollections.observableArrayList(strings));
    }

    public  static void updateCells(Goods product) {
        double amountOfProduct = Double.parseDouble(product.getAmountOfProduct());
        double oldAmountOfProduct = Double.parseDouble(product.getOldAmountOfProduct());

        product.setCarb(product.getCarb() * amountOfProduct / oldAmountOfProduct);
        product.setFat(product.getFat() * amountOfProduct / oldAmountOfProduct);
        product.setProtein(product.getProtein() * amountOfProduct / oldAmountOfProduct);
        product.setAmountOfEnergy(product.getAmountOfEnergy() * amountOfProduct / oldAmountOfProduct);
    }
}
