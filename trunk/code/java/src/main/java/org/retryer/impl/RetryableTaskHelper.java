package org.retryer.impl;

import org.retryer.IRetryableTask;


/**
 * Abstract helper class, simplifying anonymous implementation of {@link IRetryableTask}.
 * It gives default implementation of {@link IRetryableTask#isFatalReason(int, Throwable)} as just
 * {@code return false}, which means "no one error are fatal -> always use
 * {@link org.retryer.IRetryStrategy} for deciding retry or not"
 *
 * @author cheremin
 * @since 28.09.11,  16:57
 */
public abstract class RetryableTaskHelper<R, E extends Throwable>
        implements IRetryableTask<R, E> {
    @Override
    public boolean isFatalReason( final int tryNo,
                                  final Throwable reason ) {
        return false;
    }
}
