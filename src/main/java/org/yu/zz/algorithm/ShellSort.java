package org.yu.zz.algorithm;

public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 1};
//        int[] arr = {1};
        sort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int gap = arr.length;
        do {
            gap = gap / 2;
            System.out.println("gap == " + gap);
            // 第一个 for 分组
            for (int i = 0; i < gap; i++) {
                //开始 插入排序
                System.out.println("第" + i + "组开始");
                insertWithShell(arr, gap, i);
            }
        } while (gap != 1);
    }

    private static void insertWithShell(int[] arr, int gap, int group) {
        for (int i = group + gap; i < arr.length; i += gap) {
            if (arr[i - gap] > arr[i]) {
                // 前面的大 才进入二次循环
                int temp = arr[i];
                int j = i;
                for (; j >= group; j -= gap) {
                    if (j == group || arr[j - gap] <= temp) {
                        System.out.println("命中");
                        arr[j] = temp;
                        break;
                    } else {
                        arr[j] = arr[j - gap];
                        System.out.println("交换");
                        printAllWithLine(arr);
                    }
                }
            }
        }
    }

    private static void printAllWithLine(int[] arr) {
        for (int i : arr) {
            System.out.print(i + ",");
        }
        System.out.println("\n");
    }
}
