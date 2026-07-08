package pe.utp.micromouse.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class MazeCatalog {
    private MazeCatalog() { }

    public static List<MazeScenario> scenarios() {
        return Collections.unmodifiableList(Arrays.asList(
            new MazeScenario("S01", "Camino recto", "Muy simple", "/mazes/s01_camino_recto.txt"),
            new MazeScenario("S02", "Un giro", "Simple", "/mazes/s02_un_giro.txt"),
            new MazeScenario("S03", "Callejón sin salida", "Simple", "/mazes/s03_callejon.txt"),
            new MazeScenario("S04", "Bifurcación", "Media", "/mazes/s04_bifurcacion.txt"),
            new MazeScenario("S05", "Laberinto con bucle", "Media", "/mazes/s05_bucle.txt"),
            new MazeScenario("S06", "Camino engañoso", "Media", "/mazes/s06_engano.txt"),
            new MazeScenario("S07", "Corredores estrechos", "Media-alta", "/mazes/s07_corredores.txt"),
            new MazeScenario("S08", "Varias rutas", "Alta", "/mazes/s08_varias_rutas.txt"),
            new MazeScenario("S09", "Espiral y salidas falsas", "Alta", "/mazes/s09_espiral.txt"),
            new MazeScenario("S10", "Desafío final Micromouse", "Difícil", "/mazes/s10_desafio_final.txt")
        ));
    }
}
