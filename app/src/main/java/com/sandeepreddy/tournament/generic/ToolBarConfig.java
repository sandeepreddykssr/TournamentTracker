package com.sandeepreddy.tournament.generic;

/**
 * Created by sandeepreddy on 4/2/17.
 */

interface ToolBarConfig {

    /**
     * Set the background to a the tool. The resource should refer to
     * a Drawable object or 0 to remove the background.
     * @param resid The identifier of the resource.
     *
     * @attr ref android.R.styleable#View_background
     */
    void setToolBarBackgroundResource(int resid);

    void setToolBarTitle(String title);
}
