package com.qinglinyi.wideview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author qinglinyi
 * @since 1.0.0
 */
public class WideView extends View {

    private Bitmap mBitmap;
    private Paint mPaint;
    private int curPosition;
    private int mBitmapWidth, mBitmapHeight;

    private Rect src1, src2, dst1, dst2;

    public WideView(Context context) {
        super(context);
        init();
    }

    public WideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg);
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
        src1 = new Rect();
        dst1 = new Rect();
        src2 = new Rect();
        dst2 = new Rect();

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mWidth = getWidth();
        int mHeight = getHeight();

        int position = curPosition % mBitmapWidth;
        int height = Math.min(mBitmapHeight, mHeight);
        int width = Math.min(mWidth, mBitmapWidth - position);

        //        System.out.println("mWidth:" + mWidth + ",mBitmapWidth:" + mBitmapWidth + ",curPosition:" +
        //                curPosition);

        src1.set(position, 0, mWidth + position, height);
        dst1.set(0, 0, width, height);

        canvas.drawBitmap(mBitmap, src1, dst1, mPaint);

        if (mWidth > mBitmapWidth - position) {
            src2.set(0, 0, mWidth - width, height);
            dst2.set(width, 0, mWidth, height);
            canvas.drawBitmap(mBitmap, src2, dst2, mPaint);
        }
    }

    public void begin() {

        int mul = commonMultiple(mBitmapWidth, getWidth());

        int scale = mul / mBitmapWidth;

        System.out.println("=====scale====" + scale + ",====mul:" + mul);

        ValueAnimator animator = ValueAnimator.ofInt(0, mul);
        animator.setDuration(scale * 10000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curPosition = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

        animator.start();
    }

    private static int commonMultiple(int m, int n) {
        int scale = 0;

        if (m % 100 == 0 && n % 100 == 0) {
            scale = 2;
            m = m / 100;
            n = n / 100;
        } else if (m % 10 == 0 && n % 10 == 0) {
            scale = 1;
            m = m / 10;
            n = n / 10;
        }

        if (m > n) {
            int t = m;
            m = n;
            n = t;
        }

        for (int i = n; i <= m * n; i++) {
            if (i % m == 0 && i % n == 0) {
                int scaleResult = scale == 0 ? 1 : (int) Math.pow(10, scale);
                return i * scaleResult;
            }
        }
        return 0;
    }
}
