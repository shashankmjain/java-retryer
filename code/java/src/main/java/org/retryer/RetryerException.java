package org.retryer;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Contains log of all exceptions was thrown during all retryes done
 *
 * @author cheremin
 * @since 03.08.11,  17:24
 */
public final class RetryerException extends Exception {
    private final List<? extends Throwable> thrown;

    public RetryerException( final Iterable<? extends Throwable> thrown ) {
        Preconditions.checkArgument( !Iterables.isEmpty( thrown ), "thrown can't be empty" );

        this.thrown = Lists.newArrayList( thrown );
        final Throwable first = thrown.iterator().next();
        initCause( first );
    }

    public List<? extends Throwable> getThrown() {
        return Collections.unmodifiableList( thrown );
    }
}
