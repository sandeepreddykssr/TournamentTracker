package com.sandeepreddy.tournament.utils;

import com.sandeepreddy.tournament.R;

import java.util.Random;

/**
 * Created by sandeepreddy on 27/1/17.
 */

public enum ThemeColor {
    Red(R.color.red, R.color.red_ee),
    Orange(R.color.orange, R.color.orange_ee),
    Green(R.color.green, R.color.green_ee),
    Cyan(R.color.cyan, R.color.cyan_ee),
    Azure(R.color.azure, R.color.azure_ee),
    Blue(R.color.blue, R.color.blue_ee),
    Violet(R.color.violet, R.color.violet_ee),
    Magenta(R.color.magenta, R.color.magenta_ee),
    Tomato(R.color.tomato, R.color.tomato_ee),
    Tangerine(R.color.tangerine, R.color.tangerine_ee),
    Banana(R.color.banana, R.color.banana_ee),
    Basil(R.color.basil, R.color.basil_ee),
    Sage(R.color.sage, R.color.sage_ee),
    Peacock(R.color.peacock, R.color.peacock_ee),
    Blueberry(R.color.blueberry, R.color.blueberry_ee),
    Lavender(R.color.lavender, R.color.lavender_ee),
    Grape(R.color.grape, R.color.grape_ee),
    Flamingo(R.color.flamingo, R.color.flamingo_ee),
    Graphite(R.color.graphite, R.color.graphite_ee);

    private final int primaryColorId;
    private final int transparentColorId;

    ThemeColor(int primaryColorId, int transparentColorId) {
        this.primaryColorId = primaryColorId;
        this.transparentColorId = transparentColorId;
    }

    public int getPrimaryColorId() {
        return primaryColorId;
    }

    public int getTransparentColorId() {
        return transparentColorId;
    }

    public static ThemeColor getRandomThemeColor() {
        Random random = new Random();
        int value = random.nextInt(ThemeColor.values().length);
        return ThemeColor.values()[value];
    }

    public static ThemeColor getThemeColor(int position) {
        return ThemeColor.values()[position % ThemeColor.values().length];
    }
}
