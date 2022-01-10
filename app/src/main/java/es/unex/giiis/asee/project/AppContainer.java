package es.unex.giiis.asee.project;

import android.content.Context;

import es.unex.giiis.asee.project.data.Repository;
import es.unex.giiis.asee.project.data.roomdb.AppDatabase;

public class AppContainer {

    private AppDatabase database;
    public Repository repository;
    public MainViewModelFactory factory;

    public AppContainer(Context context) {
        database = AppDatabase.getInstance(context);
        repository = Repository.getInstance(database.getUserDao(), database.getRecipeDao(),
                database.getIngredientDao(), database.getFavouriteDao(), database.getListIngredientsDao(),
                database.getValorationDao());
        factory = new MainViewModelFactory(repository);
    }
}
