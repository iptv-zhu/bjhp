package product.prison.ui.dialog;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import product.prison.R;
import product.prison.app.App;
import product.prison.tools.SpUtils;

public class ServerIpDialog {
    Activity activity;
    Handler handler;

    public ServerIpDialog(Handler handler, Activity activity) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.handler = handler;
    }

    static AlertDialog serverip_dialog;

    public void crt() {
        // TODO Auto-generated method stub
        try {
            serverip_dialog = new AlertDialog.Builder(activity).create();
            if (serverip_dialog != null && serverip_dialog.isShowing()) {
                serverip_dialog.dismiss();
            } else {
                serverip_dialog.setView(new EditText(activity));
                serverip_dialog.show();
            }
            serverip_dialog.setContentView(R.layout.dialog_serverip);
            final EditText serverip = serverip_dialog
                    .findViewById(R.id.serverip);
            serverip.setText(SpUtils.getString(activity, "ip", serverip.getText().toString()));
            serverip.setSelection(serverip.length());
            ImageButton serverip_ok = serverip_dialog
                    .findViewById(R.id.serverip_ok);
            serverip_ok.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    SpUtils.putString(activity, "ip", serverip.getText().toString());
                    restart();
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private void restart() {
//        ActivityManager manager = (ActivityManager)activity.getSystemService(Context.ACTIVITY_SERVICE);
//        manager.restartPackage(activity.getPackageName());

        final Intent intent = activity.getPackageManager()
                .getLaunchIntentForPackage(activity.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public static void close() {
        if (serverip_dialog != null && serverip_dialog.isShowing()) {
            serverip_dialog.dismiss();

        }
    }

}
