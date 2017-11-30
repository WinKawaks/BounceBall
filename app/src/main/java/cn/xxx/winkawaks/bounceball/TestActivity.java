package cn.xxx.winkawaks.bounceball;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import cn.xxx.winkawaks.bounceball.module.service.SoundPlayUtil;

/**
 * Created by 54713 on 2017/11/29.
 */

public class TestActivity extends Activity implements View.OnClickListener {
    private Button btn1, btn2,btn3, btn4;
    private SoundPlayUtil mSoundPlayer;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mSoundPlayer = new SoundPlayUtil();
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSoundPlayer.init(this);
    }

    private void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSoundPlayer.unload();
        mSoundPlayer = null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                mSoundPlayer.play(1);
                break;
            case R.id.btn2:
                mSoundPlayer.play(2);
                break;
            case R.id.btn3:
                mSoundPlayer.play(3);
                break;
            case R.id.btn4:
                mSoundPlayer.play(4);
                break;
        }
    }
}
