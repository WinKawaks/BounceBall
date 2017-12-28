package cn.xxx.winkawaks.bounceball.module.game;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.xxx.winkawaks.bounceball.R;
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
    private TextView mTVChapter;
    private TextView mTVChapterTitle;
    private TextView mTVChapterSubtitle;
    private TextView mTVChapterAbstract;
    private LinearLayout mChapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mDrawView = (DrawView) findViewById(R.id.game_background);
        mTVChapter = (TextView) findViewById(R.id.chapter);
        mTVChapterTitle = (TextView) findViewById(R.id.chapter_title);
        mTVChapterSubtitle = (TextView) findViewById(R.id.chapter_subtitle);
        mTVChapterAbstract = (TextView) findViewById(R.id.chapter_abstract);
        mChapterView = (LinearLayout) findViewById(R.id.chapter_view);
        soundPool = new SoundPlayUtil();
        soundPool.init(this);
        Intent intent = this.getIntent();
        times = intent.getIntExtra("item", 0) * 2 + 3;
        Display display = getWindowManager().getDefaultDisplay();
        mDrawView.init(this, display.getWidth(), display.getHeight(), soundPool);
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
        Display display = getWindowManager().getDefaultDisplay();
        mDrawView.init(this, display.getWidth(), display.getHeight(), soundPool);
        mDrawView.setBackground(currentTime);
        setChapterInformation(currentTime);
        chapterShow();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (DrawView.STOP) {
        } else {
            dialogPop(this, FLAG_PAUSE, 4, 3, soundPool, mDrawView);
        }
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
                        intent.putExtra("current", currentTime);
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
        AlphaAnimation alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.chapter_anim);
        mChapterView.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mChapterView.setVisibility(View.GONE);
                DrawView.STOP = false;
                mDrawView.invalidate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
