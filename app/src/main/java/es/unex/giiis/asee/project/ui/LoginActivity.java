package es.unex.giiis.asee.project.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import es.unex.giiis.asee.project.AppContainer;
import es.unex.giiis.asee.project.MyApplication;
import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.User;
import es.unex.giiis.asee.project.ui.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private static final int CREATE_USER_REQUEST = 40;

    private EditText mEditUsername;
    private EditText mEditPassword;

    private LoginViewModel mLoginViewModel;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditUsername = findViewById(R.id.editUsernameLogin);
        mEditPassword = findViewById(R.id.editPasswordLogin);

        sharedPreferences = getSharedPreferences("userid", this.MODE_PRIVATE);
        long userid = sharedPreferences.getLong("userid", 0);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        mLoginViewModel = new ViewModelProvider(LoginActivity.this, appContainer.factory).get(LoginViewModel.class);

        if(userid != 0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Button bLogin = findViewById(R.id.bLogin);
            bLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mLoginViewModel.setUsername(mEditUsername.getText().toString());
                    mLoginViewModel.getUserByUsername().observe(LoginActivity.this, user -> {
                        if(user != null && user.getPassword().equals(mEditPassword.getText().toString())) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putLong("userid", user.getUserid());
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else if(user == null) {
                            Toast.makeText(LoginActivity.this, "This username doesn't exist!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong username or password!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

            Button bNewAccount = findViewById(R.id.bNewAccount);
            bNewAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, CreateUserActivity.class);
                    startActivityForResult(intent, CREATE_USER_REQUEST);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_USER_REQUEST) {
            if (resultCode == RESULT_OK) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Account created!")
                        .setMessage("Thank you for joining us! You can log in now.")
                        .setPositiveButton("Nice!", null)
                        .show();

                User user = new User(data);
                mLoginViewModel.insertUser(user);
            }
        }
    }
}