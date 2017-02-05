package com.sandeepreddy.tournament.generic;

import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;

import com.sandeepreddy.tournament.R;

import butterknife.BindView;

/**
 * Created by sandeepreddy on 4/2/17.
 */
public class BaseAppCompactCollapsingToolBarActivity extends BaseAppCompatActivity {
    @BindView(R.id.toolbar_layout)
    public CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    public void setToolBarBackgroundResource(int resid) {
        toolbar.setBackgroundResource(resid);
        collapsingToolbarLayout.setBackgroundResource(resid);
    }

    @Override
    public void setToolBarTitle(String title) {
        super.setToolBarTitle(title);
        collapsingToolbarLayout.setTitle(title);
    }

    @Override
    public void setToolBarHeight() {
        super.setToolBarHeight();
        // Check if the version of Android is Lollipop or higher
        if (Build.VERSION.SDK_INT >= 21) {
            collapsingToolbarLayout.setPadding(0, getStatusBarHeight(), 0, 0);
        }
    }
}
