package es.unex.giiis.asee.project.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import es.unex.giiis.asee.project.data.Repository;
import es.unex.giiis.asee.project.data.model.User;
import es.unex.giiis.asee.project.data.model.Valoration;

public class RecipeDetailViewModel extends ViewModel {

    private final Repository repository;
    private final LiveData<User> mUser;
    private final LiveData<Integer> mRecipeFvourites;
    private long userid = 0;
    private long recipeid = 0;

    public RecipeDetailViewModel(Repository repository) {
        this.repository = repository;
        mUser = this.repository.getUserById();
        mRecipeFvourites = this.repository.getNumberOfRecipeFavourites();
    }

    public void setUserid(long userid) {
        this.userid = userid;
        this.repository.setUserid(userid);
    }

    public void setRecipeid(long recipeid) {
        this.recipeid = recipeid;
        this.repository.setRecipeId(recipeid);
    }

    public LiveData<User> getUser() { return mUser; }

    public LiveData<Integer> getNumberOfRecipeFavourites() { return mRecipeFvourites; }

    public void insertValoration(Valoration valoration) { repository.insertValoration(valoration); }
}
