package zhuj.android.pref;

import android.content.Context;
import android.content.SharedPreferences;

import zhuj.android.Androids;

public class GlobalSp {
    private static SPWrap prefs;

    private static final String FILE_NAME = "sp_global";

    private static final String RUN_COUNT = "run_count";

    public static SPWrap getSP() {
        if (prefs == null) {
            SharedPreferences sp = Androids.getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            prefs = new SPWrap(sp);
        }
        return prefs;
    }

    public static int getRunCount() {
        return getSP().getInt(RUN_COUNT, 0);
    }

    public static void setRunCount(int count) {
        getSP().apply(RUN_COUNT, count);
    }
}
