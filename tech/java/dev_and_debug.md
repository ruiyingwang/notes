
#### 远程调试
IDEA 配置
```
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9098
```
Maven启动配置(本地命令行启动， IDEA调试)
```
mvn spring-boot:run -Pdevelopment-mysql

export CATALINA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9098"
```

<meta http-equiv="refresh" content="5">