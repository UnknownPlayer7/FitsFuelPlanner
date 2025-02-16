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
        if(Save.saveProduct(product))
            NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SAVE);
        else
            NotificationManager.showError(InfoType.ERROR_SAVE);
    }

    @FXML
    void addProduct(ActionEvent event){
        Goods product = createProduct();
        ControllersArchive.getCurrentTab().addProduct(product);
    }

    private Goods createProduct(){
        String name = this.name.getText();
        String productsKey = this.productsKey.getText();
        double energyOfProduct = Double.parseDouble(this.energyOfProduct.getText());
        double carb = Double.parseDouble(this.carb.getText());
        double protein = Double.parseDouble(this.protein.getText());
        String carbType;
        if(this.carbComplex.isSelected()){
            carbType = "Complex";
        }else if(this.carbSimple.isSelected()){
                carbType = "Simple";
        }else carbType = "Unknown";
        String proteinType;
        if(this.proteinAnimal.isSelected()){
            proteinType = "Animal";
        }else if(this.proteinPlant.isSelected()){
            proteinType = "Plant";
        }else proteinType = "Unknown";
        double fat = Double.parseDouble(this.fat.getText());
        String fatType;
        if(this.fatAnimal.isSelected()){
            fatType = "Animal";
        }else if(this.fatPlant.isSelected()){
            fatType = "Plant";
        }else fatType = "Unknown";
        return new Goods(name,energyOfProduct,protein,proteinType,carb,carbType,fat,fatType,productsKey);
    }

}