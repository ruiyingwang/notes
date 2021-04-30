package algorithm.tree;

public class SearchTree {

    public static int getTreeMaxHeight(BTNode root) {
        if(root == null) return 0;
        return Math.max(getTreeMaxHeight(root.left), getTreeMaxHeight(root.right)) + 1;
    }

    public static int getNodeHeight(BTNode root, Integer targetVal) {
        if(root.val == null) return 0;
        Integer targetNodeHeight = 0;
//        if()
        return 0;
    }

    public static BTNode findNode(BTNode root, Integer targetVal) {
        if(root == null) return null;
        BTNode targetNode = null;
        if(root.val == targetVal) targetNode = root;
        if(targetNode == null) targetNode = findNode(root.left, targetVal);
        if(targetNode == null) targetNode = findNode(root.right, targetVal);
        return targetNode;
    }

    public static BTNode insertNode() {
        return null;
    }

    private static BTNode resultNode;
    public static BTNode findNodev2(BTNode root, Integer targetVal) {
        SearchTree.resultNode = null;
        findNodeHelper(root, targetVal);
        return SearchTree.resultNode;
    }
    private static void findNodeHelper(BTNode node, Integer targetVal) {
        if(node == null || SearchTree.resultNode != null) return;
        if(node.val == targetVal) SearchTree.resultNode = node;
        if(SearchTree.resultNode == null) findNodeHelper(node.left, targetVal);
        if(SearchTree.resultNode == null) findNodeHelper(node.right, targetVal);
    }
}
