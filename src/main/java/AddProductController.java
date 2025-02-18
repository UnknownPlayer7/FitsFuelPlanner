import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class AddProductController {

    @FXML
    private TextField name;

    @FXML
    private TextField productsKey;

    @FXML
    private TextField energyOfProduct;

    @FXML
    private TextField carb;

    @FXML
    private RadioButton carbComplex;

    @FXML
    private RadioButton carbSimple;

    @FXML
    private TextField fat;

    @FXML
    private RadioButton fatAnimal;

    @FXML
    private RadioButton fatPlant;

    @FXML
    private TextField protein;

    @FXML
    private RadioButton proteinAnimal;

    @FXML
    private RadioButton proteinPlant;

    @FXML
    void buttonOnCarbComplex(ActionEvent event) {
        carbSimple.setSelected(false);

    }

    @FXML
    void buttonOnCarbSimple(ActionEvent event) {
        carbComplex.setSelected(false);

    }

    @FXML
    void buttonOnProteinAnimal(ActionEvent event) {
        proteinPlant.setSelected(false);

    }

    @FXML
    void buttonOnProteinPlant(ActionEvent event) {
        proteinAnimal.setSelected(false);

    }

    @FXML
    void buttonOnFatAnimal(ActionEvent event) {
        fatPlant.setSelected(false);
    }

    @FXML
    void buttonOnFatPlant(ActionEvent event) {
        fatAnimal.setSelected(false);
    }

    @FXML
    void saveProduct(ActionEvent event) {
        Goods product = createProduct();
        if (product == null) return;
        if(Save.saveProduct(product))
            NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SAVE);
        else
            NotificationManager.showError(InfoType.ERROR_SAVE);
    }

    @FXML
    void addProduct(ActionEvent event){
        Goods product = createProduct();
        if(product != null)
            ControllersArchive.getCurrentTab().addProduct(product);
    }

    private Goods createProduct(){
        try{
            String name = this.name.getText();
            String productsKey = this.productsKey.getText();
            double energyOfProduct = Double.parseDouble(this.energyOfProduct.getText());
            double carb = Double.parseDouble(this.carb.getText());
            double protein = Double.parseDouble(this.protein.getText());
            double fat = Double.parseDouble(this.fat.getText());
            String fatType = getButtonStatus(fatAnimal, fatPlant);
            String carbType = getButtonStatus(carbComplex,carbSimple);
            String proteinType = getButtonStatus(proteinAnimal, proteinPlant);
            return new Goods(name,energyOfProduct,protein,proteinType,carb,carbType,fat,fatType,productsKey);
        } catch (Exception e) {
            NotificationManager.showError(InfoType.ERROR_CREATE);
            return null;
        }
    }

    private String getButtonStatus(RadioButton firstButton, RadioButton secondButton) {
        if(firstButton.isSelected()){
            return  convertTextFromButton(firstButton);
        }else if(secondButton.isSelected()){
            return  convertTextFromButton(secondButton);
        }else {
            throw new NullPointerException();
        }
    }

    private String convertTextFromButton(RadioButton radioButton) {
        switch (radioButton.getText()) {
            case "Сложные": return "Complex";
            case "Простые": return "Simple";
            case "Животные": return "Animal";
            case "Растительные": return "Plant";
            default: return radioButton.getText();
        }
    }

}