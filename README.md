ConnectComputer | Server
===
__[English](https://github.com/gdrfgdrf/ConnectComputer/blob/Server/README.md)__ | [简体中文](https://github.com/gdrfgdrf/ConnectComputer/blob/Server/README_ChineseSimplified.md)
- ConnectComputer is a piece of software that can control remote devices.
- This branch stores the *__Server__*.

<!-- TOC -->
* [ConnectComputer | Server](#connectcomputer--server)
  * [Setup](#setup)
  * [Usage](#usage)
  * [Generate Files At Run Time](#generate-files-at-run-time)
  * [Dependencies](#dependencies)
    * [Dependencies that directly copy into the project](#dependencies-that-directly-copy-into-the-project)
    * [Copied code](#copied-code)
  * [Disclaimer](#disclaimer)
<!-- TOC -->

Setup
-------------------------
- Deployment database.
- Deployment Redis.
- Download from the Release page.
- Run with Java 17 to create the configuration file.
- Change the "url", "username", "password", "driver-class-name"  
of spring.datasource in the application.yml file.
- Change the "host", "port", "password" of spring.redis  
in the file application.yml to connect to the Redis cache.
- Modify application.yml as needed.

If the "language" in application.yml is "zh_cn.json",  
then a zh_cn.json will be created after running  
if zh_cn.json is not found.

Usage
-------------------------
Make sure your server is secure,  
Http server with RSA encryption and decryption,  
Netty server with RSA and AES encryption and decryption,  
But the data they transmit is still at risk of being hacked,  
If you are the provider of the server, please put in place security measures such as SSL,  
We are not responsible for any loss caused by server security issues.

- Start the server using Java 17.
- Publish the ip or domain name of your server to the user.

Generate Files At Run Time
-------------------------
| File Name                 | Description                                                           |
|---------------------------|-----------------------------------------------------------------------|
| application.yml           | The SpringBoot configuration file and the software configuration file |
| zh_cn.json                | Simplified Chinese                                                    |
| avatar/default_avatar.jpg | Default avatar                                                        |

Dependencies
-------------------------
| Name                                                                                                                         | Use in this software                |
|------------------------------------------------------------------------------------------------------------------------------|-------------------------------------|
| [Reflections](https://github.com/ronmamo/reflections)                                                                        | Netty packet query                  |
| [Lombok](https://github.com/projectlombok/lombok)                                                                            | Code simplification                 |
| [FASTJSON](https://github.com/alibaba/fastjson2)                                                                             | JSON serialization                  |
| [Apache Commons IO](https://github.com/apache/commons-io)                                                                    | IO operation                        |
| [Apache Commons Lang](https://github.com/apache/commons-lang)                                                                | Array, string operation             |
| [Java JWT](https://github.com/jwtk/jjwt)                                                                                     | Token generation and verification   |
| [Protocol Buffers](https://github.com/protocolbuffers/protobuf)                                                              | Netty packet                        |
| [MySQL Connector/J](https://github.com/mysql/mysql-connector-j)                                                              | MySQL Database Connector            |
| [MyBatis-Plus](https://github.com/baomidou/mybatis-plus)                                                                     | POJO code simplification            |
| [MPRelation](https://github.com/dreamyoung/mprelation)                                                                       | POJO one-to-many, many-to-one query |
| [Netty](https://github.com/netty/netty)                                                                                      | Long connection                     |
| [Spring Boot Starter Validation](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation) | Http parameter validator            |
| [Spring Boot Starter Data Redis](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis) | Http cache                          |
| [Spring Boot Starter Web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)               | Back-end                            |
| [Spring Boot Starter AOP](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-aop)               | Dynamic proxy                       |
| [Spring Boot Starter Logging](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-logging)       | Logback Log framework               |
| [Spring Boot Starter Log4j2](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-log4j2)         | Log4j2 Log framework                |
| [JUnit 5](https://github.com/junit-team/junit5)                                                                              | Unit test                           |

### Dependencies that directly copy into the project

| Name                                                                                                                                       | Use in this software      |
|--------------------------------------------------------------------------------------------------------------------------------------------|---------------------------|
| [Base64](https://github.com/apache/tomcat)                                                                                                 | Encryption and decryption |

### Copied code
Intact：
- cn.gdrfgdrf.ConnectComputerServer.Utils.FileUtils#fileNameToBaseName
    - org.apache.commons.compress.utils.FileNameUtils#fileNameToBaseName of Apache Common Compress

- cn.gdrfgdrf.ConnectComputerServer.Utils.FileUtils#fileNameToExtension
    - org.apache.commons.compress.utils.FileNameUtils#fileNameToExtension of Apache Common Compress

- cn.gdrfgdrf.ConnectComputerServer.Utils.FileUtils#getBaseName
    - org.apache.commons.compress.utils.FileNameUtils#getBaseName(java.lang.String) of Apache Common Compress

- cn.gdrfgdrf.ConnectComputerServer.Utils.FileUtils#getExtension
    - org.apache.commons.compress.utils.FileNameUtils#getExtension(java.lang.String) of Apache Common Compress

Disclaimer
-------------------------
We are not responsible for the consequences of the Software and its modification.
