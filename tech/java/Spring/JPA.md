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


<meta http-equiv="refresh" content="5">