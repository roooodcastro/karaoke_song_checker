package com.rodcastro.karaokesonglist.ui;

/**
 *
 * @author rodcastro
 */
public abstract class LoadingBarListener {

    private int maxValue = 0;

    public abstract void onUpdateBar(int value, String message);

    public abstract void onChangeMaxValue(int maxValue);

    public abstract void onFinish();

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        onChangeMaxValue(maxValue);
    }

    public int getMaxValue() {
        return maxValue;
    }
}
