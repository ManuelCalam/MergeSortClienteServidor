/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mergesort;

/**
 *
 * @author DELL
 */
public class Secuencial {
    
    public void sort(int a[], int index){
        if(index < 2){
            return;
        }
        
        int middle = index /2;
        int middle2 = 0;

        if(index%2 == 0){
            middle2 = middle;
        }
        else{
            middle2 = middle+1;
        }

        //System.out.println("Mitad 1: " + middle + " Mitad 2: " + middle2);

        int left[] = new int[middle];
        int right[] = new int[middle2];

        for(int i = 0; i<middle; i++){
            left[i] = a[i];
        }

        for(int j = middle; j<index; j++){
            right[j-middle] = a[j];
        }

        
        //Imprimir el array nuevo
        /*for(int i = 0; i<middle; i++){
            System.out.print(left[i] + " ");
        }
        System.out.println("");

        for(int i = 0; i<middle2; i++){
            System.out.print(right[i] + " ");
        }
        System.out.println("");*/

        sort(left, middle);
        sort(right, index - middle);
        merge(a, left, right);
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
