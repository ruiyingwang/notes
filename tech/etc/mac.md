

#### 测试某ip:port是否通畅
```
nc -vz -w 2 ip port
```


#### 路径
* /bin : 系统自带工具的路径
* /usr/bin : 账号安装的工具的路径

#### 在用户路径下添加路径
```bash
# 在～/下创建bin, 并将工作移动带～/bin路径下，后在~/.bash_profiles中添加如下配置，source文件
export PATH=$HOME/bin/:$PATH
```

* mac 显示隐藏文件 `Command + Shift + .`

