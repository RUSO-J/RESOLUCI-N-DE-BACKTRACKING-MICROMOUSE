package pe.utp.micromouse.model;

public enum Direction {
    NORTH(-1, 0, "Norte"),
    EAST(0, 1, "Este"),
    SOUTH(1, 0, "Sur"),
    WEST(0, -1, "Oeste");

    private final int dr;
    private final int dc;
    private final String label;

    Direction(int dr, int dc, String label) {
        this.dr = dr;
        this.dc = dc;
        this.label = label;
    }

    public int dr() { return dr; }
    public int dc() { return dc; }
    public String getLabel() { return label; }

    public Direction opposite() {
        switch (this) {
            case NORTH: return SOUTH;
            case SOUTH: return NORTH;
            case EAST: return WEST;
            default: return EAST;
        }
    }

    public Direction right() {
        switch (this) {
            case NORTH: return EAST;
            case EAST: return SOUTH;
            case SOUTH: return WEST;
            default: return NORTH;
        }
    }

    public Direction left() {
        switch (this) {
            case NORTH: return WEST;
            case WEST: return SOUTH;
            case SOUTH: return EAST;
            default: return NORTH;
        }
    }
}
