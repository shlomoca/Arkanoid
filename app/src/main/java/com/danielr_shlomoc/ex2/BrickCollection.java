package com.danielr_shlomoc.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class BrickCollection {

    private Brick[][] bricks;
    private int rows, cols;
    private float w,h;
    private Paint brickPaint;

    public BrickCollection(int rows, int cols, int height, int width) {

        bricks = new Brick[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.w =  width / cols;
        this.h =  height / 20;
        float left = 0, top = 250;

        Log.d("myLog","rows: "+rows+" cols: "+cols);
        // initialize the board bricks
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                bricks[i][j] = new Brick(left, top, (float) (left + this.w), (float) (top + this.h));
                left = (float) (left + this.w + 5);

            }
            left = 0;
            top = (float) (top + this.h + 5);
        }

        // brick pen
        brickPaint = new Paint();
        brickPaint.setColor(Color.WHITE);
        brickPaint.setStyle(Paint.Style.FILL);
        brickPaint.setStrokeWidth(5);

    }

    // This function draw the bricks on the given canvas.
    public void drawBricks(Canvas canvas) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                float left = this.bricks[i][j].getLeft();
                float top = this.bricks[i][j].getTop();
                float right = this.bricks[i][j].getRight();
                float bottom = this.bricks[i][j].getBottom();
                canvas.drawRect(left, top, right, bottom, brickPaint);
            }

        }
    }

    public Brick[][] getBricks() {
        return bricks;
    }

    public void setBricks(Brick[][] bricks) {
        this.bricks = bricks;
    }

    public float getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Paint getBrickPaint() {
        return brickPaint;
    }

    public void setBrickPaint(Paint brickPaint) {
        this.brickPaint = brickPaint;
    }
}
