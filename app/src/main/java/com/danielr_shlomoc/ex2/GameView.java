package com.danielr_shlomoc.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class GameView extends View {

    private static final int GET_READY_STATE = 1,PLAYING_STATE = 2, GAME_OVER_STATE = 3;
    private static final int NUM_OF_LIVES = 3, BALL_SIZE = 20, PADDLE_RADIUS = 10;
    private final int ROWS, COLS;
    private int bg_color, current_state, score;
    private Ball b;
    private Thread ball_thread;
    private boolean alive;
    private Paint textPaint;
    private BrickCollection bricks;
    private Lives L;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bg_color = Color.YELLOW;


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
        canvas.drawColor(bg_color);
        L.draw(canvas);
        switch (current_state){
            default:
            case GET_READY_STATE:
                break;
            case PLAYING_STATE:
                b.move(getWidth(), getHeight());
                invalidate();
                break;
            case GAME_OVER_STATE:


        bricks.drawBricks(canvas);
                break;
        }
        b.draw(canvas);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bricks = new BrickCollection(ROWS, COLS, h, w);
        init_game();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float tx = event.getX();
        float ty = event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            L.died();
            invalidate();
                if(current_state == GET_READY_STATE || current_state == GAME_OVER_STATE)
                {
                    current_state = PLAYING_STATE;
                    invalidate();
                }
//                else
//                {
//                    // check if red ball catch for dragging
//                    if(!isDraging && redBall.isInside(tx,ty))
//                        isDraging = true;
//                }
                break;

            case MotionEvent.ACTION_MOVE:
                //Log.d("mylog", "MotionEvent.ACTION_MOVE ");

//                if(isDraging)
//                {
//                    redBall.setX(tx);
//                    redBall.setY(ty);
//                }
                break;

            case MotionEvent.ACTION_UP:
                ///Log.d("mylog", "MotionEvent.ACTION_UP ");
//                isDraging = false;
                break;

        }
        return true;
    }

    private void init_game(){
        b = new Ball( (float) getWidth()/2,getHeight() - (PADDLE_RADIUS*2+10) ,BALL_SIZE,Color.BLUE);
        L = new Lives(NUM_OF_LIVES);
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
                            b.move(0, 0);
                            invalidate();
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
