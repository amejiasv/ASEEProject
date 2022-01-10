package es.unex.giiis.asee.project.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.project.data.Repository;
import es.unex.giiis.asee.project.data.model.Ingredient;
import es.unex.giiis.asee.project.data.model.Recipe;

public class IngredientViewModel extends ViewModel {

    private final Repository repository;
    private final LiveData<List<Ingredient>> ingredients;
    private final LiveData<List<Ingredient>> recipeIngredients;
    private long recipeid = 0;

    public IngredientViewModel(Repository repository) {
        this.repository = repository;
        ingredients = this.repository.getIngredients();
        recipeIngredients = this.repository.getIngredientsFromRecipe();
    }

    public void setRecipeid(long recipeid) {
        this.recipeid = recipeid;
        repository.setRecipeId(recipeid);
    }

    public LiveData<List<Ingredient>> getIngredients() { return ingredients; }

    public LiveData<List<Ingredient>> getIngredientsFromRecipe() { return recipeIngredients; }

    public void insertIngredient(Ingredient ingredient) { repository.insertIngredient(ingredient); }

}
