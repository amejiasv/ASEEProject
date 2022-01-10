package es.unex.giiis.asee.project.data.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import es.unex.giiis.asee.project.AppExecutors;
import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Ingredient;
import es.unex.giiis.asee.project.data.roomdb.AppDatabase;

public class FetchIngredients extends Thread {

    private String data = "";
    private ArrayList<Ingredient> ingredientList;
    private Handler mainHandler = new Handler();
    private ProgressDialog progressDialog;
    private Context context;

    public FetchIngredients(Context context) {
        this.context = context;
    }

    @Override
    public void run() {

        ingredientList = new ArrayList<>();

        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Fetching Data, please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        });

        try {
            URL url = new URL("https://world.openfoodfacts.org/ingredients.json");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                data = data + line;
            }

            if(!data.isEmpty()) {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray ingredients = jsonObject.getJSONArray("tags");
                if(!ingredientList.isEmpty())
                    ingredientList.clear();

                Resources resources = context.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.no_image);
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] image = stream.toByteArray();

                for (int i = 0; i < 100; i++) {
                    Ingredient ingredient = new Ingredient();
                    JSONObject ingredientName = ingredients.getJSONObject(i);
                    ingredient.setName(ingredientName.getString("name"));
                    ingredient.setNRV(Integer.parseInt(ingredientName.getString("products")));
                    ingredient.setDescription(ingredientName.getString("url"));
                    ingredient.setCategories("Any");
                    ingredient.setPhoto(image);

                    ingredientList.add(ingredient);
                }

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase database = AppDatabase.getInstance(context);
                        for (Ingredient ingredient : ingredientList) {
                            Ingredient ingredientAux = database.getIngredientDao().searchIngredient(ingredient.getName());
                            if (ingredientAux == null)
                                database.getIngredientDao().insert(ingredient);
                        }
                    }
                });
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }
}
