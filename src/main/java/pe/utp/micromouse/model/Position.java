package pe.utp.micromouse.model;

import java.util.Objects;

public final class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public Position move(Direction direction) {
        return new Position(row + direction.dr(), col + direction.dc());
    }

    public Direction directionTo(Position other) {
        int dr = other.row - row;
        int dc = other.col - col;
        for (Direction d : Direction.values()) {
            if (d.dr() == dr && d.dc() == dc) return d;
        }
        throw new IllegalArgumentException("Las posiciones no son vecinas.");
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Position)) return false;
        Position p = (Position) other;
        return row == p.row && col == p.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
