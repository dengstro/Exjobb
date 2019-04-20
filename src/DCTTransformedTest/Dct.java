package DCTTransformedTest;

/** *
 *
 * @author julius
 * https://github.com/JorenSix/TarsosDSP/blob/master/src/core/be/tarsos/dsp/mfcc/DCT.java
 */
public class Dct
{

    public Dct()
    {

    }

    public int[][] transform(int f[][])
    {
        //double g[][]=new double[8][8]; //// manuellt
        int g[][] = new int[8][8];
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                double ge = 0.0;
                for (int x = 0; x < 8; x++)
                {
                    for (int y = 0; y < 8; y++)
                    {
                        double cg1 = (2.0 * (double) x + 1.0) * (double) i * Math.PI / 16.0;
                        double cg2 = (2.0 * (double) y + 1.0) * (double) j * Math.PI / 16.0;

                        ge += ((double) f[x][y]) * Math.cos(cg1) * Math.cos(cg2);

                    }
                }
                double ci = ((i == 0) ? 1.0 / Math.sqrt(2.0) : 1.0);
                double cj = ((j == 0) ? 1.0 / Math.sqrt(2.0) : 1.0);
                ge *= ci * cj * 0.25;
                g[i][j] = (int) Math.round(ge);
                //g[i][j] = ge; // (int)Math.round(ge);
            }
        }
        return g;
    }

    public int[][] inverse(int g[][])
    {
        //double inv[][]=new double[8][8];
        int inv[][] = new int[8][8];

        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                double ge = 0.0;
                for (int i = 0; i < 8; i++)
                {
                    double cg1 = (2.0 * (double) x + 1.0) * (double) i * Math.PI / 16.0;
                    double ci = ((i == 0) ? 1.0 / Math.sqrt(2.0) : 1.0);
                    for (int j = 0; j < 8; j++)
                    {
                        double cg2 = (2.0 * (double) y + 1.0) * (double) j * Math.PI / 16.0;
                        double cj = ((j == 0) ? 1.0 / Math.sqrt(2.0) : 1.0);
                        double cij4 = ci * cj * 0.25;
                        ge += cij4 * Math.cos(cg1) * Math.cos(cg2) * (double) g[i][j];
                    }
                }
                inv[x][y] = (int) Math.round(ge);
                if (inv[x][y] < 0)
                {
                    inv[x][y] = 0;
                } else if (inv[x][y] > 255)
                {
                    inv[x][y] = 255;
                }
                //inv[x][y] = ge;// (int)Math.round(ge);
            }
        }
        return inv;
    }

    public void print(double matrix[][])
    {
        int m = 8;
        int n = 8;
        int i, j;
        for (i = 0; i < m; i++)
        {
            for (j = 0; j < n; j++)
            {
                System.out.printf("%f\t", matrix[i][j]);
            }
            System.out.println();
        }
    }

}
