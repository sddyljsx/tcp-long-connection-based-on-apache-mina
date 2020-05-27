package org.apache.mina.test;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class KeepAliveMessageFactoryImp implements KeepAliveMessageFactory {

    @Override
    public boolean isRequest(IoSession session, Object message) {
        // TODO Auto-generated method stub
        if (message instanceof String && message.equals("ping")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isResponse(IoSession session, Object message) {
        // TODO Auto-generated method stub
        if (message instanceof String && message.equals("pong")) {
            return true;
        }
        return false;
    }

    @Override
    public Object getRequest(IoSession session) {
        // TODO Auto-generated method stub
        return "ping";
    }

    @Override
    public Object getResponse(IoSession session, Object request) {
        // TODO Auto-generated method stub
        return "pong";
    }

}
