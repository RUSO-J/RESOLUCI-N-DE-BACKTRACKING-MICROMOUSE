package pe.utp.micromouse.app;

import pe.utp.micromouse.engine.PathFinder;
import pe.utp.micromouse.model.Maze;
import pe.utp.micromouse.model.MazeCatalog;
import pe.utp.micromouse.model.MazeLoader;
import pe.utp.micromouse.model.MazeScenario;

/** Prueba sin librerías externas: verifica que los 10 escenarios tengan ruta BFS. */
public final class QualityCheck {
    private QualityCheck() { }

    public static void main(String[] args) throws Exception {
        int valid = 0;
        for (MazeScenario scenario : MazeCatalog.scenarios()) {
            Maze maze = MazeLoader.load(scenario);
            int steps = PathFinder.shortestPath(maze).size();
            if (steps <= 0) throw new IllegalStateException("Sin ruta en " + scenario.getId());
            valid++;
            System.out.println(scenario.getId() + " OK - óptimo BFS=" + steps + " pasos");
        }
        System.out.println("Validación final: " + valid + " escenarios correctos.");
    }
}
