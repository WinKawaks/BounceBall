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
        soundPool.prepare();
        Intent intent = this.getIntent();
        times = intent.getIntExtra("item", 0) * 2 + 3;
        Display display = getWindowManager().getDefaultDisplay();
        mDrawView = new DrawView(this, display.getWidth(), display.getHeight(), soundPool);
        mDrawView.setBackground(1);
        setContentView(mDrawView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DrawView.STOP = true;
        //new AlertDialog.Builder(this)
        //    .setMessage("0:7")
        //    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        //        @Override
        //        public void onClick(DialogInterface dialog, int which) {
        //            DrawView.STOP = false;
        //            mDrawView.invalidate();
        //        }
        //    })
        //    .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.unload();
    }

}
