package org.yu.zz.algorithm;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LeetCode {

    /**
     * https://leetcode-cn.com/problems/flatten-binary-tree-to-linked-list/
     * <p>
     * 给定一个二叉树，原地将它展开为一个单链表。
     */
    public static void flatten(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode result = new TreeNode(0, null, null);
        while (!queue.isEmpty()) {
            TreeNode treeNode = queue.pop();
            if (treeNode.right != null) {
                queue.addFirst(treeNode.right);
            }
            if (treeNode.left != null) {
                queue.addFirst(treeNode.left);
            }
            result.right = treeNode;
            result = treeNode;
            result.left = null;
        }
    }

    /**
     * 剑指 Offer 11. 旋转数组的最小数字
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public int minArray(int[] numbers) {
        int min = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            int num = numbers[i];
            if (num < min) {
                min = num;
                break;
            }
        }
        return min;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
