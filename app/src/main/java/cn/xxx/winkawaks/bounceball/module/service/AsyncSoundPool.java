package cn.xxx.winkawaks.bounceball.module.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import cn.xxx.winkawaks.bounceball.R;

/**
 * Created by 54713 on 2017/11/15.
 */

public class AsyncSoundPool extends AsyncTask<Integer, Void, Void> {

    SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 1);
    Context mContext;

    public AsyncSoundPool(Context context) {
        mContext = context;
    }

    public void prepare() {
        soundPool.load(mContext, R.raw.shoot, 1);
        soundPool.load(mContext, R.raw.collision, 1);
        soundPool.load(mContext, R.raw.pause, 1);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        switch (integers[0]) {
            case 1:
                soundPool.play(1, 1, 1, 1, 0, 1);
                break;
            case 2:
                soundPool.play(2, 1, 1, 1, 0, 1);
                break;
            case 3:
                soundPool.play(3, 1, 1, 1, 0, 1);
                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
    }

    public void unload() {
        for (int i = 1; i <= 3; i++) {
            soundPool.unload(i);
        }
        soundPool.release();
    }
}
