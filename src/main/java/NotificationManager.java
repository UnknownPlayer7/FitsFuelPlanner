
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;

public class NotificationManager {



    public static void showError(InfoType errorType) {
        String[] titleAndContentText = {MyText.getErrorTitle(),MyText.getErrorPhrase()};
        Alert alert = createAlert(Alert.AlertType.ERROR, errorType,titleAndContentText);
        alert.show();
    }

    public static void showSuccessfulInfo(InfoType infoType) {
        String[] titleAndContentText = {MyText.getSuccessTitle(),MyText.getSuccessPhrase()};
        Alert alert = createAlert(Alert.AlertType.INFORMATION, infoType,titleAndContentText);
        alert.show();
    }

    private static Alert createAlert(Alert.AlertType alertType, InfoType infoType, String...titleAndContentText) {
        Alert alert = new Alert(alertType);
        setIconFrame(alert);
        if(containExactlyTwoElements(titleAndContentText)) {
            alert.setTitle(titleAndContentText[0]);
            alert.setContentText(titleAndContentText[1]);
        }
        alert.setHeaderText(infoType.getDescription());
        return alert;
    }

    private static boolean containExactlyTwoElements(String[] strings) {
        return strings.length == 2;
    }

    public static void showDialogAndSetElementColor(ElementType elementType) {
        TextInputDialog dialog = new TextInputDialog();
        setIconFrame(dialog);
        dialog.setTitle(elementType.getDecryption());
        dialog.setContentText("Введите HEX-код цвета:");
        dialog.setHeaderText("Не знаете, что такое HEX-код?\nА может не уверены, где его искать?\nОбратитесь в меню справки.");
        Optional<String> optional = dialog.showAndWait();
        optional.ifPresent(hexCodeColor -> chooseAndInvokeSettingMethod(elementType, hexCodeColor));
    }

    private static void chooseAndInvokeSettingMethod(ElementType elementType, String hexCodeColor) {
        switch (elementType){
            case BORDER:
                WriterPdf.setBorderColor(hexCodeColor);
                break;
            case TEXT_IN_FRAME:
                WriterPdf.setTextColorInFrame(hexCodeColor);
                break;
            case TEXT_BEYOND_FRAME:
                WriterPdf.setTextColorBeyondFrame(hexCodeColor);
                break;
            case CELL_ANIMAL_ELEMENT:
                WriterPdf.setCellColorAnimalElement(hexCodeColor);
                break;
            case CELL_PLANT_ELEMENT:
                WriterPdf.setCellColorPlantElement(hexCodeColor);
                break;
            case CELL_COMPLEX_CARB:
                WriterPdf.setCellColorComplexCarb(hexCodeColor);
                break;
            case CELL_SIMPLE_CARB:
                WriterPdf.setCellColorSimpleCarb(hexCodeColor);
        }
    }

    private static void setIconFrame(Dialog<?> dialog) {
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(Finder.findIcon("/images/Tree.png"));
    }
}
