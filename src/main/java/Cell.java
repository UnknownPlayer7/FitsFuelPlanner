import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class Cell {

    //Переопределяем ячейки для отображения имени объекта исп. setCellFactory
    public static Callback<ListView<Client>, ListCell<Client>> getCallBack(){
        Callback callback = new Callback<ListView<Client>, ListCell<Client>>() {
            @Override
            public ListCell<Client> call(ListView<Client> param) {
                return new ListCell<Client>() {
                    @Override
                    protected void updateItem(Client item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        };
        return callback;
    }

    //Переопределяем кнопку для отображения имени объекта исп. setButtonCell
    public static ListCell<Client> getListCell(){
      ListCell<Client> listCell =  new ListCell<Client>() {
            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        };
        return listCell;
    }



}
