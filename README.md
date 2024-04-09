# [译文]使用Java搭建FTP服务器

> 原文链接：https://mina.apache.org/ftpserver-project/embedding_ftpserver.html
>
> 本文是根据原文直接翻译过来。真正使用下面的代码是执行不了的。如果要查看可执行的代码，请看我根据此文档做的一个demo工程。https://gitee.com/olencc/FtpServer

FtpServer是为了轻松嵌入到你的系统中而设计的，启动和运行基本服务器非常简单，就像下面这样

```java
FtpServerFactory serverFactory = new FtpServerFactory();
// 启动服务
FtpServer server = serverFactory.createServer();
server.start();
```

想要让上面的代码运行，你需要将以下的jar包引入到你的工程中

- mina-core, 2.0-M3 or later
- slf4j-api
- A SLF4J implementation of your choice, for example slf4j-simple-1.5.3.jar
- ftplet-api
- ftpserver-core

现在，您可能希望根据您的特定需求配置服务器。例如，您可能希望在非特权端口上运行，以便在Linux / Unix上以root身份运行。为此，您需要配置一个监听器（listener）。监听器是FtpServer的一部分，用于完成网络管理。默认情况下，会创建名为“default”的监听器，但您可以根据需要添加任意数量的监听器，例如，您可以提供一个在防火墙外部使用的监听器，一个在内部使用的监听器。

现在，让我们配置默认监听器等待连接的端口。

```java
FtpServerFactory serverFactory = new FtpServerFactory();
ListenerFactory factory = new ListenerFactory();
// 设置监听器端口
factory.setPort(2221);
// 替换默认监听器
serverFactory.addListener("default", factory.createListener());
// 启动服务
FtpServer server = serverFactory.createServer();         
server.start();
```

现在，让我们使客户端可以使用FTPS（FTP 使用 SSL进行通讯）作为默认监听器。

```java
FtpServerFactory serverFactory = new FtpServerFactory();
ListenerFactory factory = new ListenerFactory();
// 设置监听器端口
factory.setPort(2221);
// 定义一个ssl配置
SslConfigurationFactory ssl = new SslConfigurationFactory();
ssl.setKeystoreFile(new File("src/test/resources/ftpserver.jks"));
ssl.setKeystorePassword("password");
// 将ssl配置添加到监听器中
factory.setSslConfiguration(ssl.createSslConfiguration());
factory.setImplicitSsl(true);
// 替换默认的监听器
serverFactory.addListener("default", factory.createListener());
// 设置用户信息
PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
userManagerFactory.setFile(new File("myusers.properties"));
serverFactory.setUserManager(userManagerFactory.createUserManager());
// 启动服务
FtpServer server = serverFactory.createServer(); 
server.start();
```

到这里就差不多了，这是你通常需要的基础知识。有关更多高级功能，请查看我们的配置文档。