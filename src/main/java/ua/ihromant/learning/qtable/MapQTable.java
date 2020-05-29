package ua.ihromant.learning.qtable;

import ua.ihromant.learning.state.State;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapQTable implements QTable {
	private final double alpha;
	private final Map<State, Double> qStates = new HashMap<>();

	public MapQTable(double alpha) {
		this.alpha = alpha;
	}

	@Override
	public Map<State, Double> getMultiple(Stream<State> actions) {
		return actions.collect(Collectors.toMap(Function.identity(), act -> qStates.getOrDefault(act, 0.0)));
	}

	@Override
	public void setMultiple(Map<State, Double> newValues) {
		newValues.forEach(this::apply);
	}

	private void apply(State action, double newValue) {
		qStates.compute(action, getStateDoubleDoubleBiFunction(newValue));
	}

	private BiFunction<State, Double, Double> getStateDoubleDoubleBiFunction(double newValue) {
		return (act, oldVal) -> {
			oldVal = oldVal != null ? oldVal : newValue;
			return (1 - alpha) * oldVal + alpha * newValue;
		};
	}
}
