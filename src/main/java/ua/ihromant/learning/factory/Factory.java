package ua.ihromant.learning.factory;

import java.util.Scanner;
import java.util.function.Supplier;

import ua.ihromant.learning.GameBoard;
import ua.ihromant.learning.agent.Agent;
import ua.ihromant.learning.ai.QLearningTemplate;
import ua.ihromant.learning.ai.qtable.NetworkQTable;
import ua.ihromant.learning.ai.qtable.NeuralNetworkAgent;
import ua.ihromant.learning.ai.qtable.NeuralNetworkConfig;
import ua.ihromant.learning.ai.qtable.NeuralNetworkConverter;
import ua.ihromant.learning.ai.qtable.QTable;
import ua.ihromant.learning.state.State;

public interface Factory {
	Supplier<State> getStateSupplier();

	Agent player(Scanner scan);

	int trainingEpisodes();

	default Agent createAI() {
		return new QLearningTemplate(getStateSupplier().get(),
				createQTable(), trainingEpisodes());
	}

	default QTable createQTable() {
		NeuralNetworkConfig config = networkConfig();
		return new NetworkQTable(config, new NeuralNetworkAgent(config));
	}

	default QTable loadQTable(String path) {
		return new NetworkQTable(networkConfig(), new NeuralNetworkAgent(path));
	}

	NeuralNetworkConfig networkConfig();

	default GameBoard createBoard() {
		return new GameBoard<>(createAI(), player(new Scanner(System.in)), getStateSupplier());
	}
}
