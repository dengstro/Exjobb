//Java program to perform discrete cosine transform 
package DCTTransformedTest;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

public class DctStego
{
    public static void encode(String imgName, List Squares)
    {
        int bitIndex = 0;
        int byteIndex = 0;

        int[][] red = new int[8][8];
        int[][] green = new int[8][8];
        int[][] blue = new int[8][8];

        int[][] tRed = new int[8][8];
        int[][] tGreen = new int[8][8];
        int[][] tBlue = new int[8][8];

        Image img = new Image();
        Dct dct = new Dct();
        BufferedImage buffImg = img.readImage(imgName);
        buffImg = img.ImageToOptSize(buffImg);

        // G� igenom alla pixlar i bild. G� igenom 8x8 �t g�ngen
        for (int k = 0; k < buffImg.getHeight(); k = k + 8)
        {
            for (int h = 0; h < buffImg.getWidth(); h = h + 8)
            {

                for (int j = k; j < k + 8; j++)
                {
                    for (int i = h; i < h + 8; i++)
                    {
                        Color color = new Color(buffImg.getRGB(i, j));
                        red[j % 8][i % 8] = color.getRed();
                        green[j % 8][i % 8] = color.getGreen();
                        blue[j % 8][i % 8] = color.getBlue();

                    }

                }

                tRed = dct.transform(red);
                tGreen = dct.transform(green);
                tBlue = dct.transform(blue);
                //
                // k�r lsb alt liknande h�r
                //

                for (int j = k; j < k + 8; j++)
                {
                    for (int i = h; i < h + 8; i++)
                    {
                        ((List) ((List) Squares.get(i % 8)).get(j % 8)).add(tRed[j % 8][i % 8]);
                        ((List) ((List) Squares.get(i % 8)).get(j % 8)).add(tGreen[j % 8][i % 8]);
                        ((List) ((List) Squares.get(i % 8)).get(j % 8)).add(tBlue[j % 8][i % 8]);
                    }
                }

            }
        }

    }

}
