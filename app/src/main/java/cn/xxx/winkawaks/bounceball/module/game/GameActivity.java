package cn.xxx.winkawaks.bounceball.module.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import cn.xxx.winkawaks.bounceball.R;
import cn.xxx.winkawaks.bounceball.module.service.AsyncSoundPool;

/**
 * Created by 54713 on 2017/10/17.
 */

public class GameActivity extends Activity implements View.OnClickListener {

    private static int times;
    private AsyncSoundPool soundPool = new AsyncSoundPool(this);
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = this.getIntent();
        times = intent.getIntExtra("item", 0) * 2 + 3;
        mBtn = (Button) findViewById(R.id.haha);
        mBtn.setOnClickListener(this);
        soundPool.prepare();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("winkawaks", "destory");
    }

    @Override
    public void onClick(View v) {
        soundPool.execute(1);
    }
}
