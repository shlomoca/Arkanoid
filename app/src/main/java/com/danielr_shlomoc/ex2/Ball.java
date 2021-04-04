package com.danielr_shlomoc.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.Random;

public class Ball
{
    private float x,y,dx,dy;
    private final float radius;
    private final Paint ballPaint;


    public Ball(float x, float y, float radius, int color)
    {
        float movement = 10;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.dx = -movement;
        this.dy = movement;

        ballPaint = new Paint();
        ballPaint.setColor(color);
        ballPaint.setStrokeWidth(10);
        ballPaint.setStyle(Paint.Style.FILL);
    }

    public void move(int w, int h)
    {
        x = x + dx;
        y = y + dy;

        // check border left or right
        if(x-radius<0 || x+radius>w)
            dx = -dx;

        // bottom or top
        if(y+radius>h || y-radius<0)
            dy = -dy;
    }

    public float getRadius() {
        return radius;
    }

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawCircle(x, y, radius, ballPaint);
    }

    public boolean collideWith(Ball other)
    {
        double dist = Math.sqrt((this.x - other.x)*(this.x - other.x) + (this.y - other.y)*(this.y - other.y));

        if(dist<(this.radius+other.radius))
            return true;
        return false;
    }

    public boolean isInside(float tx, float ty)
    {
        double dist = Math.sqrt((this.x - tx)*(this.x - tx) + (this.y - ty)*(this.y - ty));

        if(dist<(this.radius))
            return true;
        return false;
    }

    public void changeRndColor()
    {
        ballPaint.setColor(new Random().nextInt());
    }


}

