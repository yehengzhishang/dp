package org.yu.zz.algorithm;

public class QuickSort {

    public static void main(String[] args) {
//        int arr[] = new int[]{3, 3, 3, 7, 9, 122344, 4656, 34, 34, 4656, 5, 6, 7, 8, 9, 343, 57765, 23, 12321};
        int[] arr = {9, 8, 7, 8, 9, 5, 4, 8, 8, 9};
        quick(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    private static void quick(int[] arr, int left, int right) {
        int target = arr[left];
        int i = left;
        int j = right;
        while (i < j) {
            while (i < j && arr[j] >= target) {
                j--;
            }
            while (i < j && arr[i] <= target) {
                i++;
            }
            if (i < j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        arr[left] = arr[i];
        arr[i] = target;
        System.out.println("left = " + left + " right = " + right);
        for (int next : arr) {
            System.out.print(next);
        }
        System.out.println();
        if (right - 1 > left) {
            quick(arr, left, i - 1);
        }
        if (i + 1 < right) {
            quick(arr, i + 1, right);
        }
    }
}
