package cn.xxx.winkawaks.bounceball.module.game;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import cn.xxx.winkawaks.bounceball.module.service.SoundPlayUtil;
import cn.xxx.winkawaks.bounceball.view.DrawView;
import cn.xxx.winkawaks.bounceball.view.MyDialog;

/**
 * Created by 54713 on 2017/10/17.
 */

public class GameActivity extends Activity {

    private static int times;
    private static int currentTime;
    private SoundPlayUtil soundPool;
    private DrawView mDrawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        soundPool = new SoundPlayUtil();
        soundPool.init(this);
        Intent intent = this.getIntent();
        times = intent.getIntExtra("item", 0) * 2 + 3;
        Display display = getWindowManager().getDefaultDisplay();
        mDrawView = new DrawView(this, display.getWidth(), display.getHeight(), soundPool);
        mDrawView.setBackground(1);
        DrawView.STOP = false;
        setContentView(mDrawView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DrawView.STOP = true;
        soundPool.play(3);
        MyDialog.Builder builder = new MyDialog.Builder(this);
        builder.setScore1(3);
        builder.setScore2(4);
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                soundPool.play(4);
                DrawView.STOP = false;
                dialog.dismiss();
                mDrawView.invalidate();

                //TODO 加入最后一局退到MenuActivity功能。
            }
        });
        MyDialog myDialog = builder.create();
        myDialog.setCancelable(false);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.unload();
        soundPool = null;
        mDrawView = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            //TODO 加入弹出Dialog功能。
        }
        return super.onKeyDown(keyCode, event);
    }

}
