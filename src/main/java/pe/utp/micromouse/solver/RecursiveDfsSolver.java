package pe.utp.micromouse.solver;

import pe.utp.micromouse.model.Direction;
import pe.utp.micromouse.model.Maze;
import pe.utp.micromouse.model.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Calcula una traza DFS recursiva. La traza mantiene las ramas fallidas y sus retrocesos,
 * por ello se visualiza claramente el backtracking.
 */
public final class RecursiveDfsSolver implements MouseSolver {
    private final List<MoveDecision> plan = new ArrayList<MoveDecision>();
    private int index;
    private boolean planned;

    @Override public String getName() { return "DFS recursivo con backtracking"; }

    @Override
    public void reset() {
        plan.clear();
        index = 0;
        planned = false;
    }

    @Override
    public MoveDecision nextMove(SolverContext context) {
        if (!planned) {
            Maze maze = context.getMaze();
            if (maze == null) return MoveDecision.stop("DFS de referencia requiere el modo Mapa completo.");
            boolean found = dfs(maze, maze.getStart(), maze.getGoal(), new HashSet<Position>());
            planned = true;
            if (!found) return MoveDecision.stop("No existe ruta hacia la meta.");
        }
        if (index >= plan.size()) return MoveDecision.stop("La traza DFS terminó.");
        return plan.get(index++);
    }

    private boolean dfs(Maze maze, Position current, Position goal, Set<Position> visited) {
        if (current.equals(goal)) return true;
        visited.add(current);
        for (Direction direction : Direction.values()) {
            Position next = current.move(direction);
            if (maze.isOpen(next) && !visited.contains(next)) {
                plan.add(MoveDecision.move(direction, "DFS: explorar " + next));
                if (dfs(maze, next, goal, visited)) return true;
                plan.add(MoveDecision.backtrack(direction.opposite(), "Backtracking: volver desde " + next));
            }
        }
        return false;
    }

    @Override public void onMoveResult(MoveResult result) { }
}
