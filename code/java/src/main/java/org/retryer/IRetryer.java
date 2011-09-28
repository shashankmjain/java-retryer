package org.retryer;

/**
 *
 *
 * @author cheremin
 * @since 03.08.11,  17:16
 */
public interface IRetryer {

    public <R, E extends Throwable>
    R doRetryable( final IRetryableTask<R, E> task,
                   final IRetryStrategy strategy ) throws RetryerException, InterruptedException;
}
