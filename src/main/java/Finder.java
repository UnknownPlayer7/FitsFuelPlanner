import javafx.scene.image.Image;

import java.io.File;
import java.util.Scanner;

public class Finder {


    public static String changeNumber(String string, double number){
        String target = findNumber(string);
        String newTarget = target.contains(",")? target.replace(",","."): target;
        Double targetDouble = Double.parseDouble(newTarget) + number;
        String targetString = String.format("%.1f",targetDouble);
        String result = string.replace(target,targetString.replace(".",","));

        return result;
    }

    public static String minusChangeNumber(String string, double number){
        String target = findNumber(string);
        String newTarget = target.contains(",")? target.replace(",","."): target;
        Double targetDouble = Double.parseDouble(newTarget) - number;
        String targetString = String.format("%.1f",targetDouble);
        String result = string.replace(target,targetString.replace(".",","));

        return result;
    }

    public static String findNumber(String string){
        String[] words = string.split(" ");
        String target = "";
        for(int i=0; i< words.length;i++){
            Scanner scanner = new Scanner(words[i]);
            if(scanner.hasNextDouble()){
                target = words[i];
                scanner.close();
                break;
            }
        }
        return target;
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
        Image icon = new Image(String.valueOf(Finder.class.getResource(path)));
        return icon;
    }
}
