# TCP-long-connection-based-on-Apache-mina
**基于Apache mina 的tcp长连接实现,可用于android客户端推送。**

项目将Apache的mina项目移植到了android平台。

Android客户端：

核心代码如下图所示，规定了长连接ping与pong信息的规则。

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
