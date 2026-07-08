package pe.utp.micromouse.student;

import pe.utp.micromouse.model.Direction;
import pe.utp.micromouse.model.Position;
import pe.utp.micromouse.solver.MoveDecision;
import pe.utp.micromouse.solver.MoveResult;
import pe.utp.micromouse.solver.MouseSolver;
import pe.utp.micromouse.solver.SolverContext;

import java.util.List;


/**
 * Estrategia de selección óptima.
 *
 * Utiliza una función heurística basada en distancia Manhattan
 * para seleccionar el movimiento más cercano a la meta.
 *
 * La idea es aproximarse al comportamiento de BFS,
 * buscando reducir la cantidad de pasos.
 */
public class StudentOptimalSolver implements MouseSolver {


    @Override
    public String getName() {

        return "Seleccion Optima Heuristica";
    }



    /**
     * Reinicia información interna.
     *
     * Este algoritmo no necesita estructuras adicionales.
     */
    @Override
    public void reset() {

    }




    /**
     * Decide el siguiente movimiento.
     *
     * IMPORTANTE:
     * No mueve el ratón directamente.
     * Solo devuelve una decisión al motor.
     */
    @Override
    public MoveDecision nextMove(SolverContext context) {


        Position actual =
                context.getCurrentPosition();


        Position meta =
                context.getGoalPosition();



        List<Direction> movimientos =
                context.getLegalMoves();



        if(movimientos.isEmpty()) {


            return MoveDecision.stop(
                    "No existen movimientos disponibles"
            );

        }



        Direction mejorDireccion = null;


        int menorDistancia = Integer.MAX_VALUE;



        /*
         * Evaluamos cada movimiento permitido.
         */
        for(Direction d : movimientos) {


            Position siguiente =
                    actual.move(d);



            int distancia =
                    distanciaManhattan(
                            siguiente,
                            meta
                    );



            if(distancia < menorDistancia) {


                menorDistancia = distancia;

                mejorDireccion = d;

            }

        }




        if(mejorDireccion != null) {


            return MoveDecision.move(
                    mejorDireccion,
                    "Movimiento elegido por menor distancia hacia la meta"
            );

        }



        return MoveDecision.stop(
                "No se encontró movimiento adecuado"
        );

    }





    /**
     * Calcula distancia Manhattan.
     */
    private int distanciaManhattan(
            Position a,
            Position b
    ) {


        return Math.abs(
                a.getRow() - b.getRow()
        )
                +
                Math.abs(
                        a.getCol() - b.getCol()
                );

    }





    /**
     * Recibe la respuesta del motor.
     */
    @Override
    public void onMoveResult(MoveResult result) {


        /*
         * El motor actualiza:
         *
         * - posición
         * - pasos
         * - colisiones
         * - llegada a meta
         *
         * Por eso no necesitamos guardar información.
         */

    }

}