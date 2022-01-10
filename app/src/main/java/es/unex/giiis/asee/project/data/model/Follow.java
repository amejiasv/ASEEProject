package es.unex.giiis.asee.project.data.model;

import static androidx.room.ForeignKey.CASCADE;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(tableName = "follow", primaryKeys = {"userid_follow", "userid_followed"}, foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "userid", childColumns = "userid_follow", onDelete = CASCADE),
        @ForeignKey(entity = User.class, parentColumns = "userid", childColumns = "userid_followed", onDelete = CASCADE)
}, indices = {
        @Index(value = {"userid_follow"}),
        @Index(value = {"userid_followed"})
})
public class Follow {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");

    @ColumnInfo(name = "userid_follow")
    @NonNull
    private long useridFollow;
    @ColumnInfo(name = "userid_followed")
    @NonNull
    private long useridFollowed;

    @Ignore
    public static final String FOLLOW_USERID_FOLLOW = "userid_follow";
    @Ignore
    public static final String FOLLOW_USERID_FOLLOWED = "userid_followed";

    public Follow(long useridFollow, long useridFollowed) {
        this.useridFollow = useridFollow;
        this.useridFollowed = useridFollowed;
    }

    public Follow(Intent intent) {
        intent.getLongExtra(Follow.FOLLOW_USERID_FOLLOW, 0);
        intent.getLongExtra(Follow.FOLLOW_USERID_FOLLOWED, 0);
    }

    public long getUseridFollow() {
        return useridFollow;
    }

    public void setUseridFollow(long useridFollow) {
        this.useridFollow = useridFollow;
    }

    public long getUseridFollowed() {
        return useridFollowed;
    }

    public void setUseridFollowed(long useridFollowed) {
        this.useridFollowed = useridFollowed;
    }

    @Override
    public String toString() {
        return useridFollow + ITEM_SEP + useridFollowed;
    }

    public String toLog() {
        return "userid_follow: " + useridFollow + ITEM_SEP;
    }
}
