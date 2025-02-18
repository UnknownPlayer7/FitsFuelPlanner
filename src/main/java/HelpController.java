import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.nio.charset.StandardCharsets;

public class HelpController {

    @FXML
    private Label helpText;
    @FXML
    private Label infoText;

    public void printHelpInfo() {
        try{
            String text = new String(ResourceSupplier.getByteArrayFromTextFile("Help.txt"),StandardCharsets.UTF_8);
            helpText.setText(text);
        } catch (Exception e) {
            NotificationManager.showError(InfoType.ERROR_LOAD);
        }
    }

    public void printInfo(String info){
        infoText.setText(info);
    }
}
