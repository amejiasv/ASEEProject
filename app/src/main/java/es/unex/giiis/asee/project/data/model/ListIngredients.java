package es.unex.giiis.asee.project.data.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;


@Entity(tableName = "list_ingredients", primaryKeys = {"recipeid", "ingredientid"}, foreignKeys = {
        @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "recipeid", onDelete = CASCADE),
        @ForeignKey(entity = Ingredient.class, parentColumns = "id", childColumns = "ingredientid", onDelete = CASCADE)
}, indices = {
        @Index(value = {"recipeid"}),
        @Index(value = {"ingredientid"})
})
public class ListIngredients {

    @ColumnInfo(name = "recipeid")
    private long recipeid;
    @ColumnInfo(name = "ingredientid")
    private long ingredientid;

    @Ignore
    public static String LIST_INGREDIENTS_RECIPEID = "recipeid";
    @Ignore
    public static String LIST_INGREDIENTS_INGREDIENTID = "ingredientid";

    public ListIngredients(long recipeid, long ingredientid) {
        this.recipeid = recipeid;
        this.ingredientid = ingredientid;
    }

    public long getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(long recipeid) {
        this.recipeid = recipeid;
    }

    public long getIngredientid() {
        return ingredientid;
    }

    public void setIngredientid(long ingredientid) {
        this.ingredientid = ingredientid;
    }
}
