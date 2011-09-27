package org.retryer.backoffs;



import org.retryer.IRetryStrategy;
import org.retryer.RetryInfo;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * fixme:
 *
 * @author cheremin
 * @since 10.08.11,  13:42
 */
public class MaxTryesWrapper implements IRetryStrategy {
    private final int maxTries;
    private final IRetryStrategy wrapped;

    public MaxTryesWrapper( final IRetryStrategy toWrap,
                            final int maxTries ) {
        checkArgument( maxTries > 0, "maxTries(%s) must be > 0", maxTries );
        checkArgument( toWrap != null, "toWrap can't be null" );

        this.maxTries = maxTries;
        this.wrapped = toWrap;
    }

    @Override
    public RetryInfo shouldRetry( final int tryNo,
                                  final Throwable failReason ) {
        if ( tryNo >= maxTries ) {
            return RetryInfo.fail();
        } else {
            return wrapped.shouldRetry( tryNo, failReason );
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "MaxTryesWrapper[" )
                .append( wrapped );
        sb.append( ", maxTries=" ).append( maxTries );
        sb.append( ']' );
        return sb.toString();
    }
}
