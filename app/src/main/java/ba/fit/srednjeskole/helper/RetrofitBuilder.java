package ba.fit.srednjeskole.helper;

import android.content.Context;

import javax.inject.Singleton;

import ba.fit.srednjeskole.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitBuilder {
    static Retrofit retrofit = null;

    public static Retrofit Build(Context context) {
        if(retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(context.getString(R.string.ApiBaseUrl))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return  retrofit;
    }
}
