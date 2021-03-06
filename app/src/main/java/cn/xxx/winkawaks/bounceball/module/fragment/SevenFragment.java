package cn.xxx.winkawaks.bounceball.module.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import cn.xxx.winkawaks.bounceball.R;

import java.io.File;

/**
 * Created by 54713 on 2017/10/19.
 */

public class SevenFragment extends Fragment {
    private static final String FONTS_FOLDER = "fonts";
    private static final String FONT = FONTS_FOLDER
        + File.separator + "black.ttf";

    private TextView mTV7;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_7, container, false);
        AssetManager assets = getActivity().getAssets();
        final Typeface font = Typeface.createFromAsset(assets, FONT);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        mTV7 = (TextView) view.findViewById(R.id.tv_7);
        mTV7.setWidth(width);
        mTV7.setHeight(width);
        mTV7.setTypeface(font);
        return view;
    }
}

