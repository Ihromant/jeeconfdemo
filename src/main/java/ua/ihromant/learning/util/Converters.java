package ua.ihromant.learning.util;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Converters {
    private static final double[] WIN = {1, 0};
    private static final double[] LOSE = {0, 1};

    public static <O> List<O> convertFromNDArray(INDArray indArray, Function<INDArray, O> converter) {
        int length = (int) indArray.shape()[0];
        List<O> result = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            result.add(converter.apply(indArray.getRow(i)));
        }
        return result;
    }

    public static <O> INDArray convertToNDArray(List<O> objects, Function<O, double[]> converter) {
        double[][] result = new double[objects.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = converter.apply(objects.get(i));
        }
        return Nd4j.create(result);
    }

    public static double convertToWinLose(INDArray array) {
        return array.getDouble(0);
    }

    public static double[] convertFromWinLose(double qValue) {
        if (qValue <= 0) {
            return LOSE;
        }
        if (qValue >= 1) {
            return WIN;
        }
        if (Double.isFinite(qValue)) {
            return new double[] {qValue, 1 - qValue};
        }

        return LOSE; // never, but in case of NAN etc.
    }
}
