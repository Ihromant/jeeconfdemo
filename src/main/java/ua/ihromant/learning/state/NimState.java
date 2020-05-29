package ua.ihromant.learning.state;

import ua.ihromant.learning.util.Converters;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NimState implements State {
	public static final int PILES_MAX = 4;
	public static final int BINARY_NUMBERS = 3;
	private final int[] piles;
	private final Player current;

	public NimState(int[] piles) {
		this(piles, Player.X);
	}

	private NimState(int[] piles, Player player) {
		this.piles = Arrays.copyOf(piles, piles.length);
		this.current = player;
	}

	@Override
	public Stream<Object> getActs() {
		if (isTerminal()) {
			return Stream.empty();
		}

		return IntStream.rangeClosed(1, Arrays.stream(piles).max().orElse(0))
				.boxed()
				.flatMap(red -> {
					int[] idxBigger = IntStream.range(0, piles.length)
							.filter(i -> piles[i] >= red).toArray();
					return Arrays.stream(idxBigger)
							.mapToObj(idx -> new NimAction(idx, red));
				});
	}

	@Override
	public State apply(Object act) {
		NimAction action = (NimAction) act;
		return new NimState(take(this.piles, action.getIdx(), action.getReduce()),
						this.current.opponent());
	}

	private static int[] take(int[] from, int idx, int reduce) {
		int[] result = Arrays.copyOf(from, from.length);
		result[idx] = result[idx] - reduce;
		return result;
	}

	@Override
	public boolean isTerminal() {
		return Arrays.stream(piles).allMatch(i -> i == 0);
	}

	@Override
	public double getUtility(Player player) {
		if (!isTerminal()) {
			return 0.5;
		}

		return this.current == player ? 1 : 0;
	}

	@Override
	public Player getCurrent() {
		return current;
	}

	@Override
	public double[] toModel() {
		return Arrays.stream(piles)
				.flatMap(i -> Arrays.stream(Converters.toBinary(i, BINARY_NUMBERS)))
				.mapToDouble(Double::valueOf)
				.toArray();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		NimState nimState = (NimState) o;
		return Arrays.equals(piles, nimState.piles) &&
				current == nimState.current;
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(current);
		result = 31 * result + Arrays.hashCode(piles);
		return result;
	}

	@Override
	public String toString() {
		return "NimState{" +
				"piles=" + Arrays.toString(piles) +
				", current=" + current +
				'}';
	}
}