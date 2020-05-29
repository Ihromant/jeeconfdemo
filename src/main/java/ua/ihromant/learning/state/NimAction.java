package ua.ihromant.learning.state;

public class NimAction {
    private final int idx;
    private final int reduce;

    public NimAction(int idx, int reduce) {
        this.idx = idx;
        this.reduce = reduce;
    }

    public int getIdx() {
        return idx;
    }

    public int getReduce() {
        return reduce;
    }
}
