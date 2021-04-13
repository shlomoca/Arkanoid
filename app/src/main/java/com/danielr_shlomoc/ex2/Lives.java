package com.danielr_shlomoc.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

public class Lives {
    private final Paint ALIVE, DEAD;
    private final Paint TEXT_PAINT;
    private final int NUM_OF_LIVES, LIVES_HEIGHT, LIVES_MARGIN, LIVES_RADIUS;
    private int game_lives;

    public Lives(int num_of_lives, Paint p, int ballColor) {
        NUM_OF_LIVES = num_of_lives;
        game_lives = num_of_lives;
        LIVES_HEIGHT = 30;
        LIVES_MARGIN = 30;
        LIVES_RADIUS = 30;

        ALIVE = new Paint();
        DEAD = new Paint();
        TEXT_PAINT = p;
        set_painters(ballColor);

    }

    private void set_painters(int ballColor){

        ALIVE.setColor(ballColor);
        ALIVE.setStrokeWidth(10);
        ALIVE.setStyle(Paint.Style.FILL);

        DEAD.setColor(ballColor);
        DEAD.setStrokeWidth(10);
        DEAD.setStyle(Paint.Style.STROKE);


    }

    public void draw(Canvas canvas) {
        int end_of_draw_location = canvas.getWidth();
        for (int i = 1; i <= NUM_OF_LIVES; i++) {
            Paint p = i <= NUM_OF_LIVES - game_lives ? DEAD : ALIVE;
            end_of_draw_location -= (LIVES_MARGIN*2 + LIVES_RADIUS);
            canvas.drawCircle(end_of_draw_location, LIVES_HEIGHT + LIVES_RADIUS, LIVES_RADIUS, p);
        }
        String text = "Lives:";
        int width = (int) TEXT_PAINT.measureText(text);
        canvas.drawText(text,end_of_draw_location-width-LIVES_MARGIN*2,LIVES_HEIGHT+50,TEXT_PAINT);

    }


    public Boolean died() {
        game_lives--;
        return game_lives == 0;
    }

    public int getGame_lives() {
        return game_lives;
    }
}
