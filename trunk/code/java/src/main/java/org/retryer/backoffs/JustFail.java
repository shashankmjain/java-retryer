package org.retryer.backoffs;


import org.retryer.IRetryStrategy;
import org.retryer.RetryInfo;

/**
 * Strategy for just fail now (no retry)
 *
 * @author cheremin
 * @since 10.08.11,  13:41
 */
public class JustFail implements IRetryStrategy {

    public static final JustFail INSTANCE = new JustFail();

    private JustFail() {
    }

    @Override
    public RetryInfo shouldRetry( final int tryNo,
                                  final Throwable failReason ) {
        return RetryInfo.fail();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
