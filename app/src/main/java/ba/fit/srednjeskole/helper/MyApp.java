package ba.fit.srednjeskole.helper;

import android.app.Application;
import android.content.Context;

import com.onesignal.OneSignal;

import java.lang.ref.WeakReference;

public class MyApp extends Application {
    private static WeakReference<Context> context;
    public static Context getContext(){ return context.get();}

    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<>(getApplicationContext());
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    public static String ObavijestIDKey = "ObavijestId";
    public static String ObavijestKey = "Obavijest";
    public static String MaterijalIDKey = "MaterijalId";
    public static String MaterijalKey = "Materijal";
    public static String RazredGenericKey = "RazredGenericKey";
}
