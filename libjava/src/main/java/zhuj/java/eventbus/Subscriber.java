package zhuj.java.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Executor;

import javax.annotation.Nullable;

/**
 * A subscriber method on a specific object, plus the executor that should be used for dispatching
 * events to it.
 *
 * <p>Two subscribers are equivalent when they refer to the same method on the same object (not
 * class). This property is used to ensure that no subscriber method is registered more than once.
 *
 * @author Colin Decker
 */
class Subscriber {

    /**
     * Creates a {@code Subscriber} for {@code method} on {@code listener}.
     */
    static Subscriber create(EventBus bus, Object listener, Method method) {
        return isDeclaredThreadSafe(method)
                ? new Subscriber(bus, listener, method)
                : new SynchronizedSubscriber(bus, listener, method);
    }

    /**
     * The event bus this subscriber belongs to.
     */
    private EventBus bus;

    /**
     * The object with the subscriber method.
     */
    final Object target;

    /**
     * Subscriber method.
     */
    private final Method method;

    /**
     * Executor to use for dispatching events to this subscriber.
     */
    private final Executor executor;

    private Subscriber(EventBus bus, Object target, Method method) {
        this.bus = bus;
        this.target = Objects.requireNonNull( target);
        this.method = method;
        method.setAccessible(true);

        this.executor = bus.executor();
    }

    /**
     * Dispatches {@code event} to this subscriber using the proper executor.
     */
    final void dispatchEvent(final Object event) {
        executor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            invokeSubscriberMethod(event);
                        } catch (InvocationTargetException e) {
                            bus.handleSubscriberException(e.getCause(), context(event));
                        }
                    }
                });
    }

    /**
     * Invokes the subscriber method. This method can be overridden to make the invocation
     * synchronized.
     */
    void invokeSubscriberMethod(Object event) throws InvocationTargetException {
        try {
            method.invoke(target, Objects.requireNonNull( event));
        } catch (IllegalArgumentException e) {
            throw new Error("Method rejected target/argument: " + event, e);
        } catch (IllegalAccessException e) {
            throw new Error("Method became inaccessible: " + event, e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw (Error) e.getCause();
            }
            throw e;
        }
    }

    /**
     * Gets the context for the given event.
     */
    private SubscriberExceptionContext context(Object event) {
        return new SubscriberExceptionContext(bus, event, target, method);
    }

    @Override
    public final int hashCode() {
        return (31 + method.hashCode()) * 31 + System.identityHashCode(target);
    }

    @Override
    public final boolean equals(@Nullable Object obj) {
        if (obj instanceof Subscriber) {
            Subscriber that = (Subscriber) obj;
            // Use == so that different equal instances will still receive events.
            // We only guard against the case that the same object is registered
            // multiple times
            return target == that.target && method.equals(that.method);
        }
        return false;
    }

    /**
     * Checks whether {@code method} is thread-safe, as indicated by the presence of the
     * {@link AllowConcurrentEvents} annotation.
     */
    private static boolean isDeclaredThreadSafe(Method method) {
        return method.getAnnotation(AllowConcurrentEvents.class) != null;
    }

    /**
     * Subscriber that synchronizes invocations of a method to ensure that only one thread may enter
     * the method at a time.
     */
    static final class SynchronizedSubscriber extends Subscriber {

        private SynchronizedSubscriber(EventBus bus, Object target, Method method) {
            super(bus, target, method);
        }

        @Override
        void invokeSubscriberMethod(Object event) throws InvocationTargetException {
            synchronized (this) {
                super.invokeSubscriberMethod(event);
            }
        }
    }
}
