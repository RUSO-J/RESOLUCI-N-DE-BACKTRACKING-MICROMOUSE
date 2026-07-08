package pe.utp.micromouse.student;


/**
 * Implementación de Quick Sort.
 *
 * Ordena los resultados del benchmark
 * según cantidad de pasos.
 */
public final class StudentQuickSortResults {


    private StudentQuickSortResults() {

    }



    /**
     * Método público de ordenamiento.
     */
    public static void sortBySteps(int[] values) {


        quickSort(
                values,
                0,
                values.length - 1
        );

    }




    /**
     * Método recursivo Quick Sort.
     */
    private static void quickSort(
            int[] values,
            int inicio,
            int fin
    ) {


        if(inicio < fin) {


            int pivote =
                    particionar(
                            values,
                            inicio,
                            fin
                    );



            quickSort(
                    values,
                    inicio,
                    pivote - 1
            );



            quickSort(
                    values,
                    pivote + 1,
                    fin
            );

        }

    }





    /**
     * División del arreglo.
     */
    private static int particionar(
            int[] values,
            int inicio,
            int fin
    ) {


        int pivote =
                values[fin];


        int i = inicio - 1;



        for(int j = inicio; j < fin; j++) {



            if(values[j] <= pivote) {


                i++;


                intercambiar(
                        values,
                        i,
                        j
                );

            }

        }



        intercambiar(
                values,
                i + 1,
                fin
        );


        return i + 1;

    }





    /**
     * Intercambio de elementos.
     */
    private static void intercambiar(
            int[] values,
            int a,
            int b
    ) {


        int aux = values[a];


        values[a] = values[b];


        values[b] = aux;

    }


}