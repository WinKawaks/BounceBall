package cn.xxx.winkawaks.bounceball;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 54713 on 2017/11/29.
 */

public class TestActivity extends Activity implements View.OnTouchListener {
    private View mTab1;
    private View mTab2;
    private int x1, x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mTab1 = findViewById(R.id.tab_1);
        mTab2 = findViewById(R.id.tab_2);
        mTab1.setOnTouchListener(this);
        mTab2.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.tab_1:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = (int) event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x = (int) event.getRawX();
                        // 获取手指移动的距离
                        int dx = x - x1;
                        // 得到imageView最开始的各顶点的坐标
                        int l = mTab1.getLeft();
                        int r = mTab1.getRight();
                        int t = mTab1.getTop();
                        int b = mTab1.getBottom();
                        mTab1.layout(l + dx, t, r + dx, b);
                        // 获取移动后的位置
                        x1 = (int) event.getRawX();
                        break;
                }
                break;
            case R.id.tab_2:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x2 = (int) event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x = (int) event.getRawX();
                        // 获取手指移动的距离
                        int dx = x - x2;
                        int l = mTab2.getLeft();
                        int r = mTab2.getRight();
                        int t = mTab2.getTop();
                        int b = mTab2.getBottom();
                        // 更改imageView在窗体的位置
                        mTab2.layout(l + dx, t, r + dx, b);
                        // 获取移动后的位置
                        x2 = (int) event.getRawX();
                        break;
                }
                break;
        }
        return true;
    }
}
