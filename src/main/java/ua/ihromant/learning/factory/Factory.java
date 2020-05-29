package ua.ihromant.learning.factory;

import java.util.Scanner;
import java.util.function.Supplier;

import ua.ihromant.learning.GameBoard;
import ua.ihromant.learning.agent.Agent;
import ua.ihromant.learning.agent.QLearningTemplate;
import ua.ihromant.learning.network.NeuralNetworkConfig;
import ua.ihromant.learning.qtable.NetworkQTable;
import ua.ihromant.learning.network.NeuralNetworkAgent;
import ua.ihromant.learning.qtable.QTable;
import ua.ihromant.learning.state.State;

public interface Factory {
	Supplier<State> getStateSupplier();

	Agent player(Scanner scan);

	int trainingEpisodes();

	private Agent createAI() {
		return new QLearningTemplate(getStateSupplier().get(),
				createQTable(), trainingEpisodes());
	}

	private QTable createQTable() {
		NeuralNetworkConfig config = networkConfig();
		return new NetworkQTable(config, new NeuralNetworkAgent(config));
	}

	default QTable loadQTable(String path) {
		return new NetworkQTable(networkConfig(), new NeuralNetworkAgent(path));
	}

	NeuralNetworkConfig networkConfig();

	default GameBoard createBoard() {
		return new GameBoard(createAI(), player(new Scanner(System.in)), getStateSupplier());
	}
}
