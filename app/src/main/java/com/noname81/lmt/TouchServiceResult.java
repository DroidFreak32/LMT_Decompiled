package com.noname81.lmt;

public class TouchServiceResult {
    static final int Delete = 2;
    static final int Diamond = 1;
    static final int DiamondDouble = 13;
    static final int Questionmark = 22;
    static final int QuestionmarkDouble = 11;
    static final int Square = 0;
    static final int SquareDouble = 12;
    static final int SwipeDown = 17;
    static final int SwipeDownDouble = 6;
    static final int SwipeDownUp = 21;
    static final int SwipeDownUpDouble = 10;
    static final int SwipeLeft = 15;
    static final int SwipeLeftDouble = 4;
    static final int SwipeLeftRight = 19;
    static final int SwipeLeftRightDouble = 8;
    static final int SwipeRight = 14;
    static final int SwipeRightDouble = 3;
    static final int SwipeRightLeft = 18;
    static final int SwipeRightLeftDouble = 7;
    static final int SwipeUp = 16;
    static final int SwipeUpDouble = 5;
    static final int SwipeUpDown = 20;
    static final int SwipeUpDownDouble = 9;
    static final int Unknown = 23;
    static final String[] captions = {"Starting point is upper left corner", "Starting point is at the bottom", "Starting point is top left", "Swipe from left to right", "Swipe from right to left", "Swipe from bottom to top", "Swipe from top to bottom", "Swipe right and then left", "Swipe left and then right", "Swipe up and then down", "Swipe down and then up", "Starting point is top left", "Starting point is upper left corner", "Starting point is at the bottom"};
    static final String[] names = {"Square", "Diamond", "Delete", "SwipeRightDouble", "SwipeLeftDouble", "SwipeUpDouble", "SwipeDownDouble", "SwipeRightLeftDouble", "SwipeLeftRightDouble", "SwipeUpDownDouble", "SwipeDownUpDouble", "QuestionmarkDouble", "SquareDouble", "DiamondDouble"};
    private int gesture;
    private String name;
    private int overlayID;
    private float score;
    private float startX;
    private float startY;

    TouchServiceResult(int gesture2, String name2, float score2, int overlayID2, float startX2, float startY2) {
        this.gesture = gesture2;
        this.name = name2;
        this.score = score2;
        this.overlayID = overlayID2;
        this.startX = startX2;
        this.startY = startY2;
    }

    public String toString() {
        return String.format("Gesture: %s\nScore: %.2f percent\nStartX: %.2f\nStartY: %.2f", this.name, Float.valueOf(this.score * 100.0f), Float.valueOf(this.startX), Float.valueOf(this.startY));
    }

    String toDebugString() {
        return String.format("Gesture: %s, score: %.2f, startX: %.2f, startY: %.2f", this.name, Float.valueOf(this.score * 100.0f), Float.valueOf(this.startX), Float.valueOf(this.startY));
    }

    int getGesture() {
        return this.gesture;
    }

    void setGesture(int gesture2) {
        this.gesture = gesture2;
    }

    String getName() {
        return this.name;
    }

    void setName(String name2) {
        this.name = name2;
    }

    float getScore() {
        return this.score;
    }

    void setScore(float score2) {
        this.score = score2;
    }

    int getOverlayID() {
        return this.overlayID;
    }

    void setOverlayID(int overlayID2) {
        this.overlayID = overlayID2;
    }

    float getStartX() {
        return this.startX;
    }

    void setStartX(float startX2) {
        this.startX = startX2;
    }

    float getStartY() {
        return this.startY;
    }

    void setStartY(float startY2) {
        this.startY = startY2;
    }
}