package product.prison.ui.set;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import product.prison.R;
import product.prison.app.App;
import product.prison.tools.FragmentTools;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.SetAdapter;
import product.prison.ui.app.WebActivity;
import product.prison.ui.app.WifiActivity;
import product.prison.ui.dialog.PsswordDialog;
import product.prison.ui.dialog.ServerIpDialog;

public class SetActivity extends BaseActivity implements SetAdapter.OnItemClickListener {
    //    int[] set_ico = {R.drawable.wifi, R.drawable.usb, R.drawable.browser,
//            R.drawable.network, R.drawable.ip};
    String[] set_name = {"WIFI", "USB", "浏览器",
            "网络设置", "IP设置"};

    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        app = (App) getApplication();
        initview();
        setvalue();
    }


    RecyclerView left_list;
    LinearLayoutManager layoutmanager;


    private void initview() {
        left_list = $(R.id.left_list);

        layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        left_list.setLayoutManager(layoutmanager);

    }


    SetAdapter adapter;


    private void setvalue() {


        adapter = new SetAdapter(this, set_name);
        left_list.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        toFram(0);


    }

    private void toFram(int position) {

        try {
            switch (position) {
                case 0:
                    FragmentTools.Replace(getFragmentManager(),
                            new WifiActivity());
                    break;
                case 1:
                    FragmentTools.Replace(getFragmentManager(),
                            new USBFr());
                    break;
                case 2:
                    FragmentTools.Replace(getFragmentManager(),
                            new WebActivity());
                    break;
                case 3:
//                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                    new PsswordDialog(new Handler(), SetActivity.this).crt(1);
                    break;
                case 4:
//                    new ServerIpDialog(new Handler(), SetActivity.this).crt();
                    new PsswordDialog(new Handler(), SetActivity.this).crt(2);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        toFram(position);
    }

}
