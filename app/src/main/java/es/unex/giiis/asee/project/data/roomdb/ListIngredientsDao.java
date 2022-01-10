package es.unex.giiis.asee.project.data.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unex.giiis.asee.project.data.model.ListIngredients;

@Dao
public interface ListIngredientsDao {

    @Query("SELECT * FROM list_ingredients")
    public List<ListIngredients> getAll();

    @Insert
    public void insert(ListIngredients listIngredients);

    @Delete
    public void delete(ListIngredients listIngredients);
}
