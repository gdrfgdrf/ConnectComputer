package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.Synchronization;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

/**
 * @author gdrfgdrf
 */
public class SyncFuture<T> implements Future<T> {
    private final CountDownLatch latch = new CountDownLatch(1);
    private T result;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return result != null;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        latch.await();
        return this.result;
    }

    @Override
    public T get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (latch.await(timeout, unit)) {
            return this.result;
        }
        return null;
    }

    public void setResult(T result) {
        this.result = result;
        latch.countDown();
    }
}
