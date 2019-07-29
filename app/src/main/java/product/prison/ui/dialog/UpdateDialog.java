package product.prison.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import product.prison.R;
import product.prison.app.App;
import product.prison.tools.WebInstaller;

public class UpdateDialog {
    Activity activity;
    Handler handler;
    App app;

    public UpdateDialog(Handler handler, Activity activity) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        app = (App) activity.getApplication();
        this.handler = handler;
    }

    static AlertDialog update_dialog;

    public void crt() {
        // TODO Auto-generated method stub
        update_dialog = new AlertDialog.Builder(activity).create();
        if (update_dialog != null && update_dialog.isShowing()) {
            update_dialog.dismiss();
        } else {
            update_dialog.show();
        }
        update_dialog.setContentView(R.layout.dialog_update);

        ImageButton update_ok = update_dialog.findViewById(R.id.update_ok);
        update_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                close();

                new WebInstaller(activity, app.getUpdate()).downloadAndInstall(activity
                        .getString(R.string.Downloading_update));

            }
        });
        ImageButton update_cancle = update_dialog
                .findViewById(R.id.update_cancle);
        update_cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                close();
            }
        });

    }

    public static void close() {
        if (update_dialog != null && update_dialog.isShowing()) {
            update_dialog.dismiss();
        }
    }
}
