package es.unex.giiis.asee.project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import es.unex.giiis.asee.project.AppContainer;
import es.unex.giiis.asee.project.MyApplication;
import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Ingredient;
import es.unex.giiis.asee.project.ui.adapters.IngredientAdapter;
import es.unex.giiis.asee.project.ui.viewmodels.IngredientViewModel;

public class IngredientsListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private long param1;
    private long mRecipeId;

    private IngredientViewModel mIngredientViewModel;

    SearchView searchIngredients;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private IngredientAdapter adapter;

    public IngredientsListFragment() {

    }

    public static IngredientsListFragment newInstance(long param1) {
        IngredientsListFragment fragment = new IngredientsListFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mRecipeId = getArguments().getLong(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_ingredients_list, container, false);

        recyclerView = view.findViewById(R.id.ingredientsListRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        adapter = new IngredientAdapter(getActivity(), new IngredientAdapter.OnIngredientClickListener() {
            @Override
            public void onItemClick(Ingredient ingredient) {
                Intent intent = new Intent(getActivity(), IngredientDetailActivity.class);
                Ingredient.packageIntent(intent,
                        ingredient.getName(),
                        ingredient.getDescription(),
                        ingredient.getCategories(),
                        ingredient.getNRV(),
                        ingredient.getPhoto());
                startActivity(intent);
            }
        }, 1);

        recyclerView.setAdapter(adapter);

        AppContainer appContainer = ((MyApplication) getActivity().getApplication()).appContainer;
        mIngredientViewModel = new ViewModelProvider(getActivity(), appContainer.factory).get(IngredientViewModel.class);
        mIngredientViewModel.setRecipeid(mRecipeId);
        mIngredientViewModel.getIngredientsFromRecipe().observe(getActivity(), ingredients -> {
            adapter.load(ingredients);
        });

        searchIngredients = view.findViewById(R.id.searchViewIngredientList);
        searchIngredients.setIconifiedByDefault(false);
        searchIngredients.setIconified(false);
        searchIngredients.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }
}
