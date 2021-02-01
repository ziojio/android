package zhuj.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Factory and utility methods for {@link java.util.concurrent.Executor}, {@link ExecutorService},
 * and {@link ThreadFactory}.
 *
 * @author Eric Fellheimer
 * @author Kyle Littlefield
 * @author Justin Mahoney
 * @since 3.0
 */
public final class MoreExecutors {
    private MoreExecutors() {
    }

    /**
     * Add a shutdown hook to wait for thread completion in the given {@link ExecutorService service}.
     * This is useful if the given service uses daemon threads, and we want to keep the JVM from
     * exiting immediately on shutdown, instead giving these daemon threads a chance to terminate
     * normally.
     *
     * @param service            ExecutorService which uses daemon threads
     * @param terminationTimeout how long to wait for the executor to finish before terminating the
     *                           JVM
     * @param timeUnit           unit of time for the time parameter
     */
    public static void addDelayedShutdownHook(ExecutorService service, long terminationTimeout, TimeUnit timeUnit) {
        new Application().addDelayedShutdownHook(service, terminationTimeout, timeUnit);
    }

    /**
     * Represents the current application to register shutdown hooks.
     */
    static class Application {
        final void addDelayedShutdownHook(final ExecutorService service, final long terminationTimeout, final TimeUnit timeUnit) {
            Objects.requireNonNull(service);
            Objects.requireNonNull(timeUnit);
            addShutdownHook(MoreExecutors.newThread("DelayedShutdownHook-for-" + service, new Runnable() {
                @Override
                public void run() {
                    try {
                        // We'd like to log progress and failures that may arise in the
                        // following code, but unfortunately the behavior of logging
                        // is undefined in shutdown hooks.
                        // This is because the logging code installs a shutdown hook of its
                        // own. See Cleaner class inside {@link LogManager}.
                        service.shutdown();
                        service.awaitTermination(terminationTimeout, timeUnit);
                    } catch (InterruptedException ignored) {
                        // We're shutting down anyway, so just ignore.
                    }
                }
            }));
        }

        void addShutdownHook(Thread hook) {
            Runtime.getRuntime().addShutdownHook(hook);
        }
    }

    static Thread newThread(String name, Runnable runnable) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(runnable);
        Thread result = platformThreadFactory().newThread(runnable);
        try {
            result.setName(name);
        } catch (SecurityException e) {
            // OK if we can't set the name in this environment.
        }
        return result;
    }

    public static ThreadFactory platformThreadFactory() {
        if (!isAppEngine()) {
            return Executors.defaultThreadFactory();
        }
        try {
            return (ThreadFactory) Class.forName("com.google.appengine.api.ThreadManager")
                    .getMethod("currentRequestThreadFactory")
                    .invoke(null);
        } catch (IllegalAccessException | NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private static boolean isAppEngine() {
        if (System.getProperty("com.google.appengine.runtime.environment") == null) {
            return false;
        }
        try {
            // If the current environment is null, we're not inside AppEngine.
            return Class.forName("com.google.apphosting.api.ApiProxy")
                    .getMethod("getCurrentEnvironment")
                    .invoke(null)
                    != null;
        } catch (ClassNotFoundException e) {
            // If ApiProxy doesn't exist, we're not on AppEngine at all.
            return false;
        } catch (InvocationTargetException e) {
            // If ApiProxy throws an exception, we're not in a proper AppEngine environment.
            return false;
        } catch (IllegalAccessException e) {
            // If the method isn't accessible, we're not on a supported version of AppEngine;
            return false;
        } catch (NoSuchMethodException e) {
            // If the method doesn't exist, we're not on a supported version of AppEngine;
            return false;
        }
    }

    /**
     * <p>This instance is equivalent to: <pre>   {@code
     *   final class DirectExecutor implements Executor {
     *     public void execute(Runnable r) {
     *       r.run();
     *     }
     *   }}</pre>
     * <p>
     * implementing the {@link ExecutorService} subinterface necessitates significant performance overhead.
     *
     * @since 18.0
     */
    public static Executor directExecutor() {
        return DirectExecutor.INSTANCE;
    }

    /**
     * See {@link #directExecutor} for behavioral notes.
     */
    private enum DirectExecutor implements Executor {
        INSTANCE;

        @Override
        public void execute(Runnable command) {
            command.run();
        }

        @Override
        public String toString() {
            return "MoreExecutors.directExecutor()";
        }
    }


    // concurrency
    public static boolean shutdownAndAwaitTermination(
            ExecutorService service, long timeout, TimeUnit unit) {
        long halfTimeoutNanos = unit.toNanos(timeout) / 2;
        // Disable new tasks from being submitted
        service.shutdown();
        try {
            // Wait for half the duration of the timeout for existing tasks to terminate
            if (!service.awaitTermination(halfTimeoutNanos, TimeUnit.NANOSECONDS)) {
                // Cancel currently executing tasks
                service.shutdownNow();
                // Wait the other half of the timeout for tasks to respond to being cancelled
                service.awaitTermination(halfTimeoutNanos, TimeUnit.NANOSECONDS);
            }
        } catch (InterruptedException ie) {
            // Preserve interrupt status
            Thread.currentThread().interrupt();
            // (Re-)Cancel if current thread also interrupted
            service.shutdownNow();
        }
        return service.isTerminated();
    }

}
