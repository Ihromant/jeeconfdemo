package ua.ihromant.learning.ai.qtable;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.nd4j.linalg.api.ndarray.INDArray;
import ua.ihromant.learning.state.State;

import java.util.function.Function;

public interface NeuralNetworkConfig {
    Function<State, double[]> toModelConverter();

    Function<Double, double[]> qValueToResultConverter();

    Function<INDArray, Double> resultToQValueConverter();

    MultiLayerConfiguration buildConfig();
}
