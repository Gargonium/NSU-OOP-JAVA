package ru.nsu.votintsev.mythreadpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyThreadPool implements ExecutorService {

    private final BlockingQueue<Runnable> taskQueue;
    private final int maxThreads;
    private final AtomicBoolean isShutdown = new AtomicBoolean(false);

    public MyThreadPool(int maxThreads) {
        this.maxThreads = maxThreads;
        taskQueue = new ArrayBlockingQueue<>(maxThreads);
    }

    public MyThreadPool(BlockingQueue<Runnable> taskQueue, int maxThreads) {
        this.maxThreads = maxThreads;
        this.taskQueue = taskQueue;
    }

    @Override
    public void execute(Runnable command) {
        if (isShutdown.get()) {
            throw new RejectedExecutionException("ThreadPool is shutdown");
        }

        if (taskQueue.size() < maxThreads) {
            try {
                taskQueue.put(command);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            startTask();
        } else {
            throw new RejectedExecutionException("Thread limit reached");
        }
    }

    private void startTask() {
        Thread thread = new Thread(() -> {
            while (!isShutdown.get() || !taskQueue.isEmpty()) {
                Runnable task = taskQueue.poll();
                if (task != null) {
                    task.run();
                }
            }
        });
        thread.start();
    }


    @Override
    public void shutdown() {
        isShutdown.set(true);
    }

    @Override
    public List<Runnable> shutdownNow() {
        isShutdown.set(true);
        List<Runnable> tasks = new ArrayList<>();
        taskQueue.drainTo(tasks);
        return tasks;
    }

    @Override
    public boolean isShutdown() {
        return isShutdown.get();
    }

    @Override
    public boolean isTerminated() {
        return isShutdown.get() && taskQueue.isEmpty();
    }
    /*

    Ниже пока нереализованные методы

     */

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return null;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return null;
    }

    @Override
    public Future<?> submit(Runnable task) {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
