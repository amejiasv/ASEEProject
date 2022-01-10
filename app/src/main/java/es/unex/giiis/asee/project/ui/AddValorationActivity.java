package es.unex.giiis.asee.project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.unex.giiis.asee.project.AppContainer;
import es.unex.giiis.asee.project.MyApplication;
import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Valoration;
import es.unex.giiis.asee.project.ui.adapters.ValorationAdapter;
import es.unex.giiis.asee.project.ui.viewmodels.ValorationsListViewModel;

public class AddValorationActivity extends AppCompatActivity {

    private ValorationsListViewModel mAddValorationViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ValorationAdapter adapter;

    private EditText commentBox;
    private RatingBar scoreRating;
    private RatingBar difficultyRating;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_valoration);

        Toolbar toolBar = (Toolbar) findViewById(R.id.topBar);
        toolBar.setTitle(R.string.valorations);
        setSupportActionBar(toolBar);

        sharedPreferences = getSharedPreferences("userid", this.MODE_PRIVATE);
        long userid = sharedPreferences.getLong("userid", 0);

        commentBox = findViewById(R.id.commentTextbox);
        difficultyRating = findViewById(R.id.ratingDifficultyComment);
        scoreRating = findViewById(R.id.ratingScoreComment);

        recyclerView = findViewById(R.id.recyclerViewRecipeComments);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new ValorationAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        mAddValorationViewModel = new ViewModelProvider(this, appContainer.factory).get(ValorationsListViewModel.class);
        mAddValorationViewModel.getAllUsers().observe(this, users -> {
            adapter.setmUsers(users);
        });

        TextView noValorations = findViewById(R.id.noValorationsLabel);

        mAddValorationViewModel.setRecipeid(getIntent().getLongExtra(Valoration.VALORATION_RECIPEID, 0));
        mAddValorationViewModel.getRecipeValorations().observe(this, valorations -> {
            adapter.load(valorations);
            if(valorations != null && valorations.size() != 0)
                noValorations.setVisibility(View.GONE);
            else
                noValorations.setVisibility(View.VISIBLE);
        });

        difficultyRating.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                float touchPositionX = motionEvent.getX();
                float width = difficultyRating.getWidth();
                float starsf = (touchPositionX / width) * 5.0f;
                int stars = (int) starsf + 1;
                difficultyRating.setRating(stars);

                Toast.makeText(AddValorationActivity.this, "Difficulty selected: " + stars, Toast.LENGTH_SHORT).show();
                view.setPressed(false);
            }
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                view.setPressed(true);
            if(motionEvent.getAction() == MotionEvent.ACTION_CANCEL)
                view.setPressed(false);

            return true;
        });

        scoreRating.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                float touchPositionX = motionEvent.getX();
                float width = scoreRating.getWidth();
                float starsf = (touchPositionX / width) * 5.0f;
                int stars = (int) starsf + 1;
                scoreRating.setRating(stars);

                Toast.makeText(AddValorationActivity.this, "Score selected: " + stars, Toast.LENGTH_SHORT).show();
                view.setPressed(false);
            }
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                view.setPressed(true);
            if(motionEvent.getAction() == MotionEvent.ACTION_CANCEL)
                view.setPressed(false);

            return true;
        });

        Button bAddComment = findViewById(R.id.bAddComment);
        bAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = commentBox.getText().toString();
                int difficulty = (int) difficultyRating.getRating();
                int score = (int) scoreRating.getRating();

                if(comment.isEmpty() || comment.length() < 5) {
                    Toast.makeText(AddValorationActivity.this, "Your comment must be at least 5 characters long!", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent data = new Intent();
                    Valoration.packageIntent(data, comment, difficulty, score, userid,
                            getIntent().getLongExtra(Valoration.VALORATION_RECIPEID, 0));
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }
}