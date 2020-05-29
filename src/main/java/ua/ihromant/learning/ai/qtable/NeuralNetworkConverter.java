package ua.ihromant.learning.ai.qtable;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import ua.ihromant.learning.state.State;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface NeuralNetworkConverter<A> {
	double[] convertState(State state);

	default INDArray convertStatesToArray(List<State> states) {
		return convert(states, this::convertState);
	}

	static <O> INDArray convert(List<O> objects, Function<O, double[]> converter) {
		double[][] result = new double[objects.size()][];
		for (int i = 0; i < result.length; i++) {
			result[i] = converter.apply(objects.get(i));
		}
		return Nd4j.create(result);
	}

	default INDArray convertQValuesToArray(List<Double> qValues) {
		return convert(qValues, this::fromQValue);
	}

	List<Double> convertToQValues(INDArray indArray);

	double[] fromQValue(double value);
	int inputLength();
	int outputLength();
}
