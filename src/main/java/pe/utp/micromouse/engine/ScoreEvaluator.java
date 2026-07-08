package pe.utp.micromouse.engine;

public final class ScoreEvaluator {
    private ScoreEvaluator() { }

    public static ScoreReport evaluate(Metrics metrics, int optimalSteps) {
        if (!metrics.isReachedGoal()) {
            return new ScoreReport(optimalSteps, 0.0, "FALLIDO", "No llegó a la meta.");
        }
        if (optimalSteps <= 0 || metrics.getSteps() <= 0) {
            return new ScoreReport(optimalSteps, 0.0, "SIN REFERENCIA", "No se pudo calcular la ruta óptima.");
        }
        double efficiency = 100.0 * optimalSteps / metrics.getSteps();
        double ratio = (double) metrics.getSteps() / optimalSteps;
        if (ratio <= 1.0) return new ScoreReport(optimalSteps, efficiency, "ÓPTIMO", "La ruta coincide con el mínimo de pasos de BFS.");
        if (ratio <= 1.15) return new ScoreReport(optimalSteps, efficiency, "EXCELENTE", "La ruta está a menos de 15% del óptimo.");
        if (ratio <= 1.40) return new ScoreReport(optimalSteps, efficiency, "BUENO", "La ruta es válida y relativamente cercana al óptimo.");
        if (ratio <= 2.00) return new ScoreReport(optimalSteps, efficiency, "ACEPTABLE", "La ruta llega a la meta, pero explora pasos adicionales.");
        return new ScoreReport(optimalSteps, efficiency, "MEJORABLE", "La ruta llega a la meta, pero usa más del doble de pasos óptimos.");
    }
}
