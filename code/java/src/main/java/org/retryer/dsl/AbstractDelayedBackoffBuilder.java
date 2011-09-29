package org.retryer.dsl;

import org.retryer.IRetryStrategy;
import org.retryer.backoffs.RandomizingDelayDecorator;

/**
 * fixme: Class AbstractDelayedBackoffBuilder is for porn
 *
 * @author cheremin
 * @since 29.09.11,  15:16
 */
public abstract class AbstractDelayedBackoffBuilder<T extends AbstractDelayedBackoffBuilder<T>>
        extends AbstractBackoffBuilder<T> {

    private boolean randomized = false;

    protected AbstractDelayedBackoffBuilder() {
    }

    public boolean isDelayRandomized() {
        return randomized;
    }

    public T randomizedDelay() {
        this.randomized = true;
        return typedThis();
    }

    @Override
    protected IRetryStrategy build() {
        final IRetryStrategy result = super.build();
        if ( randomized ) {
            return new RandomizingDelayDecorator( result );
        } else {
            return result;
        }
    }
}
