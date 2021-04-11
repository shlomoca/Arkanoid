package com.danielr_shlomoc.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Paddle {
    private final float MOVEMENT, TOP, BOTTOM;
    private final Paint PADDLE_PAINT;
    private float right, left;

    public Paddle(float x, float y, float width, float height, int color) {
        MOVEMENT = 15;
        TOP = y - height / 2;
        BOTTOM = y + height / 2;
        right = x + width / 2;
        left = x - width / 2;


        PADDLE_PAINT = new Paint();
        PADDLE_PAINT.setColor(color);
        PADDLE_PAINT.setStyle(Paint.Style.FILL);
    }


    public void move_right(float w) {
        float step = w - right;
        if (step > MOVEMENT)
            step = MOVEMENT;
        left += step;
        right += step;

    }

    public void move_left() {
        float step = left;
        if (step > MOVEMENT)
            step = MOVEMENT;
        left -= step;
        right -= step;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(left, TOP, right, BOTTOM, PADDLE_PAINT);
    }


    public void collides(Ball ball) {
        //test to see if the ball collided with the paddle
        ball.test_hit_rectangle(right, left, TOP, BOTTOM, true);
    }

}
