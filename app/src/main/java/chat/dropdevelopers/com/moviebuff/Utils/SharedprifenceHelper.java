package chat.dropdevelopers.com.moviebuff.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SharedprifenceHelper {

    Context context;
    public static final String MyPREFERENCES = "MyPrefs" ;

    public SharedprifenceHelper(Context context){
        this.context = context;
    }


    public void setString(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        sharedPreferences .edit().putString(key, value).apply();
    }

    public String getString(String key){
        SharedPreferences preferences = context.getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public void setBoolean(String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key){
        SharedPreferences preferences = context.getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public void setArrayList(String key, ArrayList<String> data){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, context.MODE_PRIVATE);
        Set<String> set = new HashSet<String>();
        set.addAll(data);
        sharedPreferences.edit().putStringSet(key, set).apply();
    }

    public ArrayList<String> getArrayList(String key){
        SharedPreferences preferences = context.getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);
        Set<String> set = preferences.getStringSet(key, null);
        return (ArrayList<String>) set;
    }
}
