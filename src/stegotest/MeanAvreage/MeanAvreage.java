/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stegotest.MeanAvreage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import javax.imageio.ImageIO;

/**
 *
 * @author David Engström <Davids-epost@hotmail.com>
 */
public class MeanAvreage
{
    public static BigDecimal getMeanAvreage(String oldImage, String newImage)
    {
        BufferedImage orgImg = null;
        BufferedImage newImg = null;

        try
        {
            orgImg = ImageIO.read(new File(oldImage));
            newImg = ImageIO.read(new File(newImage));
        } catch (IOException e)
        {
            System.out.println("failed to load");
        }

        BigDecimal bigDecimal = new BigDecimal("0");

        for (int i = 0; i < orgImg.getHeight(); i++)
        {
            for (int j = 0; j < orgImg.getWidth(); j++)
            {
                if (orgImg.getRGB(j, i) != newImg.getRGB(j, i))
                {
                    int redDif = Math.abs(new Color(orgImg.getRGB(j, i)).getRed() - new Color(newImg.getRGB(j, i)).getRed());
                    int greenDif = Math.abs(new Color(orgImg.getRGB(j, i)).getGreen() - new Color(newImg.getRGB(j, i)).getGreen());
                    int blueDif = Math.abs(new Color(orgImg.getRGB(j, i)).getBlue() - new Color(newImg.getRGB(j, i)).getBlue());

                    bigDecimal = bigDecimal.add(bigDecimal.valueOf(redDif * redDif));
                    bigDecimal = bigDecimal.add(bigDecimal.valueOf(greenDif * greenDif));
                    bigDecimal = bigDecimal.add(bigDecimal.valueOf(blueDif * blueDif));
                }
            }
        }

        //System.out.println("total: " + bigDecimal.toString());

        BigDecimal bd = bigDecimal.valueOf(orgImg.getWidth() * orgImg.getHeight());
        //System.out.println("mean: " + bigDecimal.divide(bd, MathContext.DECIMAL32));

        return bigDecimal.divide(bd, MathContext.DECIMAL32);
    }
}
