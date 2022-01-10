package es.unex.giiis.asee.project.data.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unex.giiis.asee.project.data.model.Favourite;

@Dao
public interface FavouriteDao {

    @Query("SELECT * FROM favourite")
    public List<Favourite> getAll();

    @Insert
    public void insert(Favourite favourite);

    @Delete
    public void delete(Favourite favourite);

    @Query("SELECT * FROM favourite WHERE recipeid = :recipeid AND userid = :userid")
    public int checkIfFavouriteExists(long recipeid, long userid);
}
