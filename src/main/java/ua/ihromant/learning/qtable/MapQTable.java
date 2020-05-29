package ua.ihromant.learning.qtable;

import ua.ihromant.learning.state.State;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapQTable implements QTable {
	private final Map<State, Double> qStates = new HashMap<>();

	@Override
	public Map<State, Double> getMultiple(Stream<State> actions) {
		return actions.collect(Collectors.toMap(Function.identity(), act -> qStates.getOrDefault(act, 0.5)));
	}

	@Override
	public void setMultiple(Map<State, Double> newValues) {
		qStates.putAll(newValues);
	}
}
