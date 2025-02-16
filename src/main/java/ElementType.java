public enum ElementType {
    BORDER("Рамка","borderColor"),
    TEXT_IN_FRAME("Текст в рамке", "textColorInFrame"),
    TEXT_BEYOND_FRAME("Текст вне рамки","textColorBeyondFrame"),
    CELL_ANIMAL_ELEMENT("Ячейка животных белков/жиров","cellColorAnimalElement"),
    CELL_PLANT_ELEMENT("Ячейка растительных белков/жиров","cellColorPlantElement"),
    CELL_COMPLEX_CARB("Ячейка сложных углеводов","cellColorComplexCarb"),
    CELL_SIMPLE_CARB("Ячейка простых углеводов","cellColorSimpleCarb");

    private final String decryption;
    private final String key;

    ElementType(String decryption, String key) {
        this.decryption = decryption;
        this.key = key;
    }

    public String getDecryption() {
        return decryption;
    }

    public String getKey() {
        return key;
    }
}
