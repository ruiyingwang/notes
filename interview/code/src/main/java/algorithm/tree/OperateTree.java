package algorithm.tree;

import java.util.ArrayList;
import java.util.List;

public class OperateTree {

    private void fillBT(BTNode root) {
        if(root == null) return;
        // I need to know the heigth of tree, then fill the tree todo
    }

    // BT to List
    public static List<Integer> breadthFirstSearch(BTNode root, List<Integer> intList) {
        if(intList == null) {
            intList = new ArrayList<>();
            intList.add(root.val);
        }
        if(root.left == null && root.right == null) return new ArrayList<>();
        if(root.left != null) intList.add(root.left.val);
        if(root.right != null) intList.add(root.right.val);
        if(root.left != null) breadthFirstSearch(root.left, intList);
        if(root.right != null) breadthFirstSearch(root.right, intList);
        return intList;
    }
    public static List<Integer> firstDepthFirstSearch(BTNode node, List<Integer> intList) {
        if(intList == null) intList = new ArrayList<>();
        if(node == null) return new ArrayList<>();
        intList.add(node.val);
        firstDepthFirstSearch(node.left, intList);
        firstDepthFirstSearch(node.right, intList);
        return intList;
    }
    public static List<Integer> middleDepthFirstSearch(BTNode node, List<Integer> intList) {
        if(intList == null) intList = new ArrayList<>();
        if(node == null) return new ArrayList<>();
        middleDepthFirstSearch(node.left, intList);
        intList.add(node.val);
        middleDepthFirstSearch(node.right, intList);
        return intList;
    }
    public static List<Integer> endDepthFirstSearch(BTNode node, List<Integer> intList) {
        if(intList == null) intList = new ArrayList<>();
        if(node == null) return new ArrayList<>();
        endDepthFirstSearch(node.left, intList);
        endDepthFirstSearch(node.right, intList);
        intList.add(node.val);
        return intList;
    }

}
