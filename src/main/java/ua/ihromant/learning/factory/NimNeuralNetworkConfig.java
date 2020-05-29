package ua.ihromant.learning.factory;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import ua.ihromant.learning.qtable.NeuralNetworkConfig;
import ua.ihromant.learning.state.NimState;
import ua.ihromant.learning.state.State;
import ua.ihromant.learning.util.Converters;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class NimNeuralNetworkConfig implements NeuralNetworkConfig {
    @Override
    public Function<State, double[]> toModelConverter() {
        return State::toModel;
    }

    @Override
    public Function<Double, double[]> qValueToResultConverter() {
        return Converters::convertFromWinLose;
    }

    @Override
    public Function<INDArray, Double> resultToQValueConverter() {
        return Converters::convertToWinLose;
    }

    @Override
    public MultiLayerConfiguration buildConfig() {
        int inputLength = NimState.PILES_MAX * NimState.BINARY_NUMBERS;
        return new NeuralNetConfiguration.Builder()
                .seed(ThreadLocalRandom.current().nextLong())
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam())
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(inputLength)
                        .nOut(inputLength * 10)
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new OutputLayer
                        .Builder(LossFunctions.LossFunction.RECONSTRUCTION_CROSSENTROPY)
                        .activation(Activation.SOFTMAX)
                        .nIn(inputLength * 10)
                        .nOut(2)
                        .build())
                .build();
    }
}
