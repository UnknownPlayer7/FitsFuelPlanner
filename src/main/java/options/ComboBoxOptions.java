package options;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import models.Client;

/**
 * The ComboBoxOptions is a utility class for customizing object representation within a
 * {@link javafx.scene.control.ComboBox}.
 *
 * <p>The {@code getCallBack} method defines how objects are rendered inside the drop-down list.</p>
 * <p>The {@code getListCell} method defines how the selected object is rendered on the ComboBox button.</p>
 *
 * <p>Note: The default implementation in this class displays the object's "name" attribute.</p>
 * <p>Example: {@link Client}</p>
 */
public class ComboBoxOptions {

    public static Callback<ListView<Client>, ListCell<Client>> getCallBack() {
        return new Callback<ListView<Client>, ListCell<Client>>() {
            @Override
            public ListCell<Client> call(ListView<Client> param) {
                return getUpdatedListCell();
            }
        };
    }

    private static ListCell<Client> getUpdatedListCell() {
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

    public static ListCell<Client> getListCell() {
        return getUpdatedListCell();
    }
}
