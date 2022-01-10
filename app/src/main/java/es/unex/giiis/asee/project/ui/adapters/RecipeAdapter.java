package es.unex.giiis.asee.project.ui.adapters;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> implements Filterable {

    private List<Recipe> mRecipes;
    private List<Recipe> mRecipesAll;

    public interface OnRecipeClickListener {
        void onItemClick(Recipe recipe);

        void onLongItemClick(Recipe recipe);

        void onFavouriteButtonClick(Recipe recipe);

        void onCommentButtonClick(Recipe recipe);

        void onShareButtonClick(Recipe recipe);
    }

    private final OnRecipeClickListener listener;

    public RecipeAdapter(List<Recipe> recipesList, OnRecipeClickListener listener) {
        mRecipes = recipesList;
        mRecipesAll = new ArrayList<>(recipesList);
        this.listener = listener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_card_view, parent, false);

        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.mRecipe = mRecipes.get(position);

        holder.recipeImage.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeByteArray(holder.mRecipe.getPhoto(), 0, holder.mRecipe.getPhoto().length)));
        holder.recipeName.setText(holder.mRecipe.getName());
        holder.recipeDuration.setText(holder.mRecipe.getDuration() + " min.");
        holder.recipeDifficultyRating.setRating(holder.mRecipe.getDifficulty());
        holder.recipeScoreRating.setRating(holder.mRecipe.getScore());

        holder.favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFavouriteButtonClick(holder.mRecipe);
            }
        });

        holder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCommentButtonClick(holder.mRecipe);
            }
        });

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onShareButtonClick(holder.mRecipe);
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.mRecipe);
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongItemClick(holder.mRecipe);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String constraint = charSequence.toString().toLowerCase().trim();
                mRecipes.clear();
                if(constraint.isEmpty()) {
                    mRecipes.addAll(mRecipesAll);
                } else {
                    for(Recipe recipe : mRecipesAll) {
                        if(recipe.getName().toLowerCase().contains(constraint)) {
                            mRecipes.add(recipe);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mRecipes;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }

    public void load(List<Recipe> recipes) {
        mRecipes = recipes;
        mRecipesAll.clear();
        mRecipesAll.addAll(recipes);
        notifyDataSetChanged();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        public ImageView recipeImage;
        public TextView recipeName;
        public TextView recipeDuration;
        public TextView recipeDifficultyLabel;
        public TextView recipeScoreLabel;
        public RatingBar recipeDifficultyRating;
        public RatingBar recipeScoreRating;
        public Button favouriteButton;
        public Button commentButton;
        public Button shareButton;
        public View mView;

        public Recipe mRecipe;

        public RecipeViewHolder(View recipeView) {
            super(recipeView);

            mView = recipeView;
            recipeImage = recipeView.findViewById(R.id.recipeImageList);
            recipeName = recipeView.findViewById(R.id.recipeNameRecipeList);
            recipeDuration = recipeView.findViewById(R.id.recipeDurationList);
            recipeDifficultyLabel = recipeView.findViewById(R.id.recipeDifficultyList);
            recipeScoreLabel = recipeView.findViewById(R.id.recipeScoreList);
            recipeDifficultyRating = recipeView.findViewById(R.id.difficultyRatingList);
            recipeScoreRating = recipeView.findViewById(R.id.scoreRatingList);
            favouriteButton = recipeView.findViewById(R.id.bFavouriteRecipeList);
            commentButton = recipeView.findViewById(R.id.bCommentRecipeList);
            shareButton = recipeView.findViewById(R.id.bShareRecipeList);
        }
    }
}
