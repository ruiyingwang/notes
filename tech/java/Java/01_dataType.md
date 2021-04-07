
## 数据类型
#### 数据类型分类
* 内置数据类型(基本数据类型)
    * 8种(3类)基础数据类型,
    * 布尔类: boolean
    * 字符类: byte, char
    * 数字累: short, int, long, float, double
* 引用数据类型
    * 类似指针
    * 在Java内大部分其他类型均为引用类型: String, List, Set...

## String
####  String vs StringBuffer vs StringBuilder
* String 是不可变的
* StringBuffer与StringBuilder是可变的
* StringBuffer是线程安全的

#### StringBuffer
```java
stringBuffer.append(VALUE);
stringBuffer.deleteCharAt(TARGET_INDEX);
```
> [String、StringBuffer和StringBuilder的区别](https://blog.csdn.net/csxypr/article/details/92378336)

## 集合对象
#### 列表(List) 
* 批量初始化
```java
// Arrays -> dpn't support null
List jdks = asList("JDK6", "JDK8", "JDK10");
// anonymous
List names = new ArrayList<>() {{ add("Tom"); add("Sally");}};
// Steam
List colors = Stream.of("blue", "red", "yellow").collect(toList());
// List -> null will get error
List cups = List.of("A", "B", "C"); // JDK9, 
```

#### 数组(Array) vs 列表(ArrayList)
ArrayList -> ... -> List -> Collection -> Iterable

* 比较:
    * Array: 效率高，容量固定
    * ArrayList: 效率低, 容量动态.
    * 本质上ArrayList内是通过Array实现的，因此增加与插入操作开销较大.
* Array
    * 初始化: 
        * String[] strArray = new String[10];
        * String[] strArray = {"a", "b", "c"};
        * String[] strArray = new String[]{"a", "b", "c"};
    * 调用: strArray[0];
    * 修改: strArray[0] = "b";
    * 转化ArrayList: ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(strArray));
    * 转化ArrayList: Arrays.asList(strArray)
* ArrayList
    * 初始化: 
        * String[] List<String> list=new ArrayList<String>();
    * 调用: 
    * 修改: 
    * 转化Array: (String[])list.toArray(new String[list.size()])

> [Java中数组(Array)和列表(ArrayList)的区别](https://blog.csdn.net/sofuzi/article/details/79903981)
> [java中 列表，集合，数组之间的转换](https://blog.csdn.net/Jay112011/article/details/79999186)

? 接口实体化
? 使用子类实体化父类， 使用实体类实体化借口

#### Interable vs Interator(迭代器)
* Interable封装了Interator

> [Java-Iterable&Iterator](https://www.jianshu.com/p/0c916535aa02)






















package: java.util


* java.util.Data

* [java集合Collection常用方法详解, 衍生](https://blog.csdn.net/javaee_gao/article/details/96372530)
* 

* Collection 于 Interator?是大部分Java提供的数据结构的父类
* Collection本身提供进行交际，并集，差集等集合操作.


#### 数组, 列表, 集合

HashMap -> ... -> Map
HashSet -> Set -> Collection -> Iterable


* StringUtil

#### 字符串截取
* String[]  strs=str.split(",");

#### 字符串切片
* sb.substring(startIndex) 截取startIndex到结尾
* sb.substring(startIndex, endIndex) 截取startIndex～endIndex的字符串
```java
String str = "1234567890";
str.substring(2);   // "34567890"
str.substring(3,5); // "345"
```


