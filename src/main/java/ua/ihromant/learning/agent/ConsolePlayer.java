package ua.ihromant.learning.agent;

import java.util.List;
import java.util.Scanner;

import ua.ihromant.learning.state.State;

public abstract class ConsolePlayer implements Agent {
	private final Scanner scan;

	protected ConsolePlayer(Scanner scan) {
		this.scan = scan;
	}

	protected abstract void explanation(State state);

	protected abstract Object getAction(Scanner scan);

	@Override
	public Decision decision(State from, List<HistoryItem> currentHistory) {
		explanation(from);
		return new Decision(from.apply(getAction(scan)));
	}
}
