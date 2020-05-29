package ua.ihromant.learning.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import ua.ihromant.learning.state.Player;
import ua.ihromant.learning.state.State;

public interface Agent {
	Decision decision(State from, List<HistoryItem> currentHistory);

	static List<HistoryItem> play(Map<Player, Agent> players, State baseState) {
		List<HistoryItem> history = new ArrayList<>();
		State state = baseState;
		while (!state.isTerminal()) {
			Agent currentAgent = players.get(state.getCurrent());
			Decision dec = currentAgent.decision(state, history);
			HistoryItem item = new HistoryItem(state, dec.action, state.getCurrent(), dec.random);
			history.add(item);
			state = item.getTo();
		}
		return history;
	}

	class Decision {
		public final State action;
		public final boolean random;

		public Decision(State action) {
			this(action, false);
		}

		public Decision(State action, boolean random) {
			this.action = action;
			this.random = random;
		}
	}
}
