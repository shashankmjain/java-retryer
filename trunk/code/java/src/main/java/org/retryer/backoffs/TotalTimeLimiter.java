package org.retryer.backoffs;



import org.retryer.IRetryStrategy;
import org.retryer.RetryInfo;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * fixme: Class TotalTimeLimiter is for porn
 *
 * @author cheremin
 * @since 10.08.11,  13:49
 */
public class TotalTimeLimiter implements IRetryStrategy {
    private final IRetryStrategy wrapped;

    private final long mustFinishedBefore;

    public TotalTimeLimiter( final IRetryStrategy toWrap,
                             final long maxTimeSpent ) {
        checkArgument( toWrap != null, "toWrap can't be null" );
        checkArgument( maxTimeSpent > 0, "maxTimeSpent(%s) must be > 0", maxTimeSpent );

        this.wrapped = toWrap;
        this.mustFinishedBefore = System.currentTimeMillis() + maxTimeSpent;
    }

    @Override
    public RetryInfo shouldRetry( final int tryNo,
                                  final Throwable failReason ) {
        if ( System.currentTimeMillis() > mustFinishedBefore ) {
            return RetryInfo.fail();
        } else {
            return wrapped.shouldRetry( tryNo, failReason );
        }
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append( "TotalTimeLimiter[" )
                .append( wrapped )
                .append( ", mustFinishedBefore " )
                .append( mustFinishedBefore )
                .append( ']' )
                .toString();
    }
}
