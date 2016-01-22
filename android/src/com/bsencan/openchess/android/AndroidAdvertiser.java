package com.bsencan.openchess.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.bsencan.openchess.interfaces.NativeActions;

/**
 * Created by smartosc on 1/15/2016.
 */
public class AndroidAdvertiser implements NativeActions {

    Context context;
    public AndroidAdvertiser(Context context){
        this.context = context;
    }

    @Override
    public void showAd() {

    }

    @Override
    public void saveHighScore(int highScore) {
        context.getSharedPreferences("abc",Context.MODE_PRIVATE).edit().putInt("HIGH_SCORE",highScore).commit();
    }

    @Override
    public int getHighScore() {
        return context.getSharedPreferences("abc",Context.MODE_PRIVATE).getInt("HIGH_SCORE",0);
    }
}
