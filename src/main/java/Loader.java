import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.HashMap;

public class Loader {

    private static Tab currentTab;
    private static int countOfTabs = 1;

    public static void setCurrentTab(Tab tab) {
        Loader.currentTab = tab;
    }

    public static Tab getCurrentTab() {
        return currentTab;
    }

    public static void buildTables(Client client, NewTabController tab) {
        WorkspaceController workspace = ControllersArchive.getWorkspaceController();
        HashMap<Integer, ArrayList<Goods>> map = client.getMap();
        ObservableList<Goods> list;
        if (map.containsKey(countOfTabs) && !map.get(countOfTabs).isEmpty()) {
            list = FXCollections.observableArrayList(map.get(countOfTabs));
            tab.addToListProduct(list);
            Changer.changeLabels(map.get(countOfTabs),workspace);
            Changer.changeComboBox(map.get(countOfTabs),tab);
            countOfTabs++;
            workspace.createNewTab();
        }else{
            list = FXCollections.observableArrayList();
            tab.addToListProduct(list);
        }
        ControllersArchive.setCurrentTab(tab);



    }

    public static void setCountOfTabs(int countOfTabs) {
        Loader.countOfTabs = countOfTabs;
    }
}
