
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


## Annotation

#### @CreatedDate、@CreatedBy、@LastModifiedDate、@LastModifiedBy
* 需要启动类加上@EnableJpaAuditing
* 实体类加上@EntityListeners(AuditingEntityListener.class)
> @CreatedDate、@LastModifiedDate即可生效
> @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
* 实现interface AuditorAware
> @CreatedBy、@LastModifiedBy生效
```Java
@Configuration
public class UserIDAuditorBean implements AuditorAware<Long> {
    @Override
    public Long getCurrentAuditor() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx == null) {
            return null;
        }
        if (ctx.getAuthentication() == null) {
            return null;
        }
        if (ctx.getAuthentication().getPrincipal() == null) {
            return null;
        }
        Object principal = ctx.getAuthentication().getPrincipal();
        if (principal.getClass().isAssignableFrom(Long.class)) {
            return (Long) principal;
        } else {
            return null;
        }
    }
}
```

#### 


<meta http-equiv="refresh" content="5">