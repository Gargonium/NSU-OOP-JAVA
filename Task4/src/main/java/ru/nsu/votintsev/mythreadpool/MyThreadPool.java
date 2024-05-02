package ru.nsu.votintsev.mythreadpool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadPool implements ExecutorService {

    private final BlockingQueue<Runnable> taskQueue;
    private final int maxThreads;
    private final AtomicBoolean isShutdown = new AtomicBoolean(false);
    private final AtomicInteger tasksCounter = new AtomicInteger(0);

    private final List<WorkerThread> tasks = new ArrayList<>();

    public MyThreadPool(int maxThreads) {
        this(new ArrayBlockingQueue<>(maxThreads), maxThreads);
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

        try {
            taskQueue.put(command);
            if (tasksCounter.get() < maxThreads) {
                addNewThread();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute() {
        if (isShutdown.get()) {
            throw new RejectedExecutionException("ThreadPool is shutdown");
        }
        while (!taskQueue.isEmpty() || tasksCounter.get() < maxThreads) {
            addNewThread();
        }
    }

    private void addNewThread() {
        WorkerThread thread = new WorkerThread();
        tasks.add(thread);
        thread.start();
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            tasksCounter.incrementAndGet();
            while (!isShutdown.get() || !taskQueue.isEmpty()) {
                Runnable task = taskQueue.poll();
                if (task != null)
                    task.run();
            }
            tasksCounter.decrementAndGet();
            tasks.remove(this);
        }
    }

    @Override
    public void shutdown() {
        isShutdown.set(true);
        synchronized (this) {
            notify();
        }
    }

    @Override
    public List<Runnable> shutdownNow() {
        isShutdown.set(true);
        synchronized (this) {
            for (WorkerThread workerThread : tasks) {
                workerThread.interrupt();
            }
            notify();
        }
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

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long endTime = System.currentTimeMillis() + unit.toMillis(timeout);
        synchronized (this) {
            while (!isTerminated() && System.currentTimeMillis() < endTime) {
                long remainingTime = endTime - System.currentTimeMillis();
                if (remainingTime <= 0) {
                    break;
                }
                wait(remainingTime);
            }
        }
        return isTerminated();
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        FutureTask<T> futureTask = new FutureTask<>(task);
        execute(futureTask);
        return futureTask;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        FutureTask<T> futureTask = new FutureTask<>(task, result);
        execute(futureTask);
        return futureTask;
    }

    @Override
    public Future<?> submit(Runnable task) {
        FutureTask<?> futureTask = new FutureTask<>(task, null);
        execute(futureTask);
        return futureTask;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) {
        List<Future<T>> futures = new ArrayList<>();
        for (Callable<T> task : tasks) {
            futures.add(submit(task));
        }
        return futures;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        long startTime = System.currentTimeMillis();
        List<Future<T>> futures = new ArrayList<>();
        for (Callable<T> task : tasks) {
            long timeLeft = startTime + unit.toMillis(timeout) - System.currentTimeMillis();
            if (timeLeft <= 0) {
                return futures;
            }
            futures.add(submit(task));
        }
        return futures;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        List<Future<T>> futures = invokeAll(tasks);
        for (Future<T> future : futures) {
            try {
                return future.get();
            } catch (ExecutionException e) {
                //
            }
        }
        throw new ExecutionException("All tasks failed", null);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        long startTime = System.currentTimeMillis();
        List<Future<T>> futures = invokeAll(tasks, timeout, unit);
        long remainingTime = startTime + unit.toMillis(timeout) - System.currentTimeMillis();
        for (Future<T> future : futures) {
            try {
                return future.get(remainingTime, TimeUnit.MILLISECONDS);
            } catch (ExecutionException e) {
                //
            } catch (TimeoutException e) {
                remainingTime = startTime + unit.toMillis(timeout) - System.currentTimeMillis();
                if (remainingTime <= 0) {
                    throw new TimeoutException("Timed out waiting for any task to complete");
                }
            }
        }
        throw new ExecutionException("All tasks failed", null);
    }
}
