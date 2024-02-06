ConnectComputer | Server
===
[English](https://github.com/gdrfgdrf/ConnectComputer/blob/Server/README.md) | __[简体中文](https://github.com/gdrfgdrf/ConnectComputer/blob/Server/README_ChineseSimplified.md)__
- ConnectComputer 是一款可以控制远程设备的软件。
- 该分支存储了 *__服务器__*。

<!-- TOC -->
* [ConnectComputer | Server](#connectcomputer--server)
  * [配置](#配置)
  * [使用](#使用)
  * [运行时生成文件](#运行时生成文件)
  * [依赖](#依赖)
    * [直接复制进项目的依赖](#直接复制进项目的依赖)
    * [复制的代码](#复制的代码)
  * [免责声明](#免责声明)
<!-- TOC -->

配置
-------------------------
- 部署数据库。
- 部署 Redis。
- 从 Release 页面下载 Jar 包。
- 使用 Java 17 运行以创建配置文件。
- 修改 application.yml 的 spring.datasource 下的  
"url", "username", "password", "driver-class-name" 以连接数据库。
- 修改 application.yml 的 spring.redis 下的 "host", "port", "password" 以连接 Redis 缓存。
- 按需修改 application.yml。  

如果 application.yml 中 language 是 zh_cn.json，  
那么运行之后如果没有找到 zh_cn.json 则会创建一个。

使用
-------------------------
请确保您的服务器是安全的，  
Http 服务器带有 RSA 加解密，  
Netty 服务器带有 RSA 和 AES 加解密，  
但两者仍有被监听的风险，
若您作为服务器的提供者，请做好安全措施，比如 SSL，  
对于因服务器安全问题造成的任何损失，我们概不负责。

- 使用 Java 17 开启服务器。
- 将您服务器的 ip 或 域名公布给用户。

运行时生成文件
-------------------------
| 文件名                       | 描述                    |
|---------------------------|-----------------------|
| application.yml           | SpringBoot 以及该软件的配置文件 |
| zh_cn.json                | 简体中文                  |
| avatar/default_avatar.jpg | 默认头像                  |

依赖
-------------------------
| 依赖名                                                                                                                          | 在本软件中的用途       |
|------------------------------------------------------------------------------------------------------------------------------|----------------|
| [Reflections](https://github.com/ronmamo/reflections)                                                                        | Netty 数据包查询    |
| [Lombok](https://github.com/projectlombok/lombok)                                                                            | 代码简化           |
| [FASTJSON](https://github.com/alibaba/fastjson2)                                                                             | JSON 序列化       |
| [Apache Commons IO](https://github.com/apache/commons-io)                                                                    | IO 操作          |
| [Apache Commons Lang](https://github.com/apache/commons-lang)                                                                | 数组，字符串操作       |
| [Java JWT](https://github.com/jwtk/jjwt)                                                                                     | Token 的生成和校验   |
| [Protocol Buffers](https://github.com/protocolbuffers/protobuf)                                                              | Netty 数据包      |
| [MySQL Connector/J](https://github.com/mysql/mysql-connector-j)                                                              | MySQL 数据库的连接   |
| [MyBatis-Plus](https://github.com/baomidou/mybatis-plus)                                                                     | POJO 代码简化      |
| [MPRelation](https://github.com/dreamyoung/mprelation)                                                                       | POJO 一对多，多对一查询 |
| [Netty](https://github.com/netty/netty)                                                                                      | 长连接通讯          |
| [Spring Boot Starter Validation](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation) | Http 参数校验      |
| [Spring Boot Starter Data Redis](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis) | Http 缓存        |
| [Spring Boot Starter Web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)               | 后端             |
| [Spring Boot Starter AOP](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-aop)               | 动态代理           |
| [Spring Boot Starter Logging](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-logging)       | Logback 日志框架   |
| [Spring Boot Starter Log4j2](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-log4j2)         | Log4j2 日志框架    |
| [JUnit 5](https://github.com/junit-team/junit5)                                                                              | 单元测试           |

### 直接复制进项目的依赖

| 依赖名                                                                                                                                    | 在本软件中的用途 |
|----------------------------------------------------------------------------------------------------------------------------------------|----------|
| [Base64](https://github.com/apache/tomcat)                                                                                             | 加解密      |

### 复制的代码
原封不动的：
- cn.gdrfgdrf.ConnectComputerServer.Utils.FileUtils#fileNameToBaseName
    - Apache Common Compress 的 org.apache.commons.compress.utils.FileNameUtils#fileNameToBaseName

- cn.gdrfgdrf.ConnectComputerServer.Utils.FileUtils#fileNameToExtension
    - Apache Common Compress 的 org.apache.commons.compress.utils.FileNameUtils#fileNameToExtension

- cn.gdrfgdrf.ConnectComputerServer.Utils.FileUtils#getBaseName
    - Apache Common Compress 的 org.apache.commons.compress.utils.FileNameUtils#getBaseName(java.lang.String)

- cn.gdrfgdrf.ConnectComputerServer.Utils.FileUtils#getExtension
    - Apache Common Compress 的 org.apache.commons.compress.utils.FileNameUtils#getExtension(java.lang.String)

免责声明
-------------------------
我们不对本软件以及本软件的修改版所造成的后果承担责任。

