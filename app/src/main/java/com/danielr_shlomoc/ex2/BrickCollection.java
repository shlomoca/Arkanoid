package com.danielr_shlomoc.ex2;

import android.graphics.Canvas;

public class BrickCollection {

    private final Brick[][] bricks;
    private final float rows, cols, minimum, maximum;
    private final float w;
    private final float brickWidth;
    private final float brickHeight;
    private int alive_bricks;
    private boolean gameOver;


    public BrickCollection(int rows, int cols, int height, int width) {
        w = width;
        alive_bricks = rows * cols;
        gameOver = false;
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
        //test if the ball is in the rectangle and if so test to see if it hit an active block
        int i = 0, j = 0;
        boolean inBlocksArea = ball.test_hit_rectangle(w, 0, minimum, maximum, false);
        while (inBlocksArea) {
            if (i >= rows || i < 0 || j >= cols || j < 0)
                break;

            Brick brick = bricks[i][j];
            boolean active = brick.getActive();

            int[] location = brick.collided(ball);
            if (location != null) {
                if (location[0] == 0 && location[1] == 0) {
                    if (active) {
                        brick.setActive(false);
                        ball.hit_rectangle(brick.getRIGHT(), brick.getLEFT(), brick.getTOP(), brick.getBOTTOM());
                        alive_bricks --;
                        if (alive_bricks==0)
                            gameOver=true;
                    } else //hit non active brick stop looking for the brick
                        break;
                }
                i += location[0];
                j += location[1];
            } else {
                inBlocksArea = false;
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

    public boolean getGameOver() {
        return gameOver;
    }
}
