package ba.fit.srednjeskole.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.lang.reflect.Type;

public class MyApiRequest {
    public static <T> void request(final Activity activity, final String urlAction, final MyUrlConnection.HttpMethod httpMethod, final Object postObject,final MyRunnable<T> myCallback) {
        new AsyncTask<Void, Void, MyApiResult>() {
            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(activity, "Loading", "Sacekajte...");
            }

            @Override
            protected MyApiResult doInBackground(Void... voids) {
                String jsonPostObject = postObject==null?null:MyGson.build().toJson(postObject);
                return MyUrlConnection.request(MyConfig.baseUrl + "/" + urlAction, httpMethod, jsonPostObject, "application/json");
            }

            @Override
            protected void onPostExecute(MyApiResult result) {
                progressDialog.dismiss();
                if(result.isException){
                    View parentLayout = activity.findViewById(android.R.id.content);
                    if(result.resultcode == 0 )
                    {
                        Snackbar.make(parentLayout, "Greska u komunikaciji sa serverom ", Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        Snackbar.make(parentLayout, "Greska: " + result.resultcode +": " +  result.errorMessage, Snackbar.LENGTH_LONG).show();
                    }

                }
                else{
                    Type genericType = myCallback.getGenericType();
                    T x = null;
                    try {
                        x = MyGson.build().fromJson(result.value, genericType);
                        myCallback.run(x);
                    }
                    catch (Exception e){
                        View parentLayout = activity.findViewById(android.R.id.content);
                        Snackbar.make(parentLayout, "Greska: " + result.errorMessage, Snackbar.LENGTH_LONG).show();
                    }

                }
            }
        }.execute();
    }

    public static <T> void get(final Activity activity, final String urlAction, final MyRunnable<T> myCallback)
    {
        request(activity, urlAction, MyUrlConnection.HttpMethod.GET, null, myCallback);
    }

    public static <T> void delete(final Activity activity, final String urlAction, final MyRunnable<T> myCallback)
    {
        request(activity, urlAction, MyUrlConnection.HttpMethod.DELETE, null, myCallback);
    }

    public static <T> void post(final Activity activity, final String urlAction, Object postObject, final MyRunnable<T> myCallback)
    {
        request(activity, urlAction, MyUrlConnection.HttpMethod.POST, postObject, myCallback);
    }
    public static <T> void put(final Activity activity, final String urlAction, Object postObject, final MyRunnable<T> myCallback)
    {
        request(activity, urlAction, MyUrlConnection.HttpMethod.PUT, postObject, myCallback);
    }

}
