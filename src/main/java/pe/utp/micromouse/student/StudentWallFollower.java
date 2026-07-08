package pe.utp.micromouse.student;

import pe.utp.micromouse.model.Direction;
import pe.utp.micromouse.solver.MoveDecision;
import pe.utp.micromouse.solver.MoveResult;
import pe.utp.micromouse.solver.MouseSolver;
import pe.utp.micromouse.solver.SolverContext;

import java.util.List;

/**
 * Implementación del algoritmo Seguidor de Pared.
 *
 * Estrategia:
 * - Mantiene una pared a la derecha del ratón.
 * - Prioridad:
 *      1. Girar a la derecha si existe camino.
 *      2. Continuar hacia adelante.
 *      3. Girar a la izquierda.
 *      4. Retroceder si está encerrado.
 *
 * Este algoritmo utiliza únicamente información disponible mediante sensores
 * y movimientos legales.
 */
public class StudentWallFollower implements MouseSolver {

    // Dirección actual del movimiento del ratón
    private Direction currentDirection;

    @Override
    public String getName() {
        return "Seguidor de pared derecho";
    }

    /**
     * Reinicia las variables internas del algoritmo.
     */
    @Override
    public void reset() {

        // Al iniciar asumimos orientación hacia el norte.
        // El motor se encargará de validar los movimientos.
        currentDirection = Direction.NORTH;
    }


    /**
     * Decide el siguiente movimiento.
     *
     * Importante:
     * Este método NO mueve al ratón.
     * Solo devuelve una decisión al motor.
     */
    @Override
    public MoveDecision nextMove(SolverContext context) {

        List<Direction> movimientos = context.getLegalMoves();


        /*
         * Regla 1:
         * Intentar girar hacia la derecha.
         */
        Direction derecha = currentDirection.right();

        if (movimientos.contains(derecha)) {

            currentDirection = derecha;

            return MoveDecision.move(
                    derecha,
                    "Existe camino a la derecha, mantengo la pared derecha"
            );
        }


        /*
         * Regla 2:
         * Continuar recto.
         */
        if (movimientos.contains(currentDirection)) {

            return MoveDecision.move(
                    currentDirection,
                    "Continúo siguiendo la pared derecha"
            );
        }


        /*
         * Regla 3:
         * Si no puedo avanzar, giro izquierda.
         */
        Direction izquierda = currentDirection.left();

        if (movimientos.contains(izquierda)) {

            currentDirection = izquierda;

            return MoveDecision.move(
                    izquierda,
                    "Obstáculo encontrado, giro a la izquierda"
            );
        }


        /*
         * Regla 4:
         * Si todas las direcciones están bloqueadas,
         * regreso por donde vine.
         */
        Direction retroceso = currentDirection.opposite();

        if (movimientos.contains(retroceso)) {

            currentDirection = retroceso;

            return MoveDecision.backtrack(
                    retroceso,
                    "Sin caminos disponibles, realizo retroceso"
            );
        }


        /*
         * No existe ningún movimiento posible.
         */
        return MoveDecision.stop(
                "No existen movimientos legales disponibles"
        );
    }


    /**
     * Permite actualizar información después del movimiento.
     */
    @Override
    public void onMoveResult(MoveResult result) {

        /*
         * Si el movimiento fue válido,
         * mantenemos la dirección actual.
         *
         * El motor controla:
         * - posición
         * - colisiones
         * - llegada a meta
         * - métricas
         */
        if (!result.isValid()) {

            // Si falla, evitamos mantener una dirección incorrecta.
            currentDirection = currentDirection.opposite();
        }
    }
}