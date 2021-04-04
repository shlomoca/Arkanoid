package com.danielr_shlomoc.ex2;

public class Brick {

    private float left, top,right,bottom;

    public Brick(float left, float top, float right,float bottom) {

        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom= bottom;
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }
}
