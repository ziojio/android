package zhuj.android.utils.sqlite;

import android.content.Context;

public class DataBaseConfig {
    public static final String DEFAULT_DB_NAME = "sqlite.db";
    public static final int DEFAULT_DB_VERSION = 1;

    public Context context;
    public boolean debug;
    public String dbName = DEFAULT_DB_NAME;
    public int dbVersion = DEFAULT_DB_VERSION;
    public SQLiteHelper.OnUpdateListener onUpdateListener;

    public DataBaseConfig(Context context) {
        this(context, DEFAULT_DB_NAME, DEFAULT_DB_VERSION, false);
    }

    public DataBaseConfig(Context context, String dbName) {
        this(context, dbName, DEFAULT_DB_VERSION, false);
    }

    public DataBaseConfig(Context context, String dbName, int dbVersion) {
        this(context, dbName, dbVersion, false);
    }

    public DataBaseConfig(Context context, String dbName, int dbVersion, boolean debug) {
        this(context, dbName, dbVersion, debug, null);
    }

    public DataBaseConfig(Context context, String dbName, int dbVersion,boolean debug,
                          SQLiteHelper.OnUpdateListener onUpdateListener) {
        this.context = context.getApplicationContext();
        if (dbName != null && !dbName.trim().isEmpty()) {
            this.dbName = dbName;
        }
        if (dbVersion > 1) {
            this.dbVersion = dbVersion;
        }
        this.debug = debug;
        this.onUpdateListener = onUpdateListener;
    }

    @Override
    public String toString() {
        return "DataBaseConfig{" +
                "context=" + context +
                ", debug=" + debug +
                ", dbName='" + dbName + '\'' +
                ", dbVersion=" + dbVersion +
                ", onUpdateListener=" + onUpdateListener +
                '}';
    }
}