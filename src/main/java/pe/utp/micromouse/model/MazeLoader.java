package pe.utp.micromouse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class MazeLoader {
    private MazeLoader() { }

    public static Maze load(MazeScenario scenario) throws IOException {
        InputStream input = MazeLoader.class.getResourceAsStream(scenario.getResourcePath());
        if (input == null) {
            throw new IOException("No se encontró el escenario: " + scenario.getResourcePath());
        }
        List<String> rows = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) rows.add(line);
            }
        }
        if (rows.isEmpty()) throw new IOException("El escenario está vacío.");
        int cols = rows.get(0).length();
        char[][] cells = new char[rows.size()][cols];
        Position start = null;
        Position goal = null;
        for (int r = 0; r < rows.size(); r++) {
            if (rows.get(r).length() != cols) throw new IOException("El escenario no es rectangular.");
            for (int c = 0; c < cols; c++) {
                char value = rows.get(r).charAt(c);
                if (value != '#' && value != '.' && value != 'S' && value != 'G') {
                    throw new IOException("Símbolo inválido en el laberinto: " + value);
                }
                cells[r][c] = value;
                if (value == 'S') start = new Position(r, c);
                if (value == 'G') goal = new Position(r, c);
            }
        }
        if (start == null || goal == null) throw new IOException("El escenario debe tener S y G.");
        return new Maze(cells, start, goal);
    }
}
