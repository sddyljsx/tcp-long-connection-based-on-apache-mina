package neal.minapush.push;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by neal on 2014/12/2.
 */
public class ClientSessionHandler extends IoHandlerAdapter {
    PushEventListener pushEventListener;
    public void setPushEventListener(PushEventListener pushEventListener){
        this.pushEventListener=pushEventListener;
    }
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        if(pushEventListener!=null){
            pushEventListener.onPushConnected();
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        if(pushEventListener!=null){
            pushEventListener.onPushDisConnected();
        }
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        if(pushEventListener!=null){
            pushEventListener.onPushExceptionCaught();
        }
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        if(pushEventListener!=null){
            pushEventListener.onPushMessageReceived();
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        if(pushEventListener!=null){
            pushEventListener.onPushMessageSent();
        }
    }

    @Override
    public void inputClosed(IoSession session) throws Exception {
        super.inputClosed(session);
    }
}
