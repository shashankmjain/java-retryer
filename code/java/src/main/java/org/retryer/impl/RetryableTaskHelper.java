package org.retryer.impl;

import org.retryer.IRetryableTask;


/**
 * fixme: Class RetryableTaskHelper is for porn
 *
 * @author cheremin
 * @since 28.09.11,  16:57
 */
public abstract class RetryableTaskHelper<R, E extends Throwable>
        implements IRetryableTask<R, E> {
    @Override
    public boolean failed( final int tryNo,
                           final Throwable reason ) {
        return false;
    }
}
