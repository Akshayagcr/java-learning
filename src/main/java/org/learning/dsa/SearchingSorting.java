package org.learning.dsa;

public class SearchingSorting {

    public static int binarySearch(int[] arr, int key){
        int low = 0, high = arr.length - 1, mid;
        while(low <= high){
            mid = low + (high - low) / 2;
            if(arr[mid] == key){
                return mid;
            }
            if(arr[mid] < arr[high]){
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    public static void insertionSort(int[] arr){

    }

    public static void quickSort(int[] arr){

    }

    public static void mergeSort(int[] arr){

    }

    public static void heapSort(int[] arr){

    }
}
