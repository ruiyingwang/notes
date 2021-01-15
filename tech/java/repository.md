
## KeyPoint
* 需要在数据库内先创建好schemas
* 需要一个Repository Interface
* 需要一个Model
* 在properties中添加sql配置



```bash
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/blog?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=USERNAME
spring.datasource.password=PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```


#hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
#spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })

<meta http-equiv="refresh" content="5">