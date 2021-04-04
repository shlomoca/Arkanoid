package com.danielr_shlomoc.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Paddle {
    private final float RADIUS, MOVEMENT;
    private final Paint PADDLE_PAINT;
    private float x, y, w, h;

    public Paddle(float x, float y, float width, float height, float radius, int color) {
        MOVEMENT = 100;
        this.RADIUS = radius;
        this.x = x;
        this.y = y;
        w = width;
        h = height;


        PADDLE_PAINT = new Paint();
        PADDLE_PAINT.setColor(color);
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
//        canvas.drawLine(x - RADIUS, y, x + RADIUS, y, PADDLE_PAINT);
        canvas.drawRect(x - this.RADIUS, y, x - this.RADIUS + w, y + h / 2, PADDLE_PAINT);
    }

    public boolean collied(Ball ball) {

        float xBallCord = ball.getX();
        float yBallCord = ball.getY();
        float ballRadius = ball.getRadius();

        if ((xBallCord >= x - this.RADIUS && xBallCord <= x - this.RADIUS + w) && (yBallCord+ballRadius == y))
            return true;
        return false;
    }

}
