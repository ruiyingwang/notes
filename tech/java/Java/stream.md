
## 从元素列表中提取某列
```java

```

* 使用 stream进行条件判断
```java
final Collection<AObject> objectCollection = new ArrayList<>();
// this list will have lots of elems
objectCollection.stream().anyMatch(elemObject -> elemObject.getField() == "targetValue");
objectCollection.stream().allmatch(elemObject -> elemObject.getField() == "targetValue");
objectCollection.stream().noneMatch(elemObject -> elemObject.getField() == "targetValue");
objectCollection.stream().filter(elemObject -> elemObject.getField() == "targetValue").count(); // return long
```