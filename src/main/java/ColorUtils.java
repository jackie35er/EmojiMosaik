import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class ColorUtils {
    public static double distance(Color c1, Color c2){
        float [] c1HSB = new float[3];
        float[] c2HSB = new float[3];
        Color.RGBtoHSB(c1.getRed(),c1.getGreen(),c1.getBlue(),c1HSB);
        Color.RGBtoHSB(c2.getRed(),c2.getGreen(),c2.getBlue(),c2HSB);
        float xd = c1HSB[0] - c2HSB[0];
        float yd = c1HSB[1] - c2HSB[1];
        float zd = c1HSB[2] - c2HSB[2];

        return Math.sqrt(xd*xd + yd*yd + zd*zd);
    }
    public static Color averageColours(@NotNull BufferedImage image) {
        int count = 0;
        float[] rgb = {0, 0, 0};
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color currentColor = new Color(image.getRGB(x,y),true);
                double alpha =currentColor.getAlpha()/255.;
                if( alpha != 1) continue;
                rgb[0] += currentColor.getRed()   * alpha ;
                rgb[1] += currentColor.getGreen() * alpha;
                rgb[2] += currentColor.getBlue()  * alpha;
                count++;
            }
        }
        if(count == 0){
            return Color.white;
        }
        return new Color(Math.round(rgb[0] / count),
                Math.round(rgb[1] / count),
                Math.round(rgb[2] / count));
    }
}
