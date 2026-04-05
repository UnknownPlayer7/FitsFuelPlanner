package utils;

import java.util.Scanner;

public class StringFinder {

    public static String findNumber(String string){
        String[] words = string.split(" ");
        String number = "";

        for (String word : words) {
            Scanner scanner = new Scanner(word);

            if (scanner.hasNextDouble()) {
                number = word;
                scanner.close();
                break;
            }
        }
        return number;
    }

    public static String deleteNumber(String string) {
        StringBuilder builder = new StringBuilder(string.trim());

        int index = builder.indexOf(findNumber(string));
        builder.delete(index,builder.length());

        return builder.toString();
    }

    public static String findElementType(String string) {
        String result;

        switch (string) {
            case "Animal":
                result = "Животные";
                break;
            case "Plant":
                result = "Растительные";
                break;
            case "Complex":
                result = "Сложные";
                break;
            case "Simple":
                result = "Простые";
                break;
            default:
                result = "Unknown";
                break;
        }
        return result;
    }
}
