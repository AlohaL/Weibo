package com.sina.weibo.sdk.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;

/**
 * 屏幕相关的辅助类
 */
public class ScreenUtils {

    public static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int spToPx(Context context, float sp) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

    public static float pxToDp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dpToPxInt(Context context, float dp) {
        return (int) (dpToPx(context, dp) + 0.5f);
    }

    public static float pxToDpCeilInt(Context context, float px) {
        return (int) (pxToDp(context, px) + 0.5f);
    }

    public static int calculateGridViewColumns(Context context, int itemWidth, int itemMargin, int gridLeftMargin, int gridRightMargin) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int gridWidth = point.x - (int) dpToPx(context, gridLeftMargin + gridRightMargin);

        int columns = (gridWidth / (int) dpToPx(context, itemWidth + itemMargin));

        if (columns <= 0) {
            columns = 1;
        } else if (columns > 3) {
            columns = 3;
        }

        return columns;
    }

}
