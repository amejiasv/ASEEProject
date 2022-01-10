package es.unex.giiis.asee.project.data.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import es.unex.giiis.asee.project.data.model.Follow;

@Dao
public interface FollowDao {

    @Query("SELECT * FROM follow")
    public List<Follow> getAll();

    @Insert
    public void add(Follow follow);
}
