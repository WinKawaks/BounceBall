package cn.xxx.winkawaks.bounceball.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import cn.xxx.winkawaks.bounceball.module.game.GameActivity;
import cn.xxx.winkawaks.bounceball.module.sound.SoundPlayer;

public class Rectangle extends View {
    public static final int MAX_SIZE = 40;
    private static final int ALPHA = 255;
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;
    private int mCoordX = 0;
    private int mCoordY = 0;
    private int mRealSize = 40;
    private int mSpeedX = 3;
    private int mSpeedY = 3;

    private boolean goRight = true;
    private boolean goDown = true;
    private DrawView mDrawView;
    private View tab1, tab2;

    private Paint mInnerPaint;
    private RectF mDrawRect;
    private Context mContext;
    private Boolean soundOn;
    private SoundPlayer soundPlayer;

    public Rectangle(Context context, DrawView drawView, View tab1, View tab2, Boolean soundOn, SoundPlayer soundPool) {
        super(context);
        mContext = context;
        mDrawView = drawView;
        this.tab1 = tab1;
        this.tab2 = tab2;
        this.soundOn = soundOn;
        this.soundPlayer = soundPool;
        mInnerPaint = new Paint();
        mDrawRect = new RectF();

        mInnerPaint.setARGB(ALPHA, 0, 0, 0);
        mInnerPaint.setAntiAlias(true);
    }

    public void setARGB(int a, int r, int g, int b) {
        mInnerPaint.setARGB(a, r, g, b);
    }

    public void setX(int newValue) {
        mCoordX = newValue;
    }

    public float getX() {
        return mCoordX;
    }

    public void setY(int newValue) {
        mCoordY = newValue;
    }

    public float getY() {
        return mCoordY;
    }

    public void move() {
        moveTo(mSpeedX, mSpeedY);
    }

    private void moveTo(int goX, int goY) {

        //碰撞右侧
        if (mCoordX >= (mDrawView.width - MAX_SIZE)) {
            goRight = false;
            collision(RIGHT);
        }
        //碰撞左侧
        if (mCoordX <= 0) {
            goRight = true;
            collision(LEFT);
        }
        //碰撞下方
        if (mCoordY >= (mDrawView.height - MAX_SIZE - tab1.getHeight())) {
            if (tab1.getLeft() <= mCoordX + MAX_SIZE && tab1.getRight() >= mCoordX) {
                goDown = false;
                mInnerPaint.setARGB(ALPHA, 255, 255, 255);
                collision(DOWN);
            } else {
                GameActivity.dialogPop(mContext, GameActivity.FLAG_OVER, 4, 3, soundOn, soundPlayer, mDrawView);
            }
        }
        //碰撞上方
        if (mCoordY <= tab2.getHeight()) {
            if (tab2.getLeft() <= mCoordX + MAX_SIZE && tab2.getRight() >= mCoordX) {
                goDown = true;
                mInnerPaint.setARGB(ALPHA, 0, 0, 0);
                collision(UP);
            } else {
                GameActivity.dialogPop(mContext, GameActivity.FLAG_OVER, 4, 3, soundOn, soundPlayer, mDrawView);
            }
        }

        // move the x and y
        if (goRight) {
            mCoordX += goX;
        } else {
            mCoordX -= goX;
        }
        if (goDown) {
            mCoordY += goY;
        } else {
            mCoordY -= goY;
        }

    }

    public int getSpeedX() {
        return mSpeedX;
    }

    public void setSpeedX(int speedX) {
        mSpeedX = speedX;
    }

    public int getmSpeedY() {
        return mSpeedY;
    }

    public void setSpeedY(int speedY) {
        mSpeedY = speedY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mDrawRect.set(mCoordX, mCoordY, mCoordX + mRealSize, mCoordY
            + mRealSize);
        canvas.drawRoundRect(mDrawRect, 0, 0, mInnerPaint);
    }

    public void setSize(int newSize) {
        mRealSize = newSize;
    }

    public int getSize() {
        return mRealSize;
    }

    private void collision(int direction) {
        collisionSound(soundOn, soundPlayer);
        switch (direction) {
            case UP:
                //加速
                break;
            case DOWN:
                //加速
                break;
            case LEFT:
                break;
            case RIGHT:
                break;
        }
    }

    private void collisionSound(Boolean soundOn, SoundPlayer soundPlayer) {
        if (soundOn) {
            soundPlayer.play(2);
        }
    }
}
