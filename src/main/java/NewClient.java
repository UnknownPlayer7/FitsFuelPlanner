import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;

public class NewClient extends Client implements Serializable {
    private static NewClient instance;
    private NewClient(){
        super("Новый клиент",createTabPane());
    }

    public static NewClient getInstance(){
        if(instance == null){
            instance = new NewClient();
        }
        return instance;
    }

    private static TabPane createTabPane(){
        TabPane newTabPane = new TabPane();
        Tab tab = new Tab();
        AnchorPane anchorPane = new AnchorPane(new TableView<Goods>());
        tab.setContent(anchorPane);
        newTabPane.getTabs().add(tab);
        return newTabPane;
    }

}
