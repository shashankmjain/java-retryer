package org.retryer;

/**
 * Strategy pattern -- decision retry or not after each turn and specific error
 *
 * @author cheremin
 * @since 03.08.11,  17:18
 */
public interface IRetryStrategy {

    /**
     * @param tryNo      sequenced number of retry (0-based)
     * @param failReason reason of current fail (exception thrown by {@link IRetryableTask#execute(int)}
     * @return action to be done by {@link IRetryer} in form of {@link RetryInfo} object
     * @see RetryInfo
     */
    public RetryInfo shouldRetry( final int tryNo,
                                  final Throwable failReason );
}
