package es.unex.giiis.asee.project.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ValorationManagerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "appdb.db";

    private static final String RECIPE_TABLE_NAME = "recipe";
    private static final String USER_TABLE_NAME = "user";
    private static final String COMMA_SEP = ",";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";

    private static final String SQL_CREATE_VALORATIONS =
            "CREATE TABLE " + DBContract.Recipe.TABLE_NAME + " (" +
                    DBContract.Valoration._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    DBContract.Valoration.COLUMN_NAME_COMMENT + TEXT_TYPE + COMMA_SEP +
                    DBContract.Valoration.COLUMN_NAME_DIFFICULTY + INTEGER_TYPE + COMMA_SEP +
                    DBContract.Valoration.COLUMN_NAME_SCORE + INTEGER_TYPE + COMMA_SEP +
                    DBContract.Valoration.COLUMN_NAME_USERID + INTEGER_TYPE + COMMA_SEP +
                    DBContract.Valoration.COLUMN_NAME_RECIPEID + INTEGER_TYPE + COMMA_SEP +
                    "FOREIGN KEY(" + DBContract.Valoration.COLUMN_NAME_USERID + ") REFERENCES " + USER_TABLE_NAME + "(userid)" + COMMA_SEP +
                    "FOREIGN KEY(" + DBContract.Valoration.COLUMN_NAME_RECIPEID + ") REFERENCES " + RECIPE_TABLE_NAME + "(id)" +
                    " )";

    private static final String SQL_DELETE_VALORATIONS =
            "DROP TABLE IF EXISTS " + DBContract.Valoration.TABLE_NAME;

    public ValorationManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_VALORATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_VALORATIONS);
        onCreate(db);
    }
}
