package com.danielr_shlomoc.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Brick {

    private final float LEFT, TOP, RIGHT, BOTTOM;
    private final Paint brickPaint;
    private boolean active;


    public Brick(float left, float top, float right, float bottom) {

        this.LEFT = left;
        this.TOP = top;
        this.RIGHT = right;
        this.BOTTOM = bottom;

        active = true;

        // brick pen
        brickPaint = new Paint();
        brickPaint.setColor(Color.YELLOW);
        brickPaint.setStyle(Paint.Style.FILL);
        brickPaint.setStrokeWidth(5);
    }

    public float getBOTTOM() {
        return BOTTOM;
    }

    public float getLEFT() {
        return LEFT;
    }

    public float getRIGHT() {
        return RIGHT;
    }

    public float getTOP() {
        return TOP;
    }

    /* tests if the ball collided with the brick.
    returns a int array with 2 slots that reference the location on the scale.
    [y between 1-(-1), x between 1-(-1)] */
    public int[] collided(Ball ball) {

        float ballX = ball.getX();
        float ballY = ball.getY();
        float radius = ball.getRadius();

        int[] arr = new int[]{0, 0};

        if (ballY + radius < TOP)
            arr[0] -= 1;

        if (ballY - radius > BOTTOM)
            arr[0] += 1;

        if (ballX + radius < LEFT)
            arr[1] -= 1;

        if (ballX - radius > RIGHT)
            arr[1] += 1;

        return arr;

    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(LEFT, TOP, RIGHT, BOTTOM, brickPaint);
    }

}
