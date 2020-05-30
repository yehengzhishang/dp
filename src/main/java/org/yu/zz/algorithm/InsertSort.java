package org.yu.zz.algorithm;

public class InsertSort {

    public static void main(String[] args) {
        int[] arr = {9, 8, 6, 7, 5, 4, 3, 2, 1};
        sort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    public static void sort(int[] arr) {
        int temp;
        for (int i = 1; i < arr.length; i++) {
            //待排元素小于有序序列的最后一个元素时，向前插入
            if (arr[i] < arr[i - 1]) {
                temp = arr[i];
                for (int j = i; j >= 0; j--) {
                    if (j > 0 && arr[j - 1] > temp) {
                        arr[j] = arr[j - 1];
                    } else {
                        arr[j] = temp;
                        break;
                    }
                }
            }

        }
    }
}
