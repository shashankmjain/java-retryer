package org.retryer.backoffs;


import org.retryer.IRetryStrategy;
import org.retryer.RetryInfo;

/**
 * Just retry immediately
 *
 * @author cheremin
 * @since 03.08.11,  18:17
 */
public final class RetryNow implements IRetryStrategy {

    public static final RetryNow INSTANCE = new RetryNow();


    private RetryNow() {
    }

    @Override
    public RetryInfo shouldRetry( final int tryNo,
                                  final Throwable failReason ) {
        return RetryInfo.retryNow();

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
