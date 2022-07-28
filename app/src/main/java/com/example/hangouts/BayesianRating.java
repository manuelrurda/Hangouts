package com.example.hangouts;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.util.FastMath;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BayesianRating {

    final static int standardNormalMean = 0;
    final static int standardStdDeviation = 1;

    private static double getPpf(double x) {
        return inverseCumulativeProbability(x);
    }

    public static double getBayesianRating(List<Double> list, double significance) {
        double sum = 0;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            sum += list.get(i);
        }
        if (sum == 0) {
            return 0;
        }
        double zValue = getPpf(1 - (1 - significance) / 2);
        double firstTerm = 0;
        double secondTerm = 0;
        for (int i = 0; i < size; i++) {
            firstTerm += (i + 1) * (list.get(i) + 1) / (sum + size);
            secondTerm += (i + 1) * (i + 1) * (list.get(i) + 1) / (sum + size);
        }
        return firstTerm - zValue *
                Math.sqrt((secondTerm - firstTerm * firstTerm) / (sum + size + 1));
    }

    private static double inverseCumulativeProbability(double x) {
        return FastMath.sqrt(2.0) * inverseErrorFunction(2 * x - 1);
    }

    /***
     * The algorithm to approximate the error inverse function with the finite series method
     * would look like this, however not only this is not computationally inefficient,
     * the precision of the double data type won't be able to handle the calculations after the
     * third term.
     * final static double SQRTPI_OVER_TWO = FastMath.sqrt(FastMath.PI) / 2;
     *  final double epsilon = 0.001;
     *         double diff = 1;
     *
     *         ArrayList<Double> terms = new ArrayList<Double>();
     *         terms.add(SQRTPI_OVER_TWO * x);
     *         double sum = 0.0;
     *         // First Term
     *         double prevTerm = SQRTPI_OVER_TWO;
     *         // c is numerator
     *         List<Double> c = new ArrayList<>();
     *         c.add(1.0);
     *         int k = 1;
     *         while (diff > epsilon) {
     *
     *             // get numerator
     *             System.out.println("k = " + k);
     *             double cSum = 0;
     *             for (int m = 0; m < k; m++) {
     *                 System.out.println("m = " + m);
     *                 double newTerm = (c.get(m) * c.get(k - 1 - m)) / ((m + 1) * (2 * m + 1));
     *                 cSum += newTerm;
     *                 c.add(newTerm);
     *             }
     *
     *             double newTerm = (cSum / (2 * k + 1)) * FastMath.pow((SQRTPI_OVER_TWO) * x,
     *                     (2 * k + 1));
     *             sum += newTerm;
     *             diff = prevTerm - newTerm;
     *             prevTerm = newTerm;
     *             System.out.println("New Term = " + newTerm);
     *             k++;
     *         }
     *         return sum;
     */

    /***
     * Approximating the inverse error function with the Taylor Finite Series method,
     * first 23 terms.
     *
     * Terms are obtained with the Taylor Power Series as described in: https://dlmf.nist.gov/7.17
     */
    private static final List<Double> TAYLOR_EXPANSION_TERMS = Arrays
            .asList(0.8862269254527579,
                    0.23201366653465444,
                    0.12755617530559793,
                    0.08655212924154752,
                    0.0649596177453854,
                    0.051731281984616365,
                    0.04283672065179733,
                    0.03646592930853161,
                    0.03168900502160544,
                    0.027980632964995214,
                    0.025022275841198347,
                    0.02260986331889757,
                    0.020606780379058987,
                    0.01891821725077884,
                    0.017476370562856534,
                    0.01623150098768524,
                    0.015146315063247798,
                    0.014192316002509954,
                    0.013347364197421288,
                    0.012594004871332063,
                    0.011918295936392034,
                    0.011308970105922531,
                    0.010756825303317953,
                    0.010254274081853464,
                    0.009795005770071162);

    private static double inverseErrorFunction(double x) {
        double result = TAYLOR_EXPANSION_TERMS.get(0) * x;
        for(int i = 1; i < TAYLOR_EXPANSION_TERMS.size(); i++){
            result += TAYLOR_EXPANSION_TERMS.get(i) * FastMath.pow(x, 2*i+1);
        }
        return result;
    }
}
