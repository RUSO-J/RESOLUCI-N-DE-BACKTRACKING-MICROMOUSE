package pe.utp.micromouse.solver;

import pe.utp.micromouse.model.Direction;
import java.util.ArrayDeque;
import java.util.Queue;

public final class ManualSolver implements MouseSolver {
    private final Queue<Direction> pendingMoves = new ArrayDeque<Direction>();

    @Override public String getName() { return "Manual (flechas o botones)"; }

    public void enqueue(Direction direction) {
        if (direction != null) pendingMoves.offer(direction);
    }

    @Override public void reset() { pendingMoves.clear(); }

    @Override
    public MoveDecision nextMove(SolverContext context) {
        Direction direction = pendingMoves.poll();
        if (direction == null) return MoveDecision.stop("Esperando una flecha o un botón de movimiento.");
        return MoveDecision.move(direction, "Movimiento manual: " + direction.getLabel());
    }

    @Override public void onMoveResult(MoveResult result) { }
}
