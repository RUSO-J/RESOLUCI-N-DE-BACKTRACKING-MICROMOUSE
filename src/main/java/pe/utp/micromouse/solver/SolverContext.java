package pe.utp.micromouse.solver;

import pe.utp.micromouse.model.Direction;
import pe.utp.micromouse.model.Maze;
import pe.utp.micromouse.model.Position;
import pe.utp.micromouse.model.SensorData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class SolverContext {
    private final Maze maze;
    private final boolean mapVisible;
    private final Position currentPosition;
    private final Position goalPosition;
    private final List<Direction> legalMoves;
    private final SensorData sensors;
    private final Set<Position> visited;
    private final int stepCount;

    public SolverContext(Maze maze, boolean mapVisible, Position currentPosition,
                         Position goalPosition, List<Direction> legalMoves,
                         SensorData sensors, Set<Position> visited, int stepCount) {
        this.maze = maze;
        this.mapVisible = mapVisible;
        this.currentPosition = currentPosition;
        this.goalPosition = goalPosition;
        this.legalMoves = Collections.unmodifiableList(new ArrayList<Direction>(legalMoves));
        this.sensors = sensors;
        this.visited = Collections.unmodifiableSet(visited);
        this.stepCount = stepCount;
    }

    /** El mapa completo solo está disponible en el modo "Mapa completo". */
    public Maze getMaze() { return mapVisible ? maze : null; }
    public boolean isMapVisible() { return mapVisible; }
    public Position getCurrentPosition() { return currentPosition; }
    public Position getGoalPosition() { return goalPosition; }
    public List<Direction> getLegalMoves() { return legalMoves; }
    public SensorData getSensors() { return sensors; }
    public Set<Position> getVisited() { return visited; }
    public int getStepCount() { return stepCount; }

    public boolean hasVisited(Position position) {
        return visited.contains(position);
    }

    public void log(String ignored) {
        // El motor registra automáticamente la decisión devuelta por el solver.
        // Este método se mantiene como punto de extensión pedagógico.
    }
}
