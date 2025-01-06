import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JarPath {

    private static Path jarPath;

    public static Path getJarPath() {
        if(jarPath == null){
            File jarFile;
            try {
                jarFile = new File(Reader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                if( (jarPath = jarFile.getParentFile().toPath()) == null){
                    jarPath = Paths.get("");
                }
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        return jarPath;

    }
}
