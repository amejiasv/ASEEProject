package es.unex.giiis.asee.project.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import es.unex.giiis.asee.project.AppContainer;
import es.unex.giiis.asee.project.AppExecutors;
import es.unex.giiis.asee.project.MyApplication;
import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Recipe;
import es.unex.giiis.asee.project.data.model.Valoration;
import es.unex.giiis.asee.project.data.roomdb.AppDatabase;
import es.unex.giiis.asee.project.ui.viewmodels.RecipeDetailViewModel;

public class RecipeDetailActivity extends AppCompatActivity {

    private static final int ADD_COMMENT_REQUEST = 100;

    private RecipeDetailViewModel mRecipeDetailViewModel;

    private ImageView recipeImage;
    private TextView recipeUsername;
    private TextView recipeNFavourites;
    private TextView recipeName;
    private TextView recipeDuration;
    private TextView difficultyLabel;
    private TextView scoreLabel;
    private RatingBar recipeDifficulty;
    private RatingBar recipeScore;
    private TextView recipeCategories;
    private TextView recipeDescription;
    private Button bRecipeIngredients;
    private Button bRecipeComments;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        sharedPreferences = getSharedPreferences("userid", this.MODE_PRIVATE);
        long userid = sharedPreferences.getLong("userid", 0);

        Toolbar toolBar = (Toolbar) findViewById(R.id.topBar);
        toolBar.setTitle(R.string.recipe_information);
        setSupportActionBar(toolBar);

        recipeImage = findViewById(R.id.recipeImageRecipeDetail);
        recipeUsername = findViewById(R.id.usernameRecipeDetail);
        recipeNFavourites = findViewById(R.id.nFavouritesRecipeDetail);
        recipeName = findViewById(R.id.recipeNameRecipeDetail);
        recipeDuration = findViewById(R.id.recipeDurationRecipeDetail);
        difficultyLabel = findViewById(R.id.recipeDifficultyRecipeDetail);
        scoreLabel = findViewById(R.id.recipeScoreRecipeDetail);
        recipeDifficulty = findViewById(R.id.difficultyRatingRecipeDetail);
        recipeScore = findViewById(R.id.scoreRatingRecipeDetail);
        recipeDescription = findViewById(R.id.recipeDetailDescription);
        recipeCategories = findViewById(R.id.recipeDetailCategories);

        Intent data = getIntent();

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        mRecipeDetailViewModel = new ViewModelProvider(this, appContainer.factory).get(RecipeDetailViewModel.class);
        mRecipeDetailViewModel.setUserid(data.getLongExtra(Recipe.RECIPE_USERID, 0));
        mRecipeDetailViewModel.getUser().observe(this, user -> {
            recipeUsername.setText(user.getUsername());
        });

        mRecipeDetailViewModel.setRecipeid(data.getLongExtra(Recipe.RECIPE_ID, 0));
        mRecipeDetailViewModel.getNumberOfRecipeFavourites().observe(this, n -> {
            recipeNFavourites.setText(n + " favourites");
        });

        recipeImage.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeByteArray(data.getByteArrayExtra(Recipe.RECIPE_PHOTO), 0, data.getByteArrayExtra(Recipe.RECIPE_PHOTO).length)));
        recipeName.setText(data.getStringExtra(Recipe.RECIPE_NAME));
        recipeDuration.setText("" + data.getIntExtra(Recipe.RECIPE_DURATION, 0) + " min.");
        recipeDifficulty.setRating(data.getIntExtra(Recipe.RECIPE_DIFFICULTY, 0));
        recipeScore.setRating(data.getIntExtra(Recipe.RECIPE_SCORE, 0));
        recipeCategories.setText(Html.fromHtml("<b>Categories</b>: " + data.getStringExtra(Recipe.RECIPE_CATEGORIES)));
        recipeDescription.setText(data.getStringExtra(Recipe.RECIPE_DESCRIPTION));

        bRecipeIngredients = findViewById(R.id.bIngredientsRecipeDetail);
        bRecipeIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerRecipeDetail, IngredientsListFragment.newInstance(data.getLongExtra(Recipe.RECIPE_ID, 0)));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        bRecipeComments = findViewById(R.id.bCommentsRecipeDetail);
        bRecipeComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.getLongExtra(Recipe.RECIPE_USERID, 0) != userid) {
                    Intent intent = new Intent(RecipeDetailActivity.this, AddValorationActivity.class);
                    intent.putExtra(Valoration.VALORATION_RECIPEID, data.getLongExtra(Recipe.RECIPE_ID, 0));
                    startActivityForResult(intent, ADD_COMMENT_REQUEST);
                }
                else {
                    Intent intent = new Intent(RecipeDetailActivity.this, ValorationsListActivity.class);
                    intent.putExtra(Valoration.VALORATION_RECIPEID, data.getLongExtra(Recipe.RECIPE_ID, 0));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_COMMENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecipeDetailActivity.this);
                builder.setTitle("Comment posted!")
                        .setMessage("Your comment was successfully posted. Thanks for sharing your opinion!.")
                        .setPositiveButton("Ok", null)
                        .show();

                Valoration valoration = new Valoration(data);
                mRecipeDetailViewModel.insertValoration(valoration);

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase database = AppDatabase.getInstance(RecipeDetailActivity.this);
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
        }
    }
}