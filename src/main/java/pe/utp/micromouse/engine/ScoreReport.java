package pe.utp.micromouse.engine;

public final class ScoreReport {
    private final int optimalSteps;
    private final double efficiency;
    private final String label;
    private final String explanation;

    public ScoreReport(int optimalSteps, double efficiency, String label, String explanation) {
        this.optimalSteps = optimalSteps;
        this.efficiency = efficiency;
        this.label = label;
        this.explanation = explanation;
    }

    public int getOptimalSteps() { return optimalSteps; }
    public double getEfficiency() { return efficiency; }
    public String getLabel() { return label; }
    public String getExplanation() { return explanation; }
}
