package zhuj.android.utils.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	public interface OnUpdateListener {
		void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion);
	}

	private final OnUpdateListener onUpdateListener;

	public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, OnUpdateListener onUpdateListener) {
		super(context, name, factory, version);
		this.onUpdateListener = onUpdateListener;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (onUpdateListener != null) {
			onUpdateListener.onUpdate(db, oldVersion, newVersion);
		}
	}

}