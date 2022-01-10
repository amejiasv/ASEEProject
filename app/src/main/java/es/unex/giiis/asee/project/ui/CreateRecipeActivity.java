package es.unex.giiis.asee.project.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Recipe;

public class CreateRecipeActivity extends AppCompatActivity {

    public static final int SELECT_IMAGE = 20;
    public static final int ADD_INGREDIENTS_REQUEST = 35;

    private ImageView recipeImage;
    private Button bAddPhoto;
    private Button bRemovePhoto;
    private EditText recipeName;
    private EditText recipeDescription;
    private EditText recipeDuration;
    private TextView recipeCategories;
    private Button bAddIngredients;
    private Button bCreateRecipe;

    private long[] recipeIngredients;

    boolean[] selectedCategories;
    ArrayList<Integer> categoriesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        Toolbar toolBar = (Toolbar) findViewById(R.id.topBar);
        toolBar.setTitle(R.string.create_recipe);
        setSupportActionBar(toolBar);

        recipeImage = findViewById(R.id.recipeImageCreateRecipe);
        recipeName = findViewById(R.id.textNewRecipeName);
        recipeDescription = findViewById(R.id.textNewRecipeDescription);
        recipeDuration = findViewById(R.id.textNewRecipeDuration);
        recipeCategories = findViewById(R.id.mEditRecipeCategories);

        recipeImage.setImageDrawable(getResources().getDrawable(R.drawable.no_image));

        bAddPhoto = findViewById(R.id.btnAddNewRecipePhoto);
        bAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                if (photoPickerIntent.resolveActivity(getPackageManager()) != null) {
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_IMAGE);
                }
            }
        });

        bRemovePhoto = findViewById(R.id.btnRemoveRecipePhoto);
        bRemovePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeImage.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
            }
        });

        String[] categoriesArray = getResources().getStringArray(R.array.categories_recipe);
        selectedCategories = new boolean[categoriesArray.length];

        recipeCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateRecipeActivity.this);
                builder.setTitle(R.string.categories).setCancelable(false)
                        .setMultiChoiceItems(R.array.categories_recipe, selectedCategories, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                if (b) {
                                    categoriesList.add(i);
                                    Collections.sort(categoriesList);
                                } else
                                    categoriesList.remove((Object) i);
                            }
                        }).
                        setPositiveButton(R.string.confirm_choice, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int j = 0; j < categoriesList.size(); j++) {
                                    stringBuilder.append(categoriesArray[categoriesList.get(j)]);
                                    if (j != categoriesList.size() - 1)
                                        stringBuilder.append(", ");
                                }
                                recipeCategories.setText(stringBuilder.toString());
                            }
                        }).setNeutralButton(R.string.reset_choices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < selectedCategories.length; j++) {
                            selectedCategories[j] = false;
                            categoriesList.clear();
                            recipeCategories.setText("");
                        }
                    }
                });

                builder.show();
            }
        });

        bAddIngredients = findViewById(R.id.btnAddIngredients);
        bAddIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateRecipeActivity.this, AddIngredientsActivity.class);
                startActivityForResult(intent, ADD_INGREDIENTS_REQUEST);
            }
        });

        bCreateRecipe = findViewById(R.id.btnCreateRecipe);
        bCreateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = recipeName.getText().toString();
                String description = recipeDescription.getText().toString();
                String durationStr = recipeDuration.getText().toString();

                int duration = 0;
                if (!durationStr.equals(""))
                    duration = Integer.parseInt(durationStr);

                String categories = recipeCategories.getText().toString();

                Bitmap bitmap = ((BitmapDrawable) recipeImage.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] photo = baos.toByteArray();
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (name.equals("") || description.equals("") || recipeIngredients == null) {
                    Toast.makeText(CreateRecipeActivity.this, "Fill in all the fields", Toast.LENGTH_LONG).show();
                } else {
                    Intent data = new Intent();
                    Recipe.packageIntent(data, name, description, categories, duration, photo);
                    data.putExtra("ingredients", recipeIngredients);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    recipeImage.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(CreateRecipeActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            } else
                Toast.makeText(CreateRecipeActivity.this, "You haven't selected an image", Toast.LENGTH_LONG).show();
        } else if (requestCode == ADD_INGREDIENTS_REQUEST) {
            if (resultCode == RESULT_OK) {
                recipeIngredients = data.getLongArrayExtra("ingredients");
            }
        }
    }
}
