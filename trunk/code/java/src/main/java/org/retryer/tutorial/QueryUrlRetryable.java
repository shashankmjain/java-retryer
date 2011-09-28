package org.retryer.tutorial;


import java.net.URL;

import org.retryer.IRetryableTask;
import org.retryer.backoffs.RetryNow;
import org.retryer.impl.Retryer;

/**
 * @author cheremin
 * @since 28.09.11,  16:53
 */
public class QueryUrlRetryable {


    public static Object queryRetryableNow( final String urlQuery ) throws Exception {
        return new Retryer().doRetryable(
                new IRetryableTask<Object, Exception>() {
                    @Override
                    public Object execute( final int tryNo ) throws Exception {
                        return QueryUrlOnce.simpleQuery( urlQuery );
                    }

                    @Override
                    public boolean failed( final int tryNo,
                                           final Throwable reason ) {
                        return false;
                    }
                },
                RetryNow.INSTANCE
        );
    }

    public static void main( final String[] args ) throws Exception {
        System.out.println( queryRetryableNow( QueryUrlOnce.URL_TO_QUERY ) );
    }
}
