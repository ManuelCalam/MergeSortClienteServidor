package mergesort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Executor {

    private ExecutorService executorService;

    public Executor() {
        executorService = Executors.newFixedThreadPool(4);
    }

    public void sort(int a[], int index) {
        if (index < 2) {
            return;
        }

        int middle = index / 2;
        int middle2 = index - middle;

        int[] left = new int[middle];
        int[] right = new int[middle2];

        System.arraycopy(a, 0, left, 0, middle);
        System.arraycopy(a, middle, right, 0, middle2);

        // Ejecutar sort en paralelo
        executorService.submit(() -> sort(left, middle));
        executorService.submit(() -> sort(right, middle2));

        // Esperar a que las tareas paralelas terminen
        shutdownAndAwaitTermination(executorService);

        merge(a, left, right);
    }

    private void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("El pool no terminó");
                }
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void merge(int[] a, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                a[k++] = left[i++];
            } else {
                a[k++] = right[j++];
            }
        }
        while (i < left.length) {
            a[k++] = left[i++];
        }
        while (j < right.length) {
            a[k++] = right[j++];
        }
    }
}
