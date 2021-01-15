
#### 常用filter
* 目的地址: `ip.dst==192.168.101.8`
* 源地址: `ip.src==1.1.1.1`
* 端口: `tcp.port==80` 等效 `tcp.dstport==80 and tcp.srcport==80`
* 协议: `http`
* 方法过滤: `http.request.method=="GET"`
* 多条件过滤: and












#### 网卡含义
* https://blog.csdn.net/renwotao2009/article/details/49329713

#### 1 