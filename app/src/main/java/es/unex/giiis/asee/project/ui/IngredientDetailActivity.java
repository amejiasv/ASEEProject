package es.unex.giiis.asee.project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Ingredient;

public class IngredientDetailActivity extends AppCompatActivity {

    private ImageView image;
    private TextView name;
    private TextView description;
    private TextView categories;
    private TextView nrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_detail);

        Toolbar toolBar = (Toolbar) findViewById(R.id.topBar);
        toolBar.setTitle(R.string.ingredient_info);
        setSupportActionBar(toolBar);

        image = findViewById(R.id.imageIngredientDetail);
        name = findViewById(R.id.ingredientNameDetail);
        description = findViewById(R.id.descriptionIngredientDetail);
        categories = findViewById(R.id.ingredientCategoriesDetail);
        nrv = findViewById(R.id.ingredientNRVDetail);

        image.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra(Ingredient.INGREDIENT_PHOTO), 0, getIntent().getByteArrayExtra(Ingredient.INGREDIENT_PHOTO).length)));
        name.setText(getIntent().getStringExtra(Ingredient.INGREDIENT_NAME));
        description.setText(getIntent().getStringExtra(Ingredient.INGREDIENT_DESCRIPTION));
        categories.setText(getIntent().getStringExtra(Ingredient.INGREDIENT_CATEGORIES));
        nrv.setText("" + getIntent().getIntExtra(Ingredient.INGREDIENT_NRV, 0) + " kcal");
    }
}