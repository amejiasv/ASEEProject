package es.unex.giiis.asee.project.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import es.unex.giiis.asee.project.AppExecutors;
import es.unex.giiis.asee.project.data.model.Favourite;
import es.unex.giiis.asee.project.data.model.Ingredient;
import es.unex.giiis.asee.project.data.model.ListIngredients;
import es.unex.giiis.asee.project.data.model.Recipe;
import es.unex.giiis.asee.project.data.model.User;
import es.unex.giiis.asee.project.data.model.Valoration;
import es.unex.giiis.asee.project.data.roomdb.FavouriteDao;
import es.unex.giiis.asee.project.data.roomdb.IngredientDao;
import es.unex.giiis.asee.project.data.roomdb.ListIngredientsDao;
import es.unex.giiis.asee.project.data.roomdb.RecipeDao;
import es.unex.giiis.asee.project.data.roomdb.UserDao;
import es.unex.giiis.asee.project.data.roomdb.ValorationDao;

public class Repository {

    private static Repository sInstance;
    private final UserDao mUserDao;
    private final RecipeDao mRecipeDao;
    private final IngredientDao mIngredientDao;
    private final FavouriteDao mFavouriteDao;
    private final ListIngredientsDao mListIngredientsDao;
    private final ValorationDao mValorationDao;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private final MutableLiveData<Long> userid = new MutableLiveData<>();
    private final MutableLiveData<Long> recipeid = new MutableLiveData<>();
    private final MutableLiveData<String> username = new MutableLiveData<>();

    private Repository(UserDao userDao, RecipeDao recipeDao, IngredientDao ingredientDao,
                       FavouriteDao favouriteDao, ListIngredientsDao listIngredientsDao,
                       ValorationDao valorationDao) {
        mUserDao = userDao;
        mRecipeDao = recipeDao;
        mIngredientDao = ingredientDao;
        mFavouriteDao = favouriteDao;
        mListIngredientsDao = listIngredientsDao;
        mValorationDao = valorationDao;
    }

    public synchronized static Repository getInstance(UserDao userDao, RecipeDao recipeDao,
                                                      IngredientDao ingredientDao, FavouriteDao favouriteDao,
                                                      ListIngredientsDao listIngredientsDao,
                                                      ValorationDao valorationDao) {
        if(sInstance == null) {
            sInstance = new Repository(userDao, recipeDao, ingredientDao, favouriteDao,
                    listIngredientsDao, valorationDao);
        }
        return sInstance;
    }

    public void setUserid(final long userid){
        this.userid.setValue(userid);
    }

    public void setUsername(final String username) { this.username.setValue(username); }

    public void setRecipeId(final long recipeid) { this.recipeid.setValue(recipeid); }

    public LiveData<List<User>> getAllUsers() { return mUserDao.getAll(); }

    public LiveData<User> getUserById() { return Transformations.switchMap(userid, mUserDao::getUser); }

    public LiveData<User> getUserByUsername() { return Transformations.switchMap(username, mUserDao::getUserByUsername); }

    public LiveData<List<Ingredient>> getIngredients() { return mIngredientDao.getAll(); }

    public LiveData<List<Ingredient>> getIngredientsFromRecipe() { return Transformations.switchMap(recipeid, mIngredientDao::getIngredientsFromRecipe); }

    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeDao.getAll();
    }

    public LiveData<List<Recipe>> getUserFavouriteRecipes() { return Transformations.switchMap(userid, mRecipeDao::getUserFavouriteRecipes); }

    public LiveData<List<Recipe>> getUserRecipes() { return Transformations.switchMap(userid, mRecipeDao::getUserRecipes); }

    public LiveData<Integer> getNumberOfUserRecipes() { return Transformations.switchMap(userid, mRecipeDao::getNumberOfUserRecipes); }

    public LiveData<Integer> getNumberOfRecipeFavourites() { return Transformations.switchMap(recipeid, mRecipeDao::getNumberOfRecipeFavourites); }

    public LiveData<List<Valoration>> getValorationsFromRecipe() { return Transformations.switchMap(recipeid, mValorationDao::getRecipeValorations); }

    public void insertUser(User user) {
        mExecutors.diskIO().execute(() -> {
            mUserDao.insert(user);
        });
    }

    public void updateUser(User user) {
        mExecutors.diskIO().execute(() -> {
            mUserDao.update(user);
        });
    }

    public void insertRecipe(Recipe recipe, long[] ingredients) {
        mExecutors.diskIO().execute(() -> {
            long recipeid = mRecipeDao.insert(recipe);
            for (long ingredientid : ingredients) {
                ListIngredients listIngredients = new ListIngredients(recipeid, ingredientid);
                mListIngredientsDao.insert(listIngredients);
            }
        });
    }

    public void deleteUserRecipe(Recipe recipe) {
        mExecutors.diskIO().execute(() -> {
            mRecipeDao.delete(recipe);
        });
    }

    public void insertIngredient(Ingredient ingredient) {
        mExecutors.diskIO().execute(() -> {
            mIngredientDao.insert(ingredient);
        });
    }

    public void deleteFavouriteRecipe(Favourite favourite) {
        mExecutors.diskIO().execute(() -> {
            mFavouriteDao.delete(favourite);
        });
    }

    public void modifyFavourite(Favourite favourite) {
        mExecutors.diskIO().execute(() -> {
            if(mFavouriteDao.checkIfFavouriteExists(favourite.getRecipeid(), favourite.getUserid()) != 0)
                mFavouriteDao.delete(favourite);
            else
                mFavouriteDao.insert(favourite);
        });
    }

    public void insertValoration(Valoration valoration) {
        mExecutors.diskIO().execute(() -> {
            mValorationDao.insert(valoration);
        });
    }
}
