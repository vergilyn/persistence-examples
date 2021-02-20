# mybatis-examples

+ <https://mybatis.org/mybatis-3/>
+ mybatis: <https://github.com/mybatis/spring-boot-starter>


+ mybatis-plus: <https://github.com/baomidou/mybatis-plus>
+ mybatis-plus-samples: <https://github.com/baomidou/mybatis-plus-samples>
+ mybatis-plus-guide: <https://mp.baomidou.com/guide/>

+ mybatis-PageHelper:<https://github.com/pagehelper/Mybatis-PageHelper>

只要是`mapper.xml`形式，最繁琐的是：当table字段需要新增/修改时，都需要修改`mapper.xml`。  
即使`mapper.xml`可以通过 `mybatis-generator / free-mybatis-plugin` 生成，但如果有手写的方法呢？
（虽然手写的SQL可以放到 mapper-other.xml，但新增字段时，需要维护其中的字段映射）

所以，**相对而言**，JPA/hibernate 的 entity 映射概念会方便很多。  
个人感觉 mybatis-plus 就是JPA的思想，确实解决了很多麻烦，但感觉整体还是不如JPA完善。

从开发**简易程度**而言，如果可以选择话还是建议：JPA（复杂SQL还是用java写，这一点确实不如xml写SQL舒服）。
（mybatis太繁琐，mybatis-plus相对没JPA完善。JPA 实体类与table的映射用起来舒服的多！）

Mybatis vs JPA:
- [为什么我劝你放弃mybatis](https://zhuanlan.zhihu.com/p/45044649)
- [关于Jpa和Mybatis的一些看法](https://www.cnblogs.com/ymstars/p/10629285.html)
- [为什么mybatisplus这么好用，反而用的不多？](https://www.zhihu.com/question/314745062)
- [spring-data-jpa和mybatis可以整合在一起使用有什么优缺点？](https://www.zhihu.com/question/53706909)

其它：
- [MyBatis 中 @Param 注解的四种使用场景，最后一种经常被人忽略！](https://zhuanlan.zhihu.com/p/74853451)