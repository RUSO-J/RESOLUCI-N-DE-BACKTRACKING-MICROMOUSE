package pe.utp.micromouse.ui;

import pe.utp.micromouse.app.SolverOption;
import pe.utp.micromouse.engine.BenchmarkRunner;
import pe.utp.micromouse.engine.SimulationEngine;
import pe.utp.micromouse.model.Direction;
import pe.utp.micromouse.model.Maze;
import pe.utp.micromouse.model.MazeCatalog;
import pe.utp.micromouse.model.MazeLoader;
import pe.utp.micromouse.model.MazeScenario;
import pe.utp.micromouse.solver.ManualSolver;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public final class MainFrame extends JFrame {
    private final JComboBox<MazeScenario> scenarioCombo = new JComboBox<MazeScenario>();
    private final JComboBox<SolverOption> solverCombo = new JComboBox<SolverOption>(SolverOption.values());
    private final JComboBox<String> modeCombo = new JComboBox<String>(new String[]{"Mapa completo", "Solo sensores"});
    private final JButton loadButton = new JButton("Cargar escenario");
    private final JButton startButton = new JButton("Iniciar");
    private final JButton stepButton = new JButton("Paso a paso");
    private final JButton pauseButton = new JButton("Pausar");
    private final JButton resetButton = new JButton("Reiniciar");
    private final JButton optimalButton = new JButton("Mostrar óptimo");
    private final JButton benchmarkButton = new JButton("Comparar 10 escenarios");
    private final JSlider speedSlider = new JSlider(50, 1000, 350);

    private final MazePanel mazePanel = new MazePanel();
    private final MetricsPanel metricsPanel = new MetricsPanel();
    private final JTextArea traceArea = new JTextArea();
    private final BenchmarkTableModel benchmarkModel = new BenchmarkTableModel();
    private final Timer timer;
    private SimulationEngine engine;
    private boolean showOptimal;

    public MainFrame() {
        super("MicroMouse Lab UTP - Recursividad, Backtracking y Selección Óptima");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1180, 760));
        setSize(1280, 820);
        setLocationRelativeTo(null);
        buildUi();
        bindManualKeys();
        timer = new Timer(speedSlider.getValue(), new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { performStep(); }
        });
        for (MazeScenario scenario : MazeCatalog.scenarios()) scenarioCombo.addItem(scenario);
        loadScenario();
    }

    private void buildUi() {
        setLayout(new BorderLayout(8, 8));
        add(buildToolbar(), BorderLayout.NORTH);

        traceArea.setEditable(false);
        traceArea.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 12));
        JScrollPane traceScroll = new JScrollPane(traceArea);
        traceScroll.setBorder(BorderFactory.createTitledBorder("Traza de decisiones y retrocesos"));

        JTable table = new JTable(benchmarkModel);
        JScrollPane benchmarkScroll = new JScrollPane(table);
        benchmarkScroll.setBorder(BorderFactory.createTitledBorder("Benchmark: resultados de los 10 escenarios"));

        JTabbedPane bottomTabs = new JTabbedPane();
        bottomTabs.addTab("Traza", traceScroll);
        bottomTabs.addTab("Comparativa", benchmarkScroll);

        JPanel right = new JPanel(new BorderLayout(6, 6));
        right.add(metricsPanel, BorderLayout.NORTH);
        right.add(buildManualPanel(), BorderLayout.CENTER);

        JSplitPane center = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mazePanel, right);
        center.setResizeWeight(0.72);
        center.setDividerLocation(850);

        JSplitPane vertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, center, bottomTabs);
        vertical.setResizeWeight(0.73);
        vertical.setDividerLocation(520);
        add(vertical, BorderLayout.CENTER);
    }

    private JPanel buildToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 7, 5));
        toolbar.add(new JLabel("Escenario:")); toolbar.add(scenarioCombo);
        toolbar.add(new JLabel("Algoritmo:")); toolbar.add(solverCombo);
        toolbar.add(new JLabel("Modo:")); toolbar.add(modeCombo);
        toolbar.add(loadButton); toolbar.add(startButton); toolbar.add(stepButton); toolbar.add(pauseButton);
        toolbar.add(resetButton); toolbar.add(optimalButton); toolbar.add(benchmarkButton);
        toolbar.add(new JLabel("Velocidad:"));
        speedSlider.setToolTipText("Menor valor = animación más rápida");
        toolbar.add(speedSlider);

        loadButton.addActionListener(e -> loadScenario());
        startButton.addActionListener(e -> startSimulation());
        stepButton.addActionListener(e -> performStep());
        pauseButton.addActionListener(e -> timer.stop());
        resetButton.addActionListener(e -> resetSimulation());
        optimalButton.addActionListener(e -> { showOptimal = !showOptimal; mazePanel.setShowOptimal(showOptimal); optimalButton.setText(showOptimal ? "Ocultar óptimo" : "Mostrar óptimo"); });
        benchmarkButton.addActionListener(e -> runBenchmark());
        speedSlider.addChangeListener(e -> timer.setDelay(speedSlider.getValue()));
        return toolbar;
    }

    private JPanel buildManualPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Control manual (solo si eliges Manual)"));
        JPanel grid = new JPanel(new GridLayout(3, 3, 4, 4));
        JButton north = new JButton("↑ Norte");
        JButton west = new JButton("← Oeste");
        JButton east = new JButton("Este →");
        JButton south = new JButton("↓ Sur");
        grid.add(new JLabel()); grid.add(north); grid.add(new JLabel());
        grid.add(west); grid.add(new JLabel("Ratón", SwingConstants.CENTER)); grid.add(east);
        grid.add(new JLabel()); grid.add(south); grid.add(new JLabel());
        north.addActionListener(e -> manualMove(Direction.NORTH));
        east.addActionListener(e -> manualMove(Direction.EAST));
        south.addActionListener(e -> manualMove(Direction.SOUTH));
        west.addActionListener(e -> manualMove(Direction.WEST));
        panel.add(grid, BorderLayout.CENTER);

        JLabel hint = new JLabel("También puedes usar las flechas del teclado.", SwingConstants.CENTER);
        panel.add(hint, BorderLayout.SOUTH);
        return panel;
    }

    private void bindManualKeys() {
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "moveN");
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "moveE");
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "moveS");
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "moveW");
        getRootPane().getActionMap().put("moveN", new javax.swing.AbstractAction() { public void actionPerformed(ActionEvent e) { manualMove(Direction.NORTH); }});
        getRootPane().getActionMap().put("moveE", new javax.swing.AbstractAction() { public void actionPerformed(ActionEvent e) { manualMove(Direction.EAST); }});
        getRootPane().getActionMap().put("moveS", new javax.swing.AbstractAction() { public void actionPerformed(ActionEvent e) { manualMove(Direction.SOUTH); }});
        getRootPane().getActionMap().put("moveW", new javax.swing.AbstractAction() { public void actionPerformed(ActionEvent e) { manualMove(Direction.WEST); }});
    }

    private void manualMove(Direction direction) {
        if (engine == null || !(engine.getSolver() instanceof ManualSolver)) {
            return;
        }
        ((ManualSolver) engine.getSolver()).enqueue(direction);
        performStep();
    }

    private void loadScenario() {
        timer.stop();
        try {
            MazeScenario scenario = (MazeScenario) scenarioCombo.getSelectedItem();
            SolverOption option = (SolverOption) solverCombo.getSelectedItem();
            Maze maze = MazeLoader.load(scenario);
            boolean mapVisible = "Mapa completo".equals(modeCombo.getSelectedItem());
            engine = new SimulationEngine(maze, option.create(), mapVisible);
            showOptimal = false;
            optimalButton.setText("Mostrar óptimo");
            refresh();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al cargar escenario", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startSimulation() {
        if (engine == null) return;
        engine.start();
        timer.start();
        refresh();
    }

    private void performStep() {
        if (engine == null || engine.isFinished()) {
            timer.stop();
            refresh();
            return;
        }
        engine.step();
        if (engine.isFinished()) timer.stop();
        refresh();
    }

    private void resetSimulation() {
        timer.stop();
        if (engine != null) engine.reset();
        refresh();
    }

    private void refresh() {
        if (engine == null) return;
        mazePanel.setEngine(engine);
        mazePanel.setShowOptimal(showOptimal);
        metricsPanel.update(engine);
        StringBuilder trace = new StringBuilder();
        for (String line : engine.getTrace()) trace.append(line).append('\n');
        traceArea.setText(trace.toString());
        traceArea.setCaretPosition(traceArea.getDocument().getLength());
    }

    private void runBenchmark() {
        final SolverOption option = (SolverOption) solverCombo.getSelectedItem();
        final boolean mapVisible = "Mapa completo".equals(modeCombo.getSelectedItem());
        benchmarkButton.setEnabled(false);
        benchmarkButton.setText("Ejecutando...");
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    final List<pe.utp.micromouse.engine.BenchmarkResult> results = BenchmarkRunner.runAll(option.getFactory(), mapVisible);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override public void run() { benchmarkModel.setRows(results); }
                    });
                } catch (final Exception ex) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override public void run() { JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(), "Benchmark", JOptionPane.ERROR_MESSAGE); }
                    });
                } finally {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override public void run() { benchmarkButton.setEnabled(true); benchmarkButton.setText("Comparar 10 escenarios"); }
                    });
                }
            }
        }, "benchmark-runner").start();
    }
}
