package es.unex.giiis.asee.project.data.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unex.giiis.asee.project.data.model.Valoration;

@Dao
public interface ValorationDao {

    @Query("SELECT * FROM valoration WHERE recipeid = :recipeid")
    public LiveData<List<Valoration>> getRecipeValorations(long recipeid);

    @Query("SELECT * FROM valoration WHERE recipeid = :recipeid")
    public List<Valoration> getRecipeValorationsN(long recipeid);

    @Query("SELECT COUNT(*) FROM valoration WHERE recipeid = :recipeid")
    public int getNumberOfValorationsOfRecipe(long recipeid);

    @Insert
    public void insert(Valoration valoration);
}
