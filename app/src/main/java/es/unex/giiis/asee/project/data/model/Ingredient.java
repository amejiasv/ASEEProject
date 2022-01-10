package es.unex.giiis.asee.project.data.model;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient")
public class Ingredient {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "name")
    private String name = new String();
    @ColumnInfo(name = "description")
    private String description = new String();
    @ColumnInfo(name = "categories")
    private String categories;
    @ColumnInfo(name = "NRV")
    private int NRV;
    @ColumnInfo(name = "photo")
    private byte[] photo;
    @Ignore
    private boolean isSelected = false;

    @Ignore
    public final static String INGREDIENT_ID = "id";
    @Ignore
    public final static String INGREDIENT_NAME = "name";
    @Ignore
    public final static String INGREDIENT_DESCRIPTION = "description";
    @Ignore
    public final static String INGREDIENT_CATEGORIES = "categories";
    @Ignore
    public final static String INGREDIENT_NRV = "NRV";
    @Ignore
    public final static String INGREDIENT_PHOTO = "photo";

    @Ignore
    public Ingredient() {

    }

    @Ignore
    public Ingredient(String name, String description, String categories, int NRV) {
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.NRV = NRV;
        this.photo = null;
    }

    @Ignore
    public Ingredient(long id, String name, String description, String categories, int NRV) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.NRV = NRV;
        this.photo = null;
    }

    @Ignore
    public Ingredient(Intent intent) {
        this.id = intent.getLongExtra(Ingredient.INGREDIENT_ID, 0);
        this.name = intent.getStringExtra(Ingredient.INGREDIENT_NAME);
        this.description = intent.getStringExtra(Ingredient.INGREDIENT_DESCRIPTION);
        this.categories = intent.getStringExtra(Ingredient.INGREDIENT_CATEGORIES);
        this.NRV = intent.getIntExtra(Ingredient.INGREDIENT_NRV, 0);
        this.photo = intent.getByteArrayExtra(Ingredient.INGREDIENT_PHOTO);
    }

    public Ingredient(long id, String name, String description, String categories, int NRV, byte[] photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.NRV = NRV;
        this.photo = photo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public int getNRV() {
        return NRV;
    }

    public void setNRV(int NRV) {
        this.NRV = NRV;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public boolean isSelected() { return isSelected; }

    public void setSelected(boolean selected) { isSelected = selected; }

    public static void packageIntent(Intent intent, String name, String description,
                                     String categories, int NRV, byte[] photo) {
        intent.putExtra(Ingredient.INGREDIENT_NAME, name);
        intent.putExtra(Ingredient.INGREDIENT_DESCRIPTION, description);
        intent.putExtra(Ingredient.INGREDIENT_CATEGORIES, categories);
        intent.putExtra(Ingredient.INGREDIENT_NRV, NRV);
        intent.putExtra(Ingredient.INGREDIENT_PHOTO, photo);
    }

    @Override
    public String toString() {
        return id + ITEM_SEP + name + ITEM_SEP + description + ITEM_SEP +
                categories + ITEM_SEP + NRV;
    }

    public String toLog() {
        return "ID: " + id + ITEM_SEP + "Name: " + name + ITEM_SEP + "Description: " + description + ITEM_SEP +
                "Categories: " + categories + ITEM_SEP + "NRV: " + NRV;
    }
}
