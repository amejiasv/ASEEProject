package es.unex.giiis.asee.project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.project.AppContainer;
import es.unex.giiis.asee.project.MyApplication;
import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Ingredient;
import es.unex.giiis.asee.project.ui.adapters.IngredientAdapter;
import es.unex.giiis.asee.project.ui.viewmodels.IngredientViewModel;

public class AddIngredientsActivity extends AppCompatActivity {

    private static final int ADD_INGREDIENT_REQUEST = 30;

    private IngredientViewModel mIngredientViewModel;

    private SearchView searchIngredients;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private IngredientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients_list);

        Toolbar toolBar = (Toolbar) findViewById(R.id.topBar);
        toolBar.setTitle(R.string.add_ingredients);
        setSupportActionBar(toolBar);

        recyclerView = findViewById(R.id.addIngredientsRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(AddIngredientsActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(AddIngredientsActivity.this, DividerItemDecoration.VERTICAL));

        adapter = new IngredientAdapter(this, new IngredientAdapter.OnIngredientClickListener() {
            @Override
            public void onItemClick(Ingredient ingredient) {
                Intent intent = new Intent(AddIngredientsActivity.this, IngredientDetailActivity.class);
                Ingredient.packageIntent(intent,
                        ingredient.getName(),
                        ingredient.getDescription(),
                        ingredient.getCategories(),
                        ingredient.getNRV(),
                        ingredient.getPhoto());
                startActivity(intent);
            }
        }, 2);

        recyclerView.setAdapter(adapter);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        mIngredientViewModel = new ViewModelProvider(this, appContainer.factory).get(IngredientViewModel.class);
        mIngredientViewModel.getIngredients().observe(this, ingredients -> {
            adapter.load(ingredients);
        });

        searchIngredients = findViewById(R.id.searchViewAddIngredientsList);
        searchIngredients.setIconifiedByDefault(false);
        searchIngredients.setIconified(false);
        searchIngredients.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        Button bAddIngredients = findViewById(R.id.bAddIngredients);
        bAddIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Ingredient> checkedIngredients = new ArrayList<>();
                checkedIngredients.addAll(adapter.getCheckedIngredients());
                long[] idArray = new long[checkedIngredients.size()];

                for (int i = 0; i < checkedIngredients.size(); i++) {
                    idArray[i] = checkedIngredients.get(i).getId();
                }

                Intent data = new Intent();
                data.putExtra("ingredients", idArray);
                setResult(RESULT_OK, data);
                finish();
            }
        });

        TextView addIngredientQuestion = findViewById(R.id.addIngredientQuestion);
        addIngredientQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddIngredientsActivity.this, CreateIngredientActivity.class);
                startActivityForResult(intent, ADD_INGREDIENT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_INGREDIENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddIngredientsActivity.this);
                builder.setTitle("Congratulations!")
                        .setMessage("You successfully added a new ingredient. Thank you!")
                        .setPositiveButton("Ok", null)
                        .show();

                Ingredient ingredient = new Ingredient(data);
                mIngredientViewModel.insertIngredient(ingredient);
            }
        }
    }
}