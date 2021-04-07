


* 一对多表的建立
* 一对多表在表在Java代码中的增删查改
* 



#### Light
* @Entity
* @Audited
* @Table(uniqueConstraints = {@UniqueConstraint(name = "userrolescope_uidx", columnNames = { "user_id", "scope", "role_id" }) })
* @ManyToOne
* @JoinColumn



? @JoinColumn与OneToMany的区别













PagingAndSortingRepository
CrudRepository
paSpecificationExecutor<Blog> 

* FindById 直接用不方便
* DTO内不应该出现id，不应该暴露在外，可以考虑使用定制的唯一code来代替
* 


#### javax.persistence

* @Entity: 用于将类注册为Model
* @Table: 用于配置表的信息






## 表与表的关系

#### 一对多
```java
Book.java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Page> pages;

Page.java
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

```



keyword
* 

```java

```






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


<meta http-equiv="refresh" content="5">