package com.danielr_shlomoc.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;

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

    //test if the ball is in the rectangle and if so test to see if it hit an active block
    public int collides(Ball ball, Context context, MediaPlayer mp) {
        int i = 0, j = 0, score = 0;
        boolean inBlocksArea = ball.test_hit_rectangle(w, 0, minimum, maximum, false);
        if (inBlocksArea) {
            for (i = 0; i < rows; i++) {
                for (j = 0; j < cols; j++) {
                    Brick brick = bricks[i][j];
                    boolean active = brick.getActive();
                    if (active) {
                        int[] location = brick.collided(ball);
                        if (location[0] == 0 && location[1] == 0) {
                            brick.setActive(false);
                            ball.hit_rectangle(brick.getRIGHT(), brick.getLEFT(), brick.getTOP(), brick.getBOTTOM());
                            playSound(context);
                            score += 5;
                            alive_bricks--;
                            if (alive_bricks == 0)
                                gameOver = true;
                        }
                    }

                }
            }
        }
        return score;


    }

    //play hit sound
    private void playSound(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.break_sound);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();

            }

        });
    }

    // This function draw the bricks on the given canvas.
    public void drawBricks(Canvas canvas) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Brick brick = this.bricks[i][j];
                if (brick.getActive())
                    brick.draw(canvas);
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
