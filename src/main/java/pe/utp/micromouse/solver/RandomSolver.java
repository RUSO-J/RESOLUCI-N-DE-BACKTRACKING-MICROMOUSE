package pe.utp.micromouse.solver;

import pe.utp.micromouse.model.Direction;
import java.util.List;
import java.util.Random;

public final class RandomSolver implements MouseSolver {
    private final Random random = new Random(2026L);

    @Override public String getName() { return "Aleatorio controlado"; }
    @Override public void reset() { random.setSeed(2026L); }

    @Override
    public MoveDecision nextMove(SolverContext context) {
        List<Direction> options = context.getLegalMoves();
        if (options.isEmpty()) return MoveDecision.stop("No existen movimientos legales.");
        Direction direction = options.get(random.nextInt(options.size()));
        return MoveDecision.move(direction, "Elección aleatoria: " + direction.getLabel());
    }

    @Override public void onMoveResult(MoveResult result) { }
}
