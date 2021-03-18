
## 二叉树

#### 树的定义
```python
class Node(object):
    def __init__(self, data, left=None, right=None):
        self.data = data
        self.left = left
        self.right = right
```

#### 不完整列表树的构建(空的子节点会被直接忽略)
此种情形下不能完全靠一种遍历结果还原出树的结构

#### 完整列表树的构建(空的子节点使用None标示)
此种情形下可以靠一种遍历结果还原出树的结构

* 前序遍历列表构建
```python
nodeList = [0,1,3,4,2,5,6]
def preOrderConstruct(pre_order, mid_order):
    if len(pre_order) == 0:
        return None
    root_data = pre_order[0]
    i = mid_order.index(root_data)
    left = construct_tree(pre_order[1: 1 + i], mid_order[:i])
    right = construct_tree(pre_order[1 + i:], mid_order[i + 1:])
    return Node(root_data, left, right)

```
* 中序遍历列表构建
```python
nodeList = [3,1,4,0,5,2,6]
def ():
    pass
```
* 后序遍历列表构建
```python
nodeList = [3,4,1,5,6,2,0]
```
* 层序面试列表构建
```python
nodeList = [0,1,2,3,4,5,6]
```

#### 遍历方式
* 前序遍历(preorder traverersal)
```python
def preorderTraversal(node):
    if node is None:
        return
    print(node.data)
    preorderTraversal(node.left)
    preorderTraversal(node.right)
```
* 中序遍历(in order traversal)
```python
def inorderTraversal(node):
    if node is None:
        return
    inorderTraversal(node.left)
    print(node.data)
    inorderTraversal(node.right)
```
* 后序遍历(post order traverersal)
```python
def postorderTraversal(node):
    if node is None:
        return
    postorderTraversal(node.left)
    postorderTraversal(node.right)
    print(node.data)
```
* 层序面试(level order traverersal)
```python
def levelorderTraversal(node):
    pass
```





