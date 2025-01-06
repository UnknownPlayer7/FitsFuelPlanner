import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class Changer {

    private static void changeNumber(Label label, double number){
        String string = label.getText();
        string = Finder.changeNumber(string,number);
        label.setText(string);
    }

    private static void minusChangeNumber(Label label, double number){
        String string = label.getText();
        string = Finder.minusChangeNumber(string,number);
        label.setText(string);
    }

    private static void changeNumber(ComboBox comboBox, double number, String type){
        ArrayList<String> strings = new ArrayList<>();
        for(int i=0;i<comboBox.getItems().size();i++){
            String string = comboBox.getItems().get(i).toString();
            if(string.contains("Белки:") || string.contains("Углеводы:")
                    || string.contains("Жиры:") || string.contains(type)){
                string = Finder.changeNumber(string,number);
            }
            strings.add(string);
        }
        comboBox.setItems(FXCollections.observableArrayList(strings));

    }

    private static void minusChangeNumber(ComboBox comboBox, double number, String type){
        ArrayList<String> strings = new ArrayList<>();
        for(int i=0;i<comboBox.getItems().size();i++){
            String string = comboBox.getItems().get(i).toString();
            if(string.contains("Белки:") || string.contains("Углеводы:")
                    || string.contains("Жиры:") || string.contains(type)){
                string = Finder.minusChangeNumber(string,number);
            }
            strings.add(string);
        }
        comboBox.setItems(FXCollections.observableArrayList(strings));

    }

    private static void changeNumber(Button button, double number){
        String string = button.getText();
        string = Finder.changeNumber(string,number);
        button.setText(string);

    }

    private static void minusChangeNumber(Button button, double number){
        String string = button.getText();
        string = Finder.minusChangeNumber(string,number);
        button.setText(string);

    }

    protected static void changeLabels(Goods product, WorkspaceController controller){
        changeNumber(controller.getAmountOfEnergyLabelMain(),product.getAmountOfEnergy());
        changeNumber(controller.getFatLabelMain(),product.getFat());
        changeNumber(controller.getCarbLabelMain(),product.getCarb());
        changeNumber(controller.getProteinLabelMain(),product.getProtein());
    }

    protected static void minusChangeLabels(Goods product, WorkspaceController controller){
        minusChangeNumber(controller.getAmountOfEnergyLabelMain(),product.getAmountOfEnergy());
        minusChangeNumber(controller.getFatLabelMain(),product.getFat());
        minusChangeNumber(controller.getCarbLabelMain(),product.getCarb());
        minusChangeNumber(controller.getProteinLabelMain(),product.getProtein());
    }

    protected static void changeComboBox(Goods product, NewTabController controller){
        changeNumber(controller.getAmountOfEnergyLabel(),product.getAmountOfEnergy());
        changeNumber(controller.getFatBox(),product.getFat(), Finder.findElementType(product.getTypeOfFat()));
        controller.getFatBox().getSelectionModel().select(0);
        changeNumber(controller.getCarbBox(),product.getCarb(), Finder.findElementType(product.getTypeOfCarb()));
        controller.getCarbBox().getSelectionModel().select(0);
        changeNumber(controller.getProteinBox(),product.getProtein(),Finder.findElementType(product.getTypeOfProtein()));
        controller.getProteinBox().getSelectionModel().select(0);
    }

    protected static void minusChangeComboBox(Goods product, NewTabController controller){
        minusChangeNumber(controller.getAmountOfEnergyLabel(),product.getAmountOfEnergy());
        minusChangeNumber(controller.getFatBox(),product.getFat(), Finder.findElementType(product.getTypeOfFat()));
        controller.getFatBox().getSelectionModel().select(0);
        minusChangeNumber(controller.getCarbBox(),product.getCarb(), Finder.findElementType(product.getTypeOfCarb()));
        controller.getCarbBox().getSelectionModel().select(0);
        minusChangeNumber(controller.getProteinBox(),product.getProtein(),Finder.findElementType(product.getTypeOfProtein()));
        controller.getProteinBox().getSelectionModel().select(0);
    }

    protected  static void changeCells(Goods product){

        double amountOfProduct = Double.parseDouble(product.getAmountOfProduct());
        double oldAmountOfProduct = Double.parseDouble(product.getOldAmountOfProduct());
        product.setCarb(product.getCarb()*amountOfProduct/oldAmountOfProduct);
        product.setFat(product.getFat()*amountOfProduct/oldAmountOfProduct);
        product.setProtein(product.getProtein()*amountOfProduct/oldAmountOfProduct);
        product.setAmountOfEnergy(product.getAmountOfEnergy()*amountOfProduct/oldAmountOfProduct);
    }

    protected static void changeComboBox(ArrayList<Goods> products, NewTabController controller){
        for(Goods product: products){
            changeNumber(controller.getAmountOfEnergyLabel(),product.getAmountOfEnergy());
            changeNumber(controller.getFatBox(),product.getFat(), Finder.findElementType(product.getTypeOfFat()));
            controller.getFatBox().getSelectionModel().select(0);
            changeNumber(controller.getCarbBox(),product.getCarb(), Finder.findElementType(product.getTypeOfCarb()));
            controller.getCarbBox().getSelectionModel().select(0);
            changeNumber(controller.getProteinBox(),product.getProtein(),Finder.findElementType(product.getTypeOfProtein()));
            controller.getProteinBox().getSelectionModel().select(0);
        }

    }

    protected static void changeLabels(ArrayList<Goods> products, WorkspaceController controller){
        for(Goods product:products){
            changeNumber(controller.getAmountOfEnergyLabelMain(),product.getAmountOfEnergy());
            changeNumber(controller.getFatLabelMain(),product.getFat());
            changeNumber(controller.getCarbLabelMain(),product.getCarb());
            changeNumber(controller.getProteinLabelMain(),product.getProtein());
        }

    }

}
