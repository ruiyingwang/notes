## Shell



#### Point
* 对于if, 如果使用参数判定条件, `if [ -n "$VARIABLE_1" -a -z "$VARIABLE_2"]`
* shell 退出码 1->通用错误码 2->shell内建命令使用错误
* 字符串(变量)加上双引号使用可以保留特殊字符效果，如换行
* >> 追加写入 > 覆盖写入 :> 清空
* 获取字符串长度并做算术运算: $[ ${#TOKEN_KEY} +1]
* sed 带变量的字符串替换: `sed "${LINE}s/.*/${NEW_STRING}/g" ${FILEPATH}`
* 获得文件某字符串行数,好处事grep可以不用整段字符串 `cat ${FILE_PATH} | grep -n ${FILTER} | awk -F ":" '{print $1}'`
* sed -i 在Mac存在问题
* cut 截取某个，某段，从某段开始到结尾 `cut -c INDEX        cut -c START_INDEX-END_INDEX     cur -cSTART_INDEX-`
* shell函数返回结果使用echo, 使用 $()捕获
* 去除变量的最后一位${STRING%?}

#### Basic
* 条件分支
```
if condition1
then
    command1
elif condition2 
then 
    command2
else
    commandN
fi
```




#### Basic command
* | (Pipe) : 只接受stdin的输出，主要是文本处理命令，如：grep, sed, awk, cut, head, top, less ,more, sort, split...
```bash

```
* grep : 
* awk : 
* sed : 
* cut : 
























#### ote	 
* [/bin/sh /bin/bash /bin/dash的区别](https://blog.csdn.net/u014470361/article/details/88049410)
* [Docker 一个shell执行多个进程](https://www.cnblogs.com/sunsky303/p/11046681.html)
* [mac os 使用sed -i 出现sed: -i may not be used with stdin](https://blog.csdn.net/rainmaple20186/article/details/104632066/)
* [shell脚本修改json中某个字段的值](https://www.cnblogs.com/lyloou/p/9852991.html)








```sh
#!/bin/sh

# while [[ true ]]; do
#     sleep 1
# done

# cd ..
# cd /app/msfe
# http-server dist

init() {
	# && apt-get update \
	# && apt-get install curl \
	# && apt-get install vim \
	echo "init"
	npm install -g cnpm --registry=https://registry.npm.taobao.org
	cnpm install -g http-server
	cd /app/msfe
	cnpm install
	cnpm run build
	cd /app/feproxy
	cnpm install
}

start() {
	echo "start"
	cd /app/feproxy
	echo "===1===="
	nohup node app.js &
	echo "===2===="
	cd /app/msfe
	http-server dist
	echo "===3===="
}

getMsBaseURL() {
	# echo "ttttm"
	# nodeIP=`ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'|tr -d "addr:"`
	nodeIP=$msBeUrl
	# echo $nodeIP
	# echo $msBeUrl
	path=`pwd`/static/config/config.js
	echo $path
	echo $nodeIP

	# cat $path | while read line
	# do
	# 	echo $line
	# done
	# while read line
	# do
		# echo $line | grep 'abc.'
		# flag=$line | grep 'abc.'
		# echo ! -n "$flag"
		# echo ! -n $flag
		# echo ! $flag
		# if [ "$flag" == "" ];then
			# echo $line
		# fi
		# ^.*?".*?":".*?",
		# echo $line
	# done < $path | grep 'abc.'

	content_url_old=$(awk -F"\""	'/msBeUrl/{print $4}' $path)
	# sed -e "s@$content_url_old@$nodeIP@g" -i $path
	echo $content_url_old

	enable_feature1_line=$(awk -F"\""	'/msBeUrl/{print NR}' $path) # 记住行号
	enable_feature1_old=$(awk -F"\""	'/msBeUrl/{print $4}' $path)  # 获取旧数据
	sed -e "$enable_feature1_line s@$enable_feature1_old@$nodeIP@" -i '' $path # 替换所在行的老数据
	echo $enable_feature1_line
	echo $enable_feature1_old

	enable_feature1_line=$(awk -F"\""	'/nodeName/{print NR}' $path) # 记住行号
	enable_feature1_old=$(awk -F"\""	'/nodeName/{print $4}' $path)  # 获取旧数据
	sed -e "$enable_feature1_line s@$enable_feature1_old@$nodeName@" -i '' $path # 替换所在行的老数据
	# echo "abc9" | grep 'bbc.'

}

if [ $1 == "start" ]
then
	start
elif [ $1 == "getmsbaseurl" ]
then 
	getMsBaseURL
else
	init	
fi


```