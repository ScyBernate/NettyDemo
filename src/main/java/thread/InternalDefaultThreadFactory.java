package thread;

import io.netty.util.concurrent.DefaultThreadFactory;

public class InternalDefaultThreadFactory extends DefaultThreadFactory {

    public InternalDefaultThreadFactory(String poolName) {
        super(poolName);
    }

}
