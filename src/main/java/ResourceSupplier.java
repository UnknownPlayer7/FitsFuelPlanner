
import de.schlichtherle.truezip.file.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ResourceSupplier {

    private static final String RESOURCE_ARCHIVE = "resource.jar";
    private static final String IMAGES_DIR = "/images/";
    private static final String TEXTS_DIR = "/texts/";

    private URL getImageFromMainArchive(String name) {
        if(name != null) {
            return this.getClass().getResource(IMAGES_DIR + name);
        }
        return null;
    }

    private byte[] getFileByteArrayFromMainArchive(String name, String dir) {
        if(name != null && dir != null) {
            try(InputStream in = this.getClass().getResourceAsStream(dir+name)) {

                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                return bytes;

            } catch (Exception e) {
                System.out.println("Exception! Can't read file from main archive.");
            }
        }
        return null;
    }

    private TFile getFileFromArchive(String name, String dir) {
        if(name != null && dir != null) {
            if(fileExistsInArchive(name, dir))
                return new TFile(RESOURCE_ARCHIVE + dir + name);
        }
        return null;
    }

    private Image getImageFromArchive(String fileName) {
        if(fileName != null){
            if(fileExistsInArchive(fileName, IMAGES_DIR))
                try{
                    return new Image(new TFileInputStream(RESOURCE_ARCHIVE + IMAGES_DIR + fileName));
                } catch (IOException e) {
                    System.out.println("Exception! Can't convert file to image!");
                }
        }
        return null;
    }

    private boolean fileExistsInArchive(String fileName, String dir) {
        TFile tFile = new TFile(RESOURCE_ARCHIVE + dir + fileName);
        return tFile.exists();
    }

    public Image getImage(String fileName) {
        Image image;
        if((image = getImageFromArchive(fileName)) != null) {
            commitChangesIntoArchive();
            return image;
        } else {
            URL url = getImageFromMainArchive(fileName);
            return new Image(String.valueOf(url));
        }
    }

    public byte[] getByteArrayFromTextFile(String fileName) {
        if(fileName != null)
            return getFileByteArrayFromMainArchive(fileName, TEXTS_DIR);
        return null;
    }

    public byte[] getImageByteArray(String fileName) {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(getImage(fileName), null);
            ImageIO.write(bufferedImage,"jpg",baos);
            return baos.toByteArray();
        } catch (Exception e) {
            System.out.println("Exception! Can't convert Image to Stream");
            return null;
        }
    }

    public boolean setImage(String oldImageName, File newImageFile) {
        TFile newImage = new TFile(newImageFile);
        TFile oldImage = getFileFromArchive(oldImageName, IMAGES_DIR);
        if(oldImage == null) oldImage = new TFile(RESOURCE_ARCHIVE + IMAGES_DIR + oldImageName);
        try {
            newImage.cp_p(oldImage);
        } catch (IOException e) {
            System.out.println("Exception! Can't replace old Image.");
            return false;
        }
        commitChangesIntoArchive();
        return true;
    }

    public static Properties getProperties(String configFile, String dir) {
        Properties properties = new Properties();
        Path filePath = Paths.get(JarPath.getPathNearbyJar() + dir + configFile);
        if(isNotFileExistsNearbyJar(filePath)) {
            moveConfigFileNearbyJar(configFile,dir);
        }
        try {
            properties.load(Files.newInputStream(filePath));
        } catch (IOException e) {
            NotificationManager.showError(InfoType.ERROR_LOAD);
        }
        return properties;
    }

    private void commitChangesIntoArchive() {
        try {
            TVFS.umount();
        } catch (Exception e) {
            System.out.println("Exception! FsSyncWarningException.");
        }
    }

    public static void moveConfigFileNearbyJar(String fileName, String dir) {
        Path filePath = Paths.get(JarPath.getPathNearbyJar() + dir + fileName);
        if(isNotFileExistsNearbyJar(filePath)) {
            createDirectory(filePath.getParent());
            try(InputStream in = ResourceSupplier.class.getResourceAsStream(dir + fileName);
                BufferedWriter writer= new BufferedWriter(new FileWriter(JarPath.getPathNearbyJar() + dir + fileName))) {
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                writer.write(new String(bytes));
            } catch (Exception e) {
                System.out.println("Exception! Can't create system file.");
            }
        }
    }

    public static boolean setConfigFile(String fileName, String dir, String key, String value) {
        moveConfigFileNearbyJar(fileName,dir);
        Properties properties = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get(JarPath.getPathNearbyJar() + dir + fileName))) {
            properties.load(in);
            properties.setProperty(key,value);
        } catch (Exception e) {
            System.out.println("Exception! Can't read config file." + e);
        }
        try(OutputStream out = Files.newOutputStream(Paths.get(JarPath.getPathNearbyJar() + dir + fileName))) {
            properties.store(out,"Update properties");
            return true;
        } catch (Exception e) {
            System.out.println("Exception! Can't update config file." + e);
        }
        return false;
    }

    private static boolean isNotFileExistsNearbyJar(Path filePath) {
        return !Files.exists(filePath);
    }

    private static void createDirectory(Path pathDir){
        try{
            if(!Files.exists(pathDir)) {
                Files.createDirectory(pathDir);
            }
        }
        catch(IOException exc){
            System.out.println("Something went wrong: "+exc );
        }
    }

}
