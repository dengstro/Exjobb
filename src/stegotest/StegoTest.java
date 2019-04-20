package stegotest;


/*
obl 0
0,25 (0,11)	5,82 (11,30)	10,33 (20,37)	12,73 (26,79)	15,27 (35,78)	18,95 (43,54)	26,52 (60,93)	31,51 (75,78)	
8,97 (17,09)	10,56 (21,11)	13,46 (27,61)	15,91 (34,44)	15,30 (37,44)	20,27 (50,15)	27,45 (66,65)	37,69 (98,20)	
17,68 (34,65)	16,31 (33,86)	18,95 (39,41)	19,86 (45,19)	20,69 (50,24)	22,90 (56,10)	29,94 (73,33)	39,98 (104,19)	
22,58 (46,97)	22,45 (48,36)	23,76 (54,06)	25,32 (57,78)	24,03 (62,61)	26,60 (71,72)	35,18 (94,83)	49,37 (134,50)	
29,35 (69,59)	27,63 (67,62)	29,51 (73,78)	28,46 (74,15)	32,65 (86,99)	38,33 (100,83)	42,91 (121,36)	64,05 (169,84)	
33,63 (80,09)	36,24 (90,59)	38,89 (97,23)	39,77 (104,63)	37,93 (107,29)	45,91 (126,18)	58,78 (166,25)	80,61 (230,31)	
44,90 (106,93)	45,93 (118,43)	48,85 (123,30)	53,01 (139,44)	58,50 (165,46)	63,50 (174,53)	98,08 (277,40)	103,67 (308,85)	
44,45 (107,93)	58,06 (151,30)	62,53 (168,55)	66,80 (181,99)	77,67 (219,69)	95,49 (272,82)	124,11 (354,59)	138,24 (411,86)	

jpg 70
0,28 (0,49)	24,78 (46,95)	41,14 (78,55)	40,86 (87,07)	48,08 (95,33)	56,85 (146,19)	97,58 (272,27)	120,30 (500,02)	
29,79 (58,76)	32,79 (71,58)	38,99 (88,25)	35,94 (96,86)	34,07 (118,61)	43,43 (172,63)	77,12 (397,12)	96,31 (580,21)	
45,61 (90,91)	46,29 (100,72)	43,47 (106,33)	41,25 (130,08)	45,33 (205,75)	69,85 (358,83)	94,30 (552,39)	102,18 (699,61)	
58,71 (130,32)	52,65 (134,34)	51,17 (157,21)	54,85 (204,82)	69,42 (350,15)	90,77 (524,91)	105,54 (655,37)	108,00 (739,91)	
58,70 (170,46)	55,25 (194,13)	59,02 (264,77)	69,56 (351,03)	87,07 (531,94)	99,21 (691,64)	111,56 (787,01)	116,09 (832,98)	
76,03 (228,39)	70,66 (293,84)	81,68 (409,31)	98,26 (596,02)	101,15 (721,47)	110,17 (761,41)	109,72 (849,51)	115,15 (899,38)	
95,66 (354,64)	89,45 (438,50)	100,26 (613,25)	115,37 (688,08)	113,09 (766,19)	110,06 (829,50)	130,77 (913,03)	135,56 (909,22)	
112,18 (375,66)	116,12 (555,90)	125,05 (697,86)	129,43 (743,38)	129,45 (780,17)	129,59 (878,76)	141,84 (899,68)	138,24 (1041,93)

 */
import static AmountofBitsChanged.AmountofBitsChanged.getAmountOfBitsChanged;
import static DCTTransformedTest.DCTTransformedTest.runDCTTrandformTest;
import static Obliterator.Obliterator.Obliterate;
import java.util.ArrayList;
import java.util.List;
import static stegotest.MeanAvreage.MeanAvreage.getMeanAvreage;
import static stegotest.LSB.LSB.*;

public class StegoTest
{
    private static final String path = "Bilder/";

    public static void main(String[] args)
    {
        String text = "A";
        StringBuilder SB = new StringBuilder("");

        for (int i = 0; i < 50000; i++)
        {
            SB.append(text);
        }

        System.out.println("plus 10");
        runOblTest("seagulls.png", 0);
        runDCTTrandformTest(path + "seagulls.png", path + "obl.png");

        System.out.println("jpg 70");
        runDCTTrandformTest(path + "seagulls.png", path + "seagulls-min-70.png");

        System.out.println("obl plan 0");
        runOblTest("seagulls.png", 0);
        runDCTTrandformTest(path + "seagulls.png", path + "obl.png");
    }

    /*
        Uses ImgName as the cover image. 
        Injects the payload amountOfImages amount of times. 
        The payload for each time is text * the current iteration number
        bitLevelsFromLSB will crach the program if it is to high;
     */
    public static void runLSBTestMeanAvreage(String imgName, String text, int amountOfImages, int bitLevelsFromLSB)
    {
        List result = new ArrayList();
        String payload = "";
        for (int i = 0; i < amountOfImages; i++)
        {
            String InjectedImageName = i + ".png";
            payload += text;

            InjectWithLsb(path + imgName, payload, path + InjectedImageName, bitLevelsFromLSB);

            //System.out.println(i + 1 + ": " + getMeanAvreage(path + imgName, path + InjectedImageName));
            System.out.println(i + " / " + amountOfImages);
            result.add(getMeanAvreage(path + imgName, path + InjectedImageName));
        }

        PrintListAsMatlabVector(result);
    }

    public static void runLSBTestAmountOfchanges(String imgName, String text, int amountOfImages, int bitLevelsFromLSB)
    {
        List result = new ArrayList();
        String payload = "";
        for (int i = 0; i < amountOfImages; i++)
        {
            String InjectedImageName = i + ".png";
            payload += text;

            InjectWithLsb(path + imgName, payload, path + InjectedImageName, bitLevelsFromLSB);

            //System.out.println(i + 1 + ": " + getAmountOfBitsChanged(path + imgName, path + InjectedImageName));
            System.out.println(i + " / " + amountOfImages);
            result.add(getAmountOfBitsChanged(path + imgName, path + InjectedImageName));
        }

        PrintListAsMatlabVector(result);
    }

    public static void runOblTest(String imgName, int planeToRemove)
    {
        String InjectedImageName = "obl" + ".png";
        Obliterate(path + imgName, path + InjectedImageName, planeToRemove);
    }

    public static void runaddTest(String imgName, int planeToRemove)
    {
        String InjectedImageName = "obl" + ".png";
        add(path + imgName, path + InjectedImageName, planeToRemove);
    }

    public static void PrintListAsMatlabVector(List list)
    {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < list.size(); i++)
        {
            sb.append(list.get(i)).append(" ");
        }

        sb.append("];");
        System.out.println(sb.toString());
    }

}
