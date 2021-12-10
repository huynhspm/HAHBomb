package bom_it.engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSheet {

    private final String path;
    private int[] pixels;
    private int widthImageSheet;
    private final int heightImage;
    private final int widthImage;

    public static ImageSheet imageGame = new ImageSheet("src/main/resources/bom_it/Picture/ImageHAHBomb.png", 40, 60);
    public static ImageSheet imageMap = new ImageSheet("src/main/resources/bom_it/Picture/ChooseMap.png", 620, 430);
    public static ImageSheet imageButton = new ImageSheet("src/main/resources/bom_it/Picture/ImageButton.png", 300, 80);
    public static ImageSheet imageBorder = new ImageSheet("src/main/resources/bom_it/Picture/Border.png", 860, 600);
    public static ImageSheet imageInformation = new ImageSheet("src/main/resources/bom_it/Picture/Information.png", 180, 120);
    public static ImageSheet imageIcon = new ImageSheet("src/main/resources/bom_it/Picture/ImageIcon.png", 50, 50);
    public static ImageSheet imageLWP = new ImageSheet("src/main/resources/bom_it/Picture/ImageLWP.png", 298, 298);


    public int[] getPixels() {
        return pixels;
    }

    public int getWidthImageSheet() {
        return widthImageSheet;
    }

    public int getHeightImage() {
        return heightImage;
    }

    public int getWidthImage() {
        return widthImage;
    }

    // constructor.
    public ImageSheet(String path, int widthImage, int heightImage) {
        this.path = path;
        this.widthImage = widthImage;
        this.heightImage = heightImage;
        load();
    }

    // convert from image to array of pixels.
    private void load() {
        try {
            BufferedImage image;
            image = ImageIO.read(new File(path));
            int width = image.getWidth();
            int height = image.getHeight();

            this.widthImageSheet = width;

            pixels = new int[height * width];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

