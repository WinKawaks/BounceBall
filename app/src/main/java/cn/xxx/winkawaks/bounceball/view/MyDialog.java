package cn.xxx.winkawaks.bounceball.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
        private String message;
        private DialogInterface.OnClickListener positiveButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the positive button text and it's listener
         */
        public Builder setPositiveButton(DialogInterface.OnClickListener listener) {
            this.positiveButtonClickListener = listener;
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

            // set the dialog title
            ((LedTextView) layout.findViewById(R.id.score)).setText(message);
            // set the confirm button
            ((Button) layout.findViewById(R.id.start)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                }
            });

            dialog.setContentView(layout);
            return dialog;
        }

    }
}
