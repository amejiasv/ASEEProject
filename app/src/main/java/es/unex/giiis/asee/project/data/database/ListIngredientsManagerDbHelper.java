package es.unex.giiis.asee.project.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ListIngredientsManagerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "appdb.db";

    private static final String RECIPE_TABLE_NAME = "recipe";
    private static final String INGREDIENT_TABLE_NAME = "ingredient";
    private static final String COMMA_SEP = ",";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String SQL_CREATE_LIST_INGREDIENTS =
            "CREATE TABLE " + DBContract.ListIngredients.TABLE_NAME + " (" +
                    DBContract.ListIngredients.COLUMN_NAME_RECIPEID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    DBContract.ListIngredients.COLUMN_NAME_INGREDIENTID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    "FOREIGN KEY(" + DBContract.ListIngredients.COLUMN_NAME_RECIPEID + ") REFERENCES " + RECIPE_TABLE_NAME + "(id)" + COMMA_SEP +
                    "FOREIGN KEY(" + DBContract.ListIngredients.COLUMN_NAME_INGREDIENTID + ") REFERENCES " + INGREDIENT_TABLE_NAME + "(id)" +
                    " )";
    private static final String SQL_DELETE_LIST_INGREDIENTS =
            "DROP TABLE IF EXISTS " + DBContract.ListIngredients.TABLE_NAME;

    public ListIngredientsManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LIST_INGREDIENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_LIST_INGREDIENTS);
        onCreate(db);
    }
}
