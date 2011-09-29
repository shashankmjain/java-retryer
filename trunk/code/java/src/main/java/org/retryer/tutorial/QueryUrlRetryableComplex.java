package org.retryer.tutorial;


import java.util.concurrent.TimeUnit;

import org.retryer.IRetryableTask;
import org.retryer.backoffs.*;
import org.retryer.impl.RetryableTaskHelper;
import org.retryer.impl.Retryer;

/**
 * @author cheremin
 * @since 28.09.11,  16:53
 */
public class QueryUrlRetryableComplex {


    public static String queryRetryableComplex( final String urlQuery ) throws Exception {
        return new Retryer().doRetryable(
                new RetryableTaskHelper<String, Exception>() {
                    @Override
                    public String execute( final int tryNo ) throws Exception {
                        return QueryUrlOnce.simpleQuery( urlQuery );
                    }
                },
                BackoffBuilder.with( new ExponentialBackoff( TimeUnit.SECONDS.toMillis( 1 ) ) )
                        .maxTryes( 5 )
                        .maxDelay( 10, TimeUnit.SECONDS )
                        .build()
        );
    }

    public static void main( final String[] args ) throws Exception {
        System.out.println( queryRetryableComplex( QueryUrlOnce.URL_TO_QUERY ) );
    }
}
