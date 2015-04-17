package org.apache.mina.test;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class LongTcpClient {
	private static final String HOSTNAME = "192.168.1.15";

    private static final int PORT = 8081;

    private static final long CONNECT_TIMEOUT = 30*1000L; // 30 seconds


    public static void main(String[] args) throws Throwable {
    
        NioSocketConnector connector = new NioSocketConnector();
        
        // Configure the service.
        connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
        connector.setHandler(new ClientSessionHandler());
        //connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
        connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new ObjectSerializationCodecFactory()));
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("keeplive", new KeepAliveFilter(new ClientKeepAliveMessageFactoryImp(), IdleStatus.READER_IDLE, KeepAliveRequestTimeoutHandler.DEAF_SPEAKER,10, 5));

            try {
               connector.connect(new InetSocketAddress(
                        HOSTNAME, PORT));
            } catch (RuntimeIoException e) {
                System.err.println("Failed to connect.");
                e.printStackTrace();
               
            }
        }

    

}
