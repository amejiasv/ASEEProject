package es.unex.giiis.asee.project.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.project.data.Repository;
import es.unex.giiis.asee.project.data.model.Favourite;
import es.unex.giiis.asee.project.data.model.Recipe;

public class FavouriteRecipesViewModel extends ViewModel {

    private final Repository repository;
    private final LiveData<List<Recipe>> favouriteRecipes;
    private long loggedUserId = 0;

    public FavouriteRecipesViewModel(Repository repository) {
        this.repository = repository;
        favouriteRecipes = this.repository.getUserFavouriteRecipes();
    }

    public void setLoggedUserId(long userid) {
        loggedUserId = userid;
        repository.setUserid(userid);
    }

    public LiveData<List<Recipe>> getFavouriteRecipes() {
        return favouriteRecipes;
    }

    public void deleteFavouriteRecipe(long recipeid) {
        Favourite favourite = new Favourite(recipeid, loggedUserId);
        repository.deleteFavouriteRecipe(favourite);
    }
}
