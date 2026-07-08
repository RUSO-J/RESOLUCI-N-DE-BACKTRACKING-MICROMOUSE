package pe.utp.micromouse.student;

import pe.utp.micromouse.model.Direction;
import pe.utp.micromouse.model.Position;
import pe.utp.micromouse.solver.MoveDecision;
import pe.utp.micromouse.solver.MoveResult;
import pe.utp.micromouse.solver.MouseSolver;
import pe.utp.micromouse.solver.SolverContext;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;


/**
 * Implementación de Backtracking usando:
 *
 * - Pila para recordar el camino.
 * - Conjunto de visitados para evitar ciclos.
 */
public class StudentBacktrackingSolver implements MouseSolver {


    // Guarda las posiciones ya exploradas
    private Set<Position> visited;


    // Guarda el camino recorrido
    private Stack<Position> path;



    /**
     * Nombre del algoritmo.
     */
    @Override
    public String getName() {

        return "Backtracking con pila";
    }



    /**
     * Reinicia toda la información interna.
     */
    @Override
    public void reset() {


        visited = new HashSet<>();

        path = new Stack<>();

    }



    /**
     * Decide el próximo movimiento.
     *
     * IMPORTANTE:
     * Aquí NO se mueve el ratón.
     * Solo devolvemos una decisión.
     */
    @Override
    public MoveDecision nextMove(SolverContext context) {


        Position actual =
                context.getCurrentPosition();



        /*
         * Primera vez que estamos en una celda:
         * la guardamos.
         */
        if(!visited.contains(actual)){


            visited.add(actual);

            path.push(actual);

        }



        /*
         * Revisamos todos los movimientos legales.
         */
        for(Direction d : context.getLegalMoves()){


            Position siguiente =
                    actual.move(d);



            /*
             * Si encontramos una celda nueva,
             * avanzamos.
             */
            if(!visited.contains(siguiente)){


                return MoveDecision.move(
                        d,
                        "Celda nueva encontrada"
                );

            }

        }



        /*
         * Si no existen caminos nuevos,
         * hacemos retroceso.
         */
        if(path.size() > 1){


            Position anterior =
                    path.get(path.size()-2);



            Direction regreso =
                    actual.directionTo(anterior);



            return MoveDecision.backtrack(
                    regreso,
                    "No hay caminos nuevos, regreso"
            );

        }



        /*
         * Si no hay solución:
         */
        return MoveDecision.stop(
                "No existen movimientos disponibles"
        );

    }



    /**
     * Recibe información después del movimiento.
     */
    @Override
    public void onMoveResult(MoveResult result) {


        /*
         * Si el movimiento fue correcto:
         * guardamos la nueva posición.
         */
        if(result.isValid()){


            Position actual =
                    result.getCurrent();


            visited.add(actual);


            path.push(actual);


        }


        /*
         * Si fue retroceso:
         * quitamos la posición actual
         * del camino.
         */
        if(result.isBacktracking()
                && !path.isEmpty()){


            path.pop();

        }

    }

}