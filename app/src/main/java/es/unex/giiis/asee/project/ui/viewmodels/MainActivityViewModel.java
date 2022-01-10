package es.unex.giiis.asee.project.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import es.unex.giiis.asee.project.data.Repository;
import es.unex.giiis.asee.project.data.model.Recipe;
import es.unex.giiis.asee.project.data.model.User;
import es.unex.giiis.asee.project.data.model.Valoration;

public class MainActivityViewModel extends ViewModel {

    private final Repository repository;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public void insertRecipe(Recipe recipe, long[] ingredients) { repository.insertRecipe(recipe, ingredients); }

    public void updateUser(User user) {
        repository.updateUser(user);
    }

    public void insertValoration(Valoration valoration) { repository.insertValoration(valoration); }
}
