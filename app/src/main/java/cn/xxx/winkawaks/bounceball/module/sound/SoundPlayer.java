package cn.xxx.winkawaks.bounceball.module.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import cn.xxx.winkawaks.bounceball.R;

/**
 * Created by 54713 on 2017/11/29.
 */

public class SoundPlayer {
    // SoundPool对象
    public SoundPool mSoundPlayer = new SoundPool(4,
        AudioManager.STREAM_MUSIC, 1);

    // 上下文
    private Context mContext;

    /**
     * 初始化
     */
    public void init(Context context) {
        // 初始化声音
        mContext = context;

        mSoundPlayer.load(mContext, R.raw.shoot, 1);// 1
        mSoundPlayer.load(mContext, R.raw.collision, 1);// 2
        mSoundPlayer.load(mContext, R.raw.pause, 1);// 3
        mSoundPlayer.load(mContext, R.raw.resume, 1);// 4
    }

    /**
     * 播放声音
     */
    public void play(int soundID) {
        mSoundPlayer.play(soundID, 1, 1, 1, 0, 1);
    }

    public void unload() {
        for (int i = 1; i <= 4; i++) {
            mSoundPlayer.unload(i);
        }
        mSoundPlayer.release();
        mSoundPlayer = null;
    }

}

