## 指路牌
* SAP Conversational AI 如何传入自定义字段并将其作为Actions的判定条件使用
* Recast.AI 如何传入自定义字段并将其作为Actions的判定条件使用

## 适用场景
这篇blog是为了填补文档我没有找到的以下问题：如何在Actions使用memory作为判定条件。

设想如下场景，当用户在一个商品页面，点击了发起聊天时，Recast.AI如何能知道用户想要询问的哪一款产品呢？自己写代码这是一个很简单的问题，在调用聊天窗口时将产品id传入即可，而这篇blog，就是展示在使用Recast.AI时如何传入自定义的信息以及（重点）如何在actions中将其作为判断条件使用。

## 正文
#### 传入自定义字段
![](https://user-images.githubusercontent.com/58285760/76949999-5e5bf980-6944-11ea-8935-66bdf9bd79c0.png)
根据截图，可以在调用聊天API时，将自定义字符串以json格式传入Recast.AI，Python示例代码如下：
```Python
response = requests.post('https://api.cai.tools.sap/build/v1/dialog',
    headers={'Authorization': 'Token '+TOKEN,'Content-Type':'application/json'},
    json={
        "message": {
            "content":MESSAGE,
            "type":"text",
        }, 
        "conversation_id": CONVERSATION_ID,
        "memory": {
            #json
        }
    }
)
print(response.text)
```
> TOKEN 在Bots可在点进去后的下不活的，区别于一般的在个人中心获得
> CONVERSATION_ID 是一个可以自己随意填写的字符串，不一定是通过调API从平台获取的，所以可以直接填一个id，如0000000001
> MESSAGE 为用户在输入框输入的内容，如‘hello’，类似于用户的输入
> memory可以填入开法则需要的任意自定义json格式数据，该部分数据将在平台的后续阶段被调用到。

#### 将memory中的值作为Actions的判定条件使用
此部分是我没有在文档找到的，尤其不要跟着文档，在Requirements阶段引入，由于该部分文档前后例子代码不一致，我还不太清楚文档的具体含义，正确步骤是，直接进入来到Actions部分，在条件不选选择`_memory`，然后使用`.`即可以调用自己传入的内容了，见截图。
![](https://user-images.githubusercontent.com/58285760/76950002-6025bd00-6944-11ea-82c6-eb07883c1a2b.png)
![](https://user-images.githubusercontent.com/58285760/76950007-6156ea00-6944-11ea-817d-119fe6cdf479.png)

#### 将memory中的值作为Actions内容实用
此部分文档的正确的，在Action内部想要调用自己传入的内容，需要使用`{{memory.KEY}}`的格式，这也是让我觉得很奇怪的地方，同平台的同一个页面，操作同一个对象却是两种不同的方式，而且其中一种文档还没有写......
![](https://user-images.githubusercontent.com/58285760/76950012-6320ad80-6944-11ea-93c9-a6083e7b0a74.png)

## 后记
附上关于memory部分的文档链接
[Bot Builder -- Memory Management](https://help.sap.com/viewer/a4522a393d2b4643812b7caadfe90c18/latest/en-US/e99f8ad649a94f4a9c3c628567bede7f.html)
[/dialog(text)](https://cai.tools.sap/docs/api-reference/?shell#dialog-endpoints)
第一个就是文中提到的关于memory介绍的，默认memory已经传入了平台
第二部分是传入memory的API接口，但是也没有很明显的说明，而且没有跳转到第一部分的链接

整个文档感觉很割裂，如此重要的功能，在调用出没有很详细的使用说明，以及跳转到具体说明的链接，在具体说明部分例子也没有涵盖所有的情况，为了解决这个问题我额外花了2.5h......酒香也怕巷子深.