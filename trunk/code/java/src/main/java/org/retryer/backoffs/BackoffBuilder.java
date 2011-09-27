package org.retryer.backoffs;

import java.util.concurrent.TimeUnit;


import org.retryer.IRetryStrategy;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * fixme: Class BackoffBuilder is for porn
 *
 * @author cheremin
 * @since 10.08.11,  14:13
 */
public class BackoffBuilder {
    private final IRetryStrategy base;
    private int maxTryes = -1;
    private long maxDelay = -1;
    private long maxTotalTime = -1;

    public static BackoffBuilder with( final IRetryStrategy base ) {
        return new BackoffBuilder( base );
    }

    public BackoffBuilder( final IRetryStrategy base ) {
        checkArgument( base != null, "base can't be null" );
        this.base = base;
    }

    public BackoffBuilder maxTryes( final int maxTryes ) {
        checkArgument( maxTryes > 0, "maxTryes(%s) must be >0", maxTryes );
        this.maxTryes = maxTryes;
        return this;
    }

    public BackoffBuilder maxDelay( final long maxDelay ) {
        checkArgument( maxDelay > 0, "maxDelay(%s) must be >0", maxDelay );
        this.maxDelay = maxDelay;
        return this;
    }

    public BackoffBuilder maxDelay( final long maxDelay,
                                    final TimeUnit unit ) {
        return maxDelay( unit.toMillis( maxDelay ) );
    }

    public BackoffBuilder maxTotalTime( final long maxTotalTime ) {
        checkArgument( maxTotalTime > 0, "maxTotalTime(%s) must be >0", maxTotalTime );
        this.maxTotalTime = maxTotalTime;
        return this;
    }

    public BackoffBuilder maxTotalTime( final long maxTotalTime,
                                        final TimeUnit unit ) {
        return maxTotalTime( unit.toMillis( maxTotalTime ) );
    }

    public IRetryStrategy build() {
        IRetryStrategy result = base;
        if ( maxTryes > 0 ) {
            result = new MaxTryesWrapper( result, maxTryes );
        }
        if ( maxDelay > 0 ) {
            result = new DelayLimiter( result, maxDelay );
        }
        if ( maxTotalTime > 0 ) {
            result = new TotalTimeLimiter( result, maxTotalTime );
        }
        return result;
    }
}
