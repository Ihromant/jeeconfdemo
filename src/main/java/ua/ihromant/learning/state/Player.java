package ua.ihromant.learning.state;

public enum Player {
	X, O;

	public Player opponent() {
		switch (this) {
			case X:
				return O;
			case O:
				return X;
			default:
				throw new IllegalStateException();
		}
	}
}