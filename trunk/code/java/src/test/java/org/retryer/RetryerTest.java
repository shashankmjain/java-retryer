package org.retryer;

import org.jmock.*;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.retryer.impl.Retryer;

import static org.junit.Assert.*;

/**
 * @author cheremin
 * @since 04.08.11,  13:24
 */
@RunWith( JMock.class )
public class RetryerTest {

    private final Mockery mockery = new Mockery();

    final IRetryableTask task = mockery.mock( IRetryableTask.class );
    final IRetryStrategy strategy = mockery.mock( IRetryStrategy.class );


    @Test( expected = IllegalArgumentException.class )
    public void nullTaskFails() throws Exception {
        new Retryer().doRetryable( null, strategy );
    }

    @Test( expected = IllegalArgumentException.class )
    public void nullStrategyFails() throws Exception {
        new Retryer().doRetryable( task, null );
    }

    @Test
    public void successfulExecutionReturnsValue() throws Exception {
        final String returnValue = "123";
        mockery.checking( new Expectations() {{
            try {
                oneOf( task ).execute( 0 );
                will( returnValue( returnValue ) );
            } catch ( Throwable t ) {
            }
        }} );

        final Object value = new Retryer().doRetryable(
                task,
                strategy
        );
        assertSame( returnValue, value );
    }

    @Test( expected = RetryerException.class )
    public void failedExecutionCallsTaskFailedAndThrowsException() throws Exception {
        final Exception exception = new Exception();
        mockery.checking( new Expectations() {{
            try {
                oneOf( task ).execute( 0 );
                will( throwException( exception ) );

                oneOf( task ).failed( 0, exception );
                will( returnValue( true ) );
            } catch ( Throwable t ) {
            }
        }} );

        new Retryer().doRetryable(
                task,
                strategy
        );
    }

    @Test
    public void failedExecutionReturnReason() throws Exception {
        final Exception exception = new Exception();
        mockery.checking( new Expectations() {{
            try {
                oneOf( task ).execute( 0 );
                will( throwException( exception ) );

                oneOf( task ).failed( 0, exception );
                will( returnValue( true ) );
            } catch ( Throwable t ) {
            }
        }} );

        try {
            new Retryer().doRetryable(
                    task,
                    strategy
            );
            fail( "Must throw exception" );
        } catch ( RetryerException e ) {
            assertEquals( 1, e.getThrown().size() );
            assertSame( exception, e.getThrown().get( 0 ) );
        }
    }

    @Test
    public void retryStoppedIfStrategyReturnsFail() throws Exception {
        final Exception exception = new Exception();
        mockery.checking( new Expectations() {{
            try {
                final Sequence seq = mockery.sequence( "seq1" );
                oneOf( task ).execute( 0 );
                will( throwException( exception ) );
                inSequence( seq );


                oneOf( task ).failed( 0, exception );
                will( returnValue( false ) );
                inSequence( seq );

                oneOf( strategy ).shouldRetry( 0, exception );
                will( returnValue( RetryInfo.fail() ) );
                inSequence( seq );
            } catch ( Throwable t ) {
            }
        }} );

        try {
            new Retryer().doRetryable(
                    task,
                    strategy
            );
            fail( "Must throw exception" );
        } catch ( RetryerException e ) {
            assertEquals( 1, e.getThrown().size() );
            assertSame( exception, e.getThrown().get( 0 ) );
        }
    }

    @Test
    public void retryImmediatelyIfStrategyReturnsRetryNow() throws Exception {
        final Exception exception = new Exception();
        final String returnValue = "123";
        mockery.checking( new Expectations() {{
            try {
                final Sequence seq = mockery.sequence( "seq1" );

                oneOf( task ).execute( 0 );
                will( throwException( exception ) );
                inSequence( seq );


                oneOf( task ).failed( 0, exception );
                will( returnValue( false ) );
                inSequence( seq );

                oneOf( strategy ).shouldRetry( 0, exception );
                will( returnValue( RetryInfo.retryNow() ) );
                inSequence( seq );

                oneOf( task ).execute( 1 );
                will( returnValue( returnValue ) );
                inSequence( seq );
            } catch ( Throwable t ) {
            }
        }} );

        final Object ret = new Retryer().doRetryable(
                task,
                strategy
        );
        assertSame( returnValue, ret );
    }

    @Test
    public void retryAfterPauseIfStrategyReturnsRetryDelayed() throws Exception {
        final Exception exception = new Exception();
        final String returnValue = "123";
        final long pause = 500;
        mockery.checking( new Expectations() {{
            try {
                final Sequence seq = mockery.sequence( "seq1" );

                oneOf( task ).execute( 0 );
                will( throwException( exception ) );
                inSequence( seq );


                oneOf( task ).failed( 0, exception );
                will( returnValue( false ) );
                inSequence( seq );

                oneOf( strategy ).shouldRetry( 0, exception );

                will( returnValue( RetryInfo.retryDelayed( pause ) ) );
                inSequence( seq );

                oneOf( task ).execute( 1 );
                will( returnValue( returnValue ) );
                inSequence( seq );
            } catch ( Throwable t ) {
            }
        }} );

        final long startedAt = System.currentTimeMillis();
        new Retryer().doRetryable(
                task,
                strategy
        );

        final long finishedAt = System.currentTimeMillis();
        assertTrue( ( finishedAt - startedAt ) >= pause );
    }
}