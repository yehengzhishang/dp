package org.yu.zz.algorithm;

public class BubbleSort {
    public static void main(String[] args) {
        int[] sort = {2, 2234, 3123, 4123, 12, 32, 3, 2};
        sort(sort);
        for (int i : sort) {
            System.out.println(i);
        }
    }

    private static void sort(int[] sort) {
        int length = sort.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (sort[j] > sort[j + 1]) {
                    int temp = sort[j];
                    sort[j] = sort[j + 1];
                    sort[j + 1] = temp;
                }
            }
        }
    }
}
