package ua.ihromant.learning.factory;

import java.util.Scanner;
import java.util.function.Supplier;

import ua.ihromant.learning.agent.Agent;
import ua.ihromant.learning.agent.NimLinePlayer;
import ua.ihromant.learning.ai.qtable.NeuralNetworkConfig;
import ua.ihromant.learning.ai.qtable.NimNeuralNetworkConfig;
import ua.ihromant.learning.state.NimLineState;
import ua.ihromant.learning.state.State;

public class NimLineFactory implements Factory {
	private static final int[] base = {1, 3, 5, 7};
	@Override
	public Supplier<State> getStateSupplier() {
		return () -> new NimLineState(base);
	}

	@Override
	public Agent player(Scanner scan) {
		return new NimLinePlayer(scan);
	}

	@Override
	public int trainingEpisodes() {
		return 100000;
	}

	@Override
	public NeuralNetworkConfig networkConfig() {
		return new NimNeuralNetworkConfig();
	}
}
