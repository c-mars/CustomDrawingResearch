package mars.c.customdrawingresearch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Constantine Mars on 1/29/15.
 */
public class ShapeView extends View {
    private ShapeColor shapeColor;
    private ShapeType shapeType;

    public ShapeType getShapeType() {
        return shapeType;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public ShapeColor getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(ShapeColor shapeColor) {
        this.shapeColor = shapeColor;
    }

    public static enum ShapeType {
        SQUARE(0),
        TRIANGLE(1),
        CIRCLE(2);

        public int getVal() {
            return val;
        }

        private int val;
        ShapeType(int val) {
            this.val = val;
        }

        public static ShapeType fromInt(int val) {
            return ShapeType.values()[val];
        }
    }

    public static enum ShapeColor {
        WHITE(0),
        BLACK(1),
        RED(2),
        GREEN(3),
        BLUE(4),
        YELLOW(5);

        public int getVal() {
            return val;
        }

        private int val;
        ShapeColor(int val) {
            this.val = val;
        }

        public static ShapeColor fromInt(int val) {
            return ShapeColor.values()[val];
        }

        public int toColor() {
            switch(this) {
                case WHITE:
                    return android.R.color.white;
                case BLACK:
                    return android.R.color.black;
                case RED:
                    return android.R.color.holo_red_dark;
                case GREEN:
                    return android.R.color.holo_green_dark;
                case BLUE:
                    return android.R.color.holo_blue_dark;
                case YELLOW:
                    return android.R.color.holo_orange_light;
            }
            return android.R.color.holo_purple;
        }
    }

    private Paint shapePaint;
    private int shapeSize;
    private int startX;
    private int startY;
    private static final double SCALE = 0.8;

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ShapeView,
                0, 0);

        try {
            this.shapeColor = ShapeColor.fromInt(a.getInt(R.styleable.ShapeView_shapeColor, ShapeColor.RED.getVal()));
            this.shapeType = ShapeType.fromInt(a.getInt(R.styleable.ShapeView_shapeType, ShapeType.CIRCLE.getVal()));
        } finally {
            a.recycle();
        }

        this.shapePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.shapePaint.setStyle(Paint.Style.FILL);
        this.shapePaint.setColor( getResources().getColor(this.shapeColor.toColor()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("a", "t="+shapeType.getVal());
        switch (shapeType) {
            case SQUARE:
                drawSquare(canvas);
                break;
            case TRIANGLE:
                drawTriangle(canvas);
                break;
            case CIRCLE:
                drawCircle(canvas);
                break;
        }
    }

    private void drawSquare(Canvas canvas) {
        canvas.drawRect(startX, startY, startX+shapeSize, startY+shapeSize, shapePaint);
    }

    private void drawTriangle(Canvas canvas) {
        PointF[] points = new PointF[] {
                new PointF(0.0f, 0.0f),
                new PointF(0.6f, 1.0f),
                new PointF(1.0f, 0.2f)
        };
        Path path = new Path();
        path.moveTo(startX+points[0].x*shapeSize, startY+points[0].y*shapeSize);
        path.lineTo(startX+points[1].x*shapeSize, startY+points[1].y*shapeSize);
        path.lineTo(startX+points[2].x*shapeSize, startY+points[2].y*shapeSize);
        path.lineTo(startX+points[0].x*shapeSize, startY+points[0].y*shapeSize);
        path.close();
        canvas.drawPath(path, shapePaint);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(startX+shapeSize/2, startY+shapeSize/2, shapeSize/2, shapePaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int hpad = getPaddingLeft() + getPaddingRight();
        int vpad = getPaddingBottom() + getPaddingTop();

        int realWidth = w - hpad;
        int realHeight = h - vpad;

        shapeSize = (int)(Math.min(realWidth, realHeight)*SCALE);
        startX = (w - shapeSize)/2;
        startY = (h - shapeSize)/2;
    }
}
