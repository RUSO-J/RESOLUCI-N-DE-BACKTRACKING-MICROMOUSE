package pe.utp.micromouse.solver;

import pe.utp.micromouse.model.Direction;

public final class RightHandSolver implements MouseSolver {
    private Direction heading = Direction.EAST;

    @Override public String getName() { return "Seguidor de pared derecha"; }
    @Override public void reset() { heading = Direction.EAST; }

    @Override
    public MoveDecision nextMove(SolverContext context) {
        Direction[] preference = { heading.right(), heading, heading.left(), heading.opposite() };
        for (Direction option : preference) {
            if (context.getSensors().isOpen(option)) {
                heading = option;
                return MoveDecision.move(option, "Regla mano derecha: " + option.getLabel());
            }
        }
        return MoveDecision.stop("El ratón no tiene movimientos legales.");
    }

    @Override public void onMoveResult(MoveResult result) { }
}
