package com.sandeepreddy.tournament.utils.listeners;

import android.view.View;

/**
 * Created by sandeepreddy on 25/1/17.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
