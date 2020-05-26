package org.yu.zz.algorithm;

public class BinarySortTree {
    private final int data;
    private BinarySortTree childLeft;
    private BinarySortTree childRight;

    BinarySortTree(int root) {
        this.data = root;
    }

    public static BinarySortTree insert(BinarySortTree target, int key) {
        if (target == null) {
            return new BinarySortTree(key);
        }
        if (target.data > key) {
            return insert(target.childLeft, key);
        } else if (target.data < key) {
            return insert(target.childRight, key);
        }
        throw new RuntimeException("xxxxxx");
    }

    /**
     * 采用递归方式查找目标结点(非递归用while实现)
     */
    public static BinarySortTree query(BinarySortTree target, int key) {
        if (target == null) {
            return null;
        }
        if (target.data > key) {
            return query(target.childLeft, key);
        } else if (target.data < key) {
            return query(target.childRight, key);
        } else {
            return target;
        }
    }

}
