package es.unex.giiis.asee.project.data.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import es.unex.giiis.asee.project.data.model.Favourite;
import es.unex.giiis.asee.project.data.model.Follow;
import es.unex.giiis.asee.project.data.model.Ingredient;
import es.unex.giiis.asee.project.data.model.ListIngredients;
import es.unex.giiis.asee.project.data.model.Recipe;
import es.unex.giiis.asee.project.data.model.User;
import es.unex.giiis.asee.project.data.model.Valoration;


@Database(entities = {User.class, Recipe.class, Ingredient.class, Favourite.class, Follow.class, Valoration.class, ListIngredients.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, AppDatabase.class, "appdb.db").build();
        return instance;
    }

    public abstract UserDao getUserDao();

    public abstract RecipeDao getRecipeDao();

    public abstract IngredientDao getIngredientDao();

    public abstract FavouriteDao getFavouriteDao();

    public abstract ValorationDao getValorationDao();

    public abstract ListIngredientsDao getListIngredientsDao();
}
