public enum InfoType {

    SUCCESSFUL_CREATE("Создание выполнено!"),
    ERROR_CREATE("Ошибка при создании!"),
    SUCCESSFUL_SAVE("Сохранение выполнено!"),
    ERROR_SAVE("Ошибка сохранения!"),
    SUCCESSFUL_SETTING("Изменение выполнено!"),
    ERROR_SETTING("Ошибка при изменении!"),
    ERROR_LOAD("Ошибка при загрузке");

    private String description;

    private InfoType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
