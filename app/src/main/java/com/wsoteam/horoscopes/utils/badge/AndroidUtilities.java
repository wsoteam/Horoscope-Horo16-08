package com.wsoteam.horoscopes.utils.badge;

import com.wsoteam.horoscopes.App;

class AndroidUtilities {
    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            App.Companion.getInstance().getApplicationHandler().post(runnable);
        } else {
            App.Companion.getInstance().getApplicationHandler().postDelayed(runnable, delay);
        }
    }

    public static void cancelRunOnUIThread(Runnable runnable) {
        App.Companion.getInstance().getApplicationHandler().removeCallbacks(runnable);
    }
}
