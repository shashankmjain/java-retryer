package org.retryer.tutorial;


import java.util.concurrent.TimeUnit;

import org.retryer.IRetryableTask;
import org.retryer.backoffs.RetryDelayed;
import org.retryer.impl.Retryer;

/**
 * @author cheremin
 * @since 28.09.11,  16:53
 */
public class QueryUrlRetryableDelayed {


    public static String queryRetryableDelayed( final String urlQuery ) throws Exception {
        return new Retryer().doRetryable(
                new IRetryableTask<String, Exception>() {
                    @Override
                    public String execute( final int tryNo ) throws Exception {
                        return QueryUrlOnce.simpleQuery( urlQuery );
                    }

                    @Override
                    public boolean isFatalReason( final int tryNo,
                                                  final Throwable reason ) {
                        return false;
                    }
                },
                new RetryDelayed( TimeUnit.SECONDS.toMillis( 1 ) )
        );
    }

    public static void main( final String[] args ) throws Exception {
        System.out.println( queryRetryableDelayed( QueryUrlOnce.URL_TO_QUERY ) );
    }
}
