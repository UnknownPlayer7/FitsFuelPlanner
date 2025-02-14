import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.nio.charset.StandardCharsets;

public class HelpController {

    @FXML
    private Label helpText;
    @FXML
    private Label infoText;

    public void printHelpInfo() {
        ResourceSupplier resourceSupplier = new ResourceSupplier();
        try{
            String text = new String(resourceSupplier.getByteArrayFromTextFile("Help.txt"),StandardCharsets.UTF_8);
            helpText.setText(text);
        } catch (Exception e) {
            NotificationManager.showError(InfoType.ERROR_SETTING);
        }
    }

    public void printInfo(String info){
        infoText.setText(info);
    }
}
