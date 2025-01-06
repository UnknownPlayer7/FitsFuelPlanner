import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HelpController {

    @FXML
    private Label helpText;
    @FXML
    private Label infoText;

    public void printHelpInfo() {
        try{
            URL resourceUrl = HelpController.class.getResource("/texts/Help.txt");
            if (resourceUrl != null) {
                Path resourcePath = Paths.get(resourceUrl.toURI());
                Reader reader = new Reader(resourcePath.toString());
                List<String> lists = reader.readTextFile();
                String information = "";
                for(String list:lists){
                    information = information + list + "\n";
                }
                helpText.setText(information);
            } else  System.out.println("Help file not founded.");
        }catch (URISyntaxException e) { e.printStackTrace();}
    }

    public void printInfo(String info){
        infoText.setText(info);
    }
}
