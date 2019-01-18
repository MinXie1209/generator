###代码生成器-江南小俊

> 由于重复工作所致，为了减少重复工作，手撕了一个代码生成器

> 多数工程都会涉及到对应数据库表的CURD,

> 本工具将会自动生成大量的CURD工作，关联表以后慢慢会拓展

> 1.0版本要实现的功能有
  
  1. addTable(Table table)
  2. updateTableById(Integer tableId)
  3. deleteTableById(Integer tableId)
  4. getTableById()
  5. listTable()
  
> 生成的工程目录

    -src
        -main
            -java
                -packageName ：包
                    -controller ：控制层
                    -service ：业务接口层
                        -impl ：业务实现层
                    -mapper ：数据库层
                    -entity ： pojo类层
                    -common ： 公共层
                        -util : 工具层
                        -constant ：常量层
                            -conf ： 配置层
                        -exception ：自定义异常
                        -enum ：枚举类
                        -vo ： 返回数据封装层
                -Application.java ： 入口类
            -resources ：资源文件
                -application.yml ： 配置文件
    -pom.xml  ： maven配置文件，管理jar包





### 需要生成的内容(变的)

- controller
- service
- service.impl
- dao
- entity
- pom.xml
- application.yml
- Application.java
- common

###需要的输入参数

- 数据库url
- username
- password
- 包名前缀

###遇到的困难

- 如何根据传入的数据源参数动态设置数据源
- 数据库字段类型转换Java的类型
    - 解决方法：yml中配置相关的映射，通过@ConfigurationProperties注入到Java类中
- Velocity找不到vm文件
    - 问题根源：缺少VelocityEngine,没加载类路径，导致找不到资源
    - 解决方法：加入VelocityEngine，设置其属性键值对(RuntimeConstants.RESOURCE_LOADER, "classpath");("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
- 返回的压缩包比实际的数据不一样
    - 问题根源：ZipOutputStream和ByteArrayOutputStream关闭资源顺序弄反了
    - 解决问题：更改ZipOutputStream和ByteArrayOutputStream关闭资源顺序
- 如何生成PO类
    - Velocity模板遍历Map