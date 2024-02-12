ConnectComputer | Computer
===
[English](https://github.com/gdrfgdrf/ConnectComputer/blob/Computer/README.md) | __[简体中文](https://github.com/gdrfgdrf/ConnectComputer/blob/Computer/README_ChineseSimplified.md)__
- ConnectComputer 是一款可以控制远程设备的软件。
- 该分支存储了 *__控制端__* 和 *__被控端__*。

<!-- TOC -->
* [ConnectComputer | Computer](#connectcomputer--computer)
  * [安装](#安装)
  * [使用](#使用)
    * [控制端](#控制端)
    * [被控端](#被控端)
  * [软件运行参数](#软件运行参数)
    * [例子](#例子)
  * [JVM 运行参数](#jvm-运行参数)
    * [例子](#例子-1)
  * [运行时生成文件](#运行时生成文件)
  * [依赖](#依赖)
    * [直接复制进项目的依赖](#直接复制进项目的依赖)
    * [复制的代码](#复制的代码)
  * [免责声明](#免责声明)
<!-- TOC -->

安装
-------------------------

- 从Release页面下载Jar包。
- 使用 Java 17 运行。

使用
-------------------------
首先，您需要找到服务器提供商，  
您必须确保提供商提供的服务器是安全的，  
服务器最好采用了 SSL 等的安全等措施，  
对于因服务器安全问题造成的任何损失，我们概不负责。

### 控制端
- 成功登录到服务器之后，会显示您当前账户下已注册到服务器的设备列表，类似如下表格。

```text
+------+-----------------+----------+
| 索引 | 名称             |  是否在线 |
+------+-----------------+----------+
| 0    | Computer 1      |       否 |
| 1    | Computer 2      |       是 |
| ...  |       ...       |      ... |
+------+-----------------+----------+
```

- 之后会要求您输入需要控制的设备的索引，就比如上表的 0 或 1。
- 输入索引之后回车，会显示类似如下的内容。

```text
+------------+----------+
| 名称       |   是否在线 |
+------------+----------+
| Computer 1 |       否 |
+------------+----------+

请输入需要进行的操作  
0: 返回上级菜单  
1: 控制设备  
```

- 输入 0 则返回到设备列表。
- 输入 1 则会尝试连接该设备，连接成功后会出现远程终端窗口，该终端实际运行在被控端上，对该远程终端的操作不会影响到控制端。
  - 关闭远程终端后，被控端的实际终端也会跟着关闭，下次被连接时则会打开新的实际终端。
- 远程终端或实际终端被关闭都会终止连接。

### 被控端
- 成功登录到服务器之后，将会进入等待连接阶段直到控制端连接。
- 与控制端连接成功之后，将会开启一个实际终端，该终端窗口不可见，控制端在远程终端的操作都会反映到该实际终端。
- 远程终端或实际终端被关闭都会终止连接。

软件运行参数
-------------------------
软件运行参数会在运行时进行校验。

| 参数名        | 类型      | 默认值   | 描述                                      |
|------------|---------|-------|-----------------------------------------|
| ssl        | Boolean | 无     | 服务器是否使用 SSL                             |
| serverIp   | String  | 无     | 服务器地址                                   |
| port       | Integer | 无     | 服务器端口（0 - 65535）                        |
| username   | String  | 无     | 登录服务器时使用的用户名                            |
| password   | String  | 无     | 登录服务器时使用的密码                             |
| autoLogin  | Boolean | 无     | 自动登录（存储账号密码）                            |
| controller | Boolean | 无     | true 则作为控制端，false 则作为被控端                |
| silent     | Boolean | false | 是否启用静默模式，默认关闭，若启用则会在发送 Http 登录请求失败时一直尝试 |

### 例子
    java --add-opens=java.base/java.lang=ALL-UNNAMED -jar ConnectComputerComputer.jar ssl=true serverIp=1.2.3.4 port=1234 username=Hello password=Password123456 autoLogin=true controller=true
- 启用 SSL 去连接服务器 1.2.3.4 的 1234 端口  
- 用户名 Hello 
- 密码 Password123456 
- 启用自动登录 
- 作为控制端

JVM 运行参数
-------------------------
我们使用了 Cglib 作为动态代理，所以请在 -jar 前加上：

    --add-opens=java.base/java.lang=ALL-UNNAMED

### 例子
    java --add-opens=java.base/java.lang=ALL-UNNAMED -jar ConnectComputerComputer.jar ...

运行时生成文件
-------------------------
| 文件名                    | 描述                        |
|------------------------|---------------------------|
| RSA.json               | 存储 Http、Netty 通讯时的 RSA 密钥 |
| ServerInfo.json        | 存储服务器地址，端口，是否启用 SSL       |
| ComputerData.json      | 存储被控端设备数据                 |
| Account.json           | 存储用户名，密码，是否启用自动登录，是否作为控制端 |
| ChineseSimplified.json | 简体中文                      |

依赖
-------------------------

| 依赖名                                                                   | 在本软件中的用途           |
|-----------------------------------------------------------------------|--------------------|
| [Reflections](https://github.com/ronmamo/reflections)                 | Netty 数据包查询        |
| [Hutool](https://github.com/dromara/hutool)                           | 异步执行               |
| [Okhttp](https://github.com/square/okhttp)                            | Http               |
| [Pty4J](https://github.com/JetBrains/pty4j)                           | 模拟终端               |
| [JediTerm](https://github.com/JetBrains/jediterm)                     | 模拟终端               |
| [Lombok](https://github.com/projectlombok/lombok)                     | 代码简化               |
| [Jackson Core](https://github.com/FasterXML/jackson-core)             | Json 序列化核心         |
| [Jackson Databind](https://github.com/FasterXML/jackson-databind)     | Json 序列化数据绑定       |
| [Apache Commons Lang](https://github.com/apache/commons-lang)         | Pair、字符操作          |
| [Protocol Buffers](https://github.com/protocolbuffers/protobuf)       | Netty 数据包          |
| [Netty](https://github.com/netty/netty)                               | 长连接通讯              |
| [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)     | Http 协程异步          |
| [Kotlin Coroutines Jvm](https://github.com/Kotlin/kotlinx.coroutines) | Kotlin 协程的 Java 支持 |
| [Kotlin Stdlib Jdk8](https://github.com/JetBrains/kotlin)             | Kotlin 标准库         |
| [SLF4J](https://github.com/qos-ch/slf4j)                              | 日志框架               |
| [Logback](https://github.com/qos-ch/logback)                          | 日志框架 SLF4J 的实现     |

### 直接复制进项目的依赖

| 依赖名                                                                                                                                    | 在本软件中的用途 |
|----------------------------------------------------------------------------------------------------------------------------------------|----------|
| [ConsoleTable](https://github.com/clyoudu/clyoudu-util)                                                                                | 输出表格     |
| [Base64](https://github.com/apache/tomcat)                                                                                             | 加解密      |
| [Spring 修改过的 Asm](https://github.com/spring-projects/spring-framework/tree/main/spring-core/src/main/java/org/springframework/asm)     | 动态代理     |
| [Spring 修改过的 Cglib](https://github.com/spring-projects/spring-framework/tree/main/spring-core/src/main/java/org/springframework/cglib) | 动态代理     |

### 复制的代码
原封不动的：
- cn.gdrfgdrf.ConnectComputerComputer.Terminal.LocalTerminal#createTtyConnector
  - JediTerm 的 com.jediterm.example.BasicTerminalShellExample#createTtyConnector

- cn.gdrfgdrf.ConnectComputerComputer.Utils.Platform
  - JediTerm 的 com.jediterm.core.Platform

改动过的：
- cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal#createTerminalWidget
  - JediTerm 的 com.jediterm.example.BasicTerminalShellExample#createTerminalWidget

- cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal#create
  - JediTerm 的 com.jediterm.example.BasicTerminalShellExample#createAndShowGUI

免责声明
-------------------------
我们不对本软件以及本软件的修改版所造成的后果承担责任。
