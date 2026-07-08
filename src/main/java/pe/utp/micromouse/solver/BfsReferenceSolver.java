package pe.utp.micromouse.solver;

import pe.utp.micromouse.engine.PathFinder;
import pe.utp.micromouse.model.Direction;
import pe.utp.micromouse.model.Maze;

import java.util.Collections;
import java.util.List;

public final class BfsReferenceSolver implements MouseSolver {
    private List<Direction> route = Collections.emptyList();
    private int index;
    private boolean planned;

    @Override public String getName() { return "BFS de referencia (ruta óptima)"; }

    @Override
    public void reset() {
        route = Collections.emptyList();
        index = 0;
        planned = false;
    }

    @Override
    public MoveDecision nextMove(SolverContext context) {
        if (!planned) {
            Maze maze = context.getMaze();
            if (maze == null) return MoveDecision.stop("BFS de referencia requiere el modo Mapa completo.");
            route = PathFinder.shortestPath(maze);
            planned = true;
        }
        if (index >= route.size()) return MoveDecision.stop("Ruta óptima completada.");
        Direction direction = route.get(index++);
        return MoveDecision.move(direction, "BFS: seguir la ruta mínima por " + direction.getLabel());
    }

    @Override public void onMoveResult(MoveResult result) { }
}
