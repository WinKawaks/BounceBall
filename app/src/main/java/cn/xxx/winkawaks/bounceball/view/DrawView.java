package cn.xxx.winkawaks.bounceball.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import cn.xxx.winkawaks.bounceball.R;

public class DrawView extends View {
    private Rectangle mRectangle;
    public int width;
    public int height;

    public DrawView(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;
        mRectangle = new Rectangle(context, this);
        mRectangle.setSpeedX(10);
        mRectangle.setSpeedY(10);
        mRectangle.setX(width / 2 - Rectangle.MAX_SIZE / 2);
        mRectangle.setY(height / 2 - Rectangle.MAX_SIZE / 2);
    }

    public void setBackground(int time) {
        switch (time) {
            case 1:
                this.setBackgroundColor(getResources().getColor(R.color.grey));
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRectangle.move();
        mRectangle.draw(canvas);

        invalidate();
    }

}
