package neal.minapush.push;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class ClientKeepAliveMessageFactoryImp implements KeepAliveMessageFactory{

	@Override
	public boolean isRequest(IoSession session, Object message) {
		if(message instanceof String && message.equals(Config.PING_MESSAGE)){
			return true;
		}
		return false;
	}

	@Override
	public boolean isResponse(IoSession session, Object message) {
		if(message instanceof String && message.equals(Config.PONG_MESSAGE)){
			return true;
		}
		return false;
	}

	@Override
	public Object getRequest(IoSession session) {
		return Config.PING_MESSAGE;
	}

	@Override
	public Object getResponse(IoSession session, Object request) {
		return null;
	}

}