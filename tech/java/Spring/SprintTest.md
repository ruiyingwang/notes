
## ??
* org.springframework.test.web.servlet.MockMvc; 的 MockMvc用于发起请求
* BDDMockito的打桩与mockito的打桩有什么区别



## MockMvc
* 用于以mock的形式发起Http网络请求

#### perform()

#### andDo()

#### andExpect()

#### andReturn() 

## MockMvcResultHandlers
* 用于实现某些操作，如在andDo()中打印日志

## MockMvcResultMatchers
* 提供例如json解析工具用于在andExpect()中对结果的数据进行比较

## MockMvcRequestBuilders
* 用于创建Http请求，提供get, post, put等方法


#### 常见用例
* 打桩
```java
// 当url含有参数且不希望区分时，可以使用ArgumentMatchers.contains().
BDDMockito
    .given(this.restTemplate.exchange(
        ArgumentMatchers.contains(this.scimUserApiUrl),
        ArgumentMatchers.eq(HttpMethod.PUT),
        ArgumentMatchers.any(HttpEntity.class),
        ArgumentMatchers.eq(SCIMUserDTO.class)))
    .willReturn(new ResponseEntity<>(new SCIMUserDTO(), HttpStatus.OK))
```

* 发起请求
```java
// get
final String getUrl = "https://api.com/";
this.mockMvc
    .perform(MockMvcRequestBuilders
        .get(this.getUrl))
    .andDo(MockMvcResultHandlers.print())
    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
    .andExpect(MockMvcResultMatchers.jsonPath("$[0].permission", IsEqual.equalTo(Boolean.TRUE)))
    .andExpect(MockMvcResultMatchers.jsonPath("$[1].permission", IsEqual.equalTo(Boolean.TRUE)));

// post put
final String postUrl = "https://api.com/";
final String payload = "{\"key\":\"value\"}";
this.mockMvc
    .perform(MockMvcRequestBuilders
        .post(this.postUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.payload)
        .with(SecurityMockMvcRequestPostProcessors.csrf()))
    .andDo(MockMvcResultHandlers.print())
    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
    .andExpect(MockMvcResultMatchers.jsonPath("$.role", IsEqual.equalTo("expected String")));
```