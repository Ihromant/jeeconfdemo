package ua.ihromant.learning;

import ua.ihromant.learning.agent.Agent;
import ua.ihromant.learning.state.HistoryItem;
import ua.ihromant.learning.state.Player;
import ua.ihromant.learning.state.State;
import ua.ihromant.learning.util.WriterUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// class responsible for place where human can play against AI
public class GameBoard {
	private final Agent ai;
	private final Agent human;
	private final State baseState;

	public GameBoard(Agent ai, Agent human, State baseState) {
		this.ai = ai;
		this.human = human;
		this.baseState = baseState;
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

	private Map<Player, Agent> getPlayers(Player human) {
		Map<Player, Agent> result = new HashMap<>();
		result.put(human, this.human);
		result.put(human.opponent(), this.ai);
		return result;
	}

	public void play(Player player) {
		List<HistoryItem> history = Agent.play(getPlayers(player), baseState);
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
