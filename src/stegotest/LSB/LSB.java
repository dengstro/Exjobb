/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stegotest.LSB;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author David Engström <Davids-epost@hotmail.com>
 */
public class LSB
{
    public static void InjectWithLsb(String imgName, String payload, String ResultName, int bitLevelsFromLSB)
    {
        BufferedImage image = getImage(imgName);

        image = add_text(image, payload, bitLevelsFromLSB);

        setImage(image, new File(ResultName), "png");
    }

    public static BufferedImage add_text(BufferedImage image, String text, int bitLevelsFromLSB)
    {
        //convert all items to byte arrays: image, message, message length
        byte img[] = get_byte_data(image);
        byte msg[] = text.getBytes();
        byte len[] = bit_conversion(msg.length);
        try
        {

            encode_text(img, len, 0, bitLevelsFromLSB); //0 first positiong
            encode_text(img, msg, 32, bitLevelsFromLSB); //4 bytes of space for length: 4bytes*8bit = 32 bits
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return image;
    }

    /*
	 *Encode an array of bytes into another array of bytes at a supplied offset
	 *@param image	 Array of data representing an image
	 *@param addition Array of data to add to the supplied image data array
	 *@param offset	  The offset into the image array to add the addition data
	 *@return Returns data Array of merged image and addition data
     */
    private static byte[] encode_text(byte[] image, byte[] addition, int offset, int bitLevelsFromLSB)
    {
        //check that the data + offset will fit in the image
        //loop through each addition byte
        int bitLevelsUsed = 0;
        for (int i = 0; i < addition.length; ++i)
        {

            //loop through the 8 bits of each byte
            int add = addition[i];
            for (int bit = 7; bit >= 0; --bit, ++offset) //ensure the new offset value carries on through both loops
            {
                // If the entire plane is used up, start from the begining and increase bitLevelsUsed by 1 
                if (offset == image.length)
                {
                    offset = 0;
                    bitLevelsUsed++;
                }

                //assign an integer to b, shifted by bit spaces AND 1
                //a single bit of the current byte
                int b = (add >>> bit) & 1;
                //assign the bit by taking: [(previous byte value) AND 0xfe] OR bit to add
                //changes the last bit of the byte in the image to be the bit of addition
                //System.out.println(image[offset]);

                image[offset] = (byte) (image[offset] & ((int) (0xFF - (Math.pow(0x02, (bitLevelsFromLSB + bitLevelsUsed))))) | b);
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
	 *Gernerates proper byte format of an integer
	 *@param i The integer to convert
	 *@return Returns a byte[4] array converting the supplied integer into bytes
     */
    private static byte[] bit_conversion(int i)
    {
        //originally integers (ints) cast into bytes
        //byte byte7 = (byte)((i & 0xFF00000000000000L) >>> 56);
        //byte byte6 = (byte)((i & 0x00FF000000000000L) >>> 48);
        //byte byte5 = (byte)((i & 0x0000FF0000000000L) >>> 40);
        //byte byte4 = (byte)((i & 0x000000FF00000000L) >>> 32);

        //only using 4 bytes
        byte byte3 = (byte) ((i & 0xFF000000) >>> 24); //0
        byte byte2 = (byte) ((i & 0x00FF0000) >>> 16); //0
        byte byte1 = (byte) ((i & 0x0000FF00) >>> 8); //0
        byte byte0 = (byte) ((i & 0x000000FF));
        //{0,0,0,byte0} is equivalent, since all shifts >=8 will be 0
        return (new byte[]
        {
            byte3, byte2, byte1, byte0
        });
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
