package com.danielr_shlomoc.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class GameView extends View {

    private static final int GET_READY_STATE = 1, PLAYING_STATE = 2, GAME_OVER_STATE = 3;
    private static final int NUM_OF_LIVES = 3, BALL_SIZE = 20, PADDLE_HEIGHT=150;
    private final int ROWS, COLS;
    private final int bg_color, paddle_color;

    private int current_state, score, w, h;
    private Thread ball_thread;
    private boolean alive;
    private Paint textPaint;
    private BrickCollection bricks;
    private Ball ball;
    private Lives LIVES;
    private Paddle paddle;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bg_color = Color.BLACK;
        paddle_color = Color.GREEN;

        score = 0;

        // random number in range 2-6
        ROWS = (int) (Math.random() * 5) + 2;

        // random number in range 3-7
        COLS = (int) (Math.random() * 5) + 3;


        // text pen
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(bg_color);
        LIVES.draw(canvas);
        bricks.drawBricks(canvas);


        switch (current_state) {
            default:
            case GET_READY_STATE:
                break;
            case PLAYING_STATE:
                if (ball.move(getWidth(), getHeight()))
                    if (LIVES.died())
                        current_state = GAME_OVER_STATE;
                invalidate();
                break;
            case GAME_OVER_STATE:
                break;
        }
        ball.draw(canvas);
        bricks.drawBricks(canvas);
        paddle.draw(canvas);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;

        init_game();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float tx = event.getX();
        float ty = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                switch (current_state) {
                    case GAME_OVER_STATE:
                        init_game();
                    case GET_READY_STATE:
                    default:
                        current_state = PLAYING_STATE;
                        invalidate();
                        break;
                    case PLAYING_STATE:
                        if (tx < w / 2)
                            paddle.move_left();
                        else
                            paddle.move_right(w);

                }
                break;



            case MotionEvent.ACTION_UP:

                break;

        }
        return true;
    }

    private void init_game() {
        bricks = new BrickCollection(ROWS, COLS, h, w);
        ball = new Ball((float) getWidth() / 2, (float) getHeight() - PADDLE_HEIGHT, BALL_SIZE, Color.BLUE);
        LIVES = new Lives(NUM_OF_LIVES, textPaint);
        paddle = new Paddle((float) getWidth() / 2, (float) getHeight() - PADDLE_HEIGHT,bricks.getW()/2,paddle_color);
        current_state = GET_READY_STATE;
    }

    public void play_ball() {
        //sets a timer in the app to show the user the amount of time he is playing
        if (ball_thread == null) {
            //if timer is already set
            alive = true;
            ball_thread = new Thread(new Runnable() {
                public void run() {
                    while (alive) {
                        try {
                            Thread.sleep(1000);
                            if (ball.move(0, 0))
                                if (LIVES.died())
                                    current_state = GAME_OVER_STATE;
                            postInvalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    ball_thread = null;
                }
            });
            ball_thread.start();
        }
    }

}
