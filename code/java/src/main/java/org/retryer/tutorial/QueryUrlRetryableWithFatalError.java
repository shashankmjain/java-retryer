package org.retryer.tutorial;


import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.retryer.IRetryableTask;
import org.retryer.backoffs.BackoffBuilder;
import org.retryer.backoffs.ExponentialBackoff;
import org.retryer.dsl.Backoff;
import org.retryer.impl.Retryer;

/**
 * @author cheremin
 * @since 28.09.11,  16:53
 */
public class QueryUrlRetryableWithFatalError {


    public static String queryRetryable( final String urlQuery ) throws Exception {
        return new Retryer().doRetryable(
                new IRetryableTask<String, Exception>() {
                    @Override
                    public String execute( final int tryNo ) throws Exception {
                        return QueryUrlOnce.simpleQuery( urlQuery );
                    }

                    @Override
                    public boolean isFatalReason( final int tryNo,
                                                  final Throwable reason ) {
                        if ( reason instanceof MalformedURLException ) {
                            //if URL is malformed -- it is not make sense to retry
                            return true;
                        } else {
                            return false;
                        }
                    }
                },
                Backoff
                        .withExponentialGrowingDelay()
                        .startingWithDelay( 1, TimeUnit.SECONDS )
                        .maxTryes( 5 )
                        .maxDelay( 10, TimeUnit.SECONDS )
                        .build()
        );
    }

    public static void main( final String[] args ) throws Exception {
        System.out.println( queryRetryable( QueryUrlOnce.URL_TO_QUERY ) );
    }
}
