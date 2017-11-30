package cn.xxx.winkawaks.bounceball.module.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import cn.xxx.winkawaks.bounceball.R;
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
        setContentView(mDrawView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DrawView.STOP = true;
        soundPool.play(3);
        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog, null);
        MyDialog.Builder builder = new MyDialog.Builder(this);
        builder.setContentView(view);
        builder.setMessage("3:4");
        builder.setPositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(4);
                DrawView.STOP = false;
                mDrawView.invalidate();
            }
        });
        MyDialog myDialog = builder.create();
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
        }
        return super.onKeyDown(keyCode, event);
    }

}
