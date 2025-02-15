import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EnteringNameController {

    @FXML
    private TextField textField;

    @FXML
    private AnchorPane anchorPane;

    private String name = "Unknown";

    @FXML
    void enter(KeyEvent key) {

        if(key.getCode() == KeyCode.ENTER){
            this.name = textField.getText();
            ControllersArchive.setEnteringNameController(this);
            TabPane tabPane = ControllersArchive.getWorkspaceController().getTabPane();
            if(Save.saveClient(new Client(textField.getText(),tabPane))){
                NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SAVE);
            } else
                NotificationManager.showError(InfoType.ERROR_SAVE);

            Stage stage = (Stage)anchorPane.getScene().getWindow();
            stage.close();
        }

    }

    public String getName() {
        return this.name;
    }
}
