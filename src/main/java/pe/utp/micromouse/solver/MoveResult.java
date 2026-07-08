package pe.utp.micromouse.solver;

import pe.utp.micromouse.model.Direction;
import pe.utp.micromouse.model.Position;

public final class MoveResult {
    private final Position previous;
    private final Position current;
    private final Direction direction;
    private final boolean valid;
    private final boolean reachedGoal;
    private final boolean backtracking;
    private final String message;

    public MoveResult(Position previous, Position current, Direction direction,
                      boolean valid, boolean reachedGoal, boolean backtracking, String message) {
        this.previous = previous;
        this.current = current;
        this.direction = direction;
        this.valid = valid;
        this.reachedGoal = reachedGoal;
        this.backtracking = backtracking;
        this.message = message;
    }

    public Position getPrevious() { return previous; }
    public Position getCurrent() { return current; }
    public Direction getDirection() { return direction; }
    public boolean isValid() { return valid; }
    public boolean isReachedGoal() { return reachedGoal; }
    public boolean isBacktracking() { return backtracking; }
    public String getMessage() { return message; }
}
