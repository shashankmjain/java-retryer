package org.retryer.dsl;

import java.util.concurrent.TimeUnit;

import org.retryer.IRetryStrategy;
import org.retryer.backoffs.DelayLimiter;

import static com.google.common.base.Preconditions.*;

/**
 * @author cheremin
 * @since 29.09.11,  14:34
 */
public abstract class AbstractBackoffBuilderWithGrowingDelay<T extends AbstractBackoffBuilderWithGrowingDelay<T>>
        extends AbstractDelayedBackoffBuilder<T> {
    private long maxDelay = -1;

    protected AbstractBackoffBuilderWithGrowingDelay() {
    }

    public T maxDelay( final long maxDelay ) {
        checkArgument( maxDelay > 0, "maxDelay(%s) must be >0", maxDelay );
        this.maxDelay = maxDelay;
        return typedThis();
    }

    public T maxDelay( final long maxDelay,
                       final TimeUnit unit ) {
        return maxDelay( unit.toMillis( maxDelay ) );
    }

    @Override
    protected IRetryStrategy build() {
        final IRetryStrategy result = super.build();
        if ( maxDelay > 0 ) {
            return new DelayLimiter( result, maxDelay );
        } else {
            return result;
        }
    }
}
