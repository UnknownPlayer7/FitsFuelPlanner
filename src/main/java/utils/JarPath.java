package utils;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class JarPath {

    private static Path pathNearbyJar;

    static {
        try {
            File jarFile = new File(Reader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            JarPath.pathNearbyJar = jarFile.getParentFile().toPath();
        }
        catch (URISyntaxException e){
            System.out.println(e);
        }
    }

    public static Path getPathNearbyJar() {
        return JarPath.pathNearbyJar;
    }
}
