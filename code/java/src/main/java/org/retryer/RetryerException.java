package org.retryer;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Contains log of all exceptions was thrown during all retryes done
 *
 * @author cheremin
 * @since 03.08.11,  17:24
 */
public final class RetryerException extends Exception{
    private final List<? extends Throwable> thrown;

    public RetryerException( final Iterable<? extends Throwable> thrown ) {
        this.thrown = Lists.newArrayList( thrown );
    }

    public List<? extends Throwable> getThrown() {
        return Collections.unmodifiableList( thrown );
    }
}
