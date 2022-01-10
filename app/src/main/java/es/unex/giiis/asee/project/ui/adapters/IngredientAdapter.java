package es.unex.giiis.asee.project.ui.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Ingredient;
import es.unex.giiis.asee.project.data.model.Recipe;

public class IngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    Context context;
    private int layoutType;
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();
    private List<Ingredient> ingredientsAll = new ArrayList<Ingredient>();
    private List<Ingredient> checkedIngredients = new ArrayList<>();

    public interface OnIngredientClickListener {
        void onItemClick(Ingredient ingredient);
    }

    public OnIngredientClickListener listener;

    public IngredientAdapter(Context context, OnIngredientClickListener listener, int layoutType) {
        this.context = context;
        this.listener = listener;
        this.layoutType = layoutType;
    }

    @Override
    public int getItemViewType(int position) {
        return layoutType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (layoutType == 1) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredient, parent, false);
            return new ViewHolderIngredientList(v);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredient_checkbox, parent, false);
            return new ViewHolderAddIngredients(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (layoutType == 1) {
            initLayoutList((ViewHolderIngredientList) holder, position);
        } else if (layoutType == 2) {
            initLayoutAddIngredients((ViewHolderAddIngredients) holder, position);
        }
    }

    public void initLayoutList(ViewHolderIngredientList holder, int position) {
        holder.mIngredient = ingredients.get(position);
        holder.image.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeByteArray(holder.mIngredient.getPhoto(), 0, holder.mIngredient.getPhoto().length)));
        holder.name.setText(holder.mIngredient.getName());
        holder.description.setText(holder.mIngredient.getDescription());
        holder.categories.setText(Html.fromHtml("<b>Categories</b>: " + holder.mIngredient.getCategories()));
        holder.nrv.setText(Html.fromHtml("<b>Nutritional value</b>: " + holder.mIngredient.getNRV() + " kcal"));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(holder.mIngredient);
                }
            }
        });
    }

    public void initLayoutAddIngredients(ViewHolderAddIngredients holder, int position) {
        holder.mIngredient = ingredients.get(position);
        holder.image.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeByteArray(holder.mIngredient.getPhoto(), 0, holder.mIngredient.getPhoto().length)));
        holder.name.setText(holder.mIngredient.getName());
        holder.description.setText(holder.mIngredient.getDescription());
        holder.categories.setText(Html.fromHtml("<b>Categories</b>: " + holder.mIngredient.getCategories()));
        holder.nrv.setText(Html.fromHtml("<b>NRV</b>: " + holder.mIngredient.getNRV() + " kcal"));

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(holder.mIngredient.isSelected());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.checkBox.isChecked()) {
                    holder.mIngredient.setSelected(true);
                    checkedIngredients.add(holder.mIngredient);
                } else if(!holder.checkBox.isChecked()){
                    holder.mIngredient.setSelected(false);
                    checkedIngredients.remove(holder.mIngredient);
                }
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(holder.mIngredient);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String constraint = charSequence.toString().toLowerCase().trim();
                ingredients.clear();
                if(constraint.isEmpty()) {
                    ingredients.addAll(ingredientsAll);
                } else {
                    for(Ingredient ingredient : ingredientsAll) {
                        if(ingredient.getName().toLowerCase().contains(constraint)) {
                            ingredients.add(ingredient);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = ingredients;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }

    public List<Ingredient> getCheckedIngredients() {
        return this.checkedIngredients;
    }

    public void load(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        ingredientsAll.clear();
        ingredientsAll.addAll(ingredients);
        notifyDataSetChanged();
    }

    public static class ViewHolderIngredientList extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView name;
        public TextView description;
        public TextView categories;
        public TextView nrv;
        public View mView;

        public Ingredient mIngredient;

        public ViewHolderIngredientList(View ingredientView) {
            super(ingredientView);

            mView = ingredientView;
            image = ingredientView.findViewById(R.id.imageIngredientList);
            name = ingredientView.findViewById(R.id.nameIngredientList);
            description = ingredientView.findViewById(R.id.descriptionIngredientList);
            categories = ingredientView.findViewById(R.id.ingredientCategoriesList);
            nrv = ingredientView.findViewById(R.id.ingredientNRVList);
        }
    }

    public static class ViewHolderAddIngredients extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView name;
        public TextView description;
        public TextView categories;
        public TextView nrv;
        public CheckBox checkBox;
        public View mView;

        public Ingredient mIngredient;

        public ViewHolderAddIngredients(View ingredientView) {
            super(ingredientView);

            mView = ingredientView;
            image = ingredientView.findViewById(R.id.imageIngredientList);
            name = ingredientView.findViewById(R.id.nameIngredientList);
            description = ingredientView.findViewById(R.id.descriptionIngredientList);
            categories = ingredientView.findViewById(R.id.ingredientCategoriesList);
            nrv = ingredientView.findViewById(R.id.ingredientNRVList);
            checkBox = ingredientView.findViewById(R.id.selectIngredientCheckbox);
        }
    }
}
