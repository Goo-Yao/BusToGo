package com.rdc.bustogo.ui.customview;

import android.content.Context;
import android.widget.Toast;

public class CustomToast {
    private static Toast mToast = null;

    public static void showToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
        }
        mToast.show();
    }
}
