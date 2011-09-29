package org.retryer.dsl;

import java.util.concurrent.TimeUnit;

import org.retryer.IRetryStrategy;
import org.retryer.backoffs.*;

import static com.google.common.base.Preconditions.*;

/**
 * fixme: Class AbstractBackoffBuilder is for porn
 *
 * @author cheremin
 * @since 29.09.11,  14:32
 */
public abstract class AbstractBackoffBuilder<T extends AbstractBackoffBuilder<T>> {

    private long maxTotalTime = -1;
    private int maxTryes = -1;

    protected AbstractBackoffBuilder() {
    }

    public long getMaxTotalTime() {
        return maxTotalTime;
    }


    public int getMaxTryes() {
        return maxTryes;
    }

    public T maxTryes( final int maxTryes ) {
        checkArgument( maxTryes > 0, "maxTryes(%s) must be >0", maxTryes );
        this.maxTryes = maxTryes;
        return typedThis();
    }

    public T maxTotalTime( final long maxTotalTime ) {
        checkArgument( maxTotalTime > 0, "maxTotalTime(%s) must be >0", maxTotalTime );
        this.maxTotalTime = maxTotalTime;
        return typedThis();
    }

    public T maxTotalTime( final long maxTotalTime,
                           final TimeUnit unit ) {
        return maxTotalTime( unit.toMillis( maxTotalTime ) );
    }

    @SuppressWarnings( "unchecked" )
    protected T typedThis() {
        return ( T ) this;
    }

    protected abstract IRetryStrategy createBaseStrategy();

    protected IRetryStrategy build() {
        IRetryStrategy result = createBaseStrategy();
        checkState( result != null, "base strategy can't be null" );
        if ( maxTryes > 0 ) {
            result = new MaxTryesDecorator( result, maxTryes );
        }
        if ( maxTotalTime > 0 ) {
            result = new TotalTimeLimiter( result, maxTotalTime );
        }
        return result;
    }
}
