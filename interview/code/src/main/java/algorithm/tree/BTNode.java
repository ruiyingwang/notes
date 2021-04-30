package algorithm.tree;

public class BTNode {

    public Integer val;
    public BTNode left;
    public BTNode right;

    public BTNode(int val) { this.val = val;}

    public BTNode(int val, BTNode left, BTNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

}
