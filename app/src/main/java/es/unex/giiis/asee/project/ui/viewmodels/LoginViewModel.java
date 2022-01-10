package es.unex.giiis.asee.project.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import es.unex.giiis.asee.project.data.Repository;
import es.unex.giiis.asee.project.data.model.User;

public class LoginViewModel extends ViewModel {

    private final Repository repository;
    private LiveData<User> mUser;
    private String mUsername = "";

    public LoginViewModel(Repository repository) {
        this.repository = repository;
        mUser = this.repository.getUserByUsername();
    }

    public void setUsername(String username) {
        this.mUsername = username;
        repository.setUsername(username);
    }

    public LiveData<User> getUserByUsername() {
        return mUser;
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }
}
