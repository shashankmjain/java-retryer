package org.retryer;

/**
 *
 *
 * @author cheremin
 * @since 03.08.11,  17:16
 */
public interface IRetryer {
    /**
     * Run specified task, retrying if fails. Use strategy for finding when and how to retry.
     * This method will not return until all work is done -- successfully or not. In other words
     * -- this method is <i>not</i> asynchronous.
     *
     * @param task task to run
     * @param strategy strategy to find when to retry and how
     * @param <R> type of return value
     * @param <E> type of exceptions, throws by task
     * @return value, returned by successfull execution of task
     * @throws RetryerException if task execution was failed
     * @throws InterruptedException if task execution was interrupted
     */
    public <R, E extends Throwable>
    R doRetryable( final IRetryableTask<R, E> task,
                   final IRetryStrategy strategy ) throws RetryerException, InterruptedException;
}
