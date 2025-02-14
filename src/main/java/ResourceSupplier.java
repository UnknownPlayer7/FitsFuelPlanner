
import de.schlichtherle.truezip.file.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class ResourceSupplier {

    private static final String MAIN_ARCHIVE;
    private static final String RESOURCE_ARCHIVE = "resource.jar";
    private static final String IMAGES_DIR = "/images/";
    private static final String TEXTS_DIR = "/texts/";

    static {
        MAIN_ARCHIVE = String.valueOf(JarPath.getJarPath());
    }

    private URL getImageFromMainArchive(String name, String dir) {
        if(name != null && dir != null) {
            return this.getClass().getResource(dir+name);
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
            URL url = getImageFromMainArchive(fileName, IMAGES_DIR);
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
            System.out.println(e);
            System.out.println("Exception! Can't replace old Image.");
            return false;
        }
        commitChangesIntoArchive();
        return true;
    }

//    public boolean setProperties(String propertyName, String newValue, String configFile, String dir) {
//        try(TFileInputStream reader = new TFileInputStream(RESOURCE_ARCHIVE + dir + configFile)) {
//            byte[] bytes = new byte[reader.available()];
//            reader.read(bytes);
//            System.out.println(Arrays.toString(bytes));
//            return true;
//        }catch (Exception e) {
//            System.out.println(e);
//            return false;
//        }
//
//    }

    public InputStream getInputStream(String fileName, String dir) {
        if(fileExistsInArchive(fileName, dir)) {
            try {
                return new TFileInputStream(RESOURCE_ARCHIVE + dir + fileName);
            } catch (FileNotFoundException e) {
                System.out.println("Exception! File not found");
            }
        }else {
            try(InputStream in = this.getClass().getResourceAsStream(dir + fileName);
                TFileOutputStream out = new TFileOutputStream(RESOURCE_ARCHIVE + dir + fileName)) {
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                out.write(bytes);
            }
            catch (Exception e) {
                return null;
            }
        }
        return getInputStream(fileName, dir);
    }


    public Properties getProperties(String configFile, String dir) {
        Properties properties = new Properties();
        try {
            properties.load(getInputStream(configFile,dir));
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

}
