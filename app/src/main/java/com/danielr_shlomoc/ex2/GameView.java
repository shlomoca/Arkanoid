package com.danielr_shlomoc.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class GameView extends View {

    private static final int GET_READY_STATE = 1, PLAYING_STATE = 2, GAME_OVER_STATE = 3;
    private static final int NUM_OF_LIVES = 3, BALL_SIZE = 20, PADDLE_HEIGHT = 150;
    private static int moves = 0;
    private final int ROWS, COLS;
    private final int bg_color, paddle_color;
    private int current_state, score, w, h;
    private Thread ball_thread;
    private boolean alive;
    private Paint textPaint, gameSituation;
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
        textPaint.setFakeBoldText(true);

        // game situation text pen
        gameSituation = new Paint();
        gameSituation.setColor(Color.GREEN);
        gameSituation.setTextSize(80);
        gameSituation.setFakeBoldText(true);
        gameSituation.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(bg_color);
        canvas.drawText("Score: " + score, 30, 80, textPaint);
        LIVES.draw(canvas);
        bricks.drawBricks(canvas);


//        switch (current_state) {
//            default:
//            case GET_READY_STATE:
//                break;
//            case PLAYING_STATE:
//                if (ball.move(getWidth(), getHeight()))
//                    if (LIVES.died())
//                        current_state = GAME_OVER_STATE;
//                    else
//                        init_game(false);
//                invalidate();
//                break;
//            case GAME_OVER_STATE:
//                break;
//        }
        ball.draw(canvas);
        bricks.drawBricks(canvas);
        paddle.draw(canvas);


        if (current_state == GET_READY_STATE)
            canvas.drawText("Click to PLAY!", getWidth() / 2, getHeight() / 2, gameSituation);


        if (current_state == GAME_OVER_STATE)
            canvas.drawText("GAME OVER - You Loss!", getWidth() / 2, getHeight() / 2, gameSituation);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        init_game(true);


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
                        init_game(true);
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

    private void init_game(boolean reset) {
        if (reset) {
            bricks = new BrickCollection(ROWS, COLS, h, w);
            LIVES = new Lives(NUM_OF_LIVES, textPaint);
            moves = 0;
        }
        play_ball();
        ball = new Ball((float) getWidth() / 2, (float) getHeight() - PADDLE_HEIGHT - BALL_SIZE, BALL_SIZE, Color.BLUE);
        paddle = new Paddle((float) getWidth() / 2, (float) getHeight() - PADDLE_HEIGHT, bricks.getW(), bricks.getH(), bricks.getW() / 2, paddle_color);
//        paddle = new Paddle((float) getWidth() / 2, (float) getHeight() - PADDLE_HEIGHT, bricks.getW() / 2, paddle_color);
        current_state = GET_READY_STATE;
    }

    public void play_ball() {
        //sets a timer in the app to show the user the amount of time he is playing
        if (ball_thread == null) {
            //if timer is already set
            alive = true;
            ball_thread = new Thread(new Runnable() {
                public void run() {
                    while (current_state != GAME_OVER_STATE) {
                        try {
                            Thread.sleep(10);
                            switch (current_state) {
                                default:
                                case GET_READY_STATE:
                                    break;
                                case PLAYING_STATE:
                                    if (paddle.collied(ball))
                                        Log.d("myLog", "collied ");
                                    if (ball.move(getWidth(), getHeight(), paddle.collied(ball), moves++))
                                        if (LIVES.died()) {
                                            current_state = GAME_OVER_STATE;

                                        } else {
                                            moves = 0;
                                            init_game(false);
                                        }


                                    break;
                                case GAME_OVER_STATE:
                                    break;
                            }
//                            if (ball.move(0, 0))
//                                if (LIVES.died())
//                                    current_state = GAME_OVER_STATE;
                            postInvalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    ball_thread = null;
                }
            });
            ball_thread.start();
            invalidate();
        }
    }

}
