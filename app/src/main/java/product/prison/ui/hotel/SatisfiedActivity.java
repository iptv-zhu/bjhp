package product.prison.ui.hotel;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.GsonBean;
import product.prison.bean.Satisfied;
import product.prison.config.What;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.ui.BaseFram;
import product.prison.ui.adapter.SatisfiedAdapter;

/**
 * Created by zhu on 2017/10/23.
 */

public class SatisfiedActivity extends BaseFram implements View.OnClickListener {

    private App app;
    Context context;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = getActivity();
        app = (App) context.getApplicationContext();
        view = inflater.inflate(R.layout.activity_satisfied_backup, container, false);


        initview();
        setvalue();
        return view;
    }

    GridView satisfied_grid;
    Button satisfied_submit;
//    VListView satisfied_grid2;

    public void initview() {
        // TODO Auto-generated method stub
        satisfied_grid = view.findViewById(R.id.satisfied_grid);
        satisfied_submit = view.findViewById(R.id.satisfied_submit);
        satisfied_submit.setOnClickListener(this);
//        satisfied_grid2 = view.findViewById(R.id.satisfied_grid2);
    }

    private void setvalue() {
        exam_question();
    }

    //1.获取问题
//    参数  : mac：mac地址
//    data结构：
//    {
//        "id": 36,
//            "name": "555555",                       // 问卷标题
//            "createDate": 1495767791000,
//            "details": [                            // 问题详细
//        {
//            "id": 172,
//                "name": "您对护士的服务态度 ？"
//        }
//        ]
//    }
//    http://192.168.2.250:8105/wisdom_hotel/remote/exam_question?mac=6c:5a:b5:df:f7:1b
//            -------------------------------------------------------------------
    SatisfiedAdapter adapter;
    Satisfied satisfied;

    private void exam_question() {
//        System.out.println(IPConfig.getipaddr(this) + "exam_question?mac=" + Ini.Mac());
        String api = "exam_question";
        String url = App.requrl(api,   "");
        System.out.println(url);
        VolleyRequestUtil.RequestGet(context, url, api,
                new VolleyListenerInterface(context, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
//                            System.out.println(result);
                            GsonBean<Satisfied> json = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<Satisfied>>() {
                                    }.getType());
                            if (json.getData() != null) {
                                satisfied = json.getData();
                                adapter = new SatisfiedAdapter(context, satisfied.getDetails());
                                satisfied_grid.setAdapter(adapter);
//                                satisfied_grid2.setAdapter(adapter);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onMyError(VolleyError error) {

                    }
                }, false);


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.satisfied_submit:
                try {
                    adapter.post();
                    handler.sendEmptyMessage(disbtn);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                break;

            default:
                break;
        }

    }

    int timeout = 60;
    final int disbtn = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case disbtn:

                    if (timeout > 0) {
                        timeout--;
                        if (satisfied_submit.isEnabled()) {
                            satisfied_submit.setEnabled(!satisfied_submit.isEnabled());
                        }
                        satisfied_submit.setText(getString(R.string.Ok) + "(" + timeout + ")");
                        handler.sendEmptyMessageDelayed(disbtn, 1000);
                    } else {
                        if (!satisfied_submit.isEnabled()) {
                            satisfied_submit.setEnabled(!satisfied_submit.isEnabled());
                        }
                        satisfied_submit.setText(getString(R.string.Ok));
                        timeout = 60;
                    }


                    break;

            }
        }
    };

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_DPAD_UP)) {
//            satisfied_grid.setSelection(satisfied_grid.getSelectedItemPosition() - 1);
//        } else if ((keyCode == KeyEvent.KEYCODE_DPAD_DOWN)) {
//            satisfied_grid.setSelection(satisfied_grid.getSelectedItemPosition() + 1);
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
}
