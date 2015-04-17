package neal.minapush.push;

/**
 * Created by neal on 2014/12/7.
 */
public interface PushEventListener {
    public abstract void onPushConnected();
    public abstract void onPushExceptionCaught();
    public abstract void onPushMessageSent();
    public abstract void onPushMessageReceived();
    public abstract void onPushDisConnected();
}
