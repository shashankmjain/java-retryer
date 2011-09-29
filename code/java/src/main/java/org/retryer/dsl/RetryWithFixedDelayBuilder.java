package org.retryer.dsl;

import com.google.common.base.Preconditions;
import org.retryer.IRetryStrategy;
import org.retryer.backoffs.FixedDelayBackoff;

/**
 * @author cheremin
 * @since 29.09.11,  14:41
 */
public class RetryWithFixedDelayBuilder extends AbstractBackoffBuilder<RetryWithFixedDelayBuilder> {

    private long delay;

    RetryWithFixedDelayBuilder() {
    }

    RetryWithFixedDelayBuilder( final long delay ) {
        withDelay( delay );
    }

    public long delay() {
        return delay;
    }

    public RetryWithFixedDelayBuilder withDelay( final long delay ) {
        Preconditions.checkArgument( delay > 0, "delay(%s) must be >0", delay );
        this.delay = delay;
        return typedThis();
    }

    @Override
    protected IRetryStrategy createBaseStrategy() {
        return new FixedDelayBackoff( delay );
    }

    @Override
    public IRetryStrategy build() {
        return super.build();
    }
}
