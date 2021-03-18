
#### String
* format
```py
STRING_FORMAT_01 = "{: ^50}"
STRING_FORMAT_02 = "{: <20}, {}"
STRING_FORMAT.format(PARM_01, PARM_02)
```
* list -> string
```py
" ".join([parm01, parm02, parm03])
"/".join([subpath01, subpath02, subpath03])
```

#### time
* time.localtime()
```py
import time
time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
```

#### file
* file
```py
fo = open(FILE_PATH, "a")
fo.write(STRING)
fo.close()
```

#### Config (configparser, ini)
* string
```py
import configparser
config = configparser.ConfigParser()
config.read("config.ini")
config['default']['string']
```
* boolean
```py
config.getboolean("default", "boolean")
```
* list(dict?)
```py
import json
json.loads(config.get("default", "list"))
```

#### float
* float
```py
'%.2f' % floatNum
```

#### list
* delete item, pop item
```py
index = 0
while index < len(LIST):
    file = LIST[index]
    if file in TARGET_LIST:
        LIST.pop(index)
        index -= 1
    index += 1
```

#### regex
* regex
```py
STRING_02 = re.sub(r'\(.*?\)|\[.*?\]|【.*?】|\.$', "", STRING_01)
STRING_03 = re.sub(r'^\s*|\s*$', "", STRING_02)
STRING_04 = re.sub(r' \s*', " ", STRING_03)
```



```python

```



```py

# ---------------------------------------------------------------------
# Regular Expression Perl
'''
1.re.match(pattern, string, flags=0)
参数：标志位，用于控制正则表达式的匹配方式：re.l re.L re.M re.S re.U re.X
描述：从字符串的起始位置匹配一个模式
成功：返回一个匹配的对象,使用span(),获取坐标元组；.start()；.wnd()；.group()，获取表达式的分组返回结果，非坐标，而是结果！！！
失败：返回none

2.re.search(pattern, string, flags=0)
描述：扫描整个字符串并返回第一个成功的匹配
成功：返回一个匹配的对象
失败：返回None

3.re.sub(pattern, replace, string, count=0, flags=0)
参数：count : 模式匹配后替换的最大次数，默认 0 表示替换所有的匹配。replace 可以是一个函数
描述：替换字符串中的匹配项

4.re.compile(pattern[, flags])
参数：pattern : 一个字符串形式的正则表达式
描述：用于编译正则表达式，生成一个正则表达式（ Pattern ）对象，供 match() 和 search() 这两个函数使用

5.re.findall(string[, pos[, endpos]])
参数：pos:起始位置，endpos：结束位置
描述：在字符串中找到正则表达式所匹配的所有子串，并返回一个列表，如果没有找到匹配的，则返回空列表
其他： match 和 search 是匹配一次 findall 匹配所有 ！！！！

6.re.finditer(pattern, string, flags=0)
描述：和 findall 类似，在字符串中找到正则表达式所匹配的所有子串，并把它们作为一个迭代器返回

7.re.split(pattern, string[, maxsplit=0, flags=0])
参数：分隔次数，maxsplit=1 分隔一次，默认为 0，不限制次数
描述：split 方法按照能够匹配的子串将字符串分割后返回列表


Pattern - usual
| 匹配a或b
(re) pattern分组
re? 非贪婪方式,尽可能少的匹配(->0)

re* 贪婪方式,尽可能多的匹配(0~infinite)
re+ 贪婪方式,尽可能多的匹配(1~infinite)


\w 数字字母下划线 \W 非数字字母下划线

\s 任意空白字符 \S 任意非空字符

\d 任意数字  \D 任意非数字




Pattern - unusual
\A  匹配字符串开始
\Z  匹配字符串结束，如果是存在换行，只匹配到换行前的结束字符串。
\z  匹配字符串结束
\G  匹配最后匹配完成的位置。
\b  匹配一个单词边界，也就是指单词和空格间的位置。例如， 'er\b' 可以匹配"never" 中的 'er'，但不能匹配 "verb" 中的 'er'。
\B  匹配非单词边界。'er\B' 能匹配 "verb" 中的 'er'，但不能匹配 "never" 中的 'er'。
\n, \t, 等。  匹配一个换行符。匹配一个制表符, 等
\1...\9 匹配第n个分组的内容。
\10 匹配第n个分组的内容，如果它经匹配。否则指的是八进制字符码的表达式。


^   匹配字符串的开头
$   匹配字符串的末尾。
.   匹配任意字符，除了换行符，当re.DOTALL标记被指定时，则可以匹配包括换行符的任意字符。
[...]   用来表示一组字符,单独列出：[amk] 匹配 'a'，'m'或'k'
[^...]  不在[]中的字符：[^abc] 匹配除了a,b,c之外的字符。
re{ n}  匹配n个前面表达式。例如，"o{2}"不能匹配"Bob"中的"o"，但是能匹配"food"中的两个o。
re{ n,} 精确匹配n个前面表达式。例如，"o{2,}"不能匹配"Bob"中的"o"，但能匹配"foooood"中的所有o。"o{1,}"等价于"o+"。"o{0,}"则等价于"o*"。
re{ n, m}   匹配 n 到 m 次由前面的正则表达式定义的片段，贪婪方式
(?imx)  正则表达式包含三种可选标志：i, m, 或 x 。只影响括号中的区域。
(?-imx) 正则表达式关闭 i, m, 或 x 可选标志。只影响括号中的区域。
(?: re) 类似 (...), 但是不表示一个组
(?imx: re)  在括号中使用i, m, 或 x 可选标志
(?-imx: re) 在括号中不使用i, m, 或 x 可选标志
(?#...) 注释.
(?= re) 前向肯定界定符。如果所含正则表达式，以 ... 表示，在当前位置成功匹配时成功，否则失败。但一旦所含表达式已经尝试，匹配引擎根本没有提高；模式的剩余部分还要尝试界定符的右边。
(?! re) 前向否定界定符。与肯定界定符相反；当所含表达式不能在字符串当前位置匹配时成功。
(?> re) 匹配的独立模式，省去回溯。




## match and search
#### re.match(pattern, string, flags=0)
    |_ return MatchObject
    |__match    -> span()
    |__unmatch  -> None
    * only match form the begining

#### re.search(pattern, string, flags=0)
    |_ return MatchObject
    |_match     -> span()
    |__unmatch  -> None
    * only return the first 

## sub 
#### re.sub(pattern, repl, string, count=0, flags=0)
    |_ return new string
    |_ repl can be a function


## Pattern
#### re.compile(pattern[, flags]) -> Pattern
    |_ Pattern.xxx(targetString, pos=0, endpos=string.length)
    |_ Pattern.findall( , , )
        |_ return all match substring

#### re.finditer(pattern, string, flags=0)
    |_ like findAll, but return a iterabtor

#### re.split(pattern, string[, maxsplit=0, flags=0])
    |_ return a list split base on pattern
    |_ maxsplit: the total of substring want to split, 0 is unlimited.



## MathObject
* span(num=0)   -> get tumple index, if num != 0, return the index of group(num)
* groups()      -> get temple string match each regex pattern
* group(num=0)  -> get string which match regex, if num != 0, return the result of the index of regex pattern matched
* start()       -> start index
* end()         -> end index


## flag
re.I    使匹配对大小写不敏感
re.L    做本地化识别（locale-aware）匹配
re.M    多行匹配，影响 ^ 和 $
re.S    使 . 匹配包括换行在内的所有字符
re.U    根据Unicode字符集解析字符。这个标志影响 \w, \W, \b, \B.
re.X    该标志通过给予你更灵活的格式以便你将正则表达式写得更易于理解。

'''

# ---------------------------------------------------------------------

'''
## Regex
* isMatch(pattern) -> Boolean
* isMatch(patternList) -> Boolean
* replace(pattern, targetString) -> newString
* getIndex(pattern) -> (begin, end)
* multiple lines pattern





'''

# ---------------------------------------------------------------------

# os
'''
# -------- 常用 ------------
# os.chdir(path) 改变当前工作目录
# os.getcwd() 返回当前工作目录
# os.mkdir(path[, mode]) 以数字mode的mode创建一个名为path的文件夹.默认的 mode 是 0777 (八进制)。
# os.path.exists(path) file exist or not
# os.listdir(path) 返回path指定的文件夹包含的文件或文件夹的名字的列表。
# os.path.exists(path) 文件是否存在
# os.removedirs(path) 删除文件夹
# os.path.isdir(path) 是否是文件夹
# os.path.isfile(path) 是否是文件
# shutil.move(“oldpos”,”newpos”) 


# os.rename(src, dst) 重命名文件或目录，从 src 到 dst
# os.renames(old, new) 递归地对目录进行更名，也可以对文件进行更名。

# os.remove(path) 删除路径为path的文件。如果path 是一个文件夹，将抛出OSError; 查看下面的rmdir()删除一个 directory。
# os.rmdir(path) 删除path指定的空目录，如果目录非空，则抛出一个OSError异常。
# os.unlink(path) 删除文件路径

# os.makedirs(path[, mode]) 递归文件夹创建函数。像mkdir(), 但创建的所有intermediate-level文件夹需要包含子文件夹。

# -------- 不常用 ------------
# os.chroot(path) 改变当前进程的根目录
# os.utime(path, times) 返回指定的path文件的访问和修改的时间。
# os.walk(top[, topdown=True[, onerror=None[, followlinks=False]]]) 输出在文件夹中的文件名通过在树中游走，向上或者向下。

# -------- 文件描述符 ------------
# os.open(file, flags[, mode]) 打开一个文件，并且设置需要的打开选项，mode参数是可选的
# os.write(fd, str) 写入字符串到文件描述符 fd中. 返回实际写入的字符串长度
# os.close(fd) 关闭文件描述符 fd

# os.closerange(fd_low, fd_high) 关闭所有文件描述符，从 fd_low (包含) 到 fd_high (不包含), 错误会忽略
# os.dup(fd) 复制文件描述符 fd
# os.dup2(fd, fd2) 将一个文件描述符 fd 复制到另一个 fd2
# os.fchdir(fd) 通过文件描述符改变当前工作目录
# os.fstat(fd) 返回文件描述符fd的状态，像stat()。
# os.fsync(fd) 强制将文件描述符为fd的文件写入硬盘。
# os.fstatvfs(fd) 返回包含文件描述符fd的文件的文件系统的信息，像 statvfs()
# os.ftruncate(fd, length) 裁剪文件描述符fd对应的文件, 所以它最大不能超过文件大小。
# os.isatty(fd) 如果文件描述符fd是打开的，同时与tty(-like)设备相连，则返回true, 否则False。
# os.lseek(fd, pos, how) 设置文件描述符 fd当前位置为pos, how方式修改: SEEK_SET 或者 0 设置从文件开始的计算的pos; SEEK_CUR或者 1 则从当前位置计算; os.SEEK_END或者2则从文件尾部开始. 在unix，Windows中有效
# os.read(fd, n) 从文件描述符 fd 中读取最多 n 个字节，返回包含读取字节的字符串，文件描述符 fd对应文件已达到结尾, 返回一个空字符串。
# os.ttyname(fd) 返回一个字符串，它表示与文件描述符fd 关联的终端设备。如果fd 没有与终端设备关联，则引发一个异常。
# os.tcgetpgrp(fd) 返回与终端fd（一个由os.open()返回的打开的文件描述符）关联的进程组
# os.tcsetpgrp(fd, pg) 设置与终端fd（一个由os.open()返回的打开的文件描述符）关联的进程组为pg。

# os.tmpnam() 为创建一个临时文件返回一个唯一的路径
# os.tempnam([dir[, prefix]]) 返回唯一的路径名用于创建临时文件。

# -------- 配置属性信息 ------------
# os.path 模块 获取文件的属性信息。 
# os.pathconf(path, name) 返回相关文件的系统配置信息。
# os.statvfs(path) 获取指定路径的文件系统统计信息
# os.fpathconf(fd, name) 返回一个打开的文件的系统配置信息。name为检索的系统配置的值，它也许是一个定义系统值的字符串，这些名字在很多标准中指定（POSIX.1, Unix 95, Unix 98, 和其它）。
# os.stat(path) 获取path指定的路径的信息，功能等同于C API中的stat()系统调用。
'''

# ---------------------------------------------------------------------

# common command in python
''' 
* pwd   : os.getcwd()
* cd    : os.chdir(path)



## executive bash in python
#### os.system("command")
* run in sub thread, cann't effect main thread
* cann't get result info
#### os.popen(command[,mode[,bufsize]])
* return a file outputsyream
* not blocking if not read() or readline()
#### subprocess
* subprocess : run() call() check_all() check_output() opoen() getoutput() getstatusoutput()
* 
* 

> exit status code in linux
success : 0
error   : 1,2,126,127,128,128+n,130,255
''' 

# ---------------------------------------------------------------------

# time
'''
#!/usr/bin/python3

import time,calendar

if __name__ == '__main__':
    # ticks = time.time()
    # print("timesnap:", ticks)

    # localTime = time.localtime()
    # print("本地时间为 :", localTime)

    # localTime = time.asctime( time.localtime() )
    # print("本地时间为 :", localTime)

    # 格式化成2016-03-20 11:45:39形式
    # print(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))

    # 格式化成Sat Mar 28 22:24:24 2016形式
    # print(time.strftime("%a %b %d %H:%M:%S %Y", time.localtime()))

    # 将格式字符串转换为时间戳
    # a = "Sat Mar 28 22:24:24 2016"
    # print(time.mktime(time.strptime(a,"%a %b %d %H:%M:%S %Y")))
    for i in range(2050, 2071):
        cal = calendar.month(i, 10)
        print ("以下输出{}年10月份的日历:".format(i))
        print (cal)



    时间元组（年、月、日、时、分、秒、一周的第几日、一年的第几日、夏令时）
        一周的第几日: 0-6
        一年的第几日: 1-366
        夏令时: -1, 0, 1

    python中时间日期格式化符号：
    ------------------------------------
    %y 两位数的年份表示（00-99）
    %Y 四位数的年份表示（000-9999）
    %m 月份（01-12）
    %d 月内中的一天（0-31）
    %H 24小时制小时数（0-23）
    %I 12小时制小时数（01-12）
    %M 分钟数（00=59）
    %S 秒（00-59）
    %a 本地简化星期名称
    %A 本地完整星期名称
    %b 本地简化的月份名称
    %B 本地完整的月份名称
    %c 本地相应的日期表示和时间表示
    %j 年内的一天（001-366）
    %p 本地A.M.或P.M.的等价符
    %U 一年中的星期数（00-53）星期天为星期的开始
    %w 星期（0-6），星期天为星期的开始
    %W 一年中的星期数（00-53）星期一为星期的开始
    %x 本地相应的日期表示
    %X 本地相应的时间表示
    %Z 当前时区的名称  # 乱码
    %% %号本身



# （1）当前时间戳
# 1538271871.226226
time.time()


# （2）时间戳 → 时间元组，默认为当前时间
# time.struct_time(tm_year=2018, tm_mon=9, tm_mday=3, tm_hour=9, tm_min=4, tm_sec=1, tm_wday=6, tm_yday=246, tm_isdst=0)
time.localtime()
time.localtime(1538271871.226226)


# （3）时间戳 → 可视化时间
# time.ctime(时间戳)，默认为当前时间
time.ctime(1538271871.226226)


# （4）时间元组 → 时间戳
# 1538271871
time.mktime((2018, 9, 30, 9, 44, 31, 6, 273, 0))

# print(time.mktime((2018, 10, 1, 9, 44, 31, 6, 273, 0)))


# （5）时间元组 → 可视化时间
# time.asctime(时间元组)，默认为当前时间
time.asctime()
time.asctime((2018, 9, 30, 9, 44, 31, 6, 273, 0))
time.asctime(time.localtime(1538271871.226226))


# （6）时间元组 → 可视化时间（定制）
# time.strftime(要转换成的格式，时间元组)
time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())


# （7）可视化时间（定制） → 时间元祖
# time.strptime(时间字符串，时间格式)
# print(time.strptime('2018-9-30 11:32:23', '%Y-%m-%d %H:%M:%S'))


# （8）浮点数秒数，用于衡量不同程序的耗时，前后两次调用的时间差
time.clock()



%y 两位数的年份表示（00-99）
%Y 四位数的年份表示（000-9999）
%m 月份（01-12）
%d 月内中的一天（0-31）
%H 24小时制小时数（0-23）
%I 12小时制小时数（01-12）
%M 分钟数（00=59）
%S 秒（00-59）
%a 本地简化星期名称
%A 本地完整星期名称
%b 本地简化的月份名称
%B 本地完整的月份名称
%c 本地相应的日期表示和时间表示
%j 年内的一天（001-366）
%p 本地A.M.或P.M.的等价符
%U 一年中的星期数（00-53）星期天为星期的开始
%w 星期（0-6），星期天为星期的开始
%W 一年中的星期数（00-53）星期一为星期的开始
%x 本地相应的日期表示
%X 本地相应的时间表示
%Z 当前时区的名称
%% %号本身

'''

# ---------------------------------------------------------------------


```