package com.example.phamngocan.testmediaapp.Animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class Translate {
    static CustomInterpolator customInterpolator = CustomInterpolator.getInstance();

    public static void setAnim(View v) {
        AnimatorSet animatorSet = new AnimatorSet();


        ObjectAnimator animatorX = ObjectAnimator.ofFloat(
                v, "scaleX", 0, 1);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(v,
                "scaleY",0,1);


        animatorX.setDuration(500);
        animatorX.setInterpolator(customInterpolator.getInterpolator());
        animatorY.setDuration(500);
        animatorY.setInterpolator(customInterpolator.getInterpolator());

        animatorSet.play(animatorX).with(animatorY);
        animatorSet.start();

    }
}
