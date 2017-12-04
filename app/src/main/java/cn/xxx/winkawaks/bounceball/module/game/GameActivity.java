package cn.xxx.winkawaks.bounceball.module.game;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private static int currentTime = 1;
    private static final int FLAG_PAUSE = 1;
    private static final int FLAG_OVER = 2;

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
        mDrawView.setBackground(currentTime);
        DrawView.STOP = false;
        setContentView(mDrawView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dialogPop(this, FLAG_PAUSE, 4, 3, soundPool, mDrawView);
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
            dialogPop(this, FLAG_OVER, 4, 3, soundPool, mDrawView);
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void dialogPop(final Context context, final int flag, final int score1, final int score2, final SoundPlayUtil soundPlayUtil, final DrawView drawView) {
        DrawView.STOP = true;
        soundPlayUtil.play(3);
        MyDialog.Builder builder = new MyDialog.Builder(context);
        builder.setScore1(score1);
        builder.setScore2(score2);
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                soundPlayUtil.play(4);
                DrawView.STOP = false;
                dialog.dismiss();
                if (flag == FLAG_PAUSE) {
                    drawView.invalidate();
                } else {
                    if (currentTime < times) {
                        currentTime++;
                        Intent intent = new Intent(context, GameActivity.class);
                        intent.putExtra("item", times);
                        //TODO 切换游戏背景没有终结 思考一下为什么新的Activity没有动画绘制。
                        Log.i("winkawaks", "current = " + currentTime + "  times" + times);
                        ((Activity) context).finish();
                        context.startActivity(intent);
                    } else {
                        currentTime = 1;
                        ((Activity) context).finish();
                    }
                }
            }
        });
        builder.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                soundPlayUtil.play(4);
                currentTime = 1;
                dialog.dismiss();
                ((Activity) context).finish();
            }
        });
        MyDialog myDialog = builder.create();
        myDialog.setCancelable(false);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show();
    }

}
