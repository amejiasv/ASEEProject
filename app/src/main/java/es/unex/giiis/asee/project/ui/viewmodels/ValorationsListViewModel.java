package es.unex.giiis.asee.project.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.project.data.Repository;
import es.unex.giiis.asee.project.data.model.User;
import es.unex.giiis.asee.project.data.model.Valoration;

public class ValorationsListViewModel extends ViewModel {

    private final Repository repository;
    private final LiveData<List<Valoration>> recipeValorations;
    private final LiveData<List<User>> users;
    private long recipeid = 0;

    public ValorationsListViewModel(Repository repository) {
        this.repository = repository;
        recipeValorations = this.repository.getValorationsFromRecipe();
        users = this.repository.getAllUsers();
    }

    public void setRecipeid(long recipeid) {
        this.recipeid = recipeid;
        this.repository.setRecipeId(recipeid);
    }

    public LiveData<List<Valoration>> getRecipeValorations() { return recipeValorations; }

    public LiveData<List<User>> getAllUsers() { return users; }
}
