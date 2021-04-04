package com.danielr_shlomoc.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class GameView extends View {

    private static final int GET_READY_STATE = 1;
    private static final int PLAYING_STATE = 2;
    private static final int GAME_OVER_STATE = 3;
    private final int ROWS;
    private final int COLS;
    private int lives, score;
    private Paint textPaint;
    private BrickCollection bricks;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        lives = 3;
        score = 0;

        // random number in range 2-6
        ROWS = (int) (Math.random() * 5) + 2;

        // random number in range 3-7
        COLS = (int) (Math.random() * 5) + 3;


        // text pen
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        bricks.drawBricks(canvas);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bricks = new BrickCollection(ROWS, COLS, h, w);
    }


}
