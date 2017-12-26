package cn.xxx.winkawaks.bounceball.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import cn.xxx.winkawaks.bounceball.R;
import cn.xxx.winkawaks.bounceball.module.service.SoundPlayUtil;

public class DrawView extends View {
    private Rectangle mRectangle;
    public int width;
    public int height;
    public SoundPlayUtil soundPlayUtil;
    public static Boolean STOP = false;

    public DrawView(Context context, int width, int height, SoundPlayUtil soundPool) {
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
                this.setBackgroundResource(R.mipmap.vertical);
                break;
            case 3:
                this.setBackgroundResource(R.mipmap.horizontal);
                break;
            case 4:
                this.setBackgroundResource(R.mipmap.lattice);
                break;
            case 5:
                this.setBackgroundResource(R.mipmap.diamond);
                break;
            case 6:
                this.setBackgroundResource(R.mipmap.vortex);
                break;
            case 7:
                this.setBackgroundResource(R.mipmap.fairyland);
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (STOP) {

        } else {
            mRectangle.move();
            mRectangle.draw(canvas);

            invalidate();
        }
    }
}
