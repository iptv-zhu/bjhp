package product.prison.tools;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import product.prison.R;


public class FragmentTools {
    private static FragmentTransaction ft;
    private static int fragmentid = R.id.fragment;

    public static void Replace(FragmentManager fm, Fragment fragment) {
        // TODO Auto-generated method stub
        if (fragment != null) {
            ft = fm.beginTransaction();
//            ft.addToBackStack(null);
            ft.replace(fragmentid, fragment).commit();
        }

    }


}
