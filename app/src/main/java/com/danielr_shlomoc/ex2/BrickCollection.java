package com.danielr_shlomoc.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BrickCollection {

    private final Brick[][] bricks;
    private final float rows, cols,minimum,maximum;
    private final float w, h;
    private Paint brickPaint;

    public BrickCollection(int rows, int cols, int height, int width) {

        minimum = 250;
        bricks = new Brick[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.w = (float) (width / cols);
        this.h = (float) (height / 20);
        float left = 0, top = minimum;

        // initialize the board bricks
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                bricks[i][j] = new Brick(left, top,  left + this.w, top + this.h);
                left += this.w + 5;
            }
            left = 0;
            top += this.h + 5;
        }
        maximum = top;
    }

    public int[] collides(Ball ball) {
        int top = (int) rows, bottom = 0, left = (int) cols ,right = 0 ;
        float ballX= ball.getX(), ballY = ball.getY(), ballRadius = ball.getRadius();
        int i = top/2,j = left/2;

        while(true){
            Brick brick = bricks[i][j];
            boolean active = brick.getActive();

            int [] location = brick.collided(ball);
            if(location[0]==0 && location[1]==0){
                if(active){
                    brick.setActive(false);
                    ball.hit_rectangle(brick.getRIGHT(),brick.getLEFT(),brick.getTOP(),brick.getBOTTOM());
                }
                else //hit non active brick
                    return null;
            }
            if( location[0]==1){//

            }
            break;
        }
        return null;

    }
    // This function draw the bricks on the given canvas.
    public void drawBricks(Canvas canvas) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Brick brick = this.bricks[i][j];
                if (brick.getActive())
                    brick.drew(canvas);
            }
        }
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }

    public Paint getBrickPaint() {
        return brickPaint;
    }

    public void setBrickPaint(Paint brickPaint) {
        this.brickPaint = brickPaint;
    }
}
