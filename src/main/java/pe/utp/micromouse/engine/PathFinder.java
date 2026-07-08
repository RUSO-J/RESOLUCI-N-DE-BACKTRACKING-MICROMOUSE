package pe.utp.micromouse.engine;

import pe.utp.micromouse.model.Direction;
import pe.utp.micromouse.model.Maze;
import pe.utp.micromouse.model.Position;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class PathFinder {
    private PathFinder() { }

    public static List<Direction> shortestPath(Maze maze) {
        Position start = maze.getStart();
        Position goal = maze.getGoal();
        Queue<Position> queue = new ArrayDeque<Position>();
        Set<Position> visited = new HashSet<Position>();
        Map<Position, Position> parent = new HashMap<Position, Position>();
        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            if (current.equals(goal)) break;
            for (Direction direction : Direction.values()) {
                Position next = current.move(direction);
                if (maze.isOpen(next) && !visited.contains(next)) {
                    visited.add(next);
                    parent.put(next, current);
                    queue.offer(next);
                }
            }
        }

        if (!visited.contains(goal)) return Collections.emptyList();
        List<Direction> reverse = new ArrayList<Direction>();
        Position current = goal;
        while (!current.equals(start)) {
            Position previous = parent.get(current);
            reverse.add(previous.directionTo(current));
            current = previous;
        }
        Collections.reverse(reverse);
        return reverse;
    }

    public static List<Position> pathPositions(Maze maze) {
        List<Direction> directions = shortestPath(maze);
        if (directions.isEmpty() && !maze.getStart().equals(maze.getGoal())) return Collections.emptyList();
        List<Position> positions = new ArrayList<Position>();
        Position current = maze.getStart();
        positions.add(current);
        for (Direction direction : directions) {
            current = current.move(direction);
            positions.add(current);
        }
        return positions;
    }
}
