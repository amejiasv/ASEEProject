package es.unex.giiis.asee.project.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class RecipeManagerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "appdb.db";

    private static final String USER_TABLE_NAME = "user";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String IMAGE_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_RECIPES =
            "CREATE TABLE " + DBContract.Recipe.TABLE_NAME + " (" +
                    DBContract.Recipe._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    DBContract.Recipe.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    DBContract.Recipe.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    DBContract.Recipe.COLUMN_NAME_CATEGORIES + TEXT_TYPE + COMMA_SEP +
                    DBContract.Recipe.COLUMN_NAME_DURATION + INTEGER_TYPE + COMMA_SEP +
                    DBContract.Recipe.COLUMN_NAME_DIFFICULTY + INTEGER_TYPE + COMMA_SEP +
                    DBContract.Recipe.COLUMN_NAME_SCORE + INTEGER_TYPE + COMMA_SEP +
                    DBContract.Recipe.COLUMN_NAME_USERID + INTEGER_TYPE + COMMA_SEP +
                    DBContract.Recipe.COLUMN_NAME_PHOTO + IMAGE_TYPE + COMMA_SEP +
                    "FOREIGN KEY(" + DBContract.Recipe.COLUMN_NAME_USERID + ") REFERENCES " + USER_TABLE_NAME + "(userid)" +
                    " )";

    private static final String SQL_DELETE_RECIPES =
            "DROP TABLE IF EXISTS " + DBContract.Recipe.TABLE_NAME;

    public RecipeManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RECIPES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_RECIPES);
        onCreate(db);
    }
}
