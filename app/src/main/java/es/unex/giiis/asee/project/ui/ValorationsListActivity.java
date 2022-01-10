package es.unex.giiis.asee.project.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.unex.giiis.asee.project.AppContainer;
import es.unex.giiis.asee.project.MyApplication;
import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Valoration;
import es.unex.giiis.asee.project.ui.adapters.RecipeAdapter;
import es.unex.giiis.asee.project.ui.adapters.ValorationAdapter;
import es.unex.giiis.asee.project.ui.viewmodels.ValorationsListViewModel;

public class ValorationsListActivity extends AppCompatActivity {

    private ValorationsListViewModel mValorationsViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ValorationAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valorations_list);

        Toolbar toolBar = (Toolbar) findViewById(R.id.topBar);
        toolBar.setTitle(R.string.valorations);
        setSupportActionBar(toolBar);

        recyclerView = findViewById(R.id.recyclerViewRecipeDetailComments);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new ValorationAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        mValorationsViewModel = new ViewModelProvider(this, appContainer.factory).get(ValorationsListViewModel.class);
        mValorationsViewModel.getAllUsers().observe(this, users -> {
            adapter.setmUsers(users);
        });

        TextView noValorations = findViewById(R.id.noValorationsLabel2);

        mValorationsViewModel.setRecipeid(getIntent().getLongExtra(Valoration.VALORATION_RECIPEID, 0));
        mValorationsViewModel.getRecipeValorations().observe(this, valorations -> {
            adapter.load(valorations);
            if(valorations != null && valorations.size() != 0)
                noValorations.setVisibility(View.GONE);
            else
                noValorations.setVisibility(View.VISIBLE);
        });
    }
}
