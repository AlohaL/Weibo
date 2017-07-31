package com.sina.weibo.sdk.demo.view;


import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.sina.weibo.sdk.demo.R;

public class ScalableImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener,
        View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {

    public static final float SCALE_MAX = 8.0f;
    private static final float SCALE_MID = 2.0f;
    private float initScale = 1.0f;
    private boolean once = true;
    private final float[] matrixValues = new float[9];
    private ScaleGestureDetector mScaleGestureDetector = null;
    private final Matrix mScaleMatrix = new Matrix();
    private GestureDetector mGestureDetector;
    private boolean isAutoScale;
    private int mTouchSlop;
    private float mLastX;
    private float mLastY;
    private boolean isCanDrag;
    private int lastPointerCount;
    private boolean isCheckTopAndBottom = true;
    private boolean isCheckLeftAndRight = true;

    public ScalableImageView(Context context) {
        this(context, null);
    }

    public ScalableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        setBackgroundColor(getResources().getColor(R.color.black));
        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (singleTapListener != null) {
                            singleTapListener.singleTapped();
                        }
                        return true;
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        if (isAutoScale == true)
                            return true;

                        float x = e.getX();
                        float y = e.getY();

                        if (getScale() < SCALE_MID) {
                            ScalableImageView.this.postDelayed(
                                    new AutoScaleRunnable(SCALE_MID, x, y), 16);
                            isAutoScale = true;
                        } else if (getScale() >= SCALE_MID
                                && getScale() < SCALE_MAX) {
                            ScalableImageView.this.postDelayed(
                                    new AutoScaleRunnable(SCALE_MAX, x, y), 16);
                            isAutoScale = true;
                        } else {
                            ScalableImageView.this.postDelayed(
                                    new AutoScaleRunnable(initScale, x, y), 16);
                            isAutoScale = true;
                        }

                        return true;
                    }
                });
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
    }


    private class AutoScaleRunnable implements Runnable {
        static final float BIGGER = 1.07f;
        static final float SMALLER = 0.93f;
        private float mTargetScale;
        private float tmpScale;
        private float x;
        private float y;

        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.mTargetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
            } else {
                tmpScale = SMALLER;
            }

        }

        @Override
        public void run() {
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
            final float currentScale = getScale();
            if (((tmpScale > 1f) && (currentScale < mTargetScale))
                    || ((tmpScale < 1f) && (mTargetScale < currentScale))) {
                ScalableImageView.this.postDelayed(this, 16);
            } else {
                final float deltaScale = mTargetScale / currentScale;
                mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }

        }
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();
        if (getDrawable() == null)
            return true;
        if ((scale < SCALE_MAX && scaleFactor > 1.0f) || (scale > initScale && scaleFactor < 1.0f)) {
            if (scaleFactor * scale < initScale) {
                scaleFactor = initScale / scale;
            }
            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;
            }
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }
        return true;

    }

    private void checkBorderAndCenterWhenScale() {

        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        if (rect.width() < width) {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height) {
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);

    }

    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event))
            return true;
        mScaleGestureDetector.onTouchEvent(event);
        float x = 0, y = 0;
        final int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;
        if (pointerCount != lastPointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }

        lastPointerCount = pointerCount;
        RectF rectF = getMatrixRectF();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!isCanDrag) {
                    isCanDrag = isCanDrag(dx, dy);
                }
                if (isCanDrag) {


                    if (getDrawable() != null) {

                        if (getMatrixRectF().left == 0 && dx > 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }

                        if (getMatrixRectF().right == getWidth() && dx < 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        if (rectF.width() < getWidth()) {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        }
                        if (rectF.height() < getHeight()) {
                            dy = 0;
                            isCheckTopAndBottom = false;
                        }

                        mScaleMatrix.postTranslate(dx, dy);
                        checkMatrixBounds();
                        setImageMatrix(mScaleMatrix);
                    }
                }


                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointerCount = 0;
                break;
        }

        return true;
    }

    public final float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if (once) {
            Drawable d = getDrawable();
            if (d == null)
                return;
            int width = getWidth();
            int height = getHeight();
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();
            float scale = 1.0f;
            if (dw > dh) {//图片宽大于高 ；手机宽都是小于高的，所以不需要比较屏幕和图片的宽高比例
                scale = width * 1.0f / dw;
            } else {
                if ((height * 1.0f / width) <= (dh * 1.0f / dw))
                    scale = height * 1.0f / dh;
                else
                    scale = width * 1.0f / dw;
            }
            initScale = scale;
            mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
            mScaleMatrix.postScale(scale, scale, getWidth() / 2,
                    getHeight() / 2);
            setImageMatrix(mScaleMatrix);
            once = false;
        }

    }

    private void checkMatrixBounds() {
        RectF rect = getMatrixRectF();

        float deltaX = 0, deltaY = 0;
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        if (rect.top > 0 && isCheckTopAndBottom) {
            deltaY = -rect.top;
        }
        if (rect.bottom < viewHeight && isCheckTopAndBottom) {
            deltaY = viewHeight - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight) {
            deltaX = -rect.left;
        }
        if (rect.right < viewWidth && isCheckLeftAndRight) {
            deltaX = viewWidth - rect.right;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }


    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }


    private SingleTapListener singleTapListener;

    public interface SingleTapListener {
        public void singleTapped();
    }


    public void setSingleTapListener(SingleTapListener sListener) {
        singleTapListener = sListener;
    }


}