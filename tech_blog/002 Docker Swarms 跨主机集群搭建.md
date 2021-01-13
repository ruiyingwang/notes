## 解决的问题
1. 想要在多台物理实体机或虚拟机，而不是一台机器上构建集群。
2. 由于使用windows10 + VirtualBox，并在VirtualBox中安装linux系列系统，并卡在文档"docker-machine create --driver virtualbox myvm1"

## 搭建环境
搭建时间：2019.8.16
设备系统：windows10
虚拟软件：VirtualBox
虚拟系统：Ubuntu 18.04 LTS

## 参考博客
感谢以下博客在问题解决中提供的帮助
JerryWangSAP --- [错误消息 This computer doesn't have VT-X/AMD-v enabled](https://www.jianshu.com/p/4f5a2bb30e0f)
向上的路 --- [docker跨主机通信方式四 docker-machine](https://www.jianshu.com/p/7bb475832399)


## 背景
为什么我会想到要在多台物理实体主机或虚拟机来搭建集群呢？原因有两个：单台设备性能极限、灾备。第一条原因很好理解，单台设备的性能极限。灾备的出发点并不是容器内单个服务宕机的情况，而是物理范畴的灾备。国内可靠性达到99.99%的云服务供应商不时宕机的新闻时常会会有报导，但我们是否有在同一时间段内，两个或两个以上云服务上同时宕机的新闻，我们有曾听过吗？

理论分析一下，以99.99%作为标准，这个数字乍一看确实有种万无一失的感觉，但折算成绝对值的时间，却是：52.56分钟/年。如果我们将服务同时部署在两个不同的云服务商上呢？服务理论宕机时间为：0.31秒/年。当然这只是个理论分析，只是在相同预算下，购买分布在2个或多个云服务商的主机来搭建集群，对服务的可靠性提升是否是一个更好的方案呢？

## 思路
Docker文档在Swarms部分通过"docker-machine create --driver virtualbox myvm1"命令宿主系统内创建虚拟节点，但该命令在本就已经是由虚拟机创建的ubuntu系统内是无法执行成功的，参见JerryWang的Blog。解决这个问题的方法有很多，双系统、使用Mac等都可行，但受限于某些客观原因，并且想要尝试跨主机构建集群，因此采用了以下解决方案：在VirtualBox虚拟出3套ubuntu系统：A、B、C。其中将A等价为教程中的宿主操作系统，B、C等价为教程中宿主操作系统中的两个节点。通过连通A、B、C来达到与教程在该处相同的效果，同时也是实现了跨主机集群的搭建。


## 具体步骤
1. 在主机A中预先装好docker(参见官网教程), B、C虚拟机只需要安装ssh服务器即可。（B、C可以为云服务商的虚拟主机）
2. 使用以下指令获取B、C虚拟机的IP地址。
     > ifconfig                              // 若信息过长使用 ifconfig >> output 输出大文件里使用vim打开查看
3. B、C主机配置ssh允许直接使用root帐号登录：在/etc/ssh/sshd_config文件内，添加
     > PermitRootLogin yes
4. 在A中执行以下命令生成密钥文件(此步骤开始的所有操作都只需在A中完成) 
    > ssh-keygen 
5. 将公钥复制到B、C
    > ssh-copy-id IP    
    > //执行两次，分别将IP替换为B、C的IP

![](https://user-images.githubusercontent.com/37465243/63159783-de3c8100-c04e-11e9-8797-088e1620f22c.png)

6. 使用一下指令验证是否配置成功
    > ssh IP    //成功后使用logout推出登录
7. 使用以下指令在A中给B、C安装docker。（同时也将建立起A与B、C的链接）
    > // docker文档到此处前并没有要求安装docker-machine，参见[官方文档](https://docs.docker.com/machine/install-machine/)安装
    > // 此命令需要执行两次，分别使用B、C的IP，NodeName官方文档分别命名为了：myvm1 、myvm2，此处我命名为docker1、docker2
    > docker-machine create -d generic --generic-ip-address=IP NodeName
 
![](https://user-images.githubusercontent.com/37465243/63159782-dda3ea80-c04e-11e9-8133-b47f0c4bf86f.png)

8. 使用以下指令检查连接是否建立完成
    > docker-machine ls

![](https://user-images.githubusercontent.com/37465243/63159785-de3c8100-c04e-11e9-92ee-2a92c24fc053.png)


## 解释说明
1. 以上步骤实现的效果就可以将独立的多台物理主机或位于不同平台的虚拟机实现连接,同时也等效官方文档以下两条指令，可以缝衔后续接教程内容
```
docker-machine create --driver virtualbox myvm1
docker-machine create --driver virtualbox myvm2
```
2. 关于IP，若是同我一样的场景(拥有固定IP的主机和云服务器除外)，不推荐去花费时间在局域网内配置静态IP，因为还需要在/etv/netplan(ubuntu17.04以后启用的)的配置文件里配置子网掩码、网关以及DNS服务器等，很容易出错。花费时间与学到东西的性价比不高，IP是会发生变化，但从实际操作来说，这个时间差足够完成练习。
3. 为什么需要在B、C中修改ssh配置文件？由于ssh默认不可使用root帐号登录，常规流程我们都是使用自己创建的帐号，如：ubuntu登录，再使用sudo su切换到root帐号，但在该场景下需要主机允许直接使用root账号登陆。


## 后记
完成docker swarms教程效果截图如下
![](https://user-images.githubusercontent.com/37465243/63169829-59f7f700-c06a-11e9-8369-52c94bae500e.png)

完成docker所有get-star教程的效果如下
![](https://user-images.githubusercontent.com/37465243/63170239-721c4600-c06b-11e9-9c39-f5fa1ae59972.png)



最后推荐一篇k8s与Docker Swarms对比的文章Ankit Kumar -- [Kubernetes vs. Docker Swarm: A Complete Comparison Guide](https://dzone.com/articles/kubernetes-vs-docker-swarm-a-complete-comparison-g)

####
***要获取更多Haytham原创文章，请关注公众号"许聚龙":***
![Haytham的微信公众号](https://user-images.githubusercontent.com/37465243/63688227-5b2ede00-c839-11e9-9aa9-2b461444f463.png)

