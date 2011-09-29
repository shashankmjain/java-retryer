package org.retryer.dsl;

import org.retryer.IRetryStrategy;
import org.retryer.backoffs.RetryNow;

/**
 * @author cheremin
 * @since 29.09.11,  14:41
 */
public class RetryNowBuilder extends AbstractBackoffBuilder<RetryNowBuilder> {

    RetryNowBuilder() {
    }

    @Override
    protected IRetryStrategy createBaseStrategy() {
        return RetryNow.INSTANCE;
    }

    @Override
    public IRetryStrategy build() {
        return super.build();
    }
}
