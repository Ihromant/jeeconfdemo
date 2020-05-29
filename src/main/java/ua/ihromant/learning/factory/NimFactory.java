package ua.ihromant.learning.factory;

import java.util.Scanner;

import ua.ihromant.learning.agent.Agent;
import ua.ihromant.learning.agent.NimConsolePlayer;
import ua.ihromant.learning.network.NeuralNetworkConfig;
import ua.ihromant.learning.network.NimNeuralNetworkConfig;

public class NimFactory implements Factory {
	@Override
	public Agent player(Scanner scan) {
		return new NimConsolePlayer(scan);
	}

	@Override
	public NeuralNetworkConfig networkConfig() {
		return new NimNeuralNetworkConfig();
	}
}
