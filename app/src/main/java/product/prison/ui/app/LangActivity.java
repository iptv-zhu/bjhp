package product.prison.ui.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import product.prison.R;
import product.prison.app.App;
import product.prison.tools.AppTool;
import product.prison.ui.BaseActivity;
import product.prison.ui.BaseFram;

/**
 * Created by zhu on 2017/9/26.
 */

public class LangActivity extends BaseFram implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private App app;

    Context context;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = getActivity();
        app = (App) context.getApplicationContext();
        view = inflater.inflate(R.layout.activity_lang, container, false);


        initview();
        setvalue();
        return view;
    }


    private void setvalue() {
        try {

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    ImageButton lang_ok, lang_cancle;
    RadioGroup lang_group;
    RadioButton lang_zh, lang_en, lang_ft;

    private void initview() {
        lang_ok = view.findViewById(R.id.lang_ok);
        lang_cancle = view.findViewById(R.id.lang_cancle);
        lang_ok.setOnClickListener(this);
        lang_cancle.setOnClickListener(this);
        lang_group = view.findViewById(R.id.lang_group);
        lang_zh = view.findViewById(R.id.lang_zh);
        lang_zh.setChecked(true);
        lang_en = view.findViewById(R.id.lang_en);
        lang_ft = view.findViewById(R.id.lang_ft);

        lang_group.setOnCheckedChangeListener(this);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.lang_ok:
                AppTool.toast(context, this.getString(R.string.lang_scuess), 0, Gravity.CENTER, 0, 0);
                break;
            case R.id.lang_cancle:
                ((Activity) context).finish();
                break;
            default:
                break;
        }
    }

    int cutlang = -1;

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == lang_zh.getId()) {
            cutlang = 1;
        } else if (i == lang_en.getId()) {
            cutlang = 2;
        } else if (i == lang_ft.getId()) {
            cutlang = 3;
        }

    }
}