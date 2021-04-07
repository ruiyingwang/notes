? 线程安全？
？ 锁

## 线程

#### 进程与线程
* 进程 1:n 线程
* 一个应用(如IDEA)由一个进程运行

#### 线程安全
* 定义
    * 概括来说: 存在资源竞争的线程就是不安全的, 不存在资源竞争的线程就是安全的.
    * 主线程工作线程都有各自的内存，当工作线程需要读取主内存数据，又需要写入主内存，就可能不安全.
* 如何确保线程安全: 锁(synchronized关键字)
* 自线程安全的类: Stack，HashTable，StringBuffer...
* 非线程安全的类: ArrayList, HashMap, StringBuilder...




> 参考资料
> [Java线程安全与常见的线程安全的类](https://blog.csdn.net/tiandao321/article/details/81300489)