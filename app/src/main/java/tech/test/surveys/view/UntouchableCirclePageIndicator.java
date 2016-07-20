package tech.test.surveys.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by GOLF on 7/21/2016.
 */
public class UntouchableCirclePageIndicator extends CirclePageIndicator {
    public UntouchableCirclePageIndicator(Context context) {
        super(context);
    }

    public UntouchableCirclePageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UntouchableCirclePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
