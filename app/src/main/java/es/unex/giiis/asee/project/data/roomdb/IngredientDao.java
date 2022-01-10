package es.unex.giiis.asee.project.data.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.asee.project.data.model.Ingredient;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredient")
    public LiveData<List<Ingredient>> getAll();

    @Query("SELECT * FROM ingredient WHERE id IN (SELECT ingredientid FROM list_ingredients WHERE recipeid = :recipeid)")
    public LiveData<List<Ingredient>> getIngredientsFromRecipe(long recipeid);

    @Query("SELECT * FROM ingredient WHERE name = :name")
    public Ingredient searchIngredient(String name);

    @Insert
    public long insert(Ingredient ingredient);

    @Update
    public int update(Ingredient ingredient);
}
