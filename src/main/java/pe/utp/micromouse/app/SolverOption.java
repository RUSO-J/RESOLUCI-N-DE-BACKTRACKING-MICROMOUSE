package pe.utp.micromouse.app;

import pe.utp.micromouse.solver.BfsReferenceSolver;
import pe.utp.micromouse.solver.ManualSolver;
import pe.utp.micromouse.solver.MouseSolver;
import pe.utp.micromouse.solver.RandomSolver;
import pe.utp.micromouse.solver.RecursiveDfsSolver;
import pe.utp.micromouse.solver.RightHandSolver;
import pe.utp.micromouse.student.StudentBacktrackingSolver;
import pe.utp.micromouse.student.StudentOptimalSolver;
import pe.utp.micromouse.student.StudentRecursiveDfs;
import pe.utp.micromouse.student.StudentWallFollower;

import java.util.function.Supplier;

public enum SolverOption {
    MANUAL("Manual (flechas o botones)", ManualSolver::new),
    RANDOM("Aleatorio controlado", RandomSolver::new),
    WALL("Seguidor de pared derecha", RightHandSolver::new),
    DFS("DFS recursivo con backtracking", RecursiveDfsSolver::new),
    BFS("BFS de referencia (ruta óptima)", BfsReferenceSolver::new),
    STUDENT_WALL("STUB: Seguidor de pared", StudentWallFollower::new),
    STUDENT_DFS("STUB: DFS recursivo", StudentRecursiveDfs::new),
    STUDENT_BACKTRACK("STUB: Backtracking", StudentBacktrackingSolver::new),
    STUDENT_OPTIMAL("STUB: Selección óptima", StudentOptimalSolver::new);

    private final String label;
    private final Supplier<MouseSolver> factory;

    SolverOption(String label, Supplier<MouseSolver> factory) {
        this.label = label;
        this.factory = factory;
    }

    public MouseSolver create() { return factory.get(); }
    public Supplier<MouseSolver> getFactory() { return factory; }
    @Override public String toString() { return label; }
}
