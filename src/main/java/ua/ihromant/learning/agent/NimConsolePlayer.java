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
		System.out.println("Enter your move, firstly number to take, then index (starting from 0)");
	}

	@Override
	protected Object getAction(Scanner scan) {
		int toTake = Integer.parseInt(scan.nextLine());
		return new NimAction(Integer.parseInt(scan.nextLine()), toTake);
	}
}
