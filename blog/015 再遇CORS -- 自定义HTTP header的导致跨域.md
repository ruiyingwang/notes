## 指路牌
* 后端配置好了跨域，但是前端在HTTP header添加token后，又产生跨域的问题 
* Flask、Vue(Axios)、跨域

## 适用场景
* 前后端分离，想要使用token来管理登录状态，或调用后台接口

## 环境
* 平台无关

## 参考博客
* [axios 在header中配置token信息后，向后端请求会报跨域的问题。但是用postman测试的时候没有什么问题。](https://segmentfault.com/q/1010000017757881)
> 这个问题的回答其实没有给出直接性的帮助，甚至回答的有点奇怪，但是帮我打开了思路。

## 背景
出于多种考虑，放弃了使用类似WordPress这种现成博客解决方案，准备自己搭建一个博客系统，技术选型为：后端：Flask，前端：Vue。登录状态管理放弃cookie，采用token。开发进行到路由保护处时出现了CORS的问题，具体情形是Vue将从后台获取的token添加到HTTP请求的header中，调用相应接口时出现跨域。

在此次跨域出现前实际上已经在Flask通过flask_cors配置了跨域解决方案，因此跨域的产生是让我十分不解的，又由于问题比较奇特在搜索引擎中没有找到很好的解决方案(也可能是我不知道怎么描述，没有搜出来)，因此自己重新研究可以一下跨域，又有了一些新收获。

## 分析
相信使用前后端分离的开发者在开发之初就会碰到跨域的问题，跨域的解决方案有很多种，我选择通过后台解决。
> 跨域是浏览器同源策略导致的问题，网上相关文章很多，此处不赘述。备注一点：postman不会产生跨域。

Flask解决跨域的方案非常简单，以下代码即可完成。
```py
from flask_cors import CORS
CORS(app,supports_credentials=True

@app.after_request
def after_request(resp):
	resp = make_response(resp)
	resp.headers['Access-Control-Allow-Origin'] = 'http://127.0.0.1:8080'
	resp.headers['Access-Control-Allow-Methods'] = 'GET,POST'
	resp.headers['Access-Control-Allow-Headers'] = 'x-requested-with,content-type'
	return resp
```
配置完成后，一直也没有没有出过问题，因此也就没有去进一步了解其配置的含义，直到这一次被卡住，让我不得不去了解一下跨域我究竟配置了什么东西？


其实这个问题的关键点就在于那三条配置：`Access-Control-Allow-Origin`、`Access-Control-Allow-Methods`、`Access-Control-Allow-Headers`.
他们到底代表什么含义？


首先`Access-Control-Allow-Origin`，字面上的意思，配置这个可以允许相应的源来访问后台资源，网上大多在此处的写的是`*`，也即允许所有源，这样很不安全，由于我此处是本地开发阶段，Vue的启动端口是8080，所以我写的是`http://127.0.0.1:8080`,根据你的开发需要改成自己需要的三元组即可。

其次`Access-Control-Allow-Methods`，也是字面上的意思，允许用的HTTP的Method有哪些，GET，POST是最常见的，此处只写了两个，如果你需要使用其他Method，在这里要写进来，否则也会会出现跨域问题。

以上两个配置都没有问题，问题在了最后一部分：

`Access-Control-Allow-Headers`，和上面两个一样，字面的意思，之所以是她出问题了，是因为我们在前端给HTTP请求添加了一个自定义的字段`token`，而这不在许可范围内(许可的只有`x-requested-with`和`content-type` )，因此被判定为了不符合同源策略的非法请求，所以我们只需要将自定义的header添加进去即可。答案已经出来了。

继续挖一下，这个字段的两个含义分别还是什么？`x-requested-with`,`content-type`.

`x-requested-with`是一个用来判断客户端请求是否由Ajax发起的，所以和Axios有什么关系？答案是：没有关系...可以直接删了。贴上这段代码的人或者是默认了发起请求使用的是Ajax，又或者没有分析字段含义，所以很直接贴了这段代码，但是对于使用Axios的开发者来说，这个字段不是必然的。

`content-type`: 指明POST请求的数据格式以及编码方式。数据格式最常见的莫过于josn，其形式如下：`application/json`在后端打印出POST请求的HTTP Header，就会发现有下面这条和数据。
```
Content-Type: application/json;charset=UTF-8
```



## 解决方案
 在`Access-Control-Allow-Headers`中添加上自定义的header名称，整体如下：
```py
from flask_cors import CORS
CORS(app,supports_credentials=True

@app.after_request
def after_request(resp):
	resp = make_response(resp)
	resp.headers['Access-Control-Allow-Origin'] = 'http://127.0.0.1:8080'
	resp.headers['Access-Control-Allow-Methods'] = 'GET,POST'
	resp.headers['Access-Control-Allow-Headers'] = 'content-type,token'
	return resp
```
> 其实直接删掉`Access-Control-Allow-Headers`这条配置，也能解决问题，但是枚举出所有固定情形当然是更安全的。

