package es.unex.giiis.asee.project.data.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.asee.project.data.model.Recipe;


@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    public LiveData<List<Recipe>> getAll();

    @Query("SELECT * FROM recipe WHERE id = :recipeid")
    public Recipe getRecipeById(long recipeid);

    @Query("SELECT * FROM recipe WHERE userid = :userid")
    public LiveData<List<Recipe>> getUserRecipes(long userid);

    @Query("SELECT * FROM recipe WHERE id IN (SELECT recipeid FROM favourite WHERE userid = :userid)")
    public LiveData<List<Recipe>> getUserFavouriteRecipes(long userid);

    @Query("SELECT COUNT(*) FROM recipe WHERE userid = :userid")
    public LiveData<Integer> getNumberOfUserRecipes(long userid);

    @Query("SELECT COUNT(*) FROM favourite WHERE recipeid = :recipeid")
    public LiveData<Integer> getNumberOfRecipeFavourites(long recipeid);

    @Insert
    public long insert(Recipe recipe);

    @Delete
    public void delete(Recipe recipe);

    @Query("UPDATE recipe SET difficulty = :difficulty, score = :score WHERE id = :recipeid")
    public void update(int difficulty, int score, long recipeid);
}
