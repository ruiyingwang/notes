[TOC]

## Common Command
* 进入容器: `docker exec -it e1066fe2db35 /bin/bash`
```
docker build --force-rm -t IMAGE_NAME:TAG .
docker run -it -p HOST_PORT:CONTAINER_PORT --rm --name CONTAINER_NAME IMAGE_NAME:TAG
docker run -d -p HOST_PORT:CONTAINER_PORT --rm --name CONTAINER_NAME IMAGE_NAME:TAG

docker build --force-rm -t ms/msfe:0.3 .
docker run -it -p 7001:8080 --rm --name msfe0_3 ms/msfe:0.3
docker run -d -p 7001:8080 --rm --name msfe0_3 ms/msfe:0.3

docker build --force-rm -t ms/msbe:0.2 .
docker run -d -p 8081:8081 --name msbe0_2 ms/msbe:0.2
```

## common link
* [filter](https://segmentfault.com/q/1010000007462486) `docker images | grep <groupName>`
* [Connection refused? Docker networking and how it impacts your image](https://pythonspeed.com/articles/docker-connection-refused/)
* [Dockerfile的CMD指令](https://blog.csdn.net/chengqiuming/article/details/79038772?utm_medium=distribute.pc_aggpage_search_result.none-task-blog-2~all~first_rank_v2~rank_v25-1-79038772.nonecase&utm_term=dockerfile%20%E6%89%A7%E8%A1%8C%E5%A4%9A%E4%B8%AAcmd&spm=1000.2123.3001.4430)
* [Dockerfile中ENTRYPOINT和CMD的区别和最佳实践](https://www.jianshu.com/p/54cfa5721d5f)
* [docker logs](https://www.jianshu.com/p/1eb1d1d3f25e)


## todo
* 添加路由表让host可以通过容器ip访问容器
> 
```sh
route -n add -net 10.10.0.0 -netmask 255.255.0.0  172.16.111.254
192.168.65.0/24
route -p add 172.17.0.0 MASK 255.255.255.0 10.0.75.2
route delete 172.17.0.2
route -n add -net 172.17.0.0 MASK 255.255.255.0 192.168.65.0
```


## Document
#### Docker
* logs
* run
> -a stdin: 指定标准输入输出内容类型，可选 STDIN/STDOUT/STDERR 三项 <br/>
-d: 后台运行容器，并返回容器ID；<br/>
-i: 以交互模式运行容器，通常与 -t 同时使用；<br/>
-P: 随机端口映射，容器内部端口随机映射到主机的端口<br/>
-p: 指定端口映射，格式为：主机(宿主)端口:容器端口<br/>
-t: 为容器重新分配一个伪输入终端，通常与 -i 同时使用；<br/>
--name="nginx-lb": 为容器指定一个名称； <br/>
--dns 8.8.8.8: 指定容器使用的DNS服务器，默认和宿主一致； <br/>
--dns-search example.com: 指定容器DNS搜索域名，默认和宿主一致； <br/>
-h "mars": 指定容器的hostname；<br/>
-e username="ritchie": 设置环境变量；<br/>
--env-file=[]: 从指定文件读入环境变量；<br/>
--cpuset="0-2" or --cpuset="0,1,2": 绑定容器到指定CPU运行；<br/>
-m :设置容器使用内存最大值；<br/>
--net="bridge": 指定容器的网络连接类型，支持 bridge/host/none/container: 四种类型；<br/>
--link=[]: 添加链接到另一个容器；<br/>
--expose=[]: 开放一个端口或一组端口；<br/>
--volume , -v: 绑定一个卷<br/>

* build
* start
* stop
* rm
* rmi


#### Dockerfile
* FROM
* RUN
* CMD
* VOLUMN




> Dockerfile Sample
> * [Vue](https://cn.vuejs.org/v2/cookbook/dockerize-vuejs-app.html)
> * [SpringBoot-1](https://zhuanlan.zhihu.com/p/89161347)
> * [SpringBoot-2](https://www.baeldung.com/spring-boot-docker-images#:~:text=%20Creating%20Docker%20Images%20with%20Spring%20Boot%20,provides%20framework%20and%20application%20dependencies.%20For...%20More%20)
> * [SpringBoot-3](https://www.jianshu.com/p/397929dbc27d)
> * [SpringBoot-4](https://spring.io/guides/gs/spring-boot-docker/)
> * [SpringBoot-5]()

<meta http-equiv="refresh" content="30">

