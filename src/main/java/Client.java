import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Client extends BagWithProducts implements Serializable {

    private String name;
    private HashMap<Integer, ArrayList<Goods>> map = new HashMap<>();

    Client(String name, TabPane tabPane){
        this.name = name;
        createStorageProduct(tabPane);
    }

    public String getName() {
        return this.name;
    }

    public HashMap<Integer, ArrayList<Goods>> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, ArrayList<Goods>> map) {
        this.map = map;
    }

    public void createStorageProduct(TabPane tabPane){
        HashMap<Integer, ArrayList<Goods>> map = new HashMap<>();
        TableView tableView;
        AnchorPane anchorPane;
        Goods product;
        int i=0;
        for(Tab tab: tabPane.getTabs()){
            Node content = tab.getContent();
            if(content instanceof AnchorPane){
                anchorPane = (AnchorPane) content;
                for(Node content2:anchorPane.getChildren()){
                    if(content2 instanceof TableView){
                        i++;
                        tableView =(TableView) content2;
                        ArrayList<Goods> products = new ArrayList<>();
                        for(Object object2: tableView.getItems()){
                            if(object2 instanceof Goods){
                                product = (Goods) object2;
                                products.add(product);
                            }
                        }
                        map.put(i,products);
                    }
                }
            }


        }
        setMap(map);
    }

}
