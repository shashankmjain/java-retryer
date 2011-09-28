package org.retryer;

/**
 * Strategy pattern -- decision retry or not after each turn and specific error
 *
 * @author cheremin
 * @since 03.08.11,  17:18
 */
public interface IRetryStrategy {

    public RetryInfo shouldRetry( final int tryNo,
                                  final Throwable failReason );
}
