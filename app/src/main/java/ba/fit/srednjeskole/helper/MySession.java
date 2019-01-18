package ba.fit.srednjeskole.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ba.fit.srednjeskole.LoginActivity;
import ba.fit.srednjeskole.R;
import ba.fit.srednjeskole.model.UIKorisnik;

public class MySession {
    private static final String KEY_UIKORISNIK = "Key_korisnik";

    public static void fillAccessSharedPreferences(Context context, UIKorisnik model) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.AuthTokenPreferencesFile), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

//        editor.putString(context.getString(R.string.AuthTokenKey), model.AuthToken);
//        editor.putString(context.getString(R.string.FirstnameKey), model.ime);
//        editor.putString(context.getString(R.string.LastnameKey), model.prezime);
//        editor.putString(context.getString(R.string.UsernameKey), model.username);
//        editor.putString(context.getString(R.string.EmailKey), model.email);
//        editor.putString(context.getString(R.string.RazrediKey), String.valueOf(model.razredi));
//        editor.putString(context.getString(R.string.RazrediBrojcanoKey), String.valueOf(model.razrediBrojcano));

        String strJson = model!=null?MyGson.build().toJson(model):"";
        editor.putString(KEY_UIKORISNIK, strJson);
        editor.apply();

        editor.apply();
    }
    public static UIKorisnik readAccessSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.AuthTokenPreferencesFile), Context.MODE_PRIVATE);

        String strJson = sharedPreferences.getString(KEY_UIKORISNIK, "");
        if(strJson.length() == 0)
            return null;


//        model.AuthToken = sharedPreferences.getString(context.getString(R.string.AuthTokenKey), null);
//        model.ime = sharedPreferences.getString(context.getString(R.string.FirstnameKey), "");
//        model.prezime = sharedPreferences.getString(context.getString(R.string.LastnameKey), "");
//        model.username = sharedPreferences.getString(context.getString(R.string.UsernameKey), "");
//        model.email = sharedPreferences.getString(context.getString(R.string.EmailKey), "");

        UIKorisnik model = MyGson.build().fromJson(strJson, UIKorisnik.class);
        return model;
    }

    public static void clearAccessSharedPreferences(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.AuthTokenPreferencesFile), Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
    }

    public static void logoutUser(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.AuthTokenPreferencesFile), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.remove(context.getString(R.string.AuthTokenKey));
        editor.remove(KEY_UIKORISNIK);
        editor.apply();
        //sharedPref.edit().clear().commit();

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
