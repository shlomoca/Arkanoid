package com.danielr_shlomoc.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Ball {
    private final float radius;
    private final Paint ballPaint;
    private float x, y, dx, dy;
    private boolean hit;


    public Ball(float x, float y, float radius, int color) {
        float movement = 15;
        this.radius = radius;
        this.x = x;
        this.y = y;
        float angle;
//        this.dx = getRandomFloat(true);
//        this.dy = getRandomFloat(false);

        // enable angle of 120 except angle of 90
        Random rand = new Random();
        do {
            angle = 30 + rand.nextFloat() * (150 - 30);
        } while (angle >= 85 && angle <= 95);
        Log.d("angle", angle + "");

        this.dx = (float) (movement * Math.cos(Math.toRadians(angle)));
        this.dy = (float) (-movement * (float) (Math.sin(Math.toRadians(angle))));
        Log.d("angle", "x-velocity: " + this.dx + "\ny-velocity: " + this.dy);

        hit = false;

        ballPaint = new Paint();
        ballPaint.setColor(color);
        ballPaint.setStrokeWidth(10);
        ballPaint.setStyle(Paint.Style.FILL);
    }

    public boolean move(int w, int h) {
        //moves the ball in the dx dy direction. If the ball hit the ground then return true
        x = x + dx;
        y = y + dy;
        hit_borders(w, h);

        return y + radius > h;
    }

    private float getRandomFloat(boolean isX) {
        Random rand = new Random();
        float miniMax = 10;
        float res = rand.nextFloat() * (radius);

        res -= radius / 2;
        if (res > 0 && res < miniMax)
            res = miniMax;
        if (res < 0 && res > -miniMax)
            res = -miniMax;
        return res;


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

    public boolean test_hit_rectangle(float right, float left, float top, float bottom, boolean moveBall) {
        boolean pastRight = x + radius >= left, pastLeft = x - radius <= right, pastTop = y + radius >= top, pastBottom = y - radius <= bottom;
        if (pastRight && pastLeft && pastTop && pastBottom) {
            if (hit && moveBall) {
                Log.d("ShlomoDebug", "ball hit already so no move");
            }
            if (moveBall && !hit) {
                hit_rectangle(right, left, top, bottom);
                hit = true;
            }

            return true;
        }
        if (moveBall)
            hit = false;
        return false;
    }

    public void hit_rectangle(float right, float left, float top, float bottom) {
        /* test where the rectangle hit the ball and shift its direction*/
        if (y >= bottom || y <= top)
            dy = -dy;
        if (x >= right || x <= left){
            Log.d("blof","x: "+x+"\nleft: "+left+", right: "+right);
            dx = -dx;
        }
    }

    private void hit_borders(float w, float h) {
        /* test if the ball hit borders and move accordingly */
        // check border left or right
        if (x - radius < 0 || x + radius > w)
            dx = -dx;

        // bottom or top
        if (y + radius > h || y - radius < 0) {
            dy = -dy;
        }

    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, ballPaint);
    }



}

