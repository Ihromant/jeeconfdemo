package ua.ihromant.learning.agent;

import ua.ihromant.learning.state.HistoryItem;
import ua.ihromant.learning.state.Player;
import ua.ihromant.learning.state.State;

import java.util.Comparator;
import java.util.List;

public final class MinimaxTemplate implements Agent {
    private final Player player;
    public MinimaxTemplate(Player player) {
        this.player = player;
    }

    @Override
    public Decision decision(State from, List<HistoryItem> currentHistory) {
        return new Decision(from.getStates().max(Comparator.comparing(this::minValue)).orElseThrow(IllegalStateException::new));
    }

    private double maxValue(State state) {
        if (state.isTerminal()) {
            return state.getUtility(player);
        }
        return state.getStates()
                .mapToDouble(this::minValue).max().orElseThrow(IllegalStateException::new);
    }

    private double minValue(State state) {
        if (state.isTerminal()) {
            return state.getUtility(player);
        }
        return state.getStates()
                .mapToDouble(this::maxValue).min().orElseThrow(IllegalStateException::new);
    }
}