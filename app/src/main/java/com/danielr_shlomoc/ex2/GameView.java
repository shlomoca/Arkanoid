package com.danielr_shlomoc.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static java.lang.Thread.sleep;


public class GameView extends View {

    private static final int GET_READY_STATE = 1, PLAYING_STATE = 2, GAME_OVER_STATE = 3;
    private static final int NUM_OF_LIVES = 3, PADDLE_HEIGHT = 150;
    private static String gameOverText;
    private final int ROWS, COLS;
    private final int bg_color, paddle_color;
    private int current_state, score, w, h, ballRadius;
    private MediaPlayer mediaPlayer;
    private boolean paddle_move, paddle_direction;
    private Thread ball_thread, paddle_thread;
    private Paint textPaint, gameSituation;
    private BrickCollection bricks;
    private Ball ball;
    private Lives LIVES;
    private Paddle paddle;
    private Context context;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bg_color = Color.BLACK;
        paddle_color = Color.GREEN;
        this.context = context;

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
        ball.draw(canvas);
        bricks.drawBricks(canvas);
        paddle.draw(canvas);
        LIVES.draw(canvas);
        String text = "";

        switch (current_state) {
            case GET_READY_STATE:
                text = "Click to PLAY!";
                break;
            case GAME_OVER_STATE:
                text = "GAME OVER - " + gameOverText;
                break;

        }

        canvas.drawText(text, (float) getWidth() / 2, (float) getHeight() / 2, gameSituation);


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
        Log.d("touchTest"," here the situation been to every nation");

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                paddle_move=true;
                move_paddle();
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
                        paddle_direction = tx >= (float) w / 2;
                        break;

                }
                break;

            case MotionEvent.ACTION_UP:
                paddle_move=false;
                break;
        }
        return true;
    }

    private void init_game(boolean reset) {
        if (reset) {
            bricks = new BrickCollection(ROWS, COLS, h, w);
            LIVES = new Lives(NUM_OF_LIVES, textPaint);
            score = 0;
            stopPlaying();
            mediaPlayer =  MediaPlayer.create(context,R.raw.break_sound);
        }
        play_ball();
        ballRadius = (int) bricks.getBrickHeight() / 2;
        ball = new Ball((float) getWidth() / 2, (float) getHeight() - PADDLE_HEIGHT - ballRadius - 20, ballRadius, Color.BLUE);
        paddle = new Paddle((float) getWidth() / 2, (float) getHeight() - PADDLE_HEIGHT, bricks.getBrickWidth(), bricks.getBrickHeight() / 2, paddle_color);
        current_state = GET_READY_STATE;
    }

    private void stopPlaying() {
        //stop the music from playing
        if (mediaPlayer != null) {

            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void play_ball() {
        //sets a timer in the app to show the user the amount of time he is playing
        if (ball_thread == null) {
            //if timer is already set
            ball_thread = new Thread(new Runnable() {
                public void run() {
                    while (current_state != GAME_OVER_STATE) {
                        try {
                            sleep(10);
                            switch (current_state) {
                                default:
                                case GET_READY_STATE:
                                case GAME_OVER_STATE:
                                    break;
                                case PLAYING_STATE:
                                    // the case that the ball hit the ground.
                                    if (ball.move(getWidth(), getHeight())) {
                                        if (LIVES.died()) {
                                            gameOverText = "You Loss!";
                                            current_state = GAME_OVER_STATE;
                                            stopPlaying();

                                        } else
                                            init_game(false);
                                    }
                                    // check if ball touch paddle or brick.
                                    else {
                                        // check if ball hit the paddle
                                        paddle.collides(ball);

                                        // check if the ball hit brick and return the points.
                                        int temp = bricks.collides(ball, context,mediaPlayer);
                                        if (temp != 0)
                                            score += temp * LIVES.getGame_lives();


                                        if (bricks.getGameOver()) {
                                            gameOverText = "You Win!";
                                            current_state = GAME_OVER_STATE;
                                        }
                                    }
                                    break;

                            }

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

    public void move_paddle() {
        if (paddle_thread == null) {
            paddle_thread = new Thread(new Runnable() {
                public void run() {
                    while (paddle_move) {
                        try {
                            sleep(10);
                            if (paddle_direction)
                                paddle.move_right(w);
                            else
                                paddle.move_left();
                            postInvalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    paddle_thread = null;
                }
            });
            paddle_thread.start();
        }
    }

}
