package algorithm.tree;

import java.util.List;

public class BuildTree {

    /**
     * 根据广度优先遍历结果构建 "完全二叉树"
     * [0,1,2,3,4,5,6] ->
     *        0
     *    1      2
     *  3   4  5   6
     */
    public static BTNode bfsToBT(List<Integer> bstArray, int index) {
        if(index >= bstArray.size() || bstArray.get(index) == null) return null;
        BTNode node = new BTNode(bstArray.get(index));
        node.left = bfsToBT(bstArray, index*2 + 1);
        node.right = bfsToBT(bstArray, index*2 + 2);
        return node;
    }

    private BTNode dfsToBT() {
        return null;
    }


}
