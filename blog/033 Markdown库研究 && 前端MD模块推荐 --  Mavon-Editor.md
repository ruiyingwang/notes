## 指路牌
* Markdown库研究
* 前端解析markdown
* 前端markdown编辑器

## 适用场景
* 前端markdown编辑器
* 前端markdown解析器

## 参考博客
* 李牧敲代码 --  [mavon-editor 使用教程](https://www.jianshu.com/p/78ea4f94a3d0)
* Jerry Wang -- [推荐一个markdown格式转html格式的开源JavaScript库](https://www.jianshu.com/p/c657195ada96)
* 等.....

## 正文
很早以前就想自己搭建一个博客系统，本以为最麻烦的应该是用户管理，后来发现最麻烦的居然是文本编辑器...我自己是不太喜欢富文本编辑器的，平时都是印象笔记写文章，md用的很习惯，就想要寻找一个前端解析md的库，结果这个坑是真的有点深...

首先markdown的本质是将md的标记语法转换成前端代码，而这个转换过程可以分为前端完成与后端完成两个大方向，我个人觉得渲染工作应该放在前端完成，这样一方面可以减轻后台的压力，充分利用起客户端的性能。(个人觉得客户端的性能是非常过剩的)，另一方面，前端语言相对比较统一，便于维护与发展，相比之下，虽然后台工在业中JAVA是龙头，但不可否认还有php庞大的用户群体，和我这种喜欢小语种开发后台的。

从结果上来讲，我看到的md库非常的不统一，我看到的有：marked、markdown-js、vue-md-loader、js-markdown-loader、parsedown、Ciconia、decoda、showdown、md-page等，这里面既有前端解析库，也有后台解析库，我没有时间能去研究和对比每一个库，大部分是通过体验别人开发的demo来体验每个库的功能，首先我pass掉了所有的后端库（其实也看了一个Python的库，使用的非常不愉快）,而是将精力放在了前端库，我常用的MD标签有：标题、图片、备注、代码块、无序序列，有序序列、链接、表格。结果上讲大部分没有符合我的预期，主要是对代码块于备注的支持不是很好，这是我无法接受的。

最后的结论是没有找到一个非常方面，能开箱即用功能比较完善的库，这一度让我blog系统开发陷入了无法推进的地步，原本计划把其他事情都延后，想先解决MD编辑器的问题，然后我就碰到了救星 -- Mavon-Editor

## Mavon-Editor
Github -- [Mavon-Editor](https://github.com/hinesboy/mavonEditor)

如果当初我选择学习Vue是因为Vue的风格，因为Vue的logo，因为尤雨溪，那现在最大的原因就是因为Mavon-Editor了

需要说明的是，Mavon-Editor是一个基于Vue的库，并不适用于其他框架(?),它提供了非常丰富的功能，开箱即用，既可以用于编辑，也可以用于解析，也是因为这个库，大大加速了我个人blog系统的开发进度。首先来看几张效果图

解析
![](https://user-images.githubusercontent.com/58285760/71776669-a966a180-2fd0-11ea-911d-f24c753c4a62.png)

编辑
![](https://user-images.githubusercontent.com/58285760/71776670-ab306500-2fd0-11ea-9731-fcb55a398630.png)

轻易的拓展 -- emoji
![](https://user-images.githubusercontent.com/58285760/71776671-abc8fb80-2fd0-11ea-87fd-078cdc56e396.png)

Mavon-Editor 非常丰富的基本功能，包括：标题、斜体、粗体、下划线、中划线、标记、上角标、下角标、居左居中具右、引用、有序序列、无序序列、连接、图片、代码、表格、标题导航、全屏编辑模式、全凭阅读模式、单栏模式、查看html文本等...

同时提供多种API，能够自定义功能栏的功能模块，基本样式，以及事件监听如：监控文本变动、模式切换变动等，详细可以参见文档。

使用也非常方便：
* 安装
```
npm install mavon-editor --save
```

* 全局引入
```javascript
    // main.js
    import mavonEditor from 'mavon-editor'
    import 'mavon-editor/dist/css/index.css'
    Vue.use(mavonEditor)
```

* 使用(默认是编辑器模式)
```html
<mavon-editor v-model="value"/>
```

* 阅读模式
```html
    <mavon-editor
        :value="input"
        :subfield = "false"
        :defaultOpen = "'preview'"
        :toolbarsFlag = "false"
        :boxShadow="false"
        :transition="false"
    ></mavon-editor>
```

Mavon-Editor功能丰富、拓展方便，渲染效果也非常可观，整体体验并不亚于简书或印象笔记的书写体验。



