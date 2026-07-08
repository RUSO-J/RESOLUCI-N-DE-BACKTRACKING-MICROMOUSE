package pe.utp.micromouse.ui;

import pe.utp.micromouse.engine.SimulationEngine;
import pe.utp.micromouse.model.Maze;
import pe.utp.micromouse.model.Position;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashSet;
import java.util.Set;

public final class MazePanel extends JPanel {
    private SimulationEngine engine;
    private boolean showOptimal;

    public MazePanel() {
        setBackground(Color.WHITE);
    }

    public void setEngine(SimulationEngine engine) {
        this.engine = engine;
        repaint();
    }

    public void setShowOptimal(boolean showOptimal) {
        this.showOptimal = showOptimal;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (engine == null) return;
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Maze maze = engine.getMaze();
        int margin = 26;
        int cell = Math.max(8, Math.min((getWidth() - 2 * margin) / maze.getCols(), (getHeight() - 2 * margin) / maze.getRows()));
        int boardWidth = cell * maze.getCols();
        int boardHeight = cell * maze.getRows();
        int ox = (getWidth() - boardWidth) / 2;
        int oy = (getHeight() - boardHeight) / 2;

        Set<Position> optimal = new HashSet<Position>(engine.getOptimalPath());
        Set<Position> finalPath = new HashSet<Position>(engine.getCurrentPath());
        Set<Position> explored = engine.getMetrics().getExplored();
        Set<Position> deadEnds = engine.getDeadEnds();

        for (int r = 0; r < maze.getRows(); r++) {
            for (int c = 0; c < maze.getCols(); c++) {
                Position p = new Position(r, c);
                int x = ox + c * cell;
                int y = oy + r * cell;
                Color color;
                if (maze.isWall(p)) color = new Color(37, 42, 48);
                else if (deadEnds.contains(p)) color = new Color(255, 205, 205);
                else if (finalPath.contains(p)) color = new Color(190, 238, 200);
                else if (explored.contains(p)) color = new Color(205, 229, 247);
                else if (showOptimal && optimal.contains(p)) color = new Color(255, 237, 163);
                else color = new Color(247, 247, 247);
                g.setColor(color);
                g.fillRect(x, y, cell, cell);
                g.setColor(new Color(185, 185, 185));
                g.drawRect(x, y, cell, cell);
            }
        }

        drawLabel(g, ox + maze.getStart().getCol() * cell, oy + maze.getStart().getRow() * cell, cell, "S", new Color(35, 120, 220));
        drawLabel(g, ox + maze.getGoal().getCol() * cell, oy + maze.getGoal().getRow() * cell, cell, "G", new Color(30, 150, 82));

        Position current = engine.getCurrent();
        int mx = ox + current.getCol() * cell;
        int my = oy + current.getRow() * cell;
        int d = Math.max(6, cell - 8);
        g.setColor(new Color(240, 130, 30));
        g.fillOval(mx + (cell - d) / 2, my + (cell - d) / 2, d, d);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(1.5f));
        g.drawOval(mx + (cell - d) / 2, my + (cell - d) / 2, d, d);

        g.setColor(new Color(45, 45, 45));
        g.drawString("Azul: explorado | Rojo: rama descartada | Verde: ruta actual | Amarillo: óptimo BFS", 14, getHeight() - 10);
        g.dispose();
    }

    private void drawLabel(Graphics2D g, int x, int y, int cell, String label, Color color) {
        g.setColor(color);
        g.fillRoundRect(x + 2, y + 2, Math.max(6, cell - 4), Math.max(6, cell - 4), 6, 6);
        g.setColor(Color.WHITE);
        g.drawString(label, x + Math.max(4, cell / 3), y + Math.max(12, cell / 2 + 5));
    }
}
