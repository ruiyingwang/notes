## 指路牌
* RedisJSON 中文编码问题
* Java RedisJSON - JReJSON 中文编码问题

## 背景
Redis本身是不支持的Json格式的，而JSON又一个当下非常常见的数据类型，当下常规的解决方案是使用redis4.0之后推出的modules模块系统来集成rejson，让redis支持json格式，这是通过搜索引擎找到的大部分解决方案，但是亲身实践后发现，这并不是一个适合“初学者”的解决发方案，尤其是已经使用apt等工具已经安装了redis的情形，环境清理不干净，更不要提二进制包配置与安装，这个时候怎么才能够简单快速获得一个支持json格式的redis呢？

答案就是，拉一个已经继承好模块的docker image......

这是我目前发现的最快速，做简单，最友好的让redis支持json的当时，而本次blog解决问题，也是在这个前提下的。

## redisLabs
首先贴上两个链接
1. Redis Lab官网地址
[Redis Labs](https://redislabs.com/)
2. 打包好的支持json格式的Redis在Docker Hub的地址，打开后里面有基础教程以及不同语言的客户端使用链接
[Redislabs/rejson](https://hub.docker.com/r/redislabs/rejson)


## 问题分析
回到问题本身，首先介绍一下项目基本技术选型，数据库为redis，后台为SpringBoot
本次问题的发现由于项目中同事在测试时输入中文出现的，问题的初始症状是Springboot报数据库连接超时，初步排查发现是由于数据库某字段长度过长(由于过长客户端在使用JSON.GET获取该数据后直接崩溃了)，导致读取时间过长，导致数据库连接超时，而后台几数据库本身，都没有出现崩溃的情况。
经询问得知，同事测试时，只是输入了十几个中文字符，而数据库内的，字段长度明显不符，而且字段为乱码并非中文。
> 字段无故变得异常长的问题暂时没有找到原因，暂未复现，暂未解决

集中到中文乱码这条分支，首先在通过本地调试排除掉了Springboot引起的乱码问题，确认写入redis的编码格式是没有问题的，再将问题锁定在Redis内

打开redis-cli，简单复现复现一下情形
```bash
127.0.0.1:6379> JSON.Set test . '{"test":"测试"}' 
OK
127.0.0.1:6379> JSON.GET test
"{\"test\":\"\\u00e6\\u00b5\\u008b\\u00e8\\u00af\\u0095\"}"
```

## 解决方案
经过查询，在RedisJson的github issue找到了一个国内开发者在2017年9月11日创建的[issue 35](https://github.com/RedisJSON/RedisJSON/issues/35), 里面有问题描述，以及解决方案“NOESCAPE”

使用格式如下：
```bash
127.0.0.1:6379> JSON.GET test NOESCAPE
"{\"test\":\"\xe6\xb5\x8b\xe8\xaf\x95\"}"
```

然后，如何在Springboot使用呢？
issue内没有详细的描述该问题如何在各客户端内使用(实际上有人提到了，只是一开始没理解)，此处补充如下：
```JAVA
JReJSON redisClient = new JReJSON(redisHost, redisPort);
// redisClient.get(key, new Path("."));  此条语句或出现编码问题
redisClient.get(key, new Path("NOESCAPE"));
```


