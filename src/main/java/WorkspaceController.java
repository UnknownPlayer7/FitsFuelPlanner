
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class WorkspaceController extends NewTabController implements Initializable {

    @FXML
    private Label amountOfEnergyLabelMain;

    @FXML
    private Label carbLabelMain;

    @FXML
    private Label fatLabelMain;

    @FXML
    private Label proteinLabelMain;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab addTab;

    @FXML
    private MenuBar menuBar;

    private int amountOfTabs = 0;

    private Client client;


    public void addProduct(Goods product){
        super.goods.add(product);
        Changer.changeLabels(product,this);
        Changer.changeComboBox(product,this);

    }

    public Label getAmountOfEnergyLabelMain() {
        return amountOfEnergyLabelMain;
    }

    public Label getCarbLabelMain() {
        return carbLabelMain;
    }

    public Label getFatLabelMain() {
        return fatLabelMain;
    }

    public Label getProteinLabelMain() {
        return proteinLabelMain;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public void createNewTab(){
        Tab newTab;
        amountOfTabs++;
        if(amountOfTabs == 1){
            newTab = new Tab("Завтрак");
        } else if(amountOfTabs == 2){
            newTab = new Tab("Обед");
        }else if(amountOfTabs == 3){
            newTab = new Tab("Ужин");
        }else newTab = new Tab("Перекус "+(amountOfTabs-3));
        Loader.setCurrentTab(newTab);
        tabPane.getTabs().add(tabPane.getTabs().size() - 1, newTab);
        tabPane.getSelectionModel().select(newTab);
        FXMLLoader fxmlLoader = new FXMLLoader(WorkspaceController.class.getResource("/FXML/NewTab-view.fxml"));
        try{
            Scene scene = new Scene(fxmlLoader.load(), 735, 377);
            Node root = scene.getRoot();
            newTab.setContent(root);
        }
        catch(IOException exc){
            NotificationManager.showError(InfoType.ERROR_LOAD);
        }
    }

    private void setPropertyTabs() {
        if(addTab.isSelected()){
            createNewTab();
        }
        addTab.setOnSelectionChanged(event -> {
            if (addTab.isSelected()) {
                createNewTab();
            }
        });

    }

    public Client getClient() {
        return client;
    }

    private void setClientOptions(){
        client = ControllersArchive.getMainMenuController().getClientBox().getSelectionModel().getSelectedItem();
    }

    @FXML
    void saveClient(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Введите имя клиента:");
        dialog.setTitle("Создание клиента");
        Optional<String> optional = dialog.showAndWait();
        optional.ifPresent(clientName -> {
            if(Save.save(new Client(clientName,tabPane))){
                NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SAVE);
            } else
                NotificationManager.showError(InfoType.ERROR_SAVE);
        });
    }

    @FXML
    void savePDF(ActionEvent event) {
        Client client = new Client(getClient().getName(),tabPane);
        ArrayList<String> tabNameList = new ArrayList<>();
        for(Tab tab: tabPane.getTabs()){
            tabNameList.add(tab.getText());
        }
        ArrayList<String> commonNutritionList = new ArrayList<>();
        commonNutritionList.add(getCarbLabelMain().getText());
        commonNutritionList.add(getProteinLabelMain().getText());
        commonNutritionList.add(getFatLabelMain().getText());
        commonNutritionList.add(getAmountOfEnergyLabelMain().getText());
        WriterPdf writerPdf =new WriterPdf(client.getName(),client,tabNameList,commonNutritionList);

        if(writerPdf.createPdf()) {
            NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SAVE);
        } else
            NotificationManager.showError(InfoType.ERROR_SAVE);

    }

    @FXML
    void toMenu(ActionEvent event) {

        try{
            Loader.setCountOfTabs(1);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/MainMenu-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage =(Stage)menuBar.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Главное меню");
            stage.getIcons().add(Finder.findIcon("/images/Tree.png"));
            stage.show();
        }
        catch(IOException exc){
            System.out.println(exc);
        }

    }

    @FXML
    void setWallpaperPDF(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        File chosenFile = chooser.showOpenDialog(menuBar.getScene().getWindow());
        if(chosenFile == null) return;
        ResourceSupplier resourceSupplier = new ResourceSupplier();
        boolean isCompleted = resourceSupplier.setImage("WallpaperPDF.jpg", chosenFile);
        if(isCompleted)
            NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SETTING);
        else
            NotificationManager.showError(InfoType.ERROR_SETTING);

    }

    @FXML
    void setTextColorPDF(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Изменение цвета текста");
        dialog.setContentText("Введите HEX-код цвета:");
        dialog.setHeaderText("Не знаете, что такое HEX-код?\nА может не уверены, где его искать?\nОбратитесь в меню справки.");
        Optional<String> optional = dialog.showAndWait();
        optional.ifPresent(hexCodeColor -> WriterPdf.setColorInFrame(hexCodeColor));
    }

    @FXML
    void setBorderColorPDF(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Изменение цвета рамки");
        dialog.setContentText("Введите HEX-код цвета:");
        dialog.setHeaderText("Не знаете, что такое HEX-код?\nА может не уверены, где его искать?\nОбратитесь в меню справки.");
        Optional<String> optional = dialog.showAndWait();
        optional.ifPresent(hexCodeColor -> WriterPdf.setBorderColor(hexCodeColor));
    }

    @FXML
    void setWallpaperMainMenu(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        File chosenFile = chooser.showOpenDialog(menuBar.getScene().getWindow());
        if(chosenFile == null) return;
        ResourceSupplier resourceSupplier = new ResourceSupplier();
        boolean isCompleted = resourceSupplier.setImage("WallpaperMainMenu.jpg", chosenFile);
        if(isCompleted)
            NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SETTING);
        else
            NotificationManager.showError(InfoType.ERROR_SETTING);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Platform.runLater(() -> {
            ControllersArchive.setWorkspaceController(this);
            setPropertyTabs();
            setClientOptions();
        });


    }


}
