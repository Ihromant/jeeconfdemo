package ua.ihromant.learning.agent;

import java.util.Scanner;

import ua.ihromant.learning.state.NimAction;
import ua.ihromant.learning.state.State;

public class NimConsolePlayer extends ConsolePlayer {
	public NimConsolePlayer(Scanner scan) {
		super(scan);
	}

	@Override
	protected void explanation(State state) {
		System.out.println(state.toString());
		System.out.println("Enter your move, firstly index (starting from 0), then number to take");
	}

	@Override
	protected Object getAction(Scanner scan) {
		return new NimAction(Integer.parseInt(scan.nextLine()), Integer.parseInt(scan.nextLine()));
	}
}
