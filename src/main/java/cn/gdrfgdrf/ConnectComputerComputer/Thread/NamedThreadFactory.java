package cn.gdrfgdrf.ConnectComputerComputer.Thread;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gdrfgdrf
 */
public class NamedThreadFactory implements ThreadFactory {
    private static final AtomicInteger POOL_ID = new AtomicInteger();
    private final AtomicInteger count = new AtomicInteger();
    private final ThreadGroup group;

    public NamedThreadFactory() {
        POOL_ID.incrementAndGet();
        SecurityManager securityManager = System.getSecurityManager();
        group = securityManager != null ?
                securityManager.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread result = new Thread(group, r);
        result.setName("Pool-" + POOL_ID.incrementAndGet() + " Thread-" + count.incrementAndGet());
        return result;
    }
}
