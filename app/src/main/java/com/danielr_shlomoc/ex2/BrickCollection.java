package com.danielr_shlomoc.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class BrickCollection {

    private final Brick[][] bricks;
    private final float rows, cols, minimum, maximum;
    private final float w, h, brickWidth, brickHeight;
    private Paint brickPaint;

    public BrickCollection(int rows, int cols, int height, int width) {
        h = height;
        w = width;
        minimum = 250;
        bricks = new Brick[rows][cols];
        this.rows = rows;
        this.cols = cols;
        brickWidth = (float) (width / cols);
        brickHeight = (float) (height / 20);
        float left = 0, top = minimum;

        // initialize the board bricks
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                bricks[i][j] = new Brick(left, top, left + brickWidth, top + brickHeight);
                left += brickWidth + 5;
            }
            left = 0;
            top += brickHeight + 5;
        }
        maximum = top;
    }

    public void collides(Ball ball) {
//        int i = (int) rows / 2, j = (int) cols / 2;
        int i = 0, j = 0;
        boolean inBlocksArea = ball.test_hit_rectangle(w, 0, minimum, maximum, false);
        while (inBlocksArea) {
            if (i >= rows || i < 0 || j >= cols || j < 0){

            Log.i("shlomoLog", "test out of bound "+i +" j: " + j);
                break;
            }

            Log.i("shlomoLog", "i: "+i +" j: " + j);
            Brick brick = bricks[i][j];
            boolean active = brick.getActive();

            int[] location = brick.collided(ball);
            if (location != null) {
                if (location[0] == 0 && location[1] == 0) {
                    if (active) {
                        brick.setActive(false);
                        ball.hit_rectangle(brick.getRIGHT(), brick.getLEFT(), brick.getTOP(), brick.getBOTTOM());
                    } else //hit non active brick
                        break;
                }
                i += location[0];
                j += location[1];
            } else {
                break;
            }
        }


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

    public float getBrickHeight() {
        return brickHeight;
    }

    public float getBrickWidth() {
        return brickWidth;
    }

    public Paint getBrickPaint() {
        return brickPaint;
    }

    public void setBrickPaint(Paint brickPaint) {
        this.brickPaint = brickPaint;
    }
}
