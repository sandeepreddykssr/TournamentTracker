package com.sandeepreddy.tournament.utils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by sandeepreddy on 3/2/17.
 */
public class Utils {
    public static int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(id, context.getTheme());
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static void setWeight(View view, int weight) {
        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) view.getLayoutParams();
        p.weight = weight;

        view.setLayoutParams(p);
    }
}
