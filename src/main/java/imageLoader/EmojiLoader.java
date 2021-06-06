package imageLoader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EmojiLoader {
    private List<BufferedImage> emojis;
    public EmojiLoader(String path) throws IOException {
        emojis = new ArrayList<>();
        loadEmojis(path);
    }
    private void loadEmojis(String path) throws IOException {
        File directory = new File(path);
        if(!directory.isDirectory()){
            throw new IllegalArgumentException("path must be a directory");
        }
        File[] files = directory.listFiles((file,name) -> name.endsWith(".png"));
        if(files == null){
            throw new IllegalArgumentException("path does not exist");
        }
        int resizeRate = 3;
        for(File file : files){
            BufferedImage currentImage = ImageIO.read(file);
            BufferedImage resizedImage = new BufferedImage(currentImage.getWidth() / resizeRate,currentImage.getHeight() / resizeRate , BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(currentImage,0,0,currentImage.getWidth() / resizeRate,currentImage.getHeight() / resizeRate,null);
            g2d.dispose();
            emojis.add(resizedImage);
        }
        System.out.println("loading done");
    }




    public static void main(String[] args) {
        EmojiLoader ej;
        try {
            ej = new EmojiLoader(EmojiLoader.class.getClassLoader().getResource("emojis/").getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<BufferedImage> getEmojis() {
        return emojis;
    }
}
