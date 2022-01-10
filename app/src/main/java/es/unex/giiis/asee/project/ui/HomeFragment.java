package es.unex.giiis.asee.project.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.unex.giiis.asee.project.AppContainer;
import es.unex.giiis.asee.project.MyApplication;
import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Recipe;
import es.unex.giiis.asee.project.data.model.Valoration;
import es.unex.giiis.asee.project.ui.adapters.RecipeAdapter;
import es.unex.giiis.asee.project.ui.viewmodels.HomeViewModel;

public class HomeFragment extends Fragment {

    private static final int ADD_COMMENT_REQUEST = 100;

    private HomeViewModel mRecipeViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecipeAdapter adapter;

    private SharedPreferences sharedPreferences;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = getActivity().getSharedPreferences("userid", getActivity().MODE_PRIVATE);
        long userid = sharedPreferences.getLong("userid", 0);

        recyclerView = view.findViewById(R.id.recipesRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecipeAdapter(new ArrayList<>(), new RecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
                Recipe.packageIntent(intent,
                        recipe.getId(),
                        recipe.getName(),
                        recipe.getDescription(),
                        recipe.getCategories(),
                        recipe.getDuration(),
                        recipe.getScore(),
                        recipe.getDifficulty(),
                        recipe.getPhoto(),
                        recipe.getUserid());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(Recipe recipe) {

            }

            @Override
            public void onFavouriteButtonClick(Recipe recipe) {
                mRecipeViewModel.modifyFavourite(recipe.getId());
            }

            @Override
            public void onCommentButtonClick(Recipe recipe) {
                if(recipe.getUserid() != userid) {
                    Intent intent = new Intent(getActivity(), AddValorationActivity.class);
                    intent.putExtra(Valoration.VALORATION_RECIPEID, recipe.getId());
                    getActivity().startActivityForResult(intent, ADD_COMMENT_REQUEST);
                }
                else {
                    Intent intent = new Intent(getActivity(), ValorationsListActivity.class);
                    intent.putExtra(Valoration.VALORATION_RECIPEID, recipe.getId());
                    startActivity(intent);
                }
            }

            @Override
            public void onShareButtonClick(Recipe recipe) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.setPackage("com.whatsapp");
                shareIntent.putExtra(Intent.EXTRA_TEXT, recipe.toString());
                try {
                    startActivity(shareIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "WhatsApp has not been installed!", Toast.LENGTH_LONG).show();
                }
            }
        });

        recyclerView.setAdapter(adapter);

        TextView noRecipes = view.findViewById(R.id.noRecipesLabel);

        AppContainer appContainer = ((MyApplication) getActivity().getApplication()).appContainer;
        mRecipeViewModel = new ViewModelProvider(getActivity(), appContainer.factory).get(HomeViewModel.class);
        mRecipeViewModel.setLoggedUserId(userid);
        mRecipeViewModel.getRecipes().observe(getActivity(), recipes -> {
            adapter.load(recipes);
            if (recipes != null && recipes.size() != 0)
                noRecipes.setVisibility(View.GONE);
            else
                noRecipes.setVisibility(View.VISIBLE);
        });

        return view;
    }
}