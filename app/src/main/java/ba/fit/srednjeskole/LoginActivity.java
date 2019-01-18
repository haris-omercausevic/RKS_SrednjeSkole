package ba.fit.srednjeskole;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import ba.fit.srednjeskole.data.api.IApiService;
import ba.fit.srednjeskole.helper.MyApp;
import ba.fit.srednjeskole.helper.MyObjects;
import ba.fit.srednjeskole.helper.RetrofitBuilder;
import ba.fit.srednjeskole.model.LoginVM;
import ba.fit.srednjeskole.model.ObavijestVM;
import ba.fit.srednjeskole.model.UIKorisnik;
import ba.fit.srednjeskole.model.Storage;
import ba.fit.srednjeskole.helper.MySession;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtKorisnickoIme);
        txtPassword = findViewById(R.id.txtLozinka);
        txtUsername.clearFocus();
        txtPassword.clearFocus();

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnLoginClick();
            }
        });
    }

    private void do_btnLoginClick() {
        final LoginVM model = new LoginVM();
        model.username = txtUsername.getText().toString();
        model.password = txtPassword.getText().toString();

        Retrofit retrofit = RetrofitBuilder.Build(MyApp.getContext());
        IApiService client = retrofit.create(IApiService.class);
        Call<UIKorisnik> call = client.Login(model);

        call.enqueue(new Callback<UIKorisnik>() {
            @Override
            public void onResponse(Call<UIKorisnik> call, Response<UIKorisnik> response) {
                if(response.isSuccessful()){
                    UIKorisnik responseData = response.body();

                    if(responseData == null){
                        Toast.makeText(LoginActivity.this, getString(R.string.ErrMsgFriendly), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        startGlavniActivity(responseData);
                    }
                }
                else{
                    if(response.code() == 404)
                        Toast.makeText(LoginActivity.this, getString(R.string.ErrMsgWrongUsernameOrPassword), Toast.LENGTH_SHORT).show();
                    else if(response.code() == 401)
                        Toast.makeText(LoginActivity.this, getString(R.string.ErrMsgAccountNotActive), Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(LoginActivity.this, getString(R.string.ErrMsgFriendly), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UIKorisnik> call, Throwable t) {
                Toast.makeText(LoginActivity.this, getString(R.string.ErrMsgApiFailure), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startGlavniActivity(UIKorisnik model) {
            MySession.fillAccessSharedPreferences(this, model);
            Intent intent = new Intent(LoginActivity.this, GlavniActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
    }
}
