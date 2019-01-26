package ba.fit.srednjeskole.helper;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class MyFragmentUtils {
    public static void openAsReplace(Activity activity, int id, Fragment fragment) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public static void openAsReplace(android.support.v4.app.FragmentActivity activity, int id, android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = activity.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void openAsDialog(Activity activity, DialogFragment dlg) {
        FragmentManager fm = activity.getFragmentManager();
        dlg.show(fm, "nekitag");
    }
}
