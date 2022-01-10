package es.unex.giiis.asee.project.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IngredientManagerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "appdb.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String IMAGE_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_INGREDIENTS =
            "CREATE TABLE " + DBContract.Ingredient.TABLE_NAME + " (" +
                    DBContract.Ingredient._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    DBContract.Ingredient.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    DBContract.Ingredient.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    DBContract.Ingredient.COLUMN_NAME_CATEGORIES + TEXT_TYPE + COMMA_SEP +
                    DBContract.Ingredient.COLUMN_NAME_NRV + INTEGER_TYPE + COMMA_SEP +
                    DBContract.Ingredient.COLUMN_NAME_PHOTO + IMAGE_TYPE +
                    " )";
    private static final String SQL_DELETE_INGREDIENTS =
            "DROP TABLE IF EXISTS " + DBContract.Ingredient.TABLE_NAME;

    public IngredientManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_INGREDIENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_INGREDIENTS);
        onCreate(db);
    }
}
