import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    private String path;
    private static String jarPath;

    public Reader(String path){
        this.path = path;
        jarPath = String.valueOf(JarPath.getJarPath());
    }

    //Read a file which locate into the jar
    public List<String> readTextFile(){

        File file = new File(path);
        try(FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader)){

            List<String> lists = new ArrayList<>();

            while(bufferedReader.ready()){
                lists.add(bufferedReader.readLine());
            }
            return lists;

        }
        catch(IOException e){
            System.out.println("Didn't have access to file: "+e);
            return null;
        }
    }

    //Read a file which locate outer the jar
    public List<String> read(){

        File file = new File(jarPath+path);
        try(FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader)){

            List<String> lists = new ArrayList<>();

            while(bufferedReader.ready()){
                lists.add(bufferedReader.readLine());
            }
            return lists;

        }
        catch(IOException e){
            System.out.println("Didn't have access to file: "+e);
            return null;
        }
    }

    public Goods readObject(){
        Goods product;

        try(FileInputStream reader = new FileInputStream(jarPath+path);
            ObjectInputStream objectReader = new ObjectInputStream(reader)){
            product = (Goods) objectReader.readObject();
        }
        catch(IOException  | ClassNotFoundException exc){
            System.out.println("Good wasn't able to read: "+exc );
            return null;
        }

        return product;
    }

    public  ArrayList<Goods> readAllObject(){
        ArrayList<Goods> array = new ArrayList<Goods>();
        Goods product;

        try(FileInputStream reader = new FileInputStream(jarPath+path);
            ObjectInputStream objectReader = new ObjectInputStream(reader)){
            while(true){
                try{
                    product = (Goods) objectReader.readObject();
                    array.add(product);
                }
                catch (EOFException exc){
                    break;
                }
            }
        }
        catch(IOException  | ClassNotFoundException exc){
            System.out.println("Goods wasn't been able to read: "+exc );
        }

        return array;
    }

    public ArrayList<Client> readAllClients(){
        ArrayList<Client> array = new ArrayList<Client>();
        Client client;

        try(FileInputStream reader = new FileInputStream(jarPath+path);
            ObjectInputStream objectReader = new ObjectInputStream(reader)){
            while(true){
                try{
                    client = (Client) objectReader.readObject();
                    array.add(client);
                }
                catch (EOFException exc){
                    break;
                }
            }
        }
        catch(IOException  | ClassNotFoundException exc){
            System.out.println("Client's wasn't able to read: "+exc );
        }

        return array;
    }


}
