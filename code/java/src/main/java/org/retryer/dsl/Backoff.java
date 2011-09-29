package org.retryer.dsl;


import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.*;

/**
 * fixme: Class Backoff is for porn
 *
 * @author cheremin
 * @since 29.09.11,  15:00
 */
public class Backoff {

    private Backoff() {
    }

    public static RetryNowBuilder immediately() {
        return new RetryNowBuilder();
    }

    public static RetryWithFixedDelayBuilder withFixedDelay( final long delayMs ) {
        return new RetryWithFixedDelayBuilder( delayMs );
    }

    public static RetryWithFixedDelayBuilder withFixedDelay( final long delay,
                                                             final TimeUnit unit ) {
        return new RetryWithFixedDelayBuilder( unit.toMillis( delay ) );
    }

    public static LinearDelayBuilder withLinearGrowingDelay() {
        return new LinearDelayBuilder();
    }

    public static ExponentialDelayBuilder withExponentialGrowingDelay() {
        return new ExponentialDelayBuilder();
    }
}
