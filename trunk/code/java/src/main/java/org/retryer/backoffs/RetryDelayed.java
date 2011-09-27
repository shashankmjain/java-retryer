package org.retryer.backoffs;



import org.retryer.IRetryStrategy;
import org.retryer.RetryInfo;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Retry with fixed delay, given in constructor
 *
 * @author cheremin
 * @since 03.08.11,  18:17
 */
public class RetryDelayed implements IRetryStrategy {
    private final long delay;

    public RetryDelayed( final long delay ) {
        checkArgument( delay > 0, "delay(%s) must be > 0", delay );
        this.delay = delay;
    }

    @Override
    public RetryInfo shouldRetry( final int tryNo,
                                  final Throwable failReason ) {
        return RetryInfo.retryDelayed( delay );
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append( "RetryDelayed[" )
                .append( delay )
                .append( " ms]" )
                .toString();
    }
}
