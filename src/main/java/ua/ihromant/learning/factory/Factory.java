package ua.ihromant.learning.factory;

import java.util.Scanner;

import ua.ihromant.learning.agent.Agent;
import ua.ihromant.learning.network.NeuralNetworkConfig;
import ua.ihromant.learning.qtable.MapQTable;
import ua.ihromant.learning.qtable.NetworkQTable;
import ua.ihromant.learning.network.NeuralNetworkAgent;
import ua.ihromant.learning.qtable.QTable;

public interface Factory {
	Agent player(Scanner scan);

	default QTable createQTable() {
		//return new MapQTable();
		NeuralNetworkConfig config = networkConfig();
		return new NetworkQTable(config, new NeuralNetworkAgent(config));
	}

	NeuralNetworkConfig networkConfig();
}
