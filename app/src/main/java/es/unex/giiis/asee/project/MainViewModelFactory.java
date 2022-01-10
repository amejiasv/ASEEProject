package es.unex.giiis.asee.project;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

import es.unex.giiis.asee.project.data.Repository;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository repository;

    public MainViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        try {
            return modelClass.getConstructor(Repository.class).newInstance(repository);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
