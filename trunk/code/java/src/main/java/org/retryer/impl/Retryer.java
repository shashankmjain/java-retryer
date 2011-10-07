package org.retryer.impl;

import java.util.List;

import com.google.common.collect.Lists;
import org.retryer.*;
import org.slf4j.*;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Implementation of {@link IRetryer} interface
 *
 * @author cheremin
 * @since 03.08.11,  17:27
 */
public class Retryer implements IRetryer {
    private static final Logger log = LoggerFactory.getLogger( Retryer.class );


    @Override
    public <R, E extends Throwable> R doRetryable( final IRetryableTask<R, E> task,
                                                   final IRetryStrategy strategy ) throws RetryerException, InterruptedException {
        checkArgument( task != null, "task can't be null" );
        checkArgument( strategy != null, "strategy can't be null" );
        int tryNo = 0;
        final List<Throwable> reasons = Lists.newArrayList();
        MDC.put( "task", task.toString() );
        try {
            while ( true ) {
                try {
                    log.debug( "try #{}...", tryNo );
                    return task.execute( tryNo );
                } catch ( final Throwable reason ) {
                    log.debug( "try #{} failed: {}", tryNo, reason.getMessage() );

                    reasons.add( reason );

                    //cleanup
                    if ( task.isFatalReason( tryNo, reason ) ) {
                        throw new RetryerException( reasons );
                    }

                    final RetryInfo retryInfo = strategy.shouldRetry(
                            tryNo,
                            reason
                    );

                    if ( retryInfo.shouldFail() ) {
                        throw new RetryerException( reasons );
                    } else {
                        final long delay = retryInfo.delay();
                        if ( delay > 0 ) {
                            log.debug( "retry after {} ms", delay );
                            Thread.sleep( delay );
                        } else {
                            log.debug( "retry now" );
                        }
                    }
                } finally {
                    tryNo++;
                }
            }
        } finally {
            MDC.remove( "task" );
        }
    }


}
