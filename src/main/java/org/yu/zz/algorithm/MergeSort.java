package org.yu.zz.algorithm;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {1, 324, 3, 24, 5, 56, 34, 6, 3};
        int[] result = sort(arr, 0, arr.length - 1);
        for (int i : result) {
            System.out.print(i + ",");
        }
    }

    public static int[] sort(int[] arr, int l, int h) {
        if (l == h) {
            return new int[]{arr[l]};
        }
        int mid = l + (h - l) / 2;
        System.out.println("mid = " + mid + " l = " + l + " h = " + h);
        int[] arrLeft = sort(arr, l, mid);
        int[] arrRight = sort(arr, mid + 1, h);
        int[] arrResult = new int[arrLeft.length + arrRight.length];
        int i = 0, j = 0, m = 0;
        while (i < arrLeft.length && j < arrRight.length) {
            arrResult[m++] = arrLeft[i] < arrRight[j] ? arrLeft[i++] : arrRight[j++];
        }
        while (i < arrLeft.length) {
            arrResult[m++] = arrLeft[i++];
        }
        while (j < arrRight.length) {
            arrResult[m++] = arrRight[j++];
        }
        return arrResult;
    }
}
