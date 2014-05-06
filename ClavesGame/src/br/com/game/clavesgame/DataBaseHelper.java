package br.com.game.clavesgame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataBaseHelper<E> extends OrmLiteSqliteOpenHelper {
	
	public DataBaseHelper(Context context) {
		super(context, "sistema.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource src) {
		try{
			TableUtils.createTable(src, Points.class);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource src, int oldVersion, int newVersion) {
		try{
			TableUtils.dropTable(src, Points.class, true);
			onCreate(db, src);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		super.close();
	}

}