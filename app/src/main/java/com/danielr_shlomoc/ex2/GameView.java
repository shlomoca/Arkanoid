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
    private Paint p;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        p = new Paint();
        p.setColor(Color.YELLOW);
        p.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(getWidth()/2,getHeight()/2,100,p);
        canvas.drawColor(Color.YELLOW);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    public void drawObj(Paint p){


    }
}
