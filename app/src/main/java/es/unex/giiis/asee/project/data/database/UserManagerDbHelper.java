package es.unex.giiis.asee.project.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserManagerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "appdb.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String IMAGE_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_USERS =
            "CREATE TABLE " + DBContract.User.TABLE_NAME + " (" +
                    DBContract.User._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    DBContract.User.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                    DBContract.User.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    DBContract.User.COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEP +
                    DBContract.User.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
                    DBContract.User.COLUMN_NAME_COUNTRY + TEXT_TYPE + COMMA_SEP +
                    DBContract.User.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP +
                    DBContract.User.COLUMN_NAME_BIRTHDATE + TEXT_TYPE + COMMA_SEP +
                    DBContract.User.COLUMN_NAME_SEX + TEXT_TYPE + COMMA_SEP +
                    DBContract.User.COLUMN_NAME_PHOTO + IMAGE_TYPE +
                    " )";

    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + DBContract.User.TABLE_NAME;

    public UserManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USERS);
    }
}
