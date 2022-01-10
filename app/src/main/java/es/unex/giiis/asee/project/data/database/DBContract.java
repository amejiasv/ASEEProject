package es.unex.giiis.asee.project.data.database;

import android.provider.BaseColumns;

public class DBContract {

    private DBContract() {}

    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_COUNTRY = "country";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_BIRTHDATE = "dateofbirth";
        public static final String COLUMN_NAME_SEX = "sex";
        public static final String COLUMN_NAME_PHOTO = "photo";
    }

    public static class Recipe implements BaseColumns {
        public static final String TABLE_NAME = "recipe";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CATEGORIES = "categories";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_DIFFICULTY = "difficulty";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_PHOTO = "photo";
        public static final String COLUMN_NAME_USERID = "userid";
    }

    public static class Ingredient implements BaseColumns {
        public static final String TABLE_NAME = "ingredient";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CATEGORIES = "categories";
        public static final String COLUMN_NAME_NRV = "NRV";
        public static final String COLUMN_NAME_PHOTO = "photo";
    }

    public static class Valoration implements BaseColumns {
        public static final String TABLE_NAME = "valoration";
        public static final String COLUMN_NAME_COMMENT = "comment";
        public static final String COLUMN_NAME_DIFFICULTY = "difficulty";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_USERID = "userid";
        public static final String COLUMN_NAME_RECIPEID = "recipeid";
    }

    public static class Favourite implements BaseColumns {
        public static final String TABLE_NAME = "favourite";
        public static final String COLUMN_NAME_RECIPEID = "recipeid";
        public static final String COLUMN_NAME_USERID = "userid";
    }

    public static class ListIngredients implements BaseColumns {
        public static final String TABLE_NAME = "list_ingredients";
        public static final String COLUMN_NAME_RECIPEID = "recipeid";
        public static final String COLUMN_NAME_INGREDIENTID = "ingredientid";
    }
}
