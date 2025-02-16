
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class NotificationManager {

    public static void showError(InfoType errorType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Зараза!");
        alert.setContentText("— Я вижу, ты отлично находишь общий язык с троллем.\n— У меня большой опыт. Я всю жизнь работаю с идиотами.");
        String message;
        switch (errorType) {
            case ERROR_SAVE:
                message = "Ошибка сохранения!";
                break;
            case ERROR_CREATE:
                message = "Ошибка при создании!";
                break;
            case ERROR_SETTING:
                message = "Ошибка при установке!";
                break;
            case ERROR_LOAD:
                message = "Ошибка при загрузке";
                break;
            default:
                message = "Как всегда, неприятности находят меня первыми.";
        }
        alert.setHeaderText(message);
        alert.show();
    }

    public static void showSuccessfulInfo(InfoType infoType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setContentText("Когда умирает мечта, то тьма заполняет опустевшее место.");
        String message;
        switch (infoType) {
            case SUCCESSFUL_SAVE:
                message = "Сохранение выполнено!";
                break;
            case SUCCESSFUL_CREATE:
                message = "Создание выполнено!";
                break;
            case SUCCESSFUL_SETTING:
                message = "Установка завершена!";
                break;
            default:
                message = "Действие выполнено!";
        }
        alert.setHeaderText(message);
        alert.show();
    }

    public static void showDialogAndSetElementColor(ElementType elementType) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(elementType.getDecryption());
        dialog.setContentText("Введите HEX-код цвета:");
        dialog.setHeaderText("Не знаете, что такое HEX-код?\nА может не уверены, где его искать?\nОбратитесь в меню справки.");
        Optional<String> optional = dialog.showAndWait();
        optional.ifPresent(hexCodeColor -> {
            chooseAndInvokeSettingMethod(elementType, hexCodeColor);
            if(ResourceSupplier.setConfigFile("PDF.properties","/config/",elementType.getKey(), hexCodeColor))
                NotificationManager.showSuccessfulInfo(InfoType.SUCCESSFUL_SETTING);
            else
                NotificationManager.showError(InfoType.ERROR_SETTING);
        });
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

}
