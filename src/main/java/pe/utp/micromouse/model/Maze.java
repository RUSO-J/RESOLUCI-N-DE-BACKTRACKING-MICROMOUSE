package pe.utp.micromouse.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Maze {
    private final char[][] cells;
    private final Position start;
    private final Position goal;

    public Maze(char[][] cells, Position start, Position goal) {
        this.cells = new char[cells.length][cells[0].length];
        for (int r = 0; r < cells.length; r++) {
            System.arraycopy(cells[r], 0, this.cells[r], 0, cells[r].length);
        }
        this.start = start;
        this.goal = goal;
    }

    public int getRows() { return cells.length; }
    public int getCols() { return cells[0].length; }
    public Position getStart() { return start; }
    public Position getGoal() { return goal; }

    public boolean isInside(Position position) {
        return position.getRow() >= 0 && position.getRow() < getRows()
                && position.getCol() >= 0 && position.getCol() < getCols();
    }

    public boolean isWall(Position position) {
        return !isInside(position) || cells[position.getRow()][position.getCol()] == '#';
    }

    public boolean isOpen(Position position) {
        return isInside(position) && !isWall(position);
    }

    public boolean canMove(Position from, Direction direction) {
        return isOpen(from.move(direction));
    }

    public List<Direction> legalMoves(Position position) {
        List<Direction> result = new ArrayList<Direction>();
        for (Direction direction : Direction.values()) {
            if (canMove(position, direction)) result.add(direction);
        }
        return Collections.unmodifiableList(result);
    }

    public char getCell(int row, int col) {
        return cells[row][col];
    }
}
