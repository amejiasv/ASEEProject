package es.unex.giiis.asee.project.data.roomdb;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.giiis.asee.project.data.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    public LiveData<List<User>> getAll();

    @Insert(onConflict = REPLACE)
    public long insert(User user);

    @Update
    public void update(User user);

    @Delete
    public void delete(User user);

    @Query("SELECT * FROM user WHERE userid = :userid")
    public LiveData<User> getUser(long userid);

    @Query("SELECT * FROM user WHERE username = :username")
    public LiveData<User> getUserByUsername(String username);
}
