package org.retryer.backoffs;

import java.util.Random;

import org.retryer.IRetryStrategy;
import org.retryer.RetryInfo;


/**
 * Like {@link ExponentialBackoff}, but delay is not exactly
 * {@code initialDelay*2^tryNo}, but {@code random[1, initialDelay*2^tryNo]}
 * <p/>
 * This likely to reduce contention
 *
 * @author cheremin
 * @since 03.08.11,  18:17
 */
public class RandomizedExponentialBackoff implements IRetryStrategy {

    private final ExponentialBackoff baseBackoff;
    private final Random random = new Random();

    public RandomizedExponentialBackoff( final long delay ) {
        baseBackoff = new ExponentialBackoff( delay );
    }

    @Override
    public RetryInfo shouldRetry( final int tryNo,
                                  final Throwable failReason ) {
        final RetryInfo info = baseBackoff.shouldRetry( tryNo, failReason );
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
                .append( "RandomizedExponentialBackoff[" )
                .append( baseBackoff )
                .append( ']' )
                .toString();
    }
}
