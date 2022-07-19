package com.example.hangouts;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.List;

public class BayesianRating {

    final static int standardNormalMean = 0;
    final static int standardStdDeviation = 1;
    final static NormalDistribution standardNormalDist = new NormalDistribution(standardNormalMean,
            standardStdDeviation);

    private static double getPpf(double x) {
        return standardNormalDist.inverseCumulativeProbability(x);
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
        double z_value = getPpf(1 - (1 - significance) / 2);
        double firstTerm = 0;
        double secondTerm = 0;
        for (int i = 0; i < size; i++) {
            firstTerm += (i + 1) * (list.get(i) + 1) / (sum + size);
            secondTerm += (i + 1) * (i + 1) * (list.get(i) + 1) / (sum + size);
        }
        return firstTerm - z_value *
                Math.sqrt((secondTerm - firstTerm*firstTerm)/(sum + size + 1));
    }
}
