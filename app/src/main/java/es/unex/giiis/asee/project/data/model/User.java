package es.unex.giiis.asee.project.data.model;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.unex.giiis.asee.project.data.roomdb.DateConverter;

@Entity(tableName = "user")
public class User implements Serializable {

    @Ignore
    public static final String ITEM_SEP = System.getProperty("line.separator");

    @PrimaryKey(autoGenerate = true)
    private long userid;
    @ColumnInfo(name = "username")
    private String username = new String();
    @ColumnInfo(name = "name")
    private String name = new String();
    @ColumnInfo(name = "password")
    private String password = new String();
    @ColumnInfo(name = "email")
    private String email = new String();
    @ColumnInfo(name = "country")
    private String country = new String();
    @ColumnInfo(name = "city")
    private String city = new String();
    @TypeConverters(DateConverter.class)
    private Date dateofbirth = new Date();
    @ColumnInfo(name = "sex")
    private String sex = new String();
    @ColumnInfo(name = "photo")
    private byte[] photo;

    @Ignore
    public final static String USER_ID = "userid";
    @Ignore
    public final static String USERNAME = "username";
    @Ignore
    public final static String USER_NAME = "name";
    @Ignore
    public final static String PASSWORD = "password";
    @Ignore
    public final static String EMAIL = "email";
    @Ignore
    public final static String COUNTRY = "country";
    @Ignore
    public final static String CITY = "city";
    @Ignore
    public final static String DATE_OF_BIRTH = "dateofbirth";
    @Ignore
    public final static String SEX = "sex";
    @Ignore
    public final static String PHOTO = "photo";

    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
        "dd-MM-yyyy", Locale.US
    );

    @Ignore
    public User(long userid, String username, String name, String password, String email,
                String country, String city, String dateofbirth, String sex, byte[] photo) {
        this.userid = userid;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.country = country;
        this.city = city;
        try {
            this.dateofbirth = User.DATE_FORMAT.parse(dateofbirth);
        } catch (ParseException e) {
            this.dateofbirth = new Date();
        }
        this.sex = sex;
        this.photo = photo;
    }

    @Ignore
    public User(Intent intent) {

        this.userid = intent.getLongExtra(User.USER_ID, 0);
        this.username = intent.getStringExtra(User.USERNAME);
        this.name = intent.getStringExtra(User.USER_NAME);
        this.password = intent.getStringExtra(User.PASSWORD);
        this.email = intent.getStringExtra(User.EMAIL);
        this.country = intent.getStringExtra(User.COUNTRY);
        this.city = intent.getStringExtra(User.CITY);
        this.sex = intent.getStringExtra(User.SEX);
        this.photo = intent.getByteArrayExtra(User.PHOTO);

        try {
            this.dateofbirth = User.DATE_FORMAT.parse(intent.getStringExtra(User.DATE_OF_BIRTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public User(long userid, String username, String name, String password, String email,
                String country, String city, Date dateofbirth, String sex, byte[] photo) {
        this.userid = userid;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.country = country;
        this.city = city;
        this.dateofbirth = dateofbirth;
        this.sex = sex;
        this.photo = photo;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public static void packageIntent(Intent intent, String username, String name,
                                     String password, String email, String country, String city,
                                     String sex, String dateofbirth, byte[] photo) {

        intent.putExtra(User.USERNAME, username);
        intent.putExtra(User.USER_NAME, name);
        intent.putExtra(User.PASSWORD, password);
        intent.putExtra(User.EMAIL, email);
        intent.putExtra(User.COUNTRY, country);
        intent.putExtra(User.CITY, city);
        intent.putExtra(User.SEX, sex);
        intent.putExtra(User.DATE_OF_BIRTH, dateofbirth);
        intent.putExtra(User.PHOTO, photo);
    }

    @Ignore
    public static void packageIntent(Intent intent, long userid, String username, String name,
                                     String password, String email, String country, String city,
                                     String sex, String dateofbirth, byte[] photo) {

        intent.putExtra(User.USER_ID, userid);
        intent.putExtra(User.USERNAME, username);
        intent.putExtra(User.USER_NAME, name);
        intent.putExtra(User.PASSWORD, password);
        intent.putExtra(User.EMAIL, email);
        intent.putExtra(User.COUNTRY, country);
        intent.putExtra(User.CITY, city);
        intent.putExtra(User.SEX, sex);
        intent.putExtra(User.DATE_OF_BIRTH, dateofbirth);
        intent.putExtra(User.PHOTO, photo);
    }

    @Override
    public String toString() {
        return "ID: " + userid + ITEM_SEP + "Password: ******" + ITEM_SEP + "Username: " +
                username + ITEM_SEP + "Name: " + name + ITEM_SEP + "Email: " + email + ITEM_SEP +
                "Country: " + country + ITEM_SEP + "City: " + city + ITEM_SEP +
                "Sex: " + sex + ITEM_SEP + "Date of birth: " + dateofbirth;
    }
}
