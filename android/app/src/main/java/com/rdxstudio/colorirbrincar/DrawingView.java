package com.rdxstudio.colorirbrincar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawingView extends View {

    private Paint paint = new Paint();
    private ArrayList<Path> paths = new ArrayList<>();
    private Path currentPath = new Path();

    public DrawingView(Context context) {
        super(context);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(12f);
        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Path path : paths) {
            canvas.drawPath(path, paint);
        }
        canvas.drawPath(currentPath, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath.moveTo(x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                currentPath.lineTo(x, y);
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                paths.add(new Path(currentPath));
                currentPath.reset();
                invalidate();
                return true;
        }

        return false;
    }
}
