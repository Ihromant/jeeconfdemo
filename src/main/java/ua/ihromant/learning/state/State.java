package ua.ihromant.learning.state;

import java.util.stream.Stream;

public interface State {
    Stream<Object> getActs(); // TODO remove

    State apply(Object action); // generally state and action are required

    boolean isTerminal();

    double getUtility(Player player);

    Player getCurrent();

    double[] toModel(); // generally it should be moved to separate class

    default Stream<State> getStates() { return getActs().map(this::apply); }
}