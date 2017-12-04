package cn.xxx.winkawaks.bounceball.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import cn.xxx.winkawaks.bounceball.R;

/**
 * Created by 54713 on 2017/11/28.
 */

public class MyDialog extends Dialog {
    public MyDialog(@NonNull Context context) {
        super(context);
    }

    public MyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private int score1;
        private int score2;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setScore1(int score1) {
            this.score1 = score1;
            return this;
        }

        public Builder setScore2(int score2) {
            this.score2 = score2;
            return this;
        }

        public Builder setPositiveButton(DialogInterface.OnClickListener listener) {
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(DialogInterface.OnClickListener listener) {
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Create the custom dialog
         */
        public MyDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final MyDialog dialog = new MyDialog(context,
                R.style.MyDialog);
            View layout = inflater.inflate(R.layout.dialog, null);

            ((ImageView)layout.findViewById(R.id.score_1)).setImageResource(scoreRes(score1));
            ((ImageView)layout.findViewById(R.id.score_2)).setImageResource(scoreRes(score2));
            // set the confirm button
            ((Button) layout.findViewById(R.id.btn_start)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                }
            });
            ((Button) layout.findViewById(R.id.btn_goback)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                }
            });

            dialog.setContentView(layout);
            return dialog;
        }

    }

    private static int scoreRes(int score) {
        int scoreResource = 0;
        switch (score) {
            case 0:
                scoreResource = R.mipmap.score_0;
                break;
            case 1:
                scoreResource = R.mipmap.score_1;
                break;
            case 2:
                scoreResource = R.mipmap.score_2;
                break;
            case 3:
                scoreResource = R.mipmap.score_3;
                break;
            case 4:
                scoreResource = R.mipmap.score_4;
                break;
            case 5:
                scoreResource = R.mipmap.score_5;
                break;
            case 6:
                scoreResource = R.mipmap.score_6;
                break;
            case 7:
                scoreResource = R.mipmap.score_7;
                break;
        }
        return scoreResource;
    }
}
