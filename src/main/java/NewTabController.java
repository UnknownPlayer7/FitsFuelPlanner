import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class NewTabController implements Initializable {

    @FXML
    private TableView table;

    private Tab tab;

    @FXML
    private ComboBox<String> carbBox;

    @FXML
    private ComboBox<String> proteinBox;

    @FXML
    private ComboBox<String> fatBox;

    @FXML
    private Button amountOfEnergyLabel;

    @FXML
    private TableColumn<Goods, String> nameColumn;

    @FXML
    private TableColumn<Goods, Double> carbColumn;

    @FXML
    private TableColumn<Goods, Double> proteinColumn;

    @FXML
    private TableColumn<Goods, Double> fatColumn;

    @FXML
    private TableColumn<Goods, Double> energyColumn;

    @FXML
    private TableColumn<Goods, String> amountOfProduct;

    private NewTabController currentTab = this;


    protected ObservableList<Goods> goods = FXCollections.observableArrayList();

    @FXML
    void ok(ActionEvent event) {
        Platform.runLater(() ->{
            ModalWindow addWindow = new ModalWindow();
            addWindow.newWindow(420,380,"Картотека","/FXML/Library-view.fxml",
                    "/images/iconLib.png");
        });

    }



    public void addProduct(Goods product){
        goods.add(new Goods(product));
        Changer.changeLabels(product,ControllersArchive.getWorkspaceController());
        Changer.changeComboBox(product,this);

    }

    protected void setPropertyColumns(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        carbColumn.setCellValueFactory(new PropertyValueFactory<>("carb"));
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("protein"));
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("fat"));
        energyColumn.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
        amountOfProduct.setCellValueFactory(new PropertyValueFactory<>("amountOfProduct"));
        amountOfProduct.setCellFactory(TextFieldTableCell.forTableColumn());
        amountOfProduct.setOnEditCommit(
                t -> {
                    Goods product = t.getTableView().getItems().get(
                            t.getTablePosition().getRow());
                    Changer.minusChangeComboBox(product,ControllersArchive.getCurrentTab());
                    Changer.minusChangeLabels(product,ControllersArchive.getWorkspaceController());
                    product.setOldAmountOfProduct(product.getAmountOfProduct());
                    product.setAmountOfProduct(t.getNewValue());
                    Changer.changeCells(product);
                    t.getTableView().refresh();
                    Changer.changeComboBox(product,ControllersArchive.getCurrentTab());
                    Changer.changeLabels(product,ControllersArchive.getWorkspaceController());

                }
        );


    }

    protected void setPropertyTable(TableView table){
        table.setItems(goods);
        table.setEditable(true);
        table.setOnKeyPressed(key ->{
            if(key.getCode() == KeyCode.DELETE){
                Goods product = (Goods) table.getSelectionModel().getSelectedItem();
                goods.remove(product);
                Changer.minusChangeComboBox(product,ControllersArchive.getCurrentTab());
                Changer.minusChangeLabels(product,ControllersArchive.getWorkspaceController());

            }
        });
    }

    protected void setPropertyBoxes(){
        carbBox.setItems(FXCollections.observableArrayList("Углеводы: 0","Простые углеводы: 0","Сложные углеводы: 0"));
        carbBox.getSelectionModel().select(0);
        fatBox.setItems(FXCollections.observableArrayList("Жиры: 0","Животные жиры: 0","Растительные жиры: 0"));
        fatBox.getSelectionModel().select(0);
        proteinBox.setItems(FXCollections.observableArrayList("Белки: 0","Животные белки: 0","Растительные белки: 0"));
        proteinBox.getSelectionModel().select(0);
    }

    private void setPropertyTab(){
        tab.selectedProperty().addListener((observable,oldValue,newValue) -> {
            if(newValue){
                ControllersArchive.setCurrentTab(currentTab);
            }

        });
    }



    public ComboBox<String> getCarbBox() {
        return carbBox;
    }

    public ComboBox<String> getFatBox() {
        return fatBox;
    }

    public ComboBox<String> getProteinBox() {
        return proteinBox;
    }

    public Button getAmountOfEnergyLabel() {
        return amountOfEnergyLabel;
    }

    public void addToListProduct(ObservableList<Goods> goods) {
        this.goods.addAll(goods);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->{
            setPropertyColumns();
            setPropertyBoxes();
            setPropertyTable(table);
            Client client = ControllersArchive.getWorkspaceController().getClient();
            tab = Loader.getCurrentTab();
            setPropertyTab();
            Loader.buildTables(client,this);
        });

    }
}
