package org.retryer.backoffs;



import org.retryer.IRetryStrategy;
import org.retryer.RetryInfo;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Retry with exponential growing delay: {@code delay(tryNo) = initialDelay * 2^tryNo},
 * with delay limited to maxDelay
 *
 * @author cheremin
 * @since 03.08.11,  18:17
 */
public class ExponentialBackoff implements IRetryStrategy {
    private final long initialDelay;


    public ExponentialBackoff( final long initialDelay ) {
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
        return ( long ) ( initialDelay * Math.pow( 2, tryNo ) );
    }

    public long initialDelay() {
        return initialDelay;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "ExponentialBackoff" );
        sb.append( "[initialDelay=" )
                .append( initialDelay );
        sb.append( ']' );
        return sb.toString();
    }
}
