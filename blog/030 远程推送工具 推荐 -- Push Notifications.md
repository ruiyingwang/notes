## 指路牌
* 推送工具
* iOS APNs

## 适用场景
以iOS为例，模拟后台服务器连接APNs，向设备远程推送信息。

## 背景
工作中碰到了iOS远程推送的需求，于是开始调研，在调研中发现了这个非常好用的工具，遗憾的是引导我跑完第一个hello world的博客是国外的网站，这个工具也是在那片文章中推荐的。而国内的网站有关APNs的博客或文档，没有一篇让我成功实现该功能的。

作为前端开发者，当我们想要测试后台API时，当然可以通过写逻辑代码来调试，但效率比之Postman要低很多，而当我们要使用APNs服务时，Push Notifications就充当了类似Postman的角色，它能够方面的串联APP与APNs服务器，极大的简化前端的开发成本，但是遗憾的是国内大多博客都自己通过某种后台语言实现一个服务，或使用繁琐的注册第三方的服务器，以上两种方案前者需要了解后台与APNs服务器的网络交互细节，后者需要了解繁琐的第三方服务配置规则，都前端开发者来说都不是很友好的选择。 Push Notifications可以非常轻易的解决这个问题。

[Push Notifications](https://github.com/onmyway133/PushNotifications)
Push Notifications是一个免费开源的工具，能够同时支持安卓与iOS的推送功能，iOS同时支持certificate与token两种模式，只需要简单的讲配置信息填写进去即可。

比较有趣的是，该工具的作者应该不是一个中国人，但该作者却以“中国龙”作为应用的图标。
![](https://user-images.githubusercontent.com/37465243/68081591-5f840280-fe4b-11e9-9b89-fe6b7c55d013.png)


## 使用(以APNs -- key方式为例)
使用工具前需要先准备几样必须的数据
1. Authentication Keys  (在苹果开发者中心生成)
2. Key ID of Authentication Keys  （Authentication Keys的ID）
3. Team ID （开发者账号的ID）
4. Bundle ID （XCode生成应用时自己设置的ID）
5. Device Token （通过代码，获取到的设备当前token）
> 以上内容以此输入下图中，由于设计平台的菜单层级实惠变化的，此处不截图罗列不走，请自行搜索最新版教程。

![](https://user-images.githubusercontent.com/37465243/68081590-5f840280-fe4b-11e9-8e67-29cdf2f0c9fc.png)

全部素材准备好后即可以填写推送内容，点击Send推送。