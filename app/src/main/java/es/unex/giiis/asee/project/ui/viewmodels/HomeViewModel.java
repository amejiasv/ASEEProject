package es.unex.giiis.asee.project.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.project.data.Repository;
import es.unex.giiis.asee.project.data.model.Favourite;
import es.unex.giiis.asee.project.data.model.Recipe;

public class HomeViewModel extends ViewModel {

    private final Repository repository;
    private final LiveData<List<Recipe>> recipes;
    private long loggedUserId = 0;

    public HomeViewModel(Repository repository) {
        this.repository = repository;
        recipes = this.repository.getRecipes();
    }

    public void setLoggedUserId(long userid) {
        loggedUserId = userid;
        repository.setUserid(userid);
    }

    public LiveData<List<Recipe>> getRecipes() { return recipes; }

    public void modifyFavourite(long recipeid) {
        Favourite favourite = new Favourite(recipeid, loggedUserId);
        repository.modifyFavourite(favourite);
    }
}
