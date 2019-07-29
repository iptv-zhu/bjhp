package product.prison.ui.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import product.prison.R;
import product.prison.app.App;
import product.prison.ui.BaseActivity;
import product.prison.ui.BaseFram;

/**
 * Created by zhu on 2017/9/26.
 */

public class ShareActivity extends BaseFram {

    private App app;

    Context context;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = getActivity();
        app = (App) context.getApplicationContext();
        view = inflater.inflate(R.layout.activity_share, container, false);


        initview();
        setvalue();
        return view;
    }

    private void setvalue() {

    }


    private void initview() {

    }


}
