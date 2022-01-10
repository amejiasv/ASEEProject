package es.unex.giiis.asee.project.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import es.unex.giiis.asee.project.AppContainer;
import es.unex.giiis.asee.project.AppExecutors;
import es.unex.giiis.asee.project.MyApplication;
import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Recipe;
import es.unex.giiis.asee.project.data.model.User;
import es.unex.giiis.asee.project.data.model.Valoration;
import es.unex.giiis.asee.project.data.network.FetchIngredients;
import es.unex.giiis.asee.project.data.roomdb.AppDatabase;
import es.unex.giiis.asee.project.ui.viewmodels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_RECIPE_REQUEST = 10;
    private static final int EDIT_USER_PROFILE_REQUEST = 50;
    private static final int ADD_COMMENT_REQUEST = 100;

    private MainActivityViewModel mMainActivityViewModel;

    private ImageView createRecipeImageButton;
    private BottomNavigationView bottomNavigation;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolBar = (Toolbar) findViewById(R.id.topBar);
        toolBar.setTitle(R.string.app_name);
        setSupportActionBar(toolBar);

        new FetchIngredients(MainActivity.this).start();

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        mMainActivityViewModel = new ViewModelProvider(this, appContainer.factory).get(MainActivityViewModel.class);

        createRecipeImageButton = findViewById(R.id.recipeImageCreateRecipe);
        createRecipeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateRecipeActivity.class);
                startActivityForResult(intent, ADD_RECIPE_REQUEST);
            }
        });

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_screen:
                        openFragment(HomeFragment.newInstance());
                        toolBar.setTitle(R.string.app_name);
                        return true;
                    case R.id.favourites_screen:
                        openFragment(FavouriteRecipesFragment.newInstance());
                        toolBar.setTitle(R.string.favourite_recipes);
                        return true;
                    case R.id.profile_screen:
                        openFragment(UserProfileFragment.newInstance());
                        toolBar.setTitle(R.string.my_profile);
                        return true;
                }
                return false;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, HomeFragment.newInstance());
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        sharedPreferences = getSharedPreferences("userid", this.MODE_PRIVATE);
        long userid = sharedPreferences.getLong("userid", 0);

        if (requestCode == EDIT_USER_PROFILE_REQUEST) {
            if (resultCode == RESULT_OK) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Changes saved!")
                        .setMessage("You successfully updated your profile's information.")
                        .setPositiveButton("Ok", null)
                        .show();

                User user = new User(data);
                mMainActivityViewModel.updateUser(user);
            }
        } else if (requestCode == ADD_COMMENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Comment posted!")
                        .setMessage("Your comment was successfully posted. Thanks for sharing your opinion!")
                        .setPositiveButton("Ok", null)
                        .show();

                Valoration valoration = new Valoration(data);
                mMainActivityViewModel.insertValoration(valoration);

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase database = AppDatabase.getInstance(MainActivity.this);
                        Recipe recipe = database.getRecipeDao().getRecipeById(valoration.getRecipeid());
                        List<Valoration> valorations = database.getValorationDao().getRecipeValorationsN(valoration.getRecipeid());

                        int difficulty = 0;
                        int score = 0;

                        for(Valoration v : valorations) {
                            difficulty += v.getDifficulty();
                            score += v.getScore();
                        }

                        database.getRecipeDao().update(difficulty / valorations.size(),
                                score / valorations.size(), recipe.getId());
                    }
                });
            }
        } else if (requestCode == ADD_RECIPE_REQUEST) {
            if (resultCode == RESULT_OK) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Recipe created!")
                        .setMessage("Congratulations! Your recipe was shared with the world!")
                        .setPositiveButton("Ok", null)
                        .show();

                Recipe recipe = new Recipe(data);
                recipe.setDifficulty(0);
                recipe.setScore(0);
                recipe.setUserid(userid);
                mMainActivityViewModel.insertRecipe(recipe, data.getLongArrayExtra("ingredients"));
            }
        }
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}