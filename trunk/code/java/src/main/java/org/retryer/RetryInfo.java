package org.retryer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * Elementary action taken by {@link IRetryer}:
 * fail, retry now, retry after given delay
 *
 * @author cheremin
 * @since 03.08.11,  17:32
 */
public final class RetryInfo {

    public static final RetryInfo FAIL = new RetryInfo( -1 );
    public static final RetryInfo RETRY_NOW = new RetryInfo( 0 );


    private final long delay;

    private RetryInfo( final long delay ) {
        this.delay = delay;
    }

    public long delay() {
        checkState( delay>=0, "can't get delay if fail" );
        return delay;
    }

    public boolean shouldRetry() {
        return delay >= 0;
    }

    public boolean shouldFail() {
        return delay < 0;
    }

    public static RetryInfo fail() {
        return FAIL;
    }

    public static RetryInfo retryNow() {
        return RETRY_NOW;
    }

    public static RetryInfo retryDelayed( final long millis ) {
        checkArgument( millis > 0, "millis(%s) must be > 0", millis );
        return new RetryInfo( millis );
    }


}
