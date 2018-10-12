package chat.dropdevelopers.com.moviebuff.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedHelper {

    public static final String MyPREFERENCES = "MyPrefs";
    private Context context;

    public SharedHelper(Context contex){
        context = contex;
    }

    public void putString(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        sharedPreferences .edit().putString(key, value).apply();
    }

    public String getString(String key){
        SharedPreferences preferences = context.getSharedPreferences(
                MyPREFERENCES, context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public void setBoolean(String bl, boolean value){

        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        sharedPreferences .edit().putBoolean(bl, value).apply();
    }

    public boolean getBoolean(String key){

        SharedPreferences preferences = context.getSharedPreferences(
                MyPREFERENCES, context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public void setInt(String key, int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        sharedPreferences .edit().putInt(key, value).apply();

    }

    public int getInt(String key){
        SharedPreferences preferences = context.getSharedPreferences(
                MyPREFERENCES, context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }
}
