package es.unex.giiis.asee.project.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import es.unex.giiis.asee.project.R;
import es.unex.giiis.asee.project.data.model.User;

public class CreateUserActivity extends AppCompatActivity {

    // 7 days in milliseconds - 7 * 24 * 60 * 60 * 1000
    private static final int SEVEN_DAYS = 604800000;
    private static final int SELECT_IMAGE = 20;

    private SharedPreferences sharedPreferences;

    private static String dateString;
    private static TextView dateViewProfile;

    private EditText mEditUsername;
    private EditText mEditName;
    private EditText mEditPassword;
    private EditText mEditEmail;
    private Spinner mEditCountry;
    private Spinner mEditCity;
    private Spinner mEditSex;
    private Date mDate;
    private ImageView mUserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        sharedPreferences = getSharedPreferences("userid", this.MODE_PRIVATE);
        long userid = sharedPreferences.getLong("userid", 0);

        Toolbar toolBar = (Toolbar) findViewById(R.id.topBar);

        mUserImage = findViewById(R.id.userImageProfile);
        mEditUsername = findViewById(R.id.editUsernameProfile);
        mEditName = findViewById(R.id.editNameProfile);
        mEditPassword = findViewById(R.id.editPasswordProfile);
        mEditEmail = findViewById(R.id.editEmailProfile);
        mEditCountry = findViewById(R.id.editCountryProfile);
        mEditCity = findViewById(R.id.editCityProfile);
        mEditSex = findViewById(R.id.editSexProfile);
        dateViewProfile = findViewById(R.id.dateViewProfile);

        setDefaultDate();

        Intent intent = getIntent();

        if (intent.getIntExtra("requestCode", 0) == 50) {
            toolBar.setTitle(R.string.edit_profile);
            setUserInfo();
        } else
            toolBar.setTitle(R.string.create_user);

        setSupportActionBar(toolBar);

        final ImageButton bDatePickerButton = findViewById(R.id.bDatePickerProfile);
        bDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        mUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                if (photoPickerIntent.resolveActivity(getPackageManager()) != null) {
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_IMAGE);
                }
            }
        });

        Button bConfirm = findViewById(R.id.bConfirmProfile);
        bConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mEditUsername.getText().toString();
                String name = mEditName.getText().toString();
                String password = mEditPassword.getText().toString();
                String email = mEditEmail.getText().toString();
                String country = mEditCountry.getSelectedItem().toString();
                String city = mEditCity.getSelectedItem().toString();
                String sex = mEditSex.getSelectedItem().toString();
                String date = dateString;

                Bitmap bitmap = ((BitmapDrawable) mUserImage.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] photo = baos.toByteArray();
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final EditText enterPassword = new EditText(CreateUserActivity.this);
                enterPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                AlertDialog.Builder builder = new AlertDialog.Builder(CreateUserActivity.this);
                builder.setTitle("Confirm password")
                        .setNegativeButton("No", null)
                        .setView(enterPassword);

                if(intent.getIntExtra("requestCode", 0) == 50) {
                    builder.setMessage("Please, enter your current password")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String cpassword = enterPassword.getText().toString();
                            if(cpassword.equals(intent.getStringExtra(User.PASSWORD))) {
                                Intent data = new Intent();
                                User.packageIntent(data, userid, username, name, password, email,
                                        country, city, sex, date, photo);
                                setResult(RESULT_OK, data);
                                finish();
                            }
                            else {
                                Toast.makeText(CreateUserActivity.this, "Wrong password!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else {
                    builder.setMessage("Please, confirm your password")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String cpassword = enterPassword.getText().toString();
                            if(cpassword.equals(mEditPassword.getText().toString())) {
                                Intent data = new Intent();
                                User.packageIntent(data, username, name, password, email, country, city, sex, date, photo);
                                setResult(RESULT_OK, data);
                                finish();
                            }
                            else {
                                Toast.makeText(CreateUserActivity.this, "Wrong password!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                builder.show();
            }
        });

        Button bReset = findViewById(R.id.bResetProfile);
        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetForm();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    mUserImage.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(CreateUserActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(CreateUserActivity.this, "You haven't selected an image", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setUserInfo() {
        Drawable image = new BitmapDrawable(getResources(),
                BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra(User.PHOTO), 0, getIntent().getByteArrayExtra(User.PHOTO).length));

        mUserImage.setImageDrawable(image);
        mEditUsername.setText(getIntent().getStringExtra(User.USERNAME));
        mEditName.setText(getIntent().getStringExtra(User.USER_NAME));
        mEditPassword.setText(getIntent().getStringExtra(User.PASSWORD));
        mEditEmail.setText(getIntent().getStringExtra(User.EMAIL));
        mEditCountry.setSelection(Arrays.asList(getResources().getStringArray(R.array.country_names)).indexOf(getIntent().getStringExtra(User.COUNTRY)));
        mEditCity.setSelection(Arrays.asList(getResources().getStringArray(R.array.city_names)).indexOf(getIntent().getStringExtra(User.CITY)));
        mEditSex.setSelection(Arrays.asList(getResources().getStringArray(R.array.sex_names)).indexOf(getIntent().getStringExtra(User.SEX)));
        dateViewProfile.setText(getIntent().getStringExtra(User.DATE_OF_BIRTH));
    }

    private void resetForm() {
        mUserImage.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
        mEditUsername.setText("");
        mEditName.setText("");
        mEditPassword.setText("");
        mEditEmail.setText("");
        mEditCountry.setSelection(0);
        mEditCity.setSelection(0);
        mEditSex.setSelection(0);
        setDefaultDate();
    }

    private void setDefaultDate() {

        // Default is current time + 7 days
        mDate = new Date();
        mDate = new Date(mDate.getTime() + SEVEN_DAYS);

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

        dateViewProfile.setText(dateString);
    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        dateString = day + "-" + mon + "-" + year;
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);

            dateViewProfile.setText(dateString);
        }
    }

    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}