package org.retryer.tutorial;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import org.retryer.IRetryableTask;
import org.retryer.backoffs.RetryNow;
import org.retryer.impl.Retryer;

/**
 * @author cheremin
 * @since 28.09.11,  16:53
 */
public class QueryUrlOnce {


    public static final String URL_TO_QUERY = "http://google.com/?q=Retryer";

    public static Object simpleQuery( final String urlString ) throws Exception {
        final URL url = new URL( urlString );
        final InputStream is = url.openStream();
        try {
            final InputStreamReader r = new InputStreamReader( is, "ISO-8859-1" );
            try {
                return CharStreams.toString( r );
            } finally {
                r.close();
            }
        } finally {
            is.close();
        }
    }

    public static void main( final String[] args ) throws Exception {
        System.out.println( simpleQuery( URL_TO_QUERY ) );
    }
}
