package cn.xxx.winkawaks.bounceball.module.game;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.xxx.winkawaks.bounceball.R;
import cn.xxx.winkawaks.bounceball.module.sound.SoundPlayer;
import cn.xxx.winkawaks.bounceball.view.DrawView;
import cn.xxx.winkawaks.bounceball.view.MyDialog;

/**
 * Created by 54713 on 2017/10/17.
 */

public class GameActivity extends Activity implements View.OnTouchListener {

    private static int times;
    private static int currentTime = 1;
    public static final int FLAG_PAUSE = 1;
    public static final int FLAG_OVER = 2;
    public static int score1 = 0;
    public static int score2 = 0;
    public static int scroll1 = 0;
    public static int scroll2 = 0;
    public static Boolean CHAPTER_SHOW = false;

    private SoundPlayer soundPool;
    private DrawView mDrawView;
    private TextView mTVChapter;
    private TextView mTVChapterTitle;
    private TextView mTVChapterSubtitle;
    private TextView mTVChapterAbstract;
    private LinearLayout mChapterView;
    private Boolean soundOn;
    private View mTab1, mTab2;
    private int x1, x2;
    private RelativeLayout mController1, mController2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
        initListener();
    }

    private void initListener() {
        mTab1.setOnTouchListener(this);
        mTab2.setOnTouchListener(this);
    }

    private void initView() {
        mDrawView = (DrawView) findViewById(R.id.game_background);
        mTVChapter = (TextView) findViewById(R.id.chapter);
        mTVChapterTitle = (TextView) findViewById(R.id.chapter_title);
        mTVChapterSubtitle = (TextView) findViewById(R.id.chapter_subtitle);
        mTVChapterAbstract = (TextView) findViewById(R.id.chapter_abstract);
        mChapterView = (LinearLayout) findViewById(R.id.chapter_view);
        mTab1 = findViewById(R.id.tab_1);
        mTab2 = findViewById(R.id.tab_2);
        mController1 = (RelativeLayout) findViewById(R.id.controller_1);
        mController2 = (RelativeLayout) findViewById(R.id.controller_2);
        mController1.setVisibility(View.INVISIBLE);
        mController2.setVisibility(View.INVISIBLE);

        SharedPreferences mSharedPreferences = getSharedPreferences("WinKawaks", Context.MODE_PRIVATE);
        soundOn = mSharedPreferences.getBoolean("sound", true);
        if (soundOn) {
            soundPool = new SoundPlayer();
            soundPool.init(this);
        }

        Intent intent = this.getIntent();
        times = intent.getIntExtra("item", 0) * 2 + 3;
        Display display = getWindowManager().getDefaultDisplay();
        mDrawView.init(this, display.getWidth(), display.getHeight(), mTab1, mTab2, soundOn, soundPool);
        mDrawView.setBackground(currentTime);
        setChapterInformation(currentTime);
        chapterShow();
    }

    /**
     * 继续游戏，关卡刷新。
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        currentTime = intent.getIntExtra("current", 1);
        score1 = intent.getIntExtra("score1", 0);
        score2 = intent.getIntExtra("score2", 0);
        Display display = getWindowManager().getDefaultDisplay();

        SharedPreferences mSharedPreferences = getSharedPreferences("WinKawaks", Context.MODE_PRIVATE);
        soundOn = mSharedPreferences.getBoolean("sound", true);
        if (soundOn) {
            soundPool = new SoundPlayer();
            soundPool.init(this);
        }

        mDrawView.init(this, display.getWidth(), display.getHeight(), mTab1, mTab2, soundOn, soundPool);
        mDrawView.setBackground(currentTime);
        setChapterInformation(currentTime);
        chapterShow();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (DrawView.STOP) {
        } else {
            dialogPop(this, FLAG_PAUSE, score1, score2, soundOn, soundPool, mDrawView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundOn) {
            soundPool.unload();
        }
        scroll1 = 0;
        scroll2 = 0;
        soundPool = null;
        mDrawView = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (CHAPTER_SHOW) {
            } else {
                dialogPop(this, FLAG_PAUSE, score1, score2, soundOn, soundPool, mDrawView);
            }
        }
        return true;
    }

    public static void dialogPop(final Context context, final int flag, final int score1, final int score2, final Boolean soundOn, final SoundPlayer soundPlayer, final DrawView drawView) {
        DrawView.STOP = true;
        if (soundOn) {
            soundPlayer.play(3);
        }
        MyDialog.Builder builder = new MyDialog.Builder(context);
        builder.setScore1(score1);
        builder.setScore2(score2);
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (soundOn) {
                    soundPlayer.play(4);
                }
                DrawView.STOP = false;
                dialog.dismiss();
                if (flag == FLAG_PAUSE) {
                    drawView.invalidate();
                } else {
                    if (currentTime < times) {
                        currentTime++;
                        Intent intent = new Intent(context, GameActivity.class);
                        intent.putExtra("current", currentTime);
                        intent.putExtra("score1", score1);
                        intent.putExtra("score2", score2);
                        context.startActivity(intent);
                    } else {
                        currentTime = 1;
                        GameActivity.score1 = 0;
                        GameActivity.score2 = 0;
                        ((Activity) context).finish();
                    }
                }
            }
        });
        builder.setNegativeButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (soundOn) {
                    soundPlayer.play(4);
                }
                currentTime = 1;
                GameActivity.score1 = 0;
                GameActivity.score2 = 0;
                dialog.dismiss();
                ((Activity) context).finish();
            }
        });
        MyDialog myDialog = builder.create();
        myDialog.setCancelable(false);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show();
    }

    private void setChapterInformation(int chapter) {
        switch (chapter) {
            case 1:
                mTVChapter.setText(R.string.chapter_1);
                mTVChapterTitle.setText(R.string.title_1);
                mTVChapterSubtitle.setText(R.string.subtitle_1);
                mTVChapterAbstract.setText(R.string.abstract_1);
                break;
            case 2:
                mTVChapter.setText(R.string.chapter_2);
                mTVChapterTitle.setText(R.string.title_2);
                mTVChapterSubtitle.setText(R.string.subtitle_2);
                mTVChapterAbstract.setText(R.string.abstract_2);
                break;
            case 3:
                mTVChapter.setText(R.string.chapter_3);
                mTVChapterTitle.setText(R.string.title_3);
                mTVChapterSubtitle.setText(R.string.subtitle_3);
                mTVChapterAbstract.setText(R.string.abstract_3);
                break;
            case 4:
                mTVChapter.setText(R.string.chapter_4);
                mTVChapterTitle.setText(R.string.title_4);
                mTVChapterSubtitle.setText(R.string.subtitle_4);
                mTVChapterAbstract.setText(R.string.abstract_4);
                break;
            case 5:
                mTVChapter.setText(R.string.chapter_5);
                mTVChapterTitle.setText(R.string.title_5);
                mTVChapterSubtitle.setText(R.string.subtitle_5);
                mTVChapterAbstract.setText(R.string.abstract_5);
                break;
            case 6:
                mTVChapter.setText(R.string.chapter_6);
                mTVChapterTitle.setText(R.string.title_6);
                mTVChapterSubtitle.setText(R.string.subtitle_6);
                mTVChapterAbstract.setText(R.string.abstract_6);
                break;
            case 7:
                mTVChapter.setText(R.string.chapter_7);
                mTVChapterTitle.setText(R.string.title_7);
                mTVChapterSubtitle.setText(R.string.subtitle_7);
                mTVChapterAbstract.setText(R.string.abstract_7);
                break;
        }
    }

    private void chapterShow() {
        DrawView.STOP = true;
        CHAPTER_SHOW = true;
        AlphaAnimation alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.chapter_anim);
        mChapterView.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mChapterView.setVisibility(View.GONE);
                CHAPTER_SHOW = false;
                mController1.setVisibility(View.VISIBLE);
                mController2.setVisibility(View.VISIBLE);
                DrawView.STOP = false;
                mDrawView.invalidate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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
                        int dx = x - x1;
                        scroll1 = modifySpeed(dx);

                        int l = mTab1.getLeft();
                        int r = mTab1.getRight();
                        int t = mTab1.getTop();
                        int b = mTab1.getBottom();

                        mTab1.layout(l + dx, t, r + dx, b);
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
                        int dx = x - x2;
                        scroll2 = modifySpeed(dx);

                        int l = mTab2.getLeft();
                        int r = mTab2.getRight();
                        int t = mTab2.getTop();
                        int b = mTab2.getBottom();

                        mTab2.layout(l + dx, t, r + dx, b);
                        x2 = (int) event.getRawX();
                        break;
                }
                break;
        }
        return true;
    }

    public static int modifySpeed(int speed) {
        if (speed > 10) {
            return 10;
        } else if (speed < -10) {
            return -10;
        } else {
            return speed;
        }
    }
}
