package utils;

import constants.OperationType;
import javafx.scene.image.Image;

import java.util.Scanner;

public class Finder {


    public static String changeNumber(String string, double number, OperationType operation){
        String target = findNumber(string);
        double targetDouble = Double.parseDouble(target.replace(",","."));

        switch (operation) {
            case SUM:
               targetDouble += number;
               break;
            case SUB:
                targetDouble -=number;
        }
        String targetString = String.format("%.1f",targetDouble);

        return string.replace(target,targetString.replace(".",","));
    }

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

    public static String deleteNumber(String string){
        StringBuilder builder = new StringBuilder(string);
        for(int i=0;i<builder.length()/2;i++){
            if(builder.charAt(i) == ' '){
                builder.deleteCharAt(i);
            }else break;
        }
        int index = builder.indexOf(findNumber(string));
        builder.delete(index,builder.length());
        return builder.toString();
    }

    public static String findElementType(String string){
        String result;
        if(string.equals("Animal")){
            result = "Животные";
        }else if(string.equals("Plant")){
            result = "Растительные";
        }else if(string.equals("Complex")){
            result = "Сложные";
        }else if(string.equals("Simple")){
            result = "Простые";
        }else result = "Unknown";

        return result;
    }

    public static Image findIcon(String path){
        return new Image(String.valueOf(Finder.class.getResource(path)));
    }
}
