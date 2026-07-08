package pe.utp.micromouse.engine;

public final class BenchmarkResult {
    private final String scenario;
    private final String difficulty;
    private final int steps;
    private final int optimal;
    private final int explored;
    private final int backtracks;
    private final double decisionMillis;
    private final String score;

    public BenchmarkResult(String scenario, String difficulty, int steps, int optimal,
                           int explored, int backtracks, double decisionMillis, String score) {
        this.scenario = scenario;
        this.difficulty = difficulty;
        this.steps = steps;
        this.optimal = optimal;
        this.explored = explored;
        this.backtracks = backtracks;
        this.decisionMillis = decisionMillis;
        this.score = score;
    }

    public String getScenario() { return scenario; }
    public String getDifficulty() { return difficulty; }
    public int getSteps() { return steps; }
    public int getOptimal() { return optimal; }
    public int getExplored() { return explored; }
    public int getBacktracks() { return backtracks; }
    public double getDecisionMillis() { return decisionMillis; }
    public String getScore() { return score; }
}
