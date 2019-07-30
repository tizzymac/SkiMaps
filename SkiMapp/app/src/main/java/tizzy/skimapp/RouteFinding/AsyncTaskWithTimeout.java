package tizzy.skimapp.RouteFinding;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * from: https://gist.github.com/scottTomaszewski/3c9af91295e8871953739bb456de937b
 */
public abstract class AsyncTaskWithTimeout<Params, Progress, Result>
        extends AsyncTask<Params, Progress, Result> {
    private final long timeout;
    private final TimeUnit units;
    private final Activity context;

    // used for interruption
    private Thread backgroundThread;

    public AsyncTaskWithTimeout(Activity context, long timeout, TimeUnit units) {
        this.context = context;
        this.timeout = timeout;
        this.units = units;
    }

    @Override
    protected final void onPreExecute() {
        Thread timeoutThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // start the timeout ticker
                    AsyncTaskWithTimeout.this.get(timeout, units);
                } catch (InterruptedException e) {
                    Log.w("grok", "Background thread for AsyncTask timeout was interrupted.  Killing timeout thread.");
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    Log.w("grok", "AsyncTask threw an exception, delegating off.");
                    onException(e);
                } catch (TimeoutException e) {
                    Log.d("grok", "Timeout reached.  Interrupting AsyncTask thread and calling #onTimeout.");
                    AsyncTaskWithTimeout.this.interruptTask();
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onTimeout();
                        }
                    });

                }
            }
        });
        timeoutThread.setDaemon(true);
        onPreExec();
        timeoutThread.start();
    }

    protected void onPreExec() {
    }

    @Override
    protected final Result doInBackground(Params... params) {
        // save off reference to background thread so it can be interrupted on timeout
        this.backgroundThread = Thread.currentThread();
        return runInBackground(params);
    }

    protected abstract Result runInBackground(Params... params);

    protected void onTimeout() {
    }

    protected void onException(ExecutionException e) {
        throw new RuntimeException(e);
    }

    private final void interruptTask() {
        if (backgroundThread != null) {
            Log.w("grok", "Interrupting AsyncTask because of timeout");
            backgroundThread.interrupt();
        }
    }
}
