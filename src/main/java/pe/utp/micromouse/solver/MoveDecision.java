package pe.utp.micromouse.solver;

import pe.utp.micromouse.model.Direction;

public final class MoveDecision {
    private final Direction direction;
    private final String rationale;
    private final boolean backtracking;
    private final boolean stop;

    private MoveDecision(Direction direction, String rationale, boolean backtracking, boolean stop) {
        this.direction = direction;
        this.rationale = rationale == null ? "" : rationale;
        this.backtracking = backtracking;
        this.stop = stop;
    }

    public static MoveDecision move(Direction direction, String rationale) {
        return new MoveDecision(direction, rationale, false, false);
    }

    public static MoveDecision backtrack(Direction direction, String rationale) {
        return new MoveDecision(direction, rationale, true, false);
    }

    public static MoveDecision stop(String rationale) {
        return new MoveDecision(null, rationale, false, true);
    }

    public Direction getDirection() { return direction; }
    public String getRationale() { return rationale; }
    public boolean isBacktracking() { return backtracking; }
    public boolean isStop() { return stop; }
}
