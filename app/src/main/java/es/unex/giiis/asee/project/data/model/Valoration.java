package es.unex.giiis.asee.project.data.model;

import static androidx.room.ForeignKey.CASCADE;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "valoration", foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "userid", childColumns = "userid", onDelete = CASCADE),
        @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "recipeid", onDelete = CASCADE)
}, indices = {
        @Index(value = {"userid"}),
        @Index(value = {"recipeid"})
})
public class Valoration {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "comment")
    private String comment;
    @ColumnInfo(name = "difficulty")
    private int difficulty;
    @ColumnInfo(name = "score")
    private int score;
    @ColumnInfo(name = "userid")
    private long userid;
    @ColumnInfo(name = "recipeid")
    private long recipeid;

    @Ignore
    public final static String VALORATION_ID = "id";
    @Ignore
    public final static String VALORATION_COMMENT = "comment";
    @Ignore
    public final static String VALORATION_DIFFICULTY = "difficulty";
    @Ignore
    public final static String VALORATION_SCORE = "score";
    @Ignore
    public final static String VALORATION_USERID = "userid";
    @Ignore
    public final static String VALORATION_RECIPEID = "recipeid";

    @Ignore
    public Valoration() {

    }

    @Ignore
    public Valoration(Intent intent) {
        this.id = intent.getLongExtra(Valoration.VALORATION_ID, 0);
        this.comment = intent.getStringExtra(Valoration.VALORATION_COMMENT);
        this.difficulty = intent.getIntExtra(Valoration.VALORATION_DIFFICULTY, 0);
        this.score = intent.getIntExtra(Valoration.VALORATION_SCORE, 0);
        this.userid = intent.getLongExtra(Valoration.VALORATION_USERID, 0);
        this.recipeid = intent.getLongExtra(Valoration.VALORATION_RECIPEID, 0);
    }

    public Valoration(long id, String comment, int difficulty, int score, long userid, long recipeid) {
        this.id = id;
        this.comment = comment;
        this.difficulty = difficulty;
        this.score = score;
        this.userid = userid;
        this.recipeid = recipeid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(long recipeid) {
        this.recipeid = recipeid;
    }

    @Ignore
    public static void packageIntent(Intent intent, String comment, int difficulty, int score, long userid, long recipeid) {
        intent.putExtra(VALORATION_COMMENT, comment);
        intent.putExtra(VALORATION_DIFFICULTY, difficulty);
        intent.putExtra(VALORATION_SCORE, score);
        intent.putExtra(VALORATION_USERID, userid);
        intent.putExtra(VALORATION_RECIPEID, recipeid);
    }
}
