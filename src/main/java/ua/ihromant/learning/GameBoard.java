package ua.ihromant.learning;

import ua.ihromant.learning.agent.Agent;
import ua.ihromant.learning.state.Player;
import ua.ihromant.learning.state.State;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Supplier;

public class GameBoard {
	private final Agent ai;
	private final Agent agent;
	private final Supplier<State> baseStateProducer;

	public GameBoard(Agent ai, Agent agent, Supplier<State> baseStateProducer) {
		this.ai = ai;
		this.agent = agent;
		this.baseStateProducer = baseStateProducer;
	}

	public void play() {
		Scanner scan = new Scanner(System.in);
		String decision;
		do {
			System.out.println("Write whether you move first or second. Other values will exit the game");
			decision = scan.nextLine();
			if (decision.equals("1")) {
				playFirst();
			}
			if (decision.equals("2")) {
				playSecond();
			}
		} while (decision.equals("1") || decision.equals("2"));
	}

	public void playFirst() {
		State state = baseStateProducer.get();
		while (!state.isTerminal()) {
			state = agent.decision(state, new ArrayList<>()).action; // TODO
			if (state.isTerminal()) {
				break;
			}
			System.out.println(state.toString());
			state = ai.decision(state, new ArrayList<>()).action; // TODO
		}
		System.out.println(state);
		switch ((int) state.getUtility(Player.X)) {
			case 0:
				System.out.println("Draw!");
				break;
			case 1:
				System.out.println("You won!");
				break;
			case -1:
				System.out.println("Computer won!");
				break;
		}
	}

	public void playSecond() {
		State state = baseStateProducer.get();
		while (!state.isTerminal()) {
			state = ai.decision(state, new ArrayList<>()).action; // TODO
			if (state.isTerminal()) {
				break;
			}
			state = agent.decision(state, new ArrayList<>()).action; // TODO
			System.out.println(state.toString());
		}
		System.out.println(state);
		switch ((int) state.getUtility(Player.O)) {
			case 0:
				System.out.println("Draw!");
				return;
			case 1:
				System.out.println("You won!");
				return;
			case -1:
				System.out.println("Computer won!");
		}
	}
}
