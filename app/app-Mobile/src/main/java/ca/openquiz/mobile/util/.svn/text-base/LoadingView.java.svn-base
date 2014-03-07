package ca.openquiz.mobile.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;
import ca.openquiz.mobile.R;

public class LoadingView extends View {

    private Movie mMovie;
    private long mMovieStart;

    public LoadingView(Context context) {
        super(context);
        init(context);
    }
    
    public LoadingView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    private void init(Context context)
    {
        setFocusable(true);

        java.io.InputStream is;
        is = context.getResources().openRawResource(R.drawable.loading);

        mMovie = Movie.decodeStream(is);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        long now = android.os.SystemClock.uptimeMillis();
        if (mMovieStart == 0) {   // first time
            mMovieStart = now;
        }
        if (mMovie != null) {
            int dur = mMovie.duration();
            if (dur == 0) {
                dur = 1000;
            }
            int relTime = (int)((now - mMovieStart) % dur);
            mMovie.setTime(relTime);
            
            // Center the loading gif
            final float xPos = (getWidth() - mMovie.width())/2;
            final float yPos = (getHeight() - mMovie.height())/2;

            mMovie.draw(canvas, xPos, yPos);
            invalidate();
        }
    }
}
