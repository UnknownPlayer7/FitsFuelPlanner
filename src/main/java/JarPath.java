import java.io.File;
import java.nio.file.Path;

public class JarPath {

    private static Path pathNearbyJar;
    private static Path jarPath;

    public static Path getPathNearbyJar() {
        if(pathNearbyJar == null){
            File jarFile;
            try {
                jarFile = new File(Reader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                pathNearbyJar = jarFile.getParentFile().toPath();
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        return pathNearbyJar;
    }

    public static Path getJarPath() {
        if(jarPath == null){
            File jarFile;
            try {
                jarFile = new File(Reader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                jarPath = jarFile.toPath();
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        return jarPath;
    }
}
