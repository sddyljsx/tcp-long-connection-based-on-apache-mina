package neal.minapush.push;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import neal.minapush.util.NetworkUtil;

/**
 * Created by neal on 2014/12/2.
 */
public class PushManager {

    private static PushManager pushManager;

    private NioSocketConnector connector;

    private ConnectFuture connectFuture;

    private IoSession session;

    private ExecutorService executorService=Executors.newSingleThreadExecutor();


    public PushManager() {

    }

    public static PushManager getInstance() {
        if (pushManager == null) {
            pushManager = new PushManager();
        }
        return pushManager;
    }

    public void setPushEventListener(PushEventListener pushEventListener){
        if(connector!=null && connector.getHandler()!=null){
            if(connector.getHandler() instanceof  ClientSessionHandler) {
                ((ClientSessionHandler) connector.getHandler()).setPushEventListener(pushEventListener);
            }
        }

    }

    public void openPush(){
        if(connector!=null){
            return;
        }
        connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(Config.SOCKET_CONNECT_TIMEOUT);
        connector.setHandler(new ClientSessionHandler());
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        connector.getFilterChain().addLast("keepalive", new KeepAliveFilter
                (new ClientKeepAliveMessageFactoryImp(), IdleStatus.READER_IDLE,
                        KeepAliveRequestTimeoutHandler.DEAF_SPEAKER, Config.KEEP_ALIVE_TIME_INTERVAL, Config.KEEP_ALIVE_RESPONSE_TIMEOUT));
    }

    /**
     *  开始连接
     * @return
     */
    public boolean Connect() {
        if(!NetworkUtil.isNetworkConnect()|| connector==null){
            return false;
        }
        if(connector!=null&&connector.isActive()&&connectFuture!=null&&connectFuture.isConnected()&&session!=null&& session.isConnected() )
        {
            return true;
        }
        FutureTask<Boolean> futureTask = new FutureTask<Boolean>(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                try {
                    connectFuture = connector.connect(new InetSocketAddress(
                            Config.HOSTNAME, Config.PORT));
                    connectFuture.awaitUninterruptibly();
                    session = connectFuture.getSession();
                    System.out.println("manager connect"+android.os.Process.myPid()+'-'+android.os.Process.myTid());
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
        });

        executorService.submit(futureTask);
        try {
            return futureTask.get();
        } catch (Exception e) {
            return false;
        }

    }



    public boolean sendMessage(ClientPushMessage clientPushMessage){
        if(session==null|| !session.isConnected()){
            return  false;
        }
        WriteFuture writeFuture=session.write(clientPushMessage);
        if(writeFuture==null){
            return false;
        }
        writeFuture.awaitUninterruptibly();
        if(writeFuture.isWritten()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 关闭连接
     */
    public void disConnect(){
        if(session!=null && session.isConnected()){
            session.close(false);
        }
        if(connectFuture!=null && connectFuture.isConnected()) {
            connectFuture.cancel();
        }
        if(connector!=null && !connector.isDisposed()) {
            connector.dispose();
        }
    }


}
