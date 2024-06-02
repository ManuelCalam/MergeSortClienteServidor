package mergesort;

import java.util.concurrent.RecursiveAction;

public class ForkJoin extends RecursiveAction {

    private static final int minimumSize = 1000;

    private int[] array;
    private int[] temp;
    private int inicio;
    private int fin;

    public ForkJoin(int[] array, int inicio, int fin) {
        this.array = array;
        this.inicio = inicio;
        this.fin = fin;
        this.temp = new int[array.length];
    }

    @Override
    protected void compute() {
        int length = fin - inicio;
        if (length < 2) {
            return;
        }
        if (length <= minimumSize) {
            mergeSort(array, inicio, fin);
        } else {
            int medio = (inicio + fin) / 2;
            ForkJoin left = new ForkJoin(array, inicio, medio);
            ForkJoin right = new ForkJoin(array, medio, fin);
            invokeAll(left, right);
            merge(array, inicio, medio, fin);
        }
    }

    private void mergeSort(int[] array, int inicio, int fin) {
        if (fin - inicio < 2) {
            return;
        }
        int medio = (inicio + fin) / 2;
        mergeSort(array, inicio, medio);
        mergeSort(array, medio, fin);
        merge(array, inicio, medio, fin);
    }

    private void merge(int[] array, int inicio, int medio, int fin) {
        System.arraycopy(array, inicio, temp, inicio, medio - inicio);
        int i = inicio;
        int j = medio;
        int k = inicio;
        while (k < fin) {
            if (i < medio && (j >= fin || temp[i] <= array[j])) {
                array[k++] = temp[i++];
            } else {
                array[k++] = array[j++];
            }
        }
    }
}
