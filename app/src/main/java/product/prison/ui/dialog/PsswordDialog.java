package product.prison.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import product.prison.R;
import product.prison.app.App;
import product.prison.ui.set.SetActivity;

public class PsswordDialog {
    Activity activity;
    Handler handler;

    public PsswordDialog(Handler handler, Activity activity) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.handler = handler;
    }

    static AlertDialog serverip_dialog;

    public void crt(final int type) {
        // TODO Auto-generated method stub
        try {
            serverip_dialog = new AlertDialog.Builder(activity).create();
            if (serverip_dialog != null && serverip_dialog.isShowing()) {
                serverip_dialog.dismiss();
            } else {
                serverip_dialog.setView(new EditText(activity));
                serverip_dialog.show();
            }
            serverip_dialog.setContentView(R.layout.dialog_password);
            final EditText serverip = serverip_dialog
                    .findViewById(R.id.serverip);
            ImageButton serverip_ok = serverip_dialog
                    .findViewById(R.id.serverip_ok);
            serverip_ok.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    System.out.println(App.password);
                    if (type == 1 && serverip.getText().toString().equals(App.password)) {
                        serverip_dialog.dismiss();
                        activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
                    } else if (type == 2 && serverip.getText().toString().equals(App.password)) {
                        serverip_dialog.dismiss();
                        new ServerIpDialog(new Handler(), activity).crt();
                    } else {
                        Toast.makeText(activity, "密码有误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private void restart() {
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
