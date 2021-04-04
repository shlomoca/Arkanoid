package com.danielr_shlomoc.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Paddle {
    private final float RADIUS, MOVEMENT;
    private final Paint PADDLE_PAINT;
    private float x, y;

    public Paddle(float x, float y, float radius, int color) {
        MOVEMENT = 100;
        this.RADIUS = radius;
        this.x = x;
        this.y = y;


        PADDLE_PAINT = new Paint();
        PADDLE_PAINT.setColor(color);
        PADDLE_PAINT.setStrokeWidth(10);
        PADDLE_PAINT.setStyle(Paint.Style.FILL);
    }

    public float getRADIUS() {
        return RADIUS;
    }

    public void move_right(float w) {
        float target = x + MOVEMENT;
        if (target + RADIUS > w)
            x = w - RADIUS;
        else
            x = target;
    }

    public void move_left() {
        float target = x - MOVEMENT;
        if (target - RADIUS < 0)
            x = RADIUS;
        else
            x = target;
    }

    public void draw(Canvas canvas) {
        canvas.drawLine(x - RADIUS, y, x + RADIUS, y, PADDLE_PAINT);
    }
}
