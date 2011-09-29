package org.retryer.dsl;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import org.retryer.IRetryStrategy;
import org.retryer.backoffs.ExponentialBackoff;
import org.retryer.backoffs.LinearBackoff;

/**
 * @author cheremin
 * @since 29.09.11,  14:41
 */
public class ExponentialDelayBuilder extends AbstractBackoffBuilderWithGrowingDelay<ExponentialDelayBuilder> {

    private long initialDelay = -1;

    ExponentialDelayBuilder() {
    }

    ExponentialDelayBuilder( final long initialDelay ) {
        withInitialDelay( initialDelay );
    }

    public long initialDelay() {
        return initialDelay;
    }

    public ExponentialDelayBuilder withInitialDelay( final long initialDelayMs ) {
        Preconditions.checkArgument( initialDelayMs > 0, "initialDelayMs(%s) must be >0", initialDelayMs );
        this.initialDelay = initialDelayMs;
        return typedThis();
    }

    public ExponentialDelayBuilder startingWithDelay( final long initialDelay,
                                                      final TimeUnit unit ) {
        return withInitialDelay( unit.toMillis( initialDelay ) );
    }

    @Override
    protected IRetryStrategy createBaseStrategy() {
        if ( initialDelay < 0 ) {
            throw new IllegalStateException( "Can't build exponentialGrowingStrategy without initialDelay" );
        }
        return new ExponentialBackoff( initialDelay );
    }

    @Override
    public IRetryStrategy build() {
        return super.build();
    }
}
