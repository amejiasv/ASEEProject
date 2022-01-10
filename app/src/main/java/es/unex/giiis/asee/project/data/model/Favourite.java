package es.unex.giiis.asee.project.data.model;

import static androidx.room.ForeignKey.CASCADE;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(tableName = "favourite", primaryKeys = {"recipeid", "userid"}, foreignKeys = {
        @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "recipeid", onDelete = CASCADE),
        @ForeignKey(entity = User.class, parentColumns = "userid", childColumns = "userid", onDelete = CASCADE)
}, indices = {
        @Index(value = {"recipeid"}),
        @Index(value = {"userid"})
})
public class Favourite {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");

    @ColumnInfo(name = "recipeid")
    @NonNull
    private long recipeid;
    @ColumnInfo(name = "userid")
    @NonNull
    private long userid;

    @Ignore
    public static final String FAVOURITE_RECIPEID = "recipeid";
    @Ignore
    public static final String FAVOURITE_USERID = "userid";

    public Favourite(long recipeid, long userid) {
        this.recipeid = recipeid;
        this.userid = userid;
    }

    @Ignore
    public Favourite(Intent intent) {
        this.recipeid = intent.getLongExtra(Favourite.FAVOURITE_RECIPEID, 0);
        this.userid = intent.getLongExtra(Favourite.FAVOURITE_USERID, 0);
    }

    public long getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(long recipeid) {
        this.recipeid = recipeid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public static void packageIntent(Intent intent, long recipeid, long userid) {
        intent.putExtra(Favourite.FAVOURITE_RECIPEID, recipeid);
        intent.putExtra(Favourite.FAVOURITE_USERID, userid);
    }

    @Override
    public String toString() {
        return recipeid + ITEM_SEP + userid;
    }

    public String toLog() {
        return "Recipe id: " + recipeid + ITEM_SEP + "User id: " + userid;
    }
}
