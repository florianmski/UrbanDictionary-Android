package com.florianmski.urbandictionary.ui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.florianmski.urbandictionary.R;

public class RibbonView extends View
{
    private Path path;
    private Paint ribbonPaint;
    private Paint textPaint;

    private int position = -1;

    public RibbonView(Context context)
    {
        super(context);
        init();
    }

    public RibbonView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RibbonView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        path = new Path();

        ribbonPaint = new Paint();
        ribbonPaint.setStyle(Paint.Style.FILL);
        ribbonPaint.setColor(getResources().getColor(R.color.accent));

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setFakeBoldText(true);
        textPaint.setTextSize(getResources().getDimension(R.dimen.ribbon_view_text_size));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(getResources().getColor(R.color.primary));
    }

    public void setPosition(int position)
    {
        this.position = position;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        float height = getHeight();
        float width = getWidth();
        float size = (height / 5f) * 4;

        path.reset();
        path.moveTo(0, 0);
        path.lineTo(0, height);
        path.lineTo(width / 2f, size);
        path.lineTo(width, height);
        path.lineTo(width, 0);
        path.close();
        canvas.drawPath(path, ribbonPaint);

        if(position != -1)
        {
            String text = String.valueOf(position);
            canvas.drawText(text, getWidth() / 2f, getHeight() / 2f, textPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, (int) (width * 2f));
    }
}
