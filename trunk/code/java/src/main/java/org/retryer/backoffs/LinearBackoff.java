package org.retryer.backoffs;


import org.retryer.IRetryStrategy;
import org.retryer.RetryInfo;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Retry with linear growing delay: {@code delay(tryNo) = initialDelay + step* (tryNo)},
 *
 * @author cheremin
 * @since 03.08.11,  18:17
 */
public class LinearBackoff implements IRetryStrategy {
    private final long initialDelay;
    private final long step;

    public LinearBackoff( final long initialDelay,
                          final long step ) {
        checkArgument( initialDelay >= 0, "initialDelay(%s) must be >=0", initialDelay );
        checkArgument( step > 0, "step(%s) must be >0", step );

        this.initialDelay = initialDelay;
        this.step = step;
    }

    @Override
    public RetryInfo shouldRetry( final int tryNo,
                                  final Throwable failReason ) {
        return shouldRetry( tryNo );
    }

    public RetryInfo shouldRetry( final int tryNo ) {
        return shouldRetry( tryNo, initialDelay, step );
    }


    public static RetryInfo shouldRetry( final int tryNo,
                                         final long initialDelay,
                                         final long step ) {
        return RetryInfo.retryDelayed(
                delay( tryNo, initialDelay, step )
        );

    }

    protected static long delay( final int tryNo,
                                 final long initialDelay,
                                 final long step ) {
        return initialDelay + step * tryNo;
    }

    public long initialDelay() {
        return initialDelay;
    }

    public long step() {
        return step;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "LinearBackoff" );
        sb.append( "[initialDelay=" )
                .append( initialDelay )
                .append( ", step=" )
                .append( step );
        sb.append( ']' );
        return sb.toString();
    }
}
