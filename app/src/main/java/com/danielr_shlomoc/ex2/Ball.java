package com.danielr_shlomoc.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public class Ball {
    private final float radius;
    private final Paint ballPaint;
    private float x, y, dx, dy;


    public Ball(float x, float y, float radius, int color) {
        float movement = -10;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.dx = movement;
        this.dy = movement;

        ballPaint = new Paint();
        ballPaint.setColor(color);
        ballPaint.setStrokeWidth(10);
        ballPaint.setStyle(Paint.Style.FILL);
    }

    public boolean move(int w, int h) {
        //moves the ball in the dx dy direction. If the ball hit the ground then return true
        x = x + dx;
        y = y + dy;
        hit_borders(w,h);

        return y + radius > h;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public void hit_rectangle(float right, float left, float top, float bottom){
        /* test where the rectangle hit the ball and shift its direction*/
        if(y >= bottom || y <= top)
            dy = -dy;
        if( x >= right || x <= left)
            dx = -dx;
    }
    private void hit_borders(float w, float h){
        /* test if the ball hit borders and move accordingly */
        // check border left or right
        if (x - radius < 0 || x + radius > w )
            dx = -dx;

        // bottom or top
        if (y + radius > h || y - radius < 0){
            dy = -dy;
        }

    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, ballPaint);
    }

    public boolean collideWith(Paddle paddle, BrickCollection collection){

        return false;
    }

}

