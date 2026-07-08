package pe.utp.micromouse.ui;

import pe.utp.micromouse.engine.BenchmarkResult;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public final class BenchmarkTableModel extends AbstractTableModel {
    private final String[] columns = {"Escenario", "Dificultad", "Pasos", "Óptimo", "Explorados", "Retrocesos", "Tiempo decisión (ms)", "Resultado"};
    private final List<BenchmarkResult> rows = new ArrayList<BenchmarkResult>();

    public void setRows(List<BenchmarkResult> values) {
        rows.clear();
        rows.addAll(values);
        fireTableDataChanged();
    }

    @Override public int getRowCount() { return rows.size(); }
    @Override public int getColumnCount() { return columns.length; }
    @Override public String getColumnName(int column) { return columns[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BenchmarkResult r = rows.get(rowIndex);
        switch (columnIndex) {
            case 0: return r.getScenario();
            case 1: return r.getDifficulty();
            case 2: return r.getSteps();
            case 3: return r.getOptimal();
            case 4: return r.getExplored();
            case 5: return r.getBacktracks();
            case 6: return String.format("%.3f", r.getDecisionMillis());
            default: return r.getScore();
        }
    }
}
