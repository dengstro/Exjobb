package DCTTransformedTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image
{

    /**
     * *
     * L�sa in en bild
     */
    public BufferedImage readImage(String Name)
    {
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File(Name));
        } catch (IOException e)
        {
        }
        return img;
    }

    /**
     * *
     * Skriva bild till fil.
     */
    public void writeImage(String Name, BufferedImage img)
    {
        File outputfile = new File(Name);
        try
        {
            ImageIO.write(img, "png", outputfile); // "png" betyder inget. �r filnamn som best�mmer.
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * *
     * �ndra bild s� den �r optimerade f�r 8x8 block.
     */
    public BufferedImage ImageToOptSize(BufferedImage image)
    {
        int height = image.getHeight();
        int width = image.getWidth();

        image = image.getSubimage(0, 0, width - (width % 8), height - (height % 8));
        return image;
    }

}
