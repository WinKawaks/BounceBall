package cn.xxx.winkawaks.bounceball.module.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.*;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import cn.xxx.winkawaks.bounceball.R;
import cn.xxx.winkawaks.bounceball.module.fragment.FiveFragment;
import cn.xxx.winkawaks.bounceball.module.fragment.SevenFragment;
import cn.xxx.winkawaks.bounceball.module.fragment.ThreeFragment;
import cn.xxx.winkawaks.bounceball.module.game.GameActivity;
import cn.xxx.winkawaks.bounceball.module.setting.SettingActivity;
import cn.xxx.winkawaks.bounceball.module.sound.BGMService;
import cn.xxx.winkawaks.bounceball.module.sound.SoundPlayer;
import cn.xxx.winkawaks.bounceball.view.BulletImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 54713 on 2017/10/17.
 */

public class MenuActivity extends FragmentActivity implements View.OnClickListener, View.OnTouchListener, GestureDetector.OnGestureListener {
    private Button mBtnSelect;
    private Button mBtnSetting;
    private ViewPager mViewPager;
    private ImageView mIVShoot;
    private Rect mBtnRect;
    private TextView currentTextView;
    private GestureDetector mGestureDetector;
    private BulletImageView mBullet;
    private SoundPlayer soundPool;
    private Boolean soundOn;

    private static final String FONTS_FOLDER = "fonts";
    private static final String FONT_BROKEN = FONTS_FOLDER
        + File.separator + "black-x.ttf";
    private static final String FONT = FONTS_FOLDER
        + File.separator + "black.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initView();
        initListener();
    }

    private void initView() {
        mBtnSelect = (Button) findViewById(R.id.button);
        mBtnSetting = (Button) findViewById(R.id.btn_setting);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mIVShoot = (ImageView) findViewById(R.id.shoot);
        mBullet = (BulletImageView) findViewById(R.id.bullet);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        mViewPager.setLayoutParams(new FrameLayout.LayoutParams(width, width));
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.shoot);
        Bitmap newShoot = scaleImg(bitmap, width, width);
        mIVShoot.setImageBitmap(newShoot);
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new ThreeFragment());
        fragments.add(new FiveFragment());
        fragments.add(new SevenFragment());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);

        mBtnRect = new Rect();
        mBtnSelect.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBtnSelect.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mBtnSelect.getHitRect(mBtnRect);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences mSharedPreferences = getSharedPreferences("WinKawaks", Context.MODE_PRIVATE);
        soundOn = mSharedPreferences.getBoolean("sound", true);
        if (soundOn) {
            soundPool = new SoundPlayer();
            soundPool.init(this);
        }
    }

    private void initListener() {
        mGestureDetector = new GestureDetector(this);
        mBtnSelect.setOnClickListener(this);
        mBtnSelect.setOnTouchListener(this);
        mBtnSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if (soundOn) {
                    soundPool.play(1);
                }

                mBtnSelect.setBackground(getResources().getDrawable(R.drawable.button_circle));
                mBtnSelect.setTextColor(Color.WHITE);
                int x = mViewPager.getCurrentItem();
                Fragment fragment = ((FragmentAdapter) mViewPager.getAdapter()).currentFragment;
                switch (x) {
                    case 0:
                        currentTextView = (TextView) fragment.getView().findViewById(R.id.tv_3);
                        break;
                    case 1:
                        currentTextView = (TextView) fragment.getView().findViewById(R.id.tv_5);
                        break;
                    case 2:
                        currentTextView = (TextView) fragment.getView().findViewById(R.id.tv_7);
                        break;
                }
                fontBroken();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                        intent.putExtra("item", mViewPager.getCurrentItem());
                        startActivity(intent);
                    }
                }, 2500);
                break;
            case R.id.btn_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mBtnSelect.setBackground(getResources().getDrawable(R.drawable.button_circle));
            mBtnSelect.setTextColor(Color.WHITE);
        }
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getX() < 0 || event.getY() < 0 || event.getX() > v.getMeasuredWidth() || event.getY() > v.getMeasuredHeight()) {
            mBtnSelect.setBackground(getResources().getDrawable(R.drawable.button_circle));
            mBtnSelect.setTextColor(Color.WHITE);
        } else {
            mBtnSelect.setBackground(getResources().getDrawable(R.drawable.button_circle_pressed));
            mBtnSelect.setTextColor(Color.BLACK);
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (mBtnRect.contains((int) e2.getX(), (int) e2.getY())) {
            mBtnSelect.setBackground(getResources().getDrawable(R.drawable.button_circle_pressed));
            mBtnSelect.setTextColor(Color.BLACK);
        } else {
            mBtnSelect.setBackground(getResources().getDrawable(R.drawable.button_circle));
            mBtnSelect.setTextColor(Color.WHITE);
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public static Bitmap scaleImg(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
            true);
        return newbm;
    }

    @Override
    protected void onStop() {
        super.onStop();
        fontNormal();
        if (soundOn) {
            soundPool.unload();
        }
        soundPool = null;
    }

    private void fontBroken() {
        if (currentTextView != null) {
            AssetManager assets = this.getAssets();
            final Typeface fontDigitalX = Typeface.createFromAsset(assets, FONT_BROKEN);
            currentTextView.setTypeface(fontDigitalX);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bullet);
            Bitmap bulletBitmap = scaleImg(bitmap, 100, 100);
            mBullet.setImage(bulletBitmap);
            mBullet.setVisibility(View.VISIBLE);
        }
    }

    private void fontNormal() {
        if (currentTextView != null) {
            AssetManager assets = this.getAssets();
            final Typeface fontDigital7 = Typeface.createFromAsset(assets, FONT);
            currentTextView.setTypeface(fontDigital7);
            mBullet.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.exit_game)
                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MenuActivity.this.finish();
                    Intent intent = new Intent(MenuActivity.this, BGMService.class);
                    stopService(intent);
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

}
