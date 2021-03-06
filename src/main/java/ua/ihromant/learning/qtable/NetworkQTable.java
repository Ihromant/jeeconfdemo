package ua.ihromant.learning.qtable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.nd4j.linalg.api.ndarray.INDArray;
import ua.ihromant.learning.network.NeuralNetworkConfig;
import ua.ihromant.learning.network.NeuralNetworkAgent;
import ua.ihromant.learning.state.State;
import ua.ihromant.learning.util.Converters;

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
		List<Double> evaluated = Converters.convertFromNDArray(agent.get(input), networkConfig.resultToQValueConverter());
		return IntStream.range(0, stateActions.size()).boxed()
				.collect(Collectors.toMap(stateActions::get,
						evaluated::get));
	}

    @Override
    public void setMultiple(Map<State, Double> newValues) {
		INDArray models = Converters.convertToNDArray(new ArrayList<>(newValues.keySet()), networkConfig.toModelConverter());
		INDArray values = Converters.convertToNDArray(new ArrayList<>(newValues.values()), networkConfig.qValueToResultConverter());
		agent.set(models, values);
    }
}
