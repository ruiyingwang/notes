## Link
* [数据库调优」屡试不爽的面试连环combo](https://zhuanlan.zhihu.com/p/147490337)
* [阿里一面，给了几条SQL，问需要执行几次树搜索操作？](https://zhuanlan.zhihu.com/p/348310440)
* [程序员：是不是但凡我回答不上一个关于MySQL的问题，就进不了你们公司？](https://zhuanlan.zhihu.com/p/161894650)
* [面试官：数据量很大，分页查询很慢，有什么优化方案？](https://zhuanlan.zhihu.com/p/73568092)
* [Spring Boot 实现原理与源码解析系统 —— 精品合集](https://www.iocoder.cn/Spring-Boot/good-collection/?zhihu)
* [2021Java面试笔记](https://shimo.im/docs/V3Q8vgdvGgwqKgY9/read)
* [数据库SQL调优的几种方式](https://blog.csdn.net/u010520146/article/details/81161762)




## 基本概念
#### 数据库系统 数据库管理系统 数据库 存储引擎

#### JPA， Mybatis, Hibernat 的关系

#### 存储引擎
* mysql的存储引擎:  InnoDB、MyISAM、MEMORY 等存储引擎。InnoDB的灾难恢复性好、支持事务、使用行级锁和
* 
* 
* 
* 

## 技术参数
* TPS
* 吞吐量
* QPS
* 缓存雪崩, 缓存击穿, 缓存穿透
* 

## 索引
* 聚簇索引
* 非聚簇索引
* 普通索引
* 唯一索引
* change buffer
* 表锁
* 行锁
* 间隙锁以及行锁并发情况下的最大TPS是多少
* 还有索引为啥会选择错误


#### 数据库调优


```
一.创建索引
1.要尽量避免全表扫描，首先应考虑在 where 及 order by 涉及的列上建立索引
2.(1)在经常需要进行检索的字段上创建索引，比如要按照表字段username进行检索，那么就应该在姓名字段上创建索引，如果经常要按照员工部门和员工岗位级别进行检索，那么就应该在员工部门和员工岗位级别这两个字段上创建索引。
(2)创建索引给检索带来的性能提升往往是巨大的，因此在发现检索速度过慢的时候应该首先想到的就是创建索引。
(3)一个表的索引数最好不要超过6个，若太多则应考虑一些不常使用到的列上建的索引是否有 必要。索引并不是越多越好，索引固然可以提高相应的 select 的效率，但同时也降低了 insert 及 update 的效率，因为 insert 或 update 时有可能会重建索引，所以怎样建索引需要慎重考虑，视具体情况而定。

二.避免在索引上使用计算
在where字句中，如果索引列是计算或者函数的一部分，DBMS的优化器将不会使用索引而使用全表查询,函数
属于计算的一种,同时在in和exists中通常情况下使用EXISTS，因为in不走索引
效率低：

 select * from user where salary*22>11000(salary是索引列)
1
效率高：

 select * from user where salary>11000/22(salary是索引列)
1
三.使用预编译查询
程序中通常是根据用户的输入来动态执行SQL，这时应该尽量使用参数化SQL,这样不仅可以避免SQL注入漏洞
攻击，最重要数据库会对这些参数化SQL进行预编译，这样第一次执行的时候DBMS会为这个SQL语句进行查询优化
并且执行预编译，这样以后再执行这个SQL的时候就直接使用预编译的结果，这样可以大大提高执行的速度。

四.调整Where字句中的连接顺序
DBMS一般采用自下而上的顺序解析where字句，根据这个原理表连接最好写在其他where条件之前，那些可以
过滤掉最大数量记录。

五.尽量将多条SQL语句压缩到一句SQL中
每次执行SQL的时候都要建立网络连接、进行权限校验、进行SQL语句的查询优化、发送执行结果，这个过程
是非常耗时的，因此应该尽量避免过多的执行SQL语句，能够压缩到一句SQL执行的语句就不要用多条来执行。

六.用where字句替换HAVING字句
避免使用HAVING字句，因为HAVING只会在检索出所有记录之后才对结果集进行过滤，而where则是在聚合前
刷选记录，如果能通过where字句限制记录的数目，那就能减少这方面的开销。HAVING中的条件一般用于聚合函数
的过滤，除此之外，应该将条件写在where字句中。

七.使用表的别名
当在SQL语句中连接多个表时，请使用表的别名并把别名前缀于每个列名上。这样就可以减少解析的时间并减
少哪些友列名歧义引起的语法错误。

八.用union all替换union
当SQL语句需要union两个查询结果集合时，即使检索结果中不会有重复的记录，如果使用union这两个结果集
同样会尝试进行合并，然后在输出最终结果前进行排序，因此如果可以判断检索结果中不会有重复的记录时候，应
该用union all，这样效率就会因此得到提高。

九.考虑使用“临时表”暂存中间结果
简化SQL语句的重要方法就是采用临时表暂存中间结果，但是，临时表的好处远远不止这些，将临时结果暂存在临时表，后面的查询就在tempdb中了，这可以避免程序中多次扫描主表，也大大减少了程序执行中“共享锁”阻塞“更新锁”，减少了阻塞，提高了并发性能。
但是也得避免频繁创建和删除临时表，以减少系统表资源的消耗。

十.只在必要的情况下才使用事务begin translation
SQL Server中一句SQL语句默认就是一个事务，在该语句执行完成后也是默认commit的。其实，这就是begin tran的一个最小化的形式，好比在每句语句开头隐含了一个begin tran，结束时隐含了一个commit。
有些情况下，我们需要显式声明begin tran，比如做“插、删、改”操作需要同时修改几个表，要求要么几个表都修改成功，要么都不成功。begin tran 可以起到这样的作用，它可以把若干SQL语句套在一起执行，最后再一起commit。 好处是保证了数据的一致性，但任何事情都不是完美无缺的。Begin tran付出的代价是在提交之前，所有SQL语句锁住的资源都不能释放，直到commit掉。
可见，如果Begin tran套住的SQL语句太多，那数据库的性能就糟糕了。在该大事务提交之前，必然会阻塞别的语句，造成block很多。
Begin tran使用的原则是，在保证数据一致性的前提下，begin tran 套住的SQL语句越少越好！有些情况下可以采用触发器同步数据，不一定要用begin tran。

十一.尽量避免使用游标
尽量避免向客户端返回大数据量，若数据量过大，应该考虑相应需求是否合理。因为游标的效率较差，如果游标操作的数据超过1万行，那么就应该考虑改写。

十二.用varchar/nvarchar 代替 char/nchar
尽可能的使用 varchar/nvarchar 代替 char/nchar ，因为首先变长字段存储空间小，可以节省存储空间，其次对于查询来说，在一个相对较小的字段内搜索效率显然要高些。
不要以为 NULL 不需要空间，比如：char(100) 型，在字段建立时，空间就固定了， 不管是否插入值（NULL也包含在内），都是占用 100个字符的空间的，如果是varchar这样的变长字段， null 不占用空间。

十三.查询select语句优化
1.任何地方都不要使用 select * from t ，用具体的字段列表代替“*”，不要返回用不到的任何字段
2.应尽量避免在 where 子句中对字段进行 null 值判断，否则将导致引擎放弃使用索引而进行全表扫描，
如：

     select id from t where num is null           
1
可以在num上设置默认值0，确保表中num列没有null值，
然后这样查询：

      select id from t where num=0
      select id from t where num=10 or num=20
1
2
可以这样查询：

      select id from t where num=10
       union all
      select id from t where num=20
1
2
3
4.不能前置百分

select id from t where name like ‘%abc%’
1
若要提高效率，可以考虑全文检索。

     select id from t where num in(1,2,3)
1
对于连续的数值，能用 between 就不要用 in 了：

    select id from t where num between 1 and 3 
1
6.如果查询的两个表大小相当，那么用in和exists差别不大。
in：
例如：表A（小表），表B（大表）

 select * from A where cc in (select cc from B) 效率低，用到了A表上cc列的索引；     
 select * from A where exists(select cc from B where cc=A.cc)   效率高，用到了B表上cc列的索引。   
1
2
相反的

 select * from B where cc in (select cc from A)  效率高，用到了B表上cc列的索引；
 select * from B where exists(select cc from A where cc=B.cc)  效率低，用到了A表上cc列的索引。         
1
2
十四.更新Update语句优化
1.如果只更改1、2个字段，不要Update全部字段，否则频繁调用会引起明显的性能消耗，同时带来大量日志

十五. 删除Delete语句优化语句
1.最高效的删除重复记录方法 ( 因为使用了ROWID)例子：

DELETE FROM EMP E WHERE E.ROWID > (SELECT MIN(X.ROWID) FROM EMP X WHERE X.EMP_NO = E.EMP_NO);
1
十六.插入Insert语句优化
1.在新建临时表时，如果一次性插入数据量很大，那么可以使用 select into 代替 create table，避免造成大量 log ，以提高速度；如果数据量不大，为了缓和系统表的资源，应先create table，然后insert。
```