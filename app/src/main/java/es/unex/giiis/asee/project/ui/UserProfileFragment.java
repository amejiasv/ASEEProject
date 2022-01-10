package es.unex.giiis.asee.project.ui;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import es.unex.giiis.asee.project.AppContainer;
import es.unex.giiis.asee.project.MyApplication;
import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.Recipe;
import es.unex.giiis.asee.project.data.model.User;
import es.unex.giiis.asee.project.data.model.Valoration;
import es.unex.giiis.asee.project.ui.adapters.RecipeAdapter;
import es.unex.giiis.asee.project.ui.viewmodels.UserProfileViewModel;


public class UserProfileFragment extends Fragment {

    private static final int EDIT_USER_PROFILE_REQUEST = 50;

    private UserProfileViewModel mUserProfileViewModel;

    ImageView userAvatar;
    TextView username;
    TextView name;
    TextView numberOfRecipes;
    TextView userCountry;
    SearchView searchRecipes;
    Button bEditProfile;
    Button bLogout;

    private SharedPreferences sharedPreferences;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecipeAdapter adapter;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public static UserProfileFragment newInstance() {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        userAvatar = view.findViewById(R.id.userAvatarProfile);
        username = view.findViewById(R.id.usernameUserProfile);
        name = view.findViewById(R.id.userNameProfile);
        numberOfRecipes = view.findViewById(R.id.numberOfRecipesLoggedUser);
        userCountry = view.findViewById(R.id.userCountryProfile);

        recyclerView = view.findViewById(R.id.recyclerViewRecipesUserProfile);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete recipe")
                        .setMessage("Are you sure you want to delete " + recipe.getName() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mUserProfileViewModel.deleteUserRecipe(recipe);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }

            @Override
            public void onFavouriteButtonClick(Recipe recipe) {
                mUserProfileViewModel.modifyFavourite(recipe.getId());
            }

            @Override
            public void onCommentButtonClick(Recipe recipe) {
                Intent intent = new Intent(getActivity(), ValorationsListActivity.class);
                intent.putExtra(Valoration.VALORATION_RECIPEID, recipe.getId());
                startActivity(intent);
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
                    Toast.makeText(getActivity(), "WhatsApp has not been installed", Toast.LENGTH_LONG).show();
                }
            }
        });

        recyclerView.setAdapter(adapter);

        TextView noRecipes = view.findViewById(R.id.noRecipesLabelUserProfile);

        AppContainer appContainer = ((MyApplication) getActivity().getApplication()).appContainer;
        mUserProfileViewModel = new ViewModelProvider(getActivity(), appContainer.factory).get(UserProfileViewModel.class);

        sharedPreferences = getActivity().getSharedPreferences("userid", getActivity().MODE_PRIVATE);
        long userid = sharedPreferences.getLong("userid", 0);

        mUserProfileViewModel.setLoggedUserId(userid);
        mUserProfileViewModel.getLoggedUser().observe(getActivity(), user -> {
            userAvatar.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeByteArray(user.getPhoto(), 0, user.getPhoto().length)));
            username.setText(user.getUsername());
            name.setText(user.getName());
            userCountry.setText(user.getCity() + ", " + user.getCountry());
            mUserProfileViewModel.getNumberOfUserRecipes().observe(getActivity(), integer -> {
                numberOfRecipes.setText(integer + " recipes posted");
            });

            bEditProfile = view.findViewById(R.id.bEditProfile);
            bEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CreateUserActivity.class);
                    intent.putExtra("requestCode", EDIT_USER_PROFILE_REQUEST);
                    User.packageIntent(intent, user.getUsername(), user.getName(),
                            user.getPassword(), user.getEmail(), user.getCountry(),
                            user.getCity(), user.getSex(), user.getDateofbirth().toString(),
                            user.getPhoto());
                    getActivity().startActivityForResult(intent, EDIT_USER_PROFILE_REQUEST);
                }
            });
        });

        mUserProfileViewModel.getUserRecipes().observe(getActivity(), recipes -> {
            adapter.load(recipes);
            if (recipes != null && recipes.size() != 0)
                noRecipes.setVisibility(View.GONE);
            else
                noRecipes.setVisibility(View.VISIBLE);
        });

        bLogout = view.findViewById(R.id.bLogout);
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        searchRecipes = view.findViewById(R.id.searchViewRecipesUserProfile);
        searchRecipes.setIconifiedByDefault(false);
        searchRecipes.setIconified(false);
        searchRecipes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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