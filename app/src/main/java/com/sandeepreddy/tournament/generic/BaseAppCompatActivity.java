package com.sandeepreddy.tournament.generic;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.sandeepreddy.tournament.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sandeepreddy on 4/2/17.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity implements ToolBarConfig {
    @BindView(R.id.app_bar)
    public AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setToolBarHeight();
    }

    public void setToolBarHeight() {
        // Check if the version of Android is Lollipop or higher
        if (Build.VERSION.SDK_INT >= 21) {

            // Set the status bar to dark-semi-transparentish
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // Set paddingTop of toolbar to height of status bar.
            // Fixes statusbar covers toolbar issue
            int statusBarHeight = getStatusBarHeight();
            toolbar.setPadding(0, statusBarHeight, 0, 0);
            ViewGroup.LayoutParams params = toolbar.getLayoutParams();
            params.height = params.height + statusBarHeight;
            toolbar.setLayoutParams(params);
        }
    }

    // A method to find height of the status bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void setToolBarBackgroundResource(int resid) {
        toolbar.setBackgroundResource(resid);
    }

    @Override
    public void setToolBarTitle(String title) {
        toolbar.setTitle(title);
    }
}
