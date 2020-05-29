package ua.ihromant.learning.state;

import java.util.stream.Stream;

public interface State {
    Stream<Object> getActs();

    State apply(Object action);

    boolean isTerminal();

    double getUtility(Player player);

    Player getCurrent();

    double[] toModel();

    default Stream<State> getStates() { return getActs().map(this::apply); }
}