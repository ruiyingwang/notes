## 指路牌
* 微信小程序 真机调试 202:net::ERR_CERT_AUTHORITY
* 微信小程序 安卓手机真机调试 202:net::ERR_CERT_AUTHORITY

## 场景描述
微信小程序开发过程中，在开发者工具中正常，但是在真机调试中出现202:net::ERR_CERT_AUTHORITY，以上情况发生在安卓系统，iOS系统未测试，但是根据微信开放社区的这个提问，[真机调试报错 request:fail -202:net::ERR_CERT_AUTHORITY？](https://developers.weixin.qq.com/community/develop/doc/000ac0dbb28228b0a50abd46c51c00), 苹果系统应该是没有问题的。

本解决方案不一定适用于所有的以上报错，在看决绝方案前请先确认以下情形：
* 微信小程序调用的后台服务为二级域名形式，SUBDOMAIN.domain.com
* https证书为通配证书


## 问题分析
通过搜索`202:net::ERR_CERT_AUTHORITY`，会发现网上大部分是Chrome浏览器出现该问题，而解决方案大都只想证书，以此为切入点，再结合服务器的配置情况，推测是安卓系统的微信对通配证书的支持存在问题导致的，有了思路后就开始实践一下。

* 首先单独申请一个新的证书![0ee3932a9e255edd1fba9c5ffc54a071.png](evernotecid://304A294F-4F6F-4959-9F4A-FB7A23735E12/appyinxiangcom/21327510/ENResource/p59)
* 添加DNS解析
![7419d576f0b2d698567683a69dcf512b.jpeg](evernotecid://304A294F-4F6F-4959-9F4A-FB7A23735E12/appyinxiangcom/21327510/ENResource/p60)
* 证书上传服务器
```
scp CERT.key USER@IP:PATH
scp CERT.pem USER@IP:PATH                        
```
* 修改配置
![376bc4bed2067a5b884375c563bc381e.png](evernotecid://304A294F-4F6F-4959-9F4A-FB7A23735E12/appyinxiangcom/21327510/ENResource/p61)

* 重新加载配置文件
```
nginx -t -c /etc/nginx/nginx.conf
nginx -s reload
```

* 使用浏览器检查一下证书是否更新
![2ba53df53b60cf8df8b09c6cb3096123.png](evernotecid://304A294F-4F6F-4959-9F4A-FB7A23735E12/appyinxiangcom/21327510/ENResource/p62)


* 再次测试
问题解决成功

## 后记
以上问题的产生不清楚是不是安卓系统对通配证书的支持存在缺陷导致的，到了这个层面有些超出我的知识范畴了，在此不乱推了。

另外吐槽一下微信，证书问题为什么返回的是202，起码应该是4XX啊......

