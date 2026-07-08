package pe.utp.micromouse.model;

public final class MazeScenario {
    private final String id;
    private final String name;
    private final String difficulty;
    private final String resourcePath;

    public MazeScenario(String id, String name, String difficulty, String resourcePath) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.resourcePath = resourcePath;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDifficulty() { return difficulty; }
    public String getResourcePath() { return resourcePath; }

    @Override
    public String toString() {
        return id + " - " + name + " (" + difficulty + ")";
    }
}
