package es.unex.giiis.asee.project.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.project.data.Repository;
import es.unex.giiis.asee.project.data.model.Favourite;
import es.unex.giiis.asee.project.data.model.Recipe;
import es.unex.giiis.asee.project.data.model.User;

public class UserProfileViewModel extends ViewModel {

    private final Repository repository;
    private final LiveData<List<Recipe>> userRecipes;
    private final LiveData<User> user;
    private long loggedUserId = 0;

    public UserProfileViewModel(Repository repository) {
        this.repository = repository;
        user = this.repository.getUserById();
        userRecipes = this.repository.getUserRecipes();
    }

    public void setLoggedUserId(long userid) {
        loggedUserId = userid;
        repository.setUserid(userid);
    }

    public LiveData<User> getLoggedUser() { return user; }

    public LiveData<List<Recipe>> getUserRecipes() { return userRecipes; }

    public void modifyFavourite(long recipeid) {
        Favourite favourite = new Favourite(recipeid, loggedUserId);
        repository.modifyFavourite(favourite);
    }

    public LiveData<Integer> getNumberOfUserRecipes() { return repository.getNumberOfUserRecipes(); }

    public void deleteUserRecipe(Recipe recipe) { repository.deleteUserRecipe(recipe); }

}
