package ua.ihromant.learning.ai.qtable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.nd4j.linalg.api.ndarray.INDArray;
import ua.ihromant.learning.state.NimLineState;
import ua.ihromant.learning.state.State;

public class NetworkQTable implements QTable {
    private final NeuralNetworkAgent agent;
    private final NeuralNetworkConfig networkConfig;

    public NetworkQTable(NeuralNetworkConfig networkConfig, NeuralNetworkAgent agent) {
    	this.networkConfig = networkConfig;
    	this.agent = agent;
    }

	@Override
	public Map<State, Double> getMultiple(Stream<State> stream) {
		List<State> stateActions = stream.collect(Collectors.toList());
		INDArray input = Converters.convertToNDArray(stateActions, networkConfig.toModelConverter());
		List<Double> evals = Converters.convertFromNDArray(agent.get(input), networkConfig.resultToQValueConverter());
		return IntStream.range(0, stateActions.size()).boxed()
				.collect(Collectors.toMap(stateActions::get,
						evals::get));
	}

    @Override
    public void setMultiple(Map<State, Double> newValues) {
		INDArray models = Converters.convertToNDArray(new ArrayList<>(newValues.keySet()), networkConfig.toModelConverter());
		INDArray values = Converters.convertToNDArray(new ArrayList<>(newValues.values()), networkConfig.qValueToResultConverter());
		agent.set(models, values);
    }
}
