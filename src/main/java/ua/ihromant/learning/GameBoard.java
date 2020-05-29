package ua.ihromant.learning;

import ua.ihromant.learning.agent.Agent;
import ua.ihromant.learning.state.HistoryItem;
import ua.ihromant.learning.state.Player;
import ua.ihromant.learning.state.State;
import ua.ihromant.learning.util.WriterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
				play(Player.X);
			}
			if (decision.equals("2")) {
				play(Player.O);
			}
		} while (decision.equals("1") || decision.equals("2"));
	}

	public void play(Player player) {
		List<HistoryItem> history = Agent.play(Map.of(player, agent, player.opponent(), ai), baseStateProducer.get());
		WriterUtil.writeHistory(history);
		switch ((int) history.get(history.size() - 1).getTo().getUtility(player)) {
			case 0:
				System.out.println("Computer won!");
				break;
			case 1:
				System.out.println("You won!");
				break;
		}
	}
}
