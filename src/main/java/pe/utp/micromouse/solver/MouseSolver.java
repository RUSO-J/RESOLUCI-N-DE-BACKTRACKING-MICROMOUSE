package pe.utp.micromouse.solver;

/**
 * Contrato que deben implementar los algoritmos del ratón.
 * El motor controla el tablero, valida el movimiento y actualiza las métricas.
 */
public interface MouseSolver {
    String getName();
    void reset();
    MoveDecision nextMove(SolverContext context);
    void onMoveResult(MoveResult result);
}
