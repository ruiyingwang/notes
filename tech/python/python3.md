
#### Python3的新特性
* 函数注解
```python
# 定义参数的数据类型: `s: str`, 可以是数据类型，也可以是一段注解，注解使用""
# 定义返回数据类型: `-> int`
def functionName(s: str) -> int:
    pass

# 默认值
def functionName(s: str = "defaultBalye") -> int:
    pass

# 返回可以是表达式，但是需要表达式的参数需要时长常量
def functionName(a: int = 5, b: int = 7) ->pow(2,3):
    z = lambda x, y:x*x  if x > y else y*y
    return z(a,b)
```