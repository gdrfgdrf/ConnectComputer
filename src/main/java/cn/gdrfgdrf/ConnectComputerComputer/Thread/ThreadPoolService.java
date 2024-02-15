package cn.gdrfgdrf.ConnectComputerComputer.Thread;

import java.util.concurrent.*;

/**
 * @author gdrfgdrf
 */
public class ThreadPoolService {
    private ThreadPoolService() {}

    private static final ThreadFactory THREAD_FACTORY = new NamedThreadFactory();
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            4,
            40,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024),
            THREAD_FACTORY,
            new ThreadPoolExecutor.AbortPolicy()
    );

    public static void newTask(Runnable runnable) {
        EXECUTOR_SERVICE.execute(runnable);
    }

}
