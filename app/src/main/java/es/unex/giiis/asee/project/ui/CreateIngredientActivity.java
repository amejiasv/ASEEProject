package es.unex.giiis.asee.project.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Ingredient;

public class CreateIngredientActivity extends AppCompatActivity {

    public static final int SELECT_IMAGE = 20;

    private ImageView mIngredientImage;
    private EditText mEditName;
    private EditText mEditDescription;
    private EditText mEditNRV;
    private TextView mEditCategories;

    boolean[] selectedCategories;
    ArrayList<Integer> categoriesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ingredient);

        Toolbar toolBar = (Toolbar) findViewById(R.id.topBar);
        toolBar.setTitle(R.string.add_ingredient);
        setSupportActionBar(toolBar);

        mIngredientImage = findViewById(R.id.ingredientImage);
        mEditName = findViewById(R.id.editIngredientName);
        mEditDescription = findViewById(R.id.editIngredientDescription);
        mEditNRV = findViewById(R.id.editNRVIngredient);
        mEditCategories = findViewById(R.id.mEditIngredientCategories);

        Button addPhoto = findViewById(R.id.bAddPhotoIngredient);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                if (photoPickerIntent.resolveActivity(getPackageManager()) != null) {
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_IMAGE);
                }
            }
        });

        Button removePhoto = findViewById(R.id.bRemovePhotoIngredient);
        removePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIngredientImage.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
            }
        });

        String[] categoriesArray = getResources().getStringArray(R.array.categories_ingredient);
        selectedCategories = new boolean[categoriesArray.length];

        mEditCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateIngredientActivity.this);
                builder.setTitle("Categories").setCancelable(false)
                        .setMultiChoiceItems(R.array.categories_ingredient, selectedCategories, new DialogInterface.OnMultiChoiceClickListener() {
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
                                mEditCategories.setText(stringBuilder.toString());
                            }
                        }).setNeutralButton(R.string.reset_choices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < selectedCategories.length; j++) {
                            selectedCategories[j] = false;
                            categoriesList.clear();
                            mEditCategories.setText("");
                        }
                    }
                });

                builder.show();
            }
        });

        Button createIngredient = findViewById(R.id.bCreateIngredient);
        createIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mEditName.getText().toString();
                String description = mEditDescription.getText().toString();
                String NRVstr = mEditNRV.getText().toString();

                int NRV = 0;
                if (!NRVstr.equals(""))
                    NRV = Integer.parseInt(NRVstr);

                String categories = mEditCategories.getText().toString();

                Bitmap bitmap = ((BitmapDrawable) mIngredientImage.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] photo = baos.toByteArray();
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (name.equals("") || description.equals("")) {
                    Toast.makeText(CreateIngredientActivity.this, "Fill in all the fields", Toast.LENGTH_LONG).show();
                } else {
                    Intent data = new Intent();
                    Ingredient.packageIntent(data, name, description, categories, NRV, photo);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    mIngredientImage.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(CreateIngredientActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            } else
                Toast.makeText(CreateIngredientActivity.this, "You haven't selected an image", Toast.LENGTH_LONG).show();
        }
    }
}