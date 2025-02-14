import javafx.scene.control.Alert;

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

}
