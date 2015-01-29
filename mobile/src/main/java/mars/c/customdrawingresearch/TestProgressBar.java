package mars.c.customdrawingresearch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Constantine Mars on 1/27/15.
 */
public class TestProgressBar extends View {
    private int inactiveColor;
    private int activeColor;
    private int doneColor;

    private int radius;
    private int startX;
    private int startY;

    private int stepsNumber;

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        invalidate();
        requestLayout();
    }

    private int currentProgress;

    private Paint activePaint;
    private Paint inactivePaint;
    private Paint donePaint;

    public int getStepsNumber() {
        return stepsNumber;
    }

    public void setStepsNumber(int stepsNumber) {
        if (stepsNumber <= 0) {
            return;
        }

        this.stepsNumber = stepsNumber;
        invalidate();
        requestLayout();
    }

    public int getDoneColor() {
        return doneColor;
    }

    public void setDoneColor(int doneColor) {
        this.doneColor = doneColor;
        invalidate();
        requestLayout();
    }

    public int getActiveColor() {
        return activeColor;
    }

    public void setActiveColor(int activeColor) {
        this.activeColor = activeColor;
        invalidate();
        requestLayout();
    }

    public int getInactiveColor() {
        return inactiveColor;
    }

    public void setInactiveColor(int inactiveColor) {
        this.inactiveColor = inactiveColor;
        invalidate();
        requestLayout();
    }

    public TestProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TestProgressBar,
                0, 0);

        try {
            inactiveColor = a.getColor(R.styleable.TestProgressBar_inactiveColor, android.R.color.black);
            activeColor = a.getColor(R.styleable.TestProgressBar_activeColor, android.R.color.holo_orange_dark);
            doneColor = a.getColor(R.styleable.TestProgressBar_doneColor, android.R.color.darker_gray);
            currentProgress = a.getInt(R.styleable.TestProgressBar_currentProgress, 5);
            stepsNumber = a.getInt(R.styleable.TestProgressBar_stepsCount, 10);
        } finally {
            a.recycle();

//            inactiveColor = android.R.color.black;
//            activeColor = android.R.color.holo_orange_dark;
//            doneColor = android.R.color.darker_gray;
        }

        stepsNumber = 5;

        activePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        activePaint.setColor(activeColor);

        inactivePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        inactivePaint.setColor(inactiveColor);

        donePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        donePaint.setColor(doneColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the shadow
        for (int i=0; i<stepsNumber; i++) {
            int offset = radius*2*i;
            Log.d("a", "i="+i+", cp="+currentProgress);
            if (i == currentProgress) {
                canvas.drawCircle(startX+offset, startY, radius, activePaint);
            } else if (i < currentProgress) {
                canvas.drawCircle(startX+offset, startY, radius, donePaint);
            } else if (i > currentProgress) {
                canvas.drawCircle(startX*offset, startY, radius, inactivePaint);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int hpad = getPaddingLeft() + getPaddingRight();
        int vpad = getPaddingBottom() + getPaddingTop();

        int cw = w - hpad;
        int ch = h - vpad;

        int diam = cw/stepsNumber;
        radius = diam/2;
        startX = w/2 - (stepsNumber/2)*diam;
        startY = h/2 - radius;
    }
}
