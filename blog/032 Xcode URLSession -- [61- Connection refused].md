#### 报错信息
nw_socket_handle_socket_event [C1:2] Socket SO_ERROR [61: Connection refused]
Connection 1: received failure notification
Connection 1: failed to connect 1:61, reason -1
Connection 1: encountered error(1:61)
Task <7815E041-FB0C-480D-912C-F24BA5CDAA6A>.<1> HTTP load failed, 0/0 bytes (error code: -1004 [1:61])

#### 版本信息：
Xcode：11.1
Swift：5
iOS：13.1

#### 问题描述
首先确认一下你是否是和我一样的情形，因为该报错信息在国内搜索引擎大部分是由RN引起的，但这个blog却与RN没有关系。
* 使用URLSession发起网络请求导致的
* 实体机测试，而非模拟机
* 在虚拟机正常，但是实体机就会错误
* 后台服务在本地[也可能不是]

#### 解决方案
如果你符合上面的情形，那你应该适合我一样的新手，其实这个问题和Xcode，iOS版本甚至和iOS开发没有一点关系......这是Http的事情，说的简单一点就是，你把url地址写错了......而且大概率写的是localhost或者127.0.0.1

为什么尼？我们看一下问题情形：虚拟机正常，但是实体机发生错误，为什么？因为虚拟机和Xcode在一台设备上，所以127.0.0.1能指向同设备上的后台服务，但是实体机尼？他们并不共享同一个IP，他们大概率在同一个局域网下面，所以在只需要把url替换为Mac局域网的ip地址，问题就会解决了........

