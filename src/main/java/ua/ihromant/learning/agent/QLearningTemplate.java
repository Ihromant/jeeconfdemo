package ua.ihromant.learning.agent;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

public class QLearningTemplate implements Agent {
    private static final double GAMMA = 0.8;
	private static final double RANDOM_GAMMA = 0.1;
    private static final double EXPLORATION = 0.1;
    private final QTable qTable;

	public QLearningTemplate(QTable qTable) {
		this.qTable = qTable;
	}

	@Override
	public void train(State baseState, int episodes) {
		Map<Player, Agent> players = Arrays.stream(Player.values()).collect(Collectors.toMap(Function.identity(), p -> this));
		long time = System.currentTimeMillis();
		for (int i = 0; i < episodes; i++) {
			List<HistoryItem> history = Agent.play(players, baseState);
			qTable.setMultiple(convert(history));
		}
		System.out.println("Learning for " + episodes + " took " + (System.currentTimeMillis() - time) + " ms");
	}

	private Map<State, Double> convert(List<HistoryItem> history) {
		Map<State, Double> oldValues = qTable.getMultiple(history.stream().map(HistoryItem::getTo));
		double factor = 1.0;
		Map<State, Double> converted = new HashMap<>();
		Collections.reverse(history);
		HistoryItem lastMove = history.get(0);
		for (HistoryItem item : history) {
			double award = lastMove.getTo().getUtility(item.getPlayer());
			double evaluation = oldValues.get(item.getTo());
			double newFactor = getFactor(item.isRandom(), award, evaluation);
			factor = factor * newFactor;
			double newEvaluation = linear(evaluation, award, factor);
			converted.put(item.getTo(), newEvaluation);
		}
		return converted;
	}

	private double getFactor(boolean random, double award, double evaluation) {
		if (random && award < evaluation) {
			return RANDOM_GAMMA;
		}

		return GAMMA;
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

		Map<State, Double> evaluations = qTable.getMultiple(state.getStates().distinct());
		return new Decision(evaluations.entrySet().stream()
				.max(Comparator.comparingDouble(Map.Entry::getValue))
				.orElseThrow(IllegalStateException::new).getKey());
	}
}
