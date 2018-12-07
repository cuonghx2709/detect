package com.gemvietnam.timekeeping.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.gemvietnam.timekeeping.data.remote.dto.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class PrefWrapper {
    public static final String X_AUTH_TOKEN = "token";
    public static final String MY_PREFERENCES = "Pref";
    public static final String KEY_USER = "user";
//    public static final String PASS_WORD = "pass";
    private static final String AUTHORIZATION = "Authorization";
    private static final String DAY_UPDATE = "day_update";

    private static final String USERNAME = "username";

    private static final String PASSWORD = "password";

    private static final String IS_FIRST_LOGIN = "is_first_login";

    private static final String LIST_VECTOR_FACE = "list_vector_face";

    private static final String EMBEDDINGS = "embeddings";

    private final String DefaultUnameValue = "";
    private String UnameValue;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;

    public static SharedPreferences getPreference(Context context){
        return context.getSharedPreferences(MY_PREFERENCES, context.MODE_PRIVATE);
    }

    public static void saveUser(Context context, User user){
        String userJson = new Gson().toJson(user);
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putString(KEY_USER,userJson);
        editor.commit();
    }

    public static User getUser(Context context){
        String userJson = getPreference(context).getString(KEY_USER,null);
        if (userJson == null){
            return null;
        }
        return new Gson().fromJson(userJson,new TypeToken<User>(){}.getType());
    }

    public static void saveListVector(Context context, ArrayList<String> listVector){
        String userJson = new Gson().toJson(listVector);
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putString(LIST_VECTOR_FACE,userJson);
        editor.commit();
    }

    public static ArrayList<String> getListVector(Context context){
        String listVectorJson = getPreference(context).getString(LIST_VECTOR_FACE,null);
        if (listVectorJson == null){
            return new ArrayList<>();
        }
        return new Gson().fromJson(listVectorJson,new TypeToken<ArrayList<String>>(){}.getType());
    }
    //remote setting by{@code key}
    public static void remove(Context context, String key){
        getPreference(context).edit()
                .remove(key)
                .apply();
    }
    public static void saveAuthorization(Context context, String authorization){
        getPreference(context).edit().putString(AUTHORIZATION, authorization).apply();
    }

    public static String getAuthorization(Context context){
        return getPreference(context).getString(AUTHORIZATION,null);
    }
    public static void saveToken(Context context,String token){
        getPreference(context).edit().putString(X_AUTH_TOKEN,token).apply();
    }
//    public static void savePass(Context context, String password){
//        getPreference(context).edit().putString(PASS_WORD,password).apply();
//    }
    public static String getxAuthToken(Context context){

        return getPreference(context).getString(X_AUTH_TOKEN,null);
    }

    public static void setDayUpdate(Context context, String idDay){
        SharedPreferences.Editor editor= getPreference(context).edit();
        editor.putString(DAY_UPDATE,idDay);
        editor.commit();
    }

    public static String getDayUpdate(Context context){
        return getPreference(context).getString(DAY_UPDATE,"");
    }

    public static void setUsername(Context context,String username){
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putString(USERNAME,username);
        editor.commit();
    }

    public static String getUsername(Context context){
        return getPreference(context).getString(USERNAME,null);
    }

    public static void setPassword(Context context, String password){
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putString(PASSWORD,password);
        editor.commit();
    }

    public static String getPassword(Context context){
        return getPreference(context).getString(PASSWORD,null);
    }

    public static void setIsFirstLogin(Context context, boolean fistLogin){
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putBoolean(IS_FIRST_LOGIN,fistLogin);
        editor.commit();
    }

    public static boolean getFirstLogin(Context context){
        return getPreference(context).getBoolean(IS_FIRST_LOGIN, false);
    }
    public static void setEmbeddings(Context context, String embeddings){
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putString(EMBEDDINGS,embeddings);
        editor.commit();
    }

    public static String getEmbeddings(Context context){
        return getPreference(context).getString(EMBEDDINGS,"");
    }

    public static String[] getListEmbedding(Context context) {
        return getPreference(context).getString(EMBEDDINGS, "").split(",");
    }


}
