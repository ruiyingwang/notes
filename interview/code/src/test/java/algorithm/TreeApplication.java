package algorithm;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import algorithm.tree.BTNode;
import algorithm.tree.BuildTree;
import algorithm.tree.OperateTree;
import algorithm.tree.SearchTree;

public class TreeApplication {
    private static BTNode ROOT_FULL;
    private static BTNode ROOT_NOT_FULL;
    private static BTNode ROOT_WITH_NULL;

    @BeforeEach
    public void beforeEach() {
        List<Integer> bstFullArray = List.of(0,1,2,3,4,5,6);
        TreeApplication.ROOT_FULL = BuildTree.bfsToBT(bstFullArray, 0);
        List<Integer> bstNotFullArray = List.of(0,1,2,3,4,5);
        TreeApplication.ROOT_NOT_FULL = BuildTree.bfsToBT(bstNotFullArray, 0);
        List<Integer> bstFullArrayWithNull = new ArrayList<>(){{add(0);add(1);add(2);add(3);add(4); add(null); add(6);}};
        TreeApplication.ROOT_WITH_NULL = BuildTree.bfsToBT(bstFullArrayWithNull, 0);
    }

    @Test
    public void t001BuildTree() {
        Assertions.assertEquals(OperateTree.breadthFirstSearch(TreeApplication.ROOT_FULL, null), List.of(0,1,2,3,4,5,6));
        Assertions.assertEquals(OperateTree.breadthFirstSearch(TreeApplication.ROOT_NOT_FULL, null), List.of(0,1,2,3,4,5));
        Assertions.assertEquals(OperateTree.breadthFirstSearch(TreeApplication.ROOT_WITH_NULL, null), List.of(0,1,2,3,4,6));
    }

    @Test
    public void t002BuildTreeWithSortedNotFullList() {
        List<Integer> firstDepthFirstSearchList = OperateTree.firstDepthFirstSearch(TreeApplication.ROOT_FULL, null);
        Assertions.assertEquals(firstDepthFirstSearchList, List.of(0,1,3,4,2,5,6));
    }

    @Test
    public void t003GetTreeMaxGeight() {
        Assertions.assertEquals(SearchTree.getTreeMaxHeight(TreeApplication.ROOT_FULL), 3);
    }

    @Test void t004FindNodeByVal() {
        Assertions.assertEquals(SearchTree.findNode(TreeApplication.ROOT_FULL, 4).val, 4);
    }

    @Test void t005GetNodeHeight() {
        Assertions.assertEquals(SearchTree.getNodeHeight(TreeApplication.ROOT_FULL, 2), 2);
    }
}
