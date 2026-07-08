package pe.utp.micromouse.model;

public final class SensorData {
    private final boolean northOpen;
    private final boolean eastOpen;
    private final boolean southOpen;
    private final boolean westOpen;

    public SensorData(boolean northOpen, boolean eastOpen, boolean southOpen, boolean westOpen) {
        this.northOpen = northOpen;
        this.eastOpen = eastOpen;
        this.southOpen = southOpen;
        this.westOpen = westOpen;
    }

    public boolean isOpen(Direction direction) {
        switch (direction) {
            case NORTH: return northOpen;
            case EAST: return eastOpen;
            case SOUTH: return southOpen;
            default: return westOpen;
        }
    }

    public boolean isNorthOpen() { return northOpen; }
    public boolean isEastOpen() { return eastOpen; }
    public boolean isSouthOpen() { return southOpen; }
    public boolean isWestOpen() { return westOpen; }
}
