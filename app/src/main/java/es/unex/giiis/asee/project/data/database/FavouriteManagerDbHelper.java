package es.unex.giiis.asee.project.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class FavouriteManagerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "appdb.db";

    private static final String RECIPE_TABLE_NAME = "recipe";
    private static final String USER_TABLE_NAME = "user";
    private static final String COMMA_SEP = ",";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String SQL_CREATE_FAVOURITES =
            "CREATE TABLE " + DBContract.Favourite.TABLE_NAME + " (" +
                    DBContract.Favourite.COLUMN_NAME_RECIPEID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    DBContract.Favourite.COLUMN_NAME_USERID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    "FOREIGN KEY(" + DBContract.Favourite.COLUMN_NAME_RECIPEID + ") REFERENCES " + RECIPE_TABLE_NAME + "(id)" + COMMA_SEP +
                    "FOREIGN KEY(" + DBContract.Favourite.COLUMN_NAME_USERID + ") REFERENCES " + USER_TABLE_NAME + "(userid)" +
                    " )";
    private static final String SQL_DELETE_FAVOURITES =
            "DROP TABLE IF EXISTS " + DBContract.Favourite.TABLE_NAME;

    public FavouriteManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVOURITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_FAVOURITES);
        onCreate(db);
    }
}
