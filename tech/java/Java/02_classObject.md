
* == 与 eqals() 的区别
    * eqals() 是 Object对象提供的一个方法, 在创建的对象没有重写的情况下比较两个对象的地址，复写后比较复写的内容(如值)
    > 因为基础数据类型不是Object的子类，因此不能使用eqals()进行比较
    * == 使用于基础数据类型，比较值， 适用于对象时，比较地址

* 浅拷贝与深拷贝
    * 浅拷贝指直接拷贝对象的引用，对新对象的修改将影响原对象
    * 深拷贝指拷贝对象的所有值，对新对象的修改对原对象无影响





* 对象的复制 https://blog.csdn.net/tounaobun/article/details/8491392

#### 对象的复制
* 8种基础数据类型的复制
    * 基础数据类型的划分:
        * boolean
        * char, byte
        * short, int, float, double, long
    * 基础数据类型间的直接赋值是创造新的"个体", 互相间是不影响的
    ```java
    int a = 1;
    int b = a;
    a = 2; // b still is 1
    ```

* 对象的复制
    * Shallow Copy(浅复制): 复写Object提供的clone()方法
    ```java
    class Student implements Cloneable{
        private int number;

        @Override
        public Object clone() {
            Student stu = null;
            try{
                stu = (Student)super.clone();
            }catch(CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return stu;
        }
    }
    ```