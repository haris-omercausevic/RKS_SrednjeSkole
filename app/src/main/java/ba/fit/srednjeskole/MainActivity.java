package ba.fit.srednjeskole;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ba.fit.srednjeskole.data.api.IApiService;
import ba.fit.srednjeskole.helper.MyObjects;
import ba.fit.srednjeskole.helper.RetrofitBuilder;
import ba.fit.srednjeskole.model.UIKorisnik;
import ba.fit.srednjeskole.helper.MySession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferences sharedPref = getSharedPreferences(getString(R.string.AuthTokenPreferencesFile), Context.MODE_PRIVATE);
        String authToken = sharedPref.getString(getString(R.string.AuthTokenKey), null);
        if(authToken == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        else{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.ApiBaseUrl))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IApiService client = retrofit.create(IApiService.class);
            Call<UIKorisnik> call = client.LoginWithToken(authToken);
            call.enqueue(new Callback<UIKorisnik>() {
                @Override
                public void onResponse(Call<UIKorisnik> call, Response<UIKorisnik> response) {
                    if(response.isSuccessful()){
                        String username = sharedPref.getString("username",null);
                        if(username == null)
                            MySession.fillAccessSharedPreferences(MainActivity.this, response.body());

                        Intent intent = new Intent(MainActivity.this, GlavniActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                    else{
                        MySession.clearAccessSharedPreferences(MainActivity.this);

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<UIKorisnik> call, Throwable t) {
                    Toast.makeText(MainActivity.this, getString(R.string.ErrMsgApiFailure), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, GlavniActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
            });
        }


        UIKorisnik korisnik = MySession.readAccessSharedPreferences(this);

        if(korisnik == null)
            startActivity(new Intent(this, LoginActivity.class  ));
        else
            startActivity(new Intent(this, GlavniActivity.class  ));
    }
}
