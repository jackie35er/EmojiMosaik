import imageLoader.EmojiLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class EmojiMosaicCreator {

    private final EmojiLoader emojis;
    private final BufferedImage srcImage;

    public EmojiMosaicCreator(String srcPath, String emojiPath) throws IOException {
        this.emojis = new EmojiLoader(emojiPath);
        this.srcImage = ImageIO.read(new File(srcPath));
        drawImage();
    }

    private void drawImage(){
        int eWidth = emojis.getEmojis().get(0).getWidth();
        int eHeight = emojis.getEmojis().get(0).getHeight();
        BufferedImage thumbnail = new BufferedImage(srcImage.getWidth()/eWidth,srcImage.getHeight()/eHeight , BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = thumbnail.createGraphics();
        g2d.drawImage(srcImage,0,0,srcImage.getWidth()/eWidth,srcImage.getHeight()/eHeight,null);
        g2d.dispose();
        Graphics g = srcImage.getGraphics();
        for(int xthumb = 0; xthumb < thumbnail.getWidth(); xthumb++){
            for(int ythumb = 0; ythumb < thumbnail.getHeight(); ythumb++){
                BufferedImage emojisImage = findClosestImage(new Color(thumbnail.getRGB(xthumb,ythumb)),emojis.getEmojis());
                g.drawImage(emojisImage,xthumb * eWidth,ythumb * eHeight,null);
            }
        }
        try {
            ImageIO.write(srcImage,"png",new FileOutputStream("src/main/resources/output.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage findClosestImage(Color srcColor, List<BufferedImage> targets){
        double min_diff = Double.MAX_VALUE;
        BufferedImage minImage = targets.get(0);
        for(BufferedImage curImage : targets){
            double distance = ColorUtils.distance(srcColor,ColorUtils.averageColours(curImage));
            if(distance < min_diff){
                minImage = curImage;
                min_diff = distance;
            }
        }
        return minImage;
    }

    public static void main(String[] args) throws IOException {
        new EmojiMosaicCreator("src/main/resources/tree.jpg",EmojiLoader.class.getClassLoader().getResource("emojis/").getPath());
    }

}
