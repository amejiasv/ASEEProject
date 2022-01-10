package es.unex.giiis.asee.project.data.model;

import static androidx.room.ForeignKey.CASCADE;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe", foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "userid", childColumns = "userid", onDelete = CASCADE)
}, indices = {
        @Index(value = {"userid"})
})
public class Recipe {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "categories")
    private String categories;
    @ColumnInfo(name = "duration")
    private int duration;
    @ColumnInfo(name = "score")
    private int score;
    @ColumnInfo(name = "difficulty")
    private int difficulty;
    @ColumnInfo(name = "photo")
    private byte[] photo;
    @ColumnInfo(name = "userid")
    private long userid;

    @Ignore
    public static final String RECIPE_ID = "id";
    @Ignore
    public static final String RECIPE_NAME = "name";
    @Ignore
    public final static String RECIPE_DESCRIPTION = "description";
    @Ignore
    public final static String RECIPE_CATEGORIES = "categories";
    @Ignore
    public final static String RECIPE_DURATION = "duration";
    @Ignore
    public final static String RECIPE_SCORE = "score";
    @Ignore
    public final static String RECIPE_DIFFICULTY = "difficulty";
    @Ignore
    public final static String RECIPE_PHOTO = "photo";
    @Ignore
    public final static String RECIPE_USERID = "userid";

    @Ignore
    public Recipe() {

    }

    @Ignore
    public Recipe(Intent intent) {
        this.id = intent.getLongExtra(Recipe.RECIPE_ID, 0);
        this.name = intent.getStringExtra(Recipe.RECIPE_NAME);
        this.description = intent.getStringExtra(Recipe.RECIPE_DESCRIPTION);
        this.categories = intent.getStringExtra(Recipe.RECIPE_CATEGORIES);
        this.duration = intent.getIntExtra(Recipe.RECIPE_DURATION, 0);
        this.score = intent.getIntExtra(Recipe.RECIPE_SCORE, 0);
        this.difficulty = intent.getIntExtra(Recipe.RECIPE_DIFFICULTY, 0);
        this.photo = intent.getByteArrayExtra(Recipe.RECIPE_PHOTO);
        this.userid = intent.getLongExtra(Recipe.RECIPE_USERID, 0);
    }

    @Ignore
    public Recipe(long id, String name, String description, String categories, int duration,
                  int score, int difficulty, long userid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.duration = duration;
        this.score = score;
        this.difficulty = difficulty;
        this.userid = userid;
    }

    public Recipe(long id, String name, String description, String categories, int duration,
                  int score, int difficulty, byte[] photo, long userid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.duration = duration;
        this.score = score;
        this.difficulty = difficulty;
        this.photo = photo;
        this.userid = userid;
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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public static void packageIntent(Intent intent, String name, String description, String categories, int duration, byte[] photo) {
        intent.putExtra(Recipe.RECIPE_NAME, name);
        intent.putExtra(Recipe.RECIPE_DESCRIPTION, description);
        intent.putExtra(Recipe.RECIPE_CATEGORIES, categories);
        intent.putExtra(Recipe.RECIPE_DURATION, duration);
        intent.putExtra(Recipe.RECIPE_PHOTO, photo);
    }

    public static void packageIntent(Intent intent, String name, String description, String categories, int duration, int score, int difficulty, byte[] photo, long userid) {
        intent.putExtra(Recipe.RECIPE_NAME, name);
        intent.putExtra(Recipe.RECIPE_DESCRIPTION, description);
        intent.putExtra(Recipe.RECIPE_CATEGORIES, categories);
        intent.putExtra(Recipe.RECIPE_DURATION, duration);
        intent.putExtra(Recipe.RECIPE_SCORE, score);
        intent.putExtra(Recipe.RECIPE_DIFFICULTY, difficulty);
        intent.putExtra(Recipe.RECIPE_PHOTO, photo);
        intent.putExtra(Recipe.RECIPE_USERID, userid);
    }

    public static void packageIntent(Intent intent, long recipeid, String name, String description, String categories, int duration, int score, int difficulty, byte[] photo, long userid) {
        intent.putExtra(Recipe.RECIPE_ID, recipeid);
        intent.putExtra(Recipe.RECIPE_NAME, name);
        intent.putExtra(Recipe.RECIPE_DESCRIPTION, description);
        intent.putExtra(Recipe.RECIPE_CATEGORIES, categories);
        intent.putExtra(Recipe.RECIPE_DURATION, duration);
        intent.putExtra(Recipe.RECIPE_SCORE, score);
        intent.putExtra(Recipe.RECIPE_DIFFICULTY, difficulty);
        intent.putExtra(Recipe.RECIPE_PHOTO, photo);
        intent.putExtra(Recipe.RECIPE_USERID, userid);
    }

    @Override
    public String toString() {
        return "Name: " + name + ITEM_SEP +
                "Description: " + description + ITEM_SEP +
                "Categories: " + categories + ITEM_SEP +
                "Duration: " + duration + ITEM_SEP +
                "Score: " + score + ITEM_SEP +
                "Difficulty: " + difficulty;
    }
}
