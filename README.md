ConnectComputer | Computer
===
__[English](https://github.com/gdrfgdrf/ConnectComputer/blob/Computer/README.md)__ | [简体中文](https://github.com/gdrfgdrf/ConnectComputer/blob/Computer/README_ChineseSimplified.md)
- ConnectComputer is a piece of software that can control remote devices.
- This branch stores both the *__Controller__* and the *__Remote device__*.

<!-- TOC -->
* [ConnectComputer | Computer](#connectcomputer--computer)
  * [Install](#install)
  * [Usage](#usage)
    * [Controller](#controller)
    * [Remote device](#remote-device)
  * [Parameter](#parameter)
    * [Example](#example)
  * [JVM Parameter](#jvm-parameter)
    * [Example](#example-1)
  * [Generate Files At Run Time](#generate-files-at-run-time)
  * [Dependencies](#dependencies)
    * [Dependencies that directly copy into the project](#dependencies-that-directly-copy-into-the-project)
    * [Copied code](#copied-code)
  * [Disclaimer](#disclaimer)
<!-- TOC -->

Install
-------------------------

- Download from the Release page.
- Run the program in Java 17.

Usage
-------------------------
First, you need to find the server provider,  
You must ensure that the server provided by the provider is secure,  
It is best that the server adopts security measures such as SSL.   
We are not responsible for any loss caused by server security issues.  

### Controller
- After successfully logging in to the server, 
a list of devices registered to the server 
under your current account will be displayed, 
similar to the table below.

```text
+-------+-----------------+---------+
| Index | Name            | Online  |
+-------+-----------------+---------+
| 0     | Computer 1      |   False |
| 1     | Computer 2      |    True |
| ...   |       ...       |     ... |
+-------+-----------------+---------+
```

- Then you need to enter the index of the device you want to control, 
such as 0 or 1 in the table above.
- Enter the index and press enter. 
Information similar to the following is displayed

```text
+------------+----------+
| Name       |   Online |
+------------+----------+
| Computer 1 |    False |
+------------+----------+

Please enter the required action  
0: Back to previous menu  
1: Control  
```

- Enter 0 to return to the device list.
- Enter 1 to try to connect to the device, 
and after successful connection, 
the remote terminal window will appear. 
The terminal actually runs on the remote device, 
and the operation of the remote terminal will not affect the current device.
  - After the remote terminal window is closed, 
  the terminal of the remote device will also be closed, 
  and a new terminal will be opened when the remote device is connected next time.

### Remote device
- After successfully logging in to the server, 
it will enter the wait for connection phase until the controller connects.
- After the connection with the controller is successful, 
an actual terminal will be opened. The terminal window is not visible, 
and the operations of the controller on the remote terminal 
will be reflected in the actual terminal
- If the remote terminal or the actual terminal is closed, the connection is terminated

Parameter
-------------------------
The parameters are verified at run time.

| Parameter  | Type    | Default | Description                                                                                                                                                            |
|------------|---------|---------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ssl        | Boolean | None    | Whether the server uses SSL                                                                                                                                            |
| serverIp   | String  | None    | Server address                                                                                                                                                         |
| port       | Integer | None    | Server port（0 - 65535）                                                                                                                                                 |
| username   | String  | None    | Username used to log in to the server                                                                                                                                  |
| password   | String  | None    | Password for logging in to the server                                                                                                                                  |
| autoLogin  | Boolean | None    | Auto login（Storage account and password）                                                                                                                               |
| controller | Boolean | None    | true is used as the controller, false is used as the remote device                                                                                                     |
| silent     | Boolean | false   | Whether to enable the silent mode. This mode is disabled by default. If this mode is enabled, Http login requests are repeatedly sent until they are successfully sent |

### Example
    java --add-opens=java.base/java.lang=ALL-UNNAMED -jar ConnectComputerComputer.jar ssl=true serverIp=1.2.3.4 port=1234 username=Hello password=Password123456 autoLogin=true controller=true
- Enable SSL to connect to port 1234 of server 1.2.3.4
- The username is Hello
- The password is Password123456
- Enable Auto login
- As controller

JVM Parameter
-------------------------
We used Cglib to implement dynamic proxies, so please precede -jar with the following:

    --add-opens=java.base/java.lang=ALL-UNNAMED

### Example
    java --add-opens=java.base/java.lang=ALL-UNNAMED -jar ConnectComputerComputer.jar ...

Generate Files At Run Time
-------------------------
| File Name              | Description                                                                           |
|------------------------|---------------------------------------------------------------------------------------|
| RSA.json               | Store Http and Netty RSA keys                                                         |
| ServerInfo.json        | Storage server address, port, and whether SSL is enabled                              |
| ComputerData.json      | Storing remote device data                                                            |
| Account.json           | Store username, password, whether auto login is enabled, whether to act as controller |
| ChineseSimplified.json | Simplified Chinese                                                                    |

Dependencies
-------------------------

| Name                                                                  | Use in this software                      |
|-----------------------------------------------------------------------|-------------------------------------------|
| [Okhttp](https://github.com/square/okhttp)                            | Http                                      |
| [Pty4J](https://github.com/JetBrains/pty4j)                           | Pseudo terminal                           |
| [JediTerm](https://github.com/JetBrains/jediterm)                     | Pseudo terminal                           |
| [Lombok](https://github.com/projectlombok/lombok)                     | Code simplification                       |
| [Jackson Core](https://github.com/FasterXML/jackson-core)             | Json Serialization core                   |
| [Jackson Databind](https://github.com/FasterXML/jackson-databind)     | Json data binding                         |
| [Apache Commons Lang](https://github.com/apache/commons-lang)         | Pair, character operation                 |
| [Protocol Buffers](https://github.com/protocolbuffers/protobuf)       | Netty packet                              |
| [Netty](https://github.com/netty/netty)                               | Long connection                           |
| [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)     | Http Coroutine asynchronous               |
| [Kotlin Coroutines Jvm](https://github.com/Kotlin/kotlinx.coroutines) | Java support for Kotlin coroutines        |
| [Kotlin Stdlib Jdk8](https://github.com/JetBrains/kotlin)             | Kotlin standard library                   |
| [SLF4J](https://github.com/qos-ch/slf4j)                              | Log framework                             |
| [Logback](https://github.com/qos-ch/logback)                          | Implementation of logging framework SLF4J |

### Dependencies that directly copy into the project

| Name                                                                                                                                       | Use in this software      |
|--------------------------------------------------------------------------------------------------------------------------------------------|---------------------------|
| [ConsoleTable](https://github.com/clyoudu/clyoudu-util)                                                                                    | Output form text          |
| [Base64](https://github.com/apache/tomcat)                                                                                                 | Encryption and decryption |
| [Spring modified Asm](https://github.com/spring-projects/spring-framework/tree/main/spring-core/src/main/java/org/springframework/asm)     | Dynamic proxy             |
| [Spring modified Cglib](https://github.com/spring-projects/spring-framework/tree/main/spring-core/src/main/java/org/springframework/cglib) | Dynamic proxy             |

### Copied code
Intact:
- cn.gdrfgdrf.ConnectComputerComputer.Terminal.LocalTerminal#createTtyConnector
    - com.jediterm.example.BasicTerminalShellExample#createTtyConnector of JediTerm

- cn.gdrfgdrf.ConnectComputerComputer.Utils.Platform
    - com.jediterm.core.Platform of JediTerm

modified:
- cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal#createTerminalWidget
    - com.jediterm.example.BasicTerminalShellExample#createTerminalWidget of JediTerm

- cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal#create
    - com.jediterm.example.BasicTerminalShellExample#createAndShowGUI of JediTerm

Disclaimer
-------------------------
We are not responsible for the consequences of the Software and its modification.
