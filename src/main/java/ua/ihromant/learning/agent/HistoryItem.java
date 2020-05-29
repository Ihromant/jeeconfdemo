package ua.ihromant.learning.agent;

import ua.ihromant.learning.state.Player;
import ua.ihromant.learning.state.State;

public class HistoryItem {
    private final State from;
    private final State to;
    private final Player player;
    private final boolean random;

    public HistoryItem(State from, State to, Player player, boolean random) {
        this.from = from;
        this.to = to;
        this.player = player;
        this.random = random;
    }

    public State getFrom() {
        return from;
    }

    public Player getPlayer() {
        return player;
    }

    public State getTo() {
        return to;
    }

    public double getUtility() {
        return to.getUtility(player);
    }

    public boolean isRandom() {
        return random;
    }
}
