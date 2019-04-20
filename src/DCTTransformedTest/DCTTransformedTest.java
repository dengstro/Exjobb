/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DCTTransformedTest;

import static DCTTransformedTest.DctStego.encode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David Engström <Davids-epost@hotmail.com>
 */
public class DCTTransformedTest
{
    private static final BigDecimal SQRT_DIG = new BigDecimal(150);
    private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

    public static List runDCTTrandformTest(String orgImg, String newImg)
    {
        List oldSquares = new ArrayList();
        List newSquares = new ArrayList();
        List Resultquare = new ArrayList();
        for (int i = 0; i < 8; i++)
        {
            oldSquares.add(new ArrayList());
            newSquares.add(new ArrayList());
            Resultquare.add(new ArrayList());
            for (int j = 0; j < 8; j++)
            {
                ((List) oldSquares.get(i)).add(new ArrayList());
                ((List) newSquares.get(i)).add(new ArrayList());
                ((List) Resultquare.get(i)).add(new ArrayList());
            }

        }

        encode(orgImg, oldSquares);
        //System.out.println(((List) ((List) oldSquares.get(0)).get(0)).get(0));
        encode(newImg, newSquares);
        //System.out.println(((List) ((List) newSquares.get(0)).get(0)).get(0));

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                BigDecimal oldTot = BigDecimal.ZERO;
                BigDecimal differenceTot = BigDecimal.ZERO;
                List<Integer> oldValuesForPixel = ((List) ((List) oldSquares.get(i)).get(j));
                List<Integer> newValuesForPixel = ((List) ((List) newSquares.get(i)).get(j));
                List<Integer> pixelDifference = new ArrayList<>();
                for (int k = 0; k < oldValuesForPixel.size(); k++)
                {
                    pixelDifference.add(Math.abs(oldValuesForPixel.get(k) - newValuesForPixel.get(k)));
                }
                List<Double> resultsForPixel = ((List) ((List) Resultquare.get(i)).get(j));

                for (int k = 0; k < pixelDifference.size(); k++)
                {
                    oldTot = oldTot.add(BigDecimal.valueOf(
                            Math.abs(
                                    oldValuesForPixel.get(k)
                            )
                    ));

                    differenceTot = differenceTot.add(BigDecimal.valueOf(
                            Math.abs(
                                    pixelDifference.get(k)
                            )
                    ));
                }


                double avreageChangeForPixel = differenceTot.divide(
                        BigDecimal.valueOf(
                                pixelDifference.size()
                        ), 2, RoundingMode.HALF_EVEN
                ).doubleValue();
                //resultsForPixel.add(avreageChangeForPixel);

                BigDecimal variance = BigDecimal.ZERO;
                for (int k = 0; k < pixelDifference.size(); k++)
                {
                    variance = variance.add(
                            BigDecimal.valueOf(
                                    Math.abs(
                                            (pixelDifference.get(k) - avreageChangeForPixel)
                                    )
                            ).pow(2)
                    );
                }

                double standardDevationForPixel = bigSqrt(
                        variance.divide(
                                BigDecimal.valueOf(pixelDifference.size()
                                ), 2, RoundingMode.HALF_EVEN)
                ).doubleValue();

                //resultsForPixel.add( standardDevationForPixel );
                double avreageValueForPixel = oldTot.divide(
                        BigDecimal.valueOf(
                                oldValuesForPixel.size()
                        ), 10, RoundingMode.HALF_EVEN
                ).doubleValue();

                //resultsForPixel.add(avreageValueForPixel);
                //System.out.println(resultsForPixel.get(2));
                System.out.print(new DecimalFormat("####0.00").format(avreageChangeForPixel)
                        + " (" + new DecimalFormat("####0.00").format(standardDevationForPixel) + ")\t");

            }
            System.out.println("");

        }
        return Resultquare;
    }

    /**
     * Private utility method used to compute the square root of a BigDecimal.
     *
     * @author Luciano Culacciatti
     * @url
     * http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
     */
    private static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision)
    {
        BigDecimal fx = xn.pow(2).add(c.negate());
        BigDecimal fpx = xn.multiply(new BigDecimal(2));
        BigDecimal xn1 = fx.divide(fpx, 2 * SQRT_DIG.intValue(), RoundingMode.HALF_DOWN);
        xn1 = xn.add(xn1.negate());
        BigDecimal currentSquare = xn1.pow(2);
        BigDecimal currentPrecision = currentSquare.subtract(c);
        currentPrecision = currentPrecision.abs();
        if (currentPrecision.compareTo(precision) <= -1)
        {
            return xn1;
        }
        return sqrtNewtonRaphson(c, xn1, precision);
    }

    /**
     * Uses Newton Raphson to compute the square root of a BigDecimal.
     *
     * @author Luciano Culacciatti
     * @url
     * http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
     */
    public static BigDecimal bigSqrt(BigDecimal c)
    {
        return sqrtNewtonRaphson(c, new BigDecimal(1), new BigDecimal(1).divide(SQRT_PRE));
    }
}
