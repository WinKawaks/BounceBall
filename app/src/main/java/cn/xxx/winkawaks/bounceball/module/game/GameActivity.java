package cn.xxx.winkawaks.bounceball.module.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import cn.xxx.winkawaks.bounceball.module.service.AsyncSoundPool;
import cn.xxx.winkawaks.bounceball.view.DrawView;

/**
 * Created by 54713 on 2017/10/17.
 */

public class GameActivity extends Activity {

    private static int times;
    private static int currentTime;
    private AsyncSoundPool soundPool = new AsyncSoundPool(this);
    private DrawView mDrawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        times = intent.getIntExtra("item", 0) * 2 + 3;
        Display display = getWindowManager().getDefaultDisplay();
        mDrawView = new DrawView(this, display.getWidth(), display.getHeight());
        mDrawView.setBackground(1);
        setContentView(mDrawView);
        soundPool.prepare();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.unload();
    }

}
