import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class HelpController {

    @FXML
    private Label helpText;
    @FXML
    private Label infoText;

    public void printHelpInfo() {
        String path = String.valueOf(HelpController.class.getResource("/resources/texts/Help.txt"));
        Reader reader = new Reader(path);
        List<String> lists = reader.readTextFile();
        String information = "";
        for(String list:lists){
            information = information + list + "\n";
        }
        helpText.setText(information);
    }

    public void printInfo(String info){
        infoText.setText(info);
    }
}
