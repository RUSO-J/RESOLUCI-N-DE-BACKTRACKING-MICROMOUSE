package pe.utp.micromouse.engine;

import pe.utp.micromouse.model.Maze;
import pe.utp.micromouse.model.MazeCatalog;
import pe.utp.micromouse.model.MazeLoader;
import pe.utp.micromouse.model.MazeScenario;
import pe.utp.micromouse.solver.MouseSolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class BenchmarkRunner {
    private BenchmarkRunner() { }

    public static List<BenchmarkResult> runAll(Supplier<MouseSolver> factory, boolean mapVisible) throws IOException {
        List<BenchmarkResult> results = new ArrayList<BenchmarkResult>();
        for (MazeScenario scenario : MazeCatalog.scenarios()) {
            Maze maze = MazeLoader.load(scenario);
            SimulationEngine engine = new SimulationEngine(maze, factory.get(), mapVisible);
            int safety = Math.max(500, maze.getRows() * maze.getCols() * 20);
            while (!engine.isFinished() && safety-- > 0) engine.step();
            if (!engine.isFinished()) {
                engine.getMetrics().stop();
            }
            ScoreReport report = engine.getScoreReport();
            Metrics m = engine.getMetrics();
            results.add(new BenchmarkResult(
                    scenario.getId(), scenario.getDifficulty(), m.getSteps(), engine.getOptimalSteps(),
                    m.getExploredCount(), m.getBacktracks(), m.getDecisionNanos() / 1_000_000.0,
                    report.getLabel()));
        }
        return results;
    }
}
