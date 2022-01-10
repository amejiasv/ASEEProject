package es.unex.giiis.asee.project.ui.adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.User;
import es.unex.giiis.asee.project.data.model.Valoration;

public class ValorationAdapter extends RecyclerView.Adapter<ValorationAdapter.ValorationViewHolder> {

    private List<Valoration> mValorations = new ArrayList<>();
    private List<User> mUsers = new ArrayList<>();

    public ValorationAdapter(List<Valoration> valorations) {
        mValorations = valorations;
    }

    @NonNull
    @Override
    public ValorationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.valoration, parent, false);

        return new ValorationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ValorationViewHolder holder, int position) {
        holder.mValoration = mValorations.get(position);

        boolean end = false;

        for(int i = 0; i < mUsers.size() && !end; i++) {
            User u = mUsers.get(i);
            if(holder.mValoration.getUserid() == u.getUserid()) {
                holder.commentBox.setText(Html.fromHtml("<b>" + u.getUsername() + "</b>: " +
                        holder.mValoration.getComment()));
                end = true;
            }
        }

        holder.difficulty.setRating(holder.mValoration.getDifficulty());
        holder.score.setRating(holder.mValoration.getScore());
    }

    @Override
    public int getItemCount() {
        return mValorations.size();
    }

    public void setmUsers(List<User> users) {
        mUsers = users;
    }

    public void load(List<Valoration> valorations) {
        mValorations = valorations;
        notifyDataSetChanged();
    }

    static class ValorationViewHolder extends RecyclerView.ViewHolder {

        public TextView commentBox;
        public RatingBar difficulty;
        public RatingBar score;

        public Valoration mValoration;

        public ValorationViewHolder(View valorationView) {
            super(valorationView);

            commentBox = valorationView.findViewById(R.id.commentBox);
            difficulty = valorationView.findViewById(R.id.ratingBarDifficultyComment);
            score = valorationView.findViewById(R.id.ratingBarScoreComment);
        }
    }
}
