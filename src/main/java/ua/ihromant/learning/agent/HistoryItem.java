package ua.ihromant.learning.agent;

import ua.ihromant.learning.state.Player;
import ua.ihromant.learning.state.State;

public class HistoryItem {
    private final State to;
    private final Player player;
    private final boolean random;

    public HistoryItem(State to, Player player, boolean random) {
        this.to = to;
        this.player = player;
        this.random = random;
    }

    public Player getPlayer() {
        return player;
    }

    public State getTo() {
        return to;
    }

    public boolean isRandom() {
        return random;
    }
}
