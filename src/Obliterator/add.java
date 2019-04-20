/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Obliterator;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author David Engström <Davids-epost@hotmail.com>
 */
public class add
{
    private static int byteToReplaceingWith = 0;

    public static void add(String imgName, String ResultName, int add)
    {
        BufferedImage image = getImage(imgName);

        image = removePlaneAndBellow(image, add);

        setImage(image, new File(ResultName), "png");
    }

    public static BufferedImage removePlaneAndBellow(BufferedImage image, int add)
    {
        byte img[] = get_byte_data(image);
        try
        {
            removePlaneAndBellowByte(img, add);
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return image;
    }

    private static byte[] removePlaneAndBellowByte(byte[] image, int add)
    {
        //check that the data + offset will fit in the image
        //loop through each addition byte
        int offset = 0;
        for (int i = 0; i < image.length / 8; ++i)
        {

            for (int bit = 7; bit >= 0; --bit, ++offset) //ensure the new offset value carries on through both loopsobl
            {

                //assign an integer to b, shifted by bit spaces AND 1
                //a single bit of the current byte
                int b = (byteToReplaceingWith >>> bit) & 1;
                //assign the bit by taking: [(previous byte value) AND 0xfe] OR bit to add
                //changes the last bit of the byte in the image to be the bit of addition
                //System.out.println(image[offset]);

                image[offset] = (byte) (image[offset] + add);
            }
        }

        return image;
    }

    public static BufferedImage getImage(String f)
    {
        BufferedImage image = null;
        File file = new File(f);

        try
        {
            image = ImageIO.read(file);
        } catch (Exception ex)
        {
            System.out.println("Image could not be read!");
        }
        return image;
    }

    private static byte[] get_byte_data(BufferedImage image)
    {
        WritableRaster raster = image.getRaster();
        DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
        return buffer.getData();
    }

    /*
	 *Set method to save an image file
	 *@param image The image file to save
	 *@param file	  File  to save the image to
	 *@param ext	  The extension and thus format of the file to be saved
	 *@return Returns true if the save is succesful
     */
    public static boolean setImage(BufferedImage image, File file, String ext)
    {
        try
        {
            file.delete(); //delete resources used by the File
            ImageIO.write(image, ext, file);
            return true;
        } catch (Exception e)
        {
            System.out.println("File could not be saved!");
            return false;
        }
    }
}
