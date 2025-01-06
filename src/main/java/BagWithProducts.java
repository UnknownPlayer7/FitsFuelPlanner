import java.io.Serializable;
import java.util.HashMap;

public class BagWithProducts implements Serializable {

    private HashMap<String,Goods> bag = new HashMap<String, Goods>();

    public HashMap<String, Goods> getBag() {
        return bag;
    }
    public void setBag(HashMap<String, Goods> bag) {
        this.bag = bag;
    }
}
