package pe.utp.micromouse.ui;

import pe.utp.micromouse.engine.Metrics;
import pe.utp.micromouse.engine.ScoreReport;
import pe.utp.micromouse.engine.SimulationEngine;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Locale;

public final class MetricsPanel extends JPanel {
    private final JLabel algorithm = new JLabel();
    private final JLabel status = new JLabel();
    private final JLabel steps = new JLabel();
    private final JLabel backtracks = new JLabel();
    private final JLabel explored = new JLabel();
    private final JLabel invalid = new JLabel();
    private final JLabel decisionTime = new JLabel();
    private final JLabel optimum = new JLabel();
    private final JLabel efficiency = new JLabel();
    private final JLabel score = new JLabel();

    public MetricsPanel() {
        setLayout(new GridLayout(0, 1, 3, 3));
        setBorder(BorderFactory.createTitledBorder("Métricas y evaluación"));
        add(algorithm); add(status); add(steps); add(backtracks); add(explored); add(invalid);
        add(decisionTime); add(optimum); add(efficiency); add(score);
    }

    public void update(SimulationEngine engine) {
        Metrics m = engine.getMetrics();
        ScoreReport report = engine.getScoreReport();
        algorithm.setText("Algoritmo: " + engine.getSolver().getName());
        status.setText("Estado: " + engine.getStopReason());
        steps.setText("Pasos: " + m.getSteps());
        backtracks.setText("Retrocesos: " + m.getBacktracks());
        explored.setText("Celdas exploradas: " + m.getExploredCount());
        invalid.setText("Movimientos inválidos: " + m.getInvalidMoves());
        decisionTime.setText(String.format(Locale.US, "Tiempo de decisión: %.3f ms", m.getDecisionNanos() / 1_000_000.0));
        optimum.setText("Óptimo BFS: " + report.getOptimalSteps() + " pasos");
        efficiency.setText(String.format(Locale.US, "Eficiencia: %.1f%%", report.getEfficiency()));
        score.setText("Resultado: " + report.getLabel());
        score.setForeground(colorFor(report.getLabel()));
    }

    private Color colorFor(String label) {
        if ("ÓPTIMO".equals(label) || "EXCELENTE".equals(label)) return new Color(0, 130, 65);
        if ("BUENO".equals(label)) return new Color(34, 105, 180);
        if ("ACEPTABLE".equals(label)) return new Color(185, 130, 0);
        return new Color(190, 25, 25);
    }
}
