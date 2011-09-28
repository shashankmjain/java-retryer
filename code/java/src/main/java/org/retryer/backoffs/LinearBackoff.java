package org.retryer.backoffs;


import org.retryer.IRetryStrategy;
import org.retryer.RetryInfo;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Retry with linear growing delay: {@code delay(tryNo) = initialDelay * (1+tryNo)},
 *
 * @author cheremin
 * @since 03.08.11,  18:17
 */
public class LinearBackoff implements IRetryStrategy {
    private final long initialDelay;

    public LinearBackoff( final long initialDelay ) {
        checkArgument( initialDelay > 0, "initialDelay(%s) must be >0", initialDelay );

        this.initialDelay = initialDelay;
    }

    @Override
    public RetryInfo shouldRetry( final int tryNo,
                                  final Throwable failReason ) {
        return shouldRetry( tryNo );
    }

    public RetryInfo shouldRetry( final int tryNo ) {
        return shouldRetry( tryNo, initialDelay );
    }


    public static RetryInfo shouldRetry( final int tryNo,
                                         final long initialDelay ) {
        return RetryInfo.retryDelayed(
                delay( tryNo, initialDelay )
        );

    }

    protected static long delay( final int tryNo,
                                 final long initialDelay ) {
        return initialDelay * ( 1 + tryNo );
    }

    public long initialDelay() {
        return initialDelay;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "LinearBackoff" );
        sb.append( "[initialDelay=" )
                .append( initialDelay );
        sb.append( ']' );
        return sb.toString();
    }
}
