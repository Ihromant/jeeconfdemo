package ua.ihromant.learning.ai.qtable;

import ua.ihromant.learning.state.State;

import java.util.Map;
import java.util.stream.Stream;

public interface QTable {
	Map<State, Double> getMultiple(Stream<State> state);

	void setMultiple(Map<State, Double> newValues);
}
