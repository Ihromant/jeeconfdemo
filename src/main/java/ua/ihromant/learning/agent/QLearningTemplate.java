package ua.ihromant.learning.agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import ua.ihromant.learning.state.HistoryItem;
import ua.ihromant.learning.qtable.QTable;
import ua.ihromant.learning.state.Player;
import ua.ihromant.learning.state.State;
import ua.ihromant.learning.util.WriterUtil;

public class QLearningTemplate implements Agent {
	private static final int STEP = 1000;
    private static final double GAMMA = 0.8;
	private static final double RANDOM_GAMMA = 0.1;
    private static final double EXPLORATION = 0.1;
    private final QTable qTable;
	private final State baseState;
	private final int episodes;
	private final Map<Player, Agent> players;

	public QLearningTemplate(State baseState, QTable qTable, int episodes) {
		this.baseState = baseState;
		this.episodes = episodes;
		this.qTable = qTable;
		this.players = Arrays.stream(Player.values()).collect(Collectors.toMap(Function.identity(), p -> this));
		init();
	}

	private void init() {
		long time = System.currentTimeMillis();
		for (int i = 0; i < episodes; i++) {
			List<HistoryItem> history = Agent.play(players, baseState);
			qTable.setMultiple(convert(history));
		}
		System.out.println("Learning for " + episodes + " took " + (System.currentTimeMillis() - time) + " ms");
	}

	private Map<State, Double> convert(List<HistoryItem> history) {
		Map<State, Double> oldValues = qTable.getMultiple(history.stream().map(HistoryItem::getTo));
		int last = history.size() - 1;
		HistoryItem lastMove = history.get(last);
		double factor = 1.0;
		Map<State, Double> converted = new HashMap<>();
		for (int i = last; i >= 0; i--) {
			HistoryItem item = history.get(i);
			double baseValue = lastMove.getTo().getUtility(item.getPlayer());
			double oldValue = oldValues.get(item.getTo());
			converted.put(item.getTo(), linear(oldValue, baseValue, factor));
			double newFactor = item.isRandom() ? oldValue > baseValue ? RANDOM_GAMMA : 1.0 : GAMMA;
			factor = factor * newFactor;
		}
		return converted;
	}

	private double linear(double oldValue, double newValue, double factor) {
		return oldValue * (1 - factor) + newValue * factor;
	}

	@Override
	public Decision decision(State state, List<HistoryItem> currentHistory) {
		if (currentHistory.isEmpty() || ThreadLocalRandom.current().nextDouble() < EXPLORATION) {
			List<State> actions = state.getStates().collect(Collectors.toList());
			return new Decision(actions.get(ThreadLocalRandom.current().nextInt(actions.size())), !currentHistory.isEmpty());
		}

		Map<State, Double> rewards = qTable.getMultiple(state.getStates().distinct());
		return new Decision(rewards.entrySet().stream()
				.max(Comparator.comparingDouble(Map.Entry::getValue))
				.orElseThrow(IllegalStateException::new).getKey());
	}
}
