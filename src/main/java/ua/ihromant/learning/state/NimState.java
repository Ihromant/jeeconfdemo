package ua.ihromant.learning.state;

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
		this.piles = Arrays.stream(piles).sorted().filter(i -> i != 0).toArray();
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
						this.current == Player.X ? Player.O : Player.X);
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
		double[] result = new double[PILES_MAX * BINARY_NUMBERS];
		for (int i = 0; i < PILES_MAX && i < piles.length; i++) {
			char[] binary = Integer.toBinaryString(piles[i]).toCharArray();
			for (int j = 0; j < binary.length && j < BINARY_NUMBERS; j++) {
				result[i * BINARY_NUMBERS + BINARY_NUMBERS - 1 - j] = binary[binary.length - 1 - j] - '0';
			}
		}
		return result;
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