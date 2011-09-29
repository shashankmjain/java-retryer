package org.retryer.dsl;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import org.retryer.IRetryStrategy;
import org.retryer.backoffs.LinearBackoff;

/**
 * @author cheremin
 * @since 29.09.11,  14:41
 */
public class LinearDelayBuilder extends AbstractBackoffBuilderWithGrowingDelay<LinearDelayBuilder> {

    private long initialDelay = -1;
    private long step = -1;

    LinearDelayBuilder() {
    }

    LinearDelayBuilder( final long initialDelay ) {
        startingWithDelay( initialDelay );
        withStep( initialDelay );
    }

    LinearDelayBuilder( final long initialDelay,
                        final long step ) {
        startingWithDelay( initialDelay );
        withStep( step );
    }

    public long initialDelay() {
        return initialDelay;
    }

    public long step() {
        return step;
    }

    public LinearDelayBuilder startingWithDelay( final long initialDelay,
                                                 final TimeUnit unit ) {
        return startingWithDelay( unit.toMillis( initialDelay ) );
    }

    public LinearDelayBuilder startingWithDelay( final long initialDelayMs ) {
        Preconditions.checkArgument( initialDelayMs > 0, "initialDelayMs(%s) must be >0", initialDelayMs );
        this.initialDelay = initialDelayMs;
        return typedThis();
    }

    public LinearDelayBuilder withStep( final long step,
                                        final TimeUnit unit ) {
        return withStep( unit.toMillis( step ) );
    }

    public LinearDelayBuilder withStep( final long stepMs ) {
        Preconditions.checkArgument( stepMs > 0, "stepMs(%s) must be >0", stepMs );
        this.step = stepMs;
        return typedThis();
    }

    @Override
    protected IRetryStrategy createBaseStrategy() {
        if ( initialDelay < 0 ) {
            throw new IllegalStateException( "Can't build linearGrowingStrategy without initialDelay" );
        }
        if ( step < 0 ) {
            return new LinearBackoff( initialDelay, initialDelay );
        } else {
            return new LinearBackoff( initialDelay, step );
        }
    }

    @Override
    public IRetryStrategy build() {
        return super.build();
    }
}
