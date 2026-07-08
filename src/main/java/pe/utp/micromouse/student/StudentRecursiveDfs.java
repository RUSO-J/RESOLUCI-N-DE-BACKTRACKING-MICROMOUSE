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
 * Implementación de búsqueda DFS (Depth First Search).
 *
 * Características:
 *
 * - Explora una ruta hasta encontrar la meta.
 * - Guarda las posiciones visitadas.
 * - Utiliza una pila para recordar el camino.
 * - Retrocede cuando encuentra un callejón sin salida.
 */
public class StudentRecursiveDfs implements MouseSolver {


    // Celdas ya exploradas
    private Set<Position> visited;


    // Camino actual del algoritmo
    private Stack<Position> path;



    @Override
    public String getName() {

        return "DFS Recursivo del estudiante";
    }



    /**
     * Reinicia el algoritmo para una nueva ejecución.
     */
    @Override
    public void reset() {


        visited = new HashSet<>();

        path = new Stack<>();

    }




    /**
     * Decide el siguiente movimiento.
     */
    @Override
    public MoveDecision nextMove(SolverContext context) {


        Position actual =
                context.getCurrentPosition();



        /*
         * Registrar posición inicial
         */
        if(!visited.contains(actual)) {


            visited.add(actual);

            path.push(actual);

        }




        /*
         * Buscar una dirección que lleve
         * a una celda no visitada.
         */
        for(Direction d : context.getLegalMoves()) {


            Position siguiente =
                    actual.move(d);



            if(!visited.contains(siguiente)) {


                return MoveDecision.move(
                        d,
                        "DFS: explorando nueva celda "
                                + siguiente
                );

            }

        }





        /*
         * Si todos los caminos fueron visitados,
         * realizamos retroceso.
         */
        if(path.size() > 1) {


            Position anterior =
                    path.get(path.size() - 2);



            Direction regreso =
                    actual.directionTo(anterior);



            return MoveDecision.backtrack(
                    regreso,
                    "DFS: retroceso por callejón sin salida"
            );

        }





        return MoveDecision.stop(
                "DFS finalizó la exploración"
        );

    }




    /**
     * Recibe información después del movimiento.
     */
    @Override
    public void onMoveResult(MoveResult result) {


        if(result.isValid()) {


            Position nueva =
                    result.getCurrent();



            visited.add(nueva);


            path.push(nueva);

        }



        /*
         * Si fue un retroceso,
         * eliminamos la última posición.
         */
        if(result.isBacktracking()
                && !path.isEmpty()) {


            path.pop();

        }


    }

}