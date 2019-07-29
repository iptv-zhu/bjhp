package product.prison.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import product.prison.R;

/**
 * Created by zhu on 2017/8/25.
 */

public class AppTool {


    public static boolean Cstart(Context context, Class tClass) {
        try {
            context.startActivity(new Intent(context, tClass));
            return true;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    public static boolean Astart(Activity activity, Class tClass, boolean close) {
        try {
            if (close) {
                activity.finish();
            }
            activity.startActivity(new Intent(activity, tClass));

            return true;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    public static boolean Ostart(Context context, String pkgNmae, String className) {
        try {
            if (IsInstall(context, pkgNmae)) {
                if (!className.trim().equals("")) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(pkgNmae, className));
                    context.startActivity(intent);
                } else {
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkgNmae);
                    context.startActivity(intent);

                }
            } else {
                //download
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    public static String getV(Context context, String packageName) {
        try {
            PackageInfo p = context.getPackageManager().getPackageInfo(
                    packageName, 0);
            if (p != null) {
                return p.versionName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean IsInstall(Context context, String packageName) {
        try {
            PackageInfo pin = context.getPackageManager().getPackageInfo(
                    packageName, 0);
            if (pin != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void toast(Context context, String msg, int duration, int gravity, int x, int y) {
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.mytoast, null);
            TextView mytoast = (TextView) view.findViewById(R.id.mytoast);
            mytoast.setText(msg);
            Toast toast = new Toast(context);
            toast.setGravity(gravity, x, y);
            toast.setDuration(toast.LENGTH_SHORT);
//            toast.setDuration(duration == 0 ? toast.LENGTH_SHORT : toast.LENGTH_LONG);
            toast.setView(view);
            toast.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void SystemSetting(Context context) {
        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
    }

    public static boolean IsRunning(Context context, String className) {
        ActivityManager myManager = (ActivityManager) context.getApplicationContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(Integer.MAX_VALUE);
        for (int i = 0; i < runningService.size(); i++) {
            System.out.println(runningService.get(i).service.getClassName().toString());
            if (runningService.get(i).service.getClassName().toString()
                    .equals(className)) {
                return true;
            }
        }
        return false;
    }
}
