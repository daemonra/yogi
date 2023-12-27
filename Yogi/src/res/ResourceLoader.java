package res;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Daemon
 */
public class ResourceLoader {

    /**
     * Helper function to read text files
     * @param resName
     * @return
     */
    public static InputStream loadResource(String resName){
        return ResourceLoader.class.getClassLoader().getResourceAsStream(resName);
    }
    
    /**
     * Helper function to read image files
     * @param resName
     * @return
     * @throws IOException
     */
    public static Image loadImage(String resName) throws IOException{
        URL url = ResourceLoader.class.getClassLoader().getResource(resName);
        return ImageIO.read(url);
    }
}
