package org.apache.mina.test;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;

public class LongTcpServer {
	private static final int SERVER_PORT = 8081;

    public static void main(String[] args) throws Throwable {
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        //acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new ObjectSerializationCodecFactory()));
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("keeplive", new KeepAliveFilter(new ServerKeepAliveMessageFactoryImp(), IdleStatus.READER_IDLE, KeepAliveRequestTimeoutHandler.CLOSE,10, 5));

        acceptor.setHandler(new ServerSessionHandler());
        acceptor.bind(new InetSocketAddress("192.168.1.15",SERVER_PORT));
        System.out.println("Listening on port " + SERVER_PORT);
    }

}
