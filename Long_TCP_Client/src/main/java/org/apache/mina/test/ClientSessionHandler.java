package org.apache.mina.test;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ClientSessionHandler extends IoHandlerAdapter {

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.sessionOpened(session);
        session.write("hello");
    }


}
