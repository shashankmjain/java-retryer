package org.retryer;

/**
 * Task, which execution may be failed, and need to be retryed once or several times
 * before "surrender" and failing at last.
 *
 * @author cheremin
 * @since 03.08.11,  17:16
 */
public interface IRetryableTask<R, E extends Throwable> {
    /**
     * Main method to execute
     *
     * @param tryNo number of try currently executed (first try is 0)
     * @return result
     * @throws E checked exception
     */
    public R execute( final int tryNo ) throws E;

    /**
     * Callback method, will be called by {@link IRetryer} after each failed
     * try to let the task itself to mark some fail reasons as "fatal" -- which
     * means what continue trying after such reasons makes no sense.
     *
     * @param tryNo  number of try just failed (first try is 0)
     * @param reason exception thrown by last failed try (last call of {@link #execute(int)}
     * @return true if task must be undoubtedly stopped to retry (fatal error detected),
     *         false if decision "retry or not retry" is up to currently used
     *         {@link IRetryStrategy}
     */
    public boolean isFatalReason( final int tryNo,
                                  final Throwable reason );


}
