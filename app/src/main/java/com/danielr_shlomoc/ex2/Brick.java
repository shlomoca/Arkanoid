package com.danielr_shlomoc.ex2;

public class Brick {

    private float left, top, right, bottom;


    public Brick(float left, float top, float right, float bottom) {

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public int[] collides(Ball ball) {

        float ballX = ball.getX();
        float ballY = ball.getY();
        float radius = ball.getRadius();

        int arr[] = {0, 0};

        if (ballY + radius < top)
            arr[0] -= 1;

        if (ballY - radius > bottom)
            arr[0] += 1;

        if (ballX + radius < left)
            arr[1] -= 1;

        if (ballX - radius > right)
            arr[1] += 1;

        return arr;


    }

    public float getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public float getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }
}
