## 指路牌
* nvm-windows
* nvm镜像源设置
* nvm使用过程中的坑
* nvm安装成功，node安装成功，能工作，但是npm使用时报错

## 适用场景
接手**祖传**旧代码，node版本太高导致无法运行成功。或同时需要使用多个版本node。

## 环境
windows 10 64bit

## 参考博客
雨临Lewis -- [Windows下完全卸载node.js并安装node.js的多版本管理工具nvm-windows](https://blog.csdn.net/lewky_liu/article/details/87959839)

蓓蕾心晴 -- [windows下nvm安装node之后npm命令找不到问题解决办法](https://www.bbsmax.com/A/1O5EPPR3J7/)

## 背景
**不注明环境和版本的教程都是耍流氓**，在刚开始接触一门新技术或者新框架时，我们时常会去搜索别人的hellow world教程，但是很气的是，按照步骤无论如何都是不成功，
在我碰到的这类的坑，基本都是环境和版本不同导致的.....(除了和网路相关的)

例如最近看的一位前辈关于以太坊教程，和现在还没爬出坑的接手的一个2年前Angular的项目。本篇是下一篇关于以太坊文章的前置文章，因为那一篇教程需要使用旧版本的node。

## 思路
1. 卸载node
2. 删除路径以及残留文件
3. 安装nvm-windows
4. 配置镜像源

## 步骤
1. 卸载系统上现有node（如果有）
    > 此处基于[windows下nvm安装node之后npm命令找不到问题解决办法](https://www.bbsmax.com/A/1O5EPPR3J7/)，在此基础上简化和完善。
    * 在卸载程序中卸载node
    * 在环境变量中删除所有与node相关的路径(无论是user级，还是system级)
    * 删除以下路径的文件(可能只有部分文件才有)

        > * C:\Program Files (x86)\nodejs
        > * C:\Program Files\nodejs
        > * C:\Users\{User}\AppData\Roaming\npm
        > * C:\Users\{User}\AppData\Roaming\npm-cache
        > * C:\Users\{User}\node_modules (在我电脑上的路径)

2. 下载最新版nvm并安装[nvm-setup.zip](https://github.com/coreybutler/nvm-windows/releases)
3. 更换镜像源
在` 路径 C:\Users\{User}\AppData\Roaming\nvm\settings.txt`下添加以下两条
```
node_mirror: https://npm.taobao.org/mirrors/node/
npm_mirror: https://npm.taobao.org/mirrors/npm/
```
>此时如果你还仔细观察，会发现`C:\Program Files`下的nodejs问家家其实是一个快捷方式，指向的是nvm的安装路径`C:\Users\i353667\AppData\Roaming\nvm`下对应的node版本。
4. nvm的使用
```
# get available node version list
    nvm list available
# install specific node version you need
    nvm install The_Version_You_Need
# list the node you have installed in your PC
    nvm list
# switch node version you need 
    nvm use The_Version_You_Need
```

## 错误处理
在Windows上nvm的常见错误解决方案基本都能搜索到，有一个特殊的我没检索到的这里列一下。

* 现象：nvm install指定版本的node，并切换后，使用node指令正常，比如`node --version`,但是使用`npm`指令会报错，且报错根据node 版本会不同，但是根本上都是npm找不到。此时如果你打开`C:\Users\{User}\AppData\Roaming\nvm\v9.11.1\node_modules`，发现没有npm文件夹，那就是这个问题。

* 解决方案
卸载当前版本，重新安装。有时我重复了两次才成功，成功标志就是node_modules文件夹下npm文件夹出现，或者命令行调用npm不报错。

<meta http-equiv="refresh" content="1">