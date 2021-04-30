## IDEA
#### 快捷键
* 压缩所有 Shift Command +
> Perference->Keymap->Main menu-> Code->Folding
> Perference->Editor -> General -> Appearance -> Show whitespaces


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