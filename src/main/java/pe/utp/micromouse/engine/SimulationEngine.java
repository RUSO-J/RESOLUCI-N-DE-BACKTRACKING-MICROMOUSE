package pe.utp.micromouse.engine;

import pe.utp.micromouse.model.Direction;
import pe.utp.micromouse.model.Maze;
import pe.utp.micromouse.model.Position;
import pe.utp.micromouse.model.SensorData;
import pe.utp.micromouse.solver.MoveDecision;
import pe.utp.micromouse.solver.MoveResult;
import pe.utp.micromouse.solver.MouseSolver;
import pe.utp.micromouse.solver.SolverContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SimulationEngine {
    private final Maze maze;
    private final MouseSolver solver;
    private final boolean mapVisible;
    private final int optimalSteps;
    private final List<Position> optimalPath;
    private final Metrics metrics = new Metrics();
    private final List<String> trace = new ArrayList<String>();
    private final Set<Position> deadEnds = new HashSet<Position>();
    private final List<Position> currentPath = new ArrayList<Position>();

    private Position current;
    private boolean started;
    private boolean finished;
    private String stopReason = "Listo para iniciar.";

    public SimulationEngine(Maze maze, MouseSolver solver, boolean mapVisible) {
        this.maze = maze;
        this.solver = solver;
        this.mapVisible = mapVisible;
        this.optimalPath = PathFinder.pathPositions(maze);
        this.optimalSteps = Math.max(0, optimalPath.size() - 1);
        reset();
    }

    public void reset() {
        current = maze.getStart();
        started = false;
        finished = false;
        stopReason = "Listo para iniciar.";
        trace.clear();
        deadEnds.clear();
        currentPath.clear();
        currentPath.add(current);
        metrics.reset(current);
        solver.reset();
        trace.add("Escenario cargado. Inicio=" + maze.getStart() + ", meta=" + maze.getGoal());
    }

    public void start() {
        if (finished) return;
        if (!started) {
            started = true;
            stopReason = "Ejecutando " + solver.getName();
            trace.add("Inicio: " + solver.getName());
        }
    }

    public void step() {
        if (finished) return;
        start();
        SolverContext context = buildContext();
        long before = System.nanoTime();
        MoveDecision decision = solver.nextMove(context);
        metrics.addDecisionTime(System.nanoTime() - before);

        if (decision == null || decision.isStop() || decision.getDirection() == null) {
            finished = true;
            metrics.stop();
            String reason = decision == null ? "El algoritmo devolvió null." : decision.getRationale();
            stopReason = reason;
            trace.add("DETENIDO: " + reason);
            return;
        }

        Position previous = current;
        Direction direction = decision.getDirection();
        Position next = current.move(direction);
        boolean valid = maze.isOpen(next);
        boolean goal = false;
        String message;

        if (!valid) {
            metrics.incrementInvalidMoves();
            message = "Movimiento inválido hacia " + direction.getLabel() + ": hay pared o límite.";
            trace.add("Paso " + (metrics.getSteps() + 1) + ": " + message);
        } else {
            current = next;
            metrics.incrementSteps();
            metrics.markExplored(current);
            if (decision.isBacktracking()) {
                metrics.incrementBacktracks();
                deadEnds.add(previous);
                if (currentPath.size() > 1) currentPath.remove(currentPath.size() - 1);
            } else {
                currentPath.add(current);
            }
            goal = current.equals(maze.getGoal());
            message = decision.getRationale();
            trace.add("Paso " + metrics.getSteps() + ": " + direction.getLabel() + " -> " + current
                    + (decision.isBacktracking() ? " [RETROCESO]" : "") + " | " + message);
        }

        if (goal) {
            finished = true;
            metrics.markGoal();
            stopReason = "Meta alcanzada.";
            trace.add("META ALCANZADA en " + metrics.getSteps() + " pasos.");
        }

        solver.onMoveResult(new MoveResult(previous, current, direction, valid, goal,
                decision.isBacktracking(), message));
    }

    private SolverContext buildContext() {
        List<Direction> legal = maze.legalMoves(current);
        SensorData sensors = new SensorData(
                maze.canMove(current, Direction.NORTH),
                maze.canMove(current, Direction.EAST),
                maze.canMove(current, Direction.SOUTH),
                maze.canMove(current, Direction.WEST));
        return new SolverContext(maze, mapVisible, current, maze.getGoal(), legal, sensors,
                metrics.getExplored(), metrics.getSteps());
    }

    public Maze getMaze() { return maze; }
    public MouseSolver getSolver() { return solver; }
    public Position getCurrent() { return current; }
    public Metrics getMetrics() { return metrics; }
    public boolean isStarted() { return started; }
    public boolean isFinished() { return finished; }
    public boolean isMapVisible() { return mapVisible; }
    public String getStopReason() { return stopReason; }
    public int getOptimalSteps() { return optimalSteps; }
    public List<Position> getOptimalPath() { return Collections.unmodifiableList(optimalPath); }
    public List<Position> getCurrentPath() { return Collections.unmodifiableList(currentPath); }
    public Set<Position> getDeadEnds() { return Collections.unmodifiableSet(deadEnds); }
    public List<String> getTrace() { return Collections.unmodifiableList(trace); }
    public ScoreReport getScoreReport() { return ScoreEvaluator.evaluate(metrics, optimalSteps); }
}
