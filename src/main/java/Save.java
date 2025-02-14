import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Save {
    private static final Path pathDir = Paths.get(JarPath.getPathNearbyJar()+"/Goods");
    private static final File FILE_PRODUCTS = new File(JarPath.getPathNearbyJar()+"/Goods/Product.txt");
    private static final File FILE_CLIENTS = new File(JarPath.getPathNearbyJar()+"/Goods/Client.txt");

    public static boolean save(Goods object){
        if(!createDirectory()) {
            System.out.println("Неудалось создать директорию и файл для хранения картотеки!");
        }
        boolean append = new File(String.valueOf(FILE_PRODUCTS)).exists();
        try(FileOutputStream writer = new FileOutputStream(FILE_PRODUCTS,true);
            ObjectOutputStream objectWriter = append? new MyObjectOutputStream(writer):
                                                        new ObjectOutputStream(writer)){
            objectWriter.writeObject(object);
            ControllersArchive.getLibraryController().putInLib(object);

        }
        catch(IOException | ClassCastException exc){
            System.out.println("Something went wrong while programme was trying to save Goods: "+exc );
            return false;
        }

        return true;
    }

    public static boolean save(Client object){
        if(!createDirectory()) {
            System.out.println("Неудалось создать директорию и файл для хранения картотеки!");
        }
        boolean append = new File(String.valueOf(FILE_CLIENTS)).exists();
        try(FileOutputStream writer = new FileOutputStream(FILE_CLIENTS,true);
            ObjectOutputStream objectWriter = append? new MyObjectOutputStream(writer):
                    new ObjectOutputStream(writer)){
            objectWriter.writeObject(object);

        }
        catch(IOException | ClassCastException exc){
            System.out.println("Something went wrong: "+exc );
            return false;
        }

        return true;
    }

    private static boolean createDirectory(){
        try{
            if(!Files.exists(pathDir)) Files.createDirectory(pathDir);
        }
        catch(IOException exc){
            System.out.println("Something went wrong: "+exc );
            return false;
        }
        return true;
    }





}
