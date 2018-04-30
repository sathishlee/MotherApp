package com.unicef.thaimai.motherapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.utility.Util;


/**
 * Created by Suthishan on 20/1/2018.
 */

public class DrawingView extends View {

    private boolean haveTouch = false;
    private Rect touchArea;
    private Paint paint;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedValue value = new TypedValue();
        context.getTheme ().resolveAttribute (R.attr.colorPrimary, value, true);
        int primary_color = value.data;

        paint = new Paint();
        paint.setColor(primary_color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Util.dpToPx(context,2));
        haveTouch = false;
    }

    public void setHaveTouch(boolean val, Rect rect) {
        haveTouch = val;
        touchArea = rect;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(haveTouch){
            canvas.drawRect(
                    touchArea.left, touchArea.top, touchArea.right, touchArea.bottom,
                    paint);
        }
    }
}
