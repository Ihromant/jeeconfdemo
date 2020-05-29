package ua.ihromant.learning.state;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NimStateTest {
	private static final double[] standardModel =
				   {0, 0, 1,
					0, 1, 1,
					1, 0, 1,
					1, 1, 1};

	private static final double[] nonStandardModel =
				   {0, 1, 0,
					1, 0, 0,
					1, 0, 1,
					1, 1, 0};

	@Test
	public void testActions() {
		NimState state = new NimState(new int[] {1, 2, 2});
		Assertions.assertEquals(5, state.getActs().count());

		state = new NimState(new int[] {1, 3, 5, 7});
		Assertions.assertEquals(16, state.getActs().count());
	}

	@Test
	public void testToModel() {
		NimState state = new NimState(new int[] {1, 3, 5, 7});
		Assertions.assertArrayEquals(state.toModel(), standardModel);
		state = new NimState(new int[] {2, 4, 5, 6});
		Assertions.assertArrayEquals(state.toModel(), nonStandardModel);
	}

	@Test
	public void testApply() {
		State state = new NimState(new int[] {1, 2, 3, 4});
		state = state.apply(new NimAction(3, 4));
		state = state.apply(new NimAction(2, 3));
		state = state.apply(new NimAction(0, 1));
		state = state.apply(new NimAction(0, 2));
		Assertions.assertTrue(state.isTerminal());
	}
}
