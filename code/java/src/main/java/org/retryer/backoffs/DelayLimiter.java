package org.retryer.backoffs;



import org.retryer.IRetryStrategy;
import org.retryer.RetryInfo;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Decorator for another {@link IRetryStrategy}, limiting maximum delay
 * between retryes.
 *
 * @author cheremin
 * @since 10.08.11,  13:49
 */
public class DelayLimiter implements IRetryStrategy {

    private final IRetryStrategy wrapped;
    private final long maxDelay;

    public DelayLimiter( final IRetryStrategy toWrap,
                         final long maxDelay ) {
        checkArgument( maxDelay > 0, "maxDelay(%s) must be >0", maxDelay );
        checkArgument( toWrap != null, "toWrap can't be null" );
        this.wrapped = toWrap;
        this.maxDelay = maxDelay;
    }

    @Override
    public RetryInfo shouldRetry( final int tryNo,
                                  final Throwable failReason ) {
        final RetryInfo info = wrapped.shouldRetry( tryNo, failReason );
        if ( info.shouldRetry() ) {
            if ( info.delay() > maxDelay ) {
                return RetryInfo.retryDelayed( maxDelay );
            }
        }
        return info;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append( "TotalTimeLimiter[" )
                .append( wrapped )
                .append( ", maxDelay=" )
                .append( maxDelay )
                .append( " ms]" )
                .toString();
    }
}
