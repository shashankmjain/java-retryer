package org.retryer.backoffs;

import java.util.Random;

import com.google.common.base.Preconditions;
import org.retryer.IRetryStrategy;
import org.retryer.RetryInfo;


/**
 * Decorate {@link IRetryStrategy}, so what if wrapped strategy decide to
 * retry with delay, this wrapper will modify delay so it will be
 * {@code random[1, delay]}
 * <p/>
 * This likely to reduce contention
 *
 * @author cheremin
 * @since 03.08.11,  18:17
 */
public class RandomizingDelayDecorator implements IRetryStrategy {

    private final IRetryStrategy wrapped;
    private final Random random = new Random();

    public RandomizingDelayDecorator( final IRetryStrategy wrapped ) {
        Preconditions.checkArgument( wrapped != null, "wrapped can't be null" );
        this.wrapped = wrapped;
    }

    @Override
    public RetryInfo shouldRetry( final int tryNo,
                                  final Throwable failReason ) {
        final RetryInfo info = wrapped.shouldRetry( tryNo, failReason );
        if ( info.shouldRetry() && info.delay() > 0 ) {
            final long delay = info.delay();
            final double _delay = ( random.nextDouble() * delay ) + 1;
            return RetryInfo.retryDelayed( ( long ) _delay );
        } else {
            return info;
        }
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append( "RandomizingDelayDecorator[" )
                .append( wrapped )
                .append( ']' )
                .toString();
    }
}
