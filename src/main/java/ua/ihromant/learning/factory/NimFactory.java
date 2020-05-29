package ua.ihromant.learning.factory;

import java.util.Scanner;
import java.util.function.Supplier;

import ua.ihromant.learning.agent.Agent;
import ua.ihromant.learning.agent.NimConsolePlayer;
import ua.ihromant.learning.network.NeuralNetworkConfig;
import ua.ihromant.learning.network.NimNeuralNetworkConfig;
import ua.ihromant.learning.state.NimState;
import ua.ihromant.learning.state.State;

public class NimFactory implements Factory {
	private static final int[] NIM_GAME = {1, 3, 5, 7};
	@Override
	public Supplier<State> getStateSupplier() {
		return () -> new NimState(NIM_GAME);
	}

	@Override
	public Agent player(Scanner scan) {
		return new NimConsolePlayer(scan);
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
