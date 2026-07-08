package pe.utp.micromouse.student;


/**
 * Implementación de Shell Sort.
 *
 * Ordena resultados experimentales
 * por número de pasos realizados.
 */
public final class StudentShellSortResults {


    private StudentShellSortResults() {

    }



    /**
     * Ordenamiento ascendente usando Shell Sort.
     */
    public static void sortBySteps(int[] values) {


        int n = values.length;



        /*
         * Reducción del salto (gap)
         */
        for(int gap = n / 2; gap > 0; gap /= 2) {



            /*
             * Inserción usando el salto actual
             */
            for(int i = gap; i < n; i++) {


                int temp = values[i];


                int j = i;



                while(j >= gap &&
                        values[j - gap] > temp) {



                    values[j] = values[j - gap];


                    j -= gap;

                }



                values[j] = temp;

            }

        }

    }

}