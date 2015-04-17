# TCP-long-connection-based-on-Apache-mina
**基于Apache mina 的tcp长连接实现,可用于android客户端推送。**

项目将Apache的mina项目移植到了android平台。实现长连接的主要思想是使用了mina的KeepAliveFilter过滤器。
    acceptor.getFilterChain().addLast("keeplive", new KeepAliveFilter(new ServerKeepAliveMessageFactoryImp(), IdleStatus.READER_IDLE, KeepAliveRequestTimeoutHandler.CLOSE,10, 5));


Android客户端：

核心代码如下图所示，规定了长连接ping与pong信息的规则，以及网络参数配置信息。

![](https://github.com/sddyljsx/Android-tcp-long-connection-based-on-Apache-mina/blob/master/002.png?raw=true)

配置信息：

    /**
     * 服务器地址
     */
    public static final String HOSTNAME = "192.168.1.15";
    /**
     * 服务器端口号
     */
    public static final int PORT = 8081;
    /**
     * 连接超时时间，30 seconds
     */
    public static final long SOCKET_CONNECT_TIMEOUT = 30 * 1000L;
    /**
     * 长连接心跳包发送频率，10 seconds
     */
    public static final int KEEP_ALIVE_TIME_INTERVAL = 10;
    /**
     * 长连接心跳包应答超时
     */
    public static final int KEEP_ALIVE_RESPONSE_TIMEOUT = 5;
    /**
     * 心跳包 ping message
     */
    public static final String PING_MESSAGE="ping";
    /**
     * 心跳包 pong message
     */
    public static final String PONG_MESSAGE="pong";
程序启动后，PushService启动，并开始与服务器连接。

**服务器端核心代码：**

![](https://github.com/sddyljsx/Android-tcp-long-connection-based-on-Apache-mina/blob/master/003.png?raw=true)

服务器与客户端的ping与pong信息要保持一致。服务器启动LongTcpServer即可。

在服务器可以看到日志信息：

![](https://github.com/sddyljsx/Android-tcp-long-connection-based-on-Apache-mina/blob/master/001.png?raw=true)

可以看到，成功建立了链接，并且每隔10秒都会受到ping信息，并发送pong信息应答。


