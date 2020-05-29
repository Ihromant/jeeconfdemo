package ua.ihromant.learning.ai.qtable;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.IOException;

public class NeuralNetworkAgent {
    private final MultiLayerNetwork network;

    public NeuralNetworkAgent(NeuralNetworkConfig config) {
        this.network = new MultiLayerNetwork(config.buildConfig());
        this.network.init();
    }

    public NeuralNetworkAgent(String path) {
        try {
            this.network = ModelSerializer.restoreMultiLayerNetwork(path);
        } catch (IOException e) {
            throw new RuntimeException("Was not able to restore model from path: " + path, e);
        }
    }

    public INDArray get(INDArray input) {
        return network.output(input);
    }

    public void set(INDArray input, INDArray result) {
        network.fit(input, result);
    }

    public void serialize(String path) {
        try {
            ModelSerializer.writeModel(this.network, path, true);
        } catch (IOException e) {
            throw new RuntimeException("Was not able to persist model to path: " + path, e);
        }
    }
}
