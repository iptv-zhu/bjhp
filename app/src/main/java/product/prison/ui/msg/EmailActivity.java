package product.prison.ui.msg;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.Email;
import product.prison.bean.GsonBean;
import product.prison.config.What;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.EmailAdapter;

public class EmailActivity extends BaseActivity implements OnItemClickListener {
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        app = (App) getApplication();
        initview();

        setvalue();
    }

    EmailAdapter emailAdapter;

    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            EmailTimeSort sort = new EmailTimeSort();
            Collections.sort(app.getEmailDatas(), sort);

            emailAdapter = new EmailAdapter(EmailActivity.this,
                    app.getEmailDatas());
            email_list.setAdapter(emailAdapter);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    ListView email_list;
    TextView email_content;
    ImageButton bottom_msg;
    TextView bottom_msg_tips;

    private void initview() {
        // TODO Auto-generated method stub
        email_list = $(R.id.email_list);
        email_list.setOnItemClickListener(this);

        email_content = $(R.id.email_content);

        bottom_msg = $(R.id.bottom_msg);
        bottom_msg.setVisibility(View.GONE);
        bottom_msg_tips = $(R.id.bottom_msg_tips);
        bottom_msg_tips.setVisibility(View.GONE);
    }

    int current;

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        try {

            EmailTimeSort sort = new EmailTimeSort();
            Collections.sort(app.getEmailDatas(), sort);

            current = arg2;
            switch (arg0.getId()) {
                case R.id.email_list:
                    email_content.setText(app.getEmailDatas().get(arg2).getNotifyNews());
                    if (app.getEmailDatas().get(arg2).getReadStatus() == 0) {
                        setread();
                    }

                    break;

                default:
                    break;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    //app.getEmailDatas().get(current).getId()
//    http://192.168.2.250:8105/wisdom_hotel/remote/updateNotice?noticeId=1
    private void setread() {

        // TODO Auto-generated method stub

//        System.out.println(IPApp.headurl + "updateNotice?noticeId=" + app.getEmailDatas().get(current).getId()
//                + "&mac=" + Ini.Mac());
        String api = "updateNotice";
        String url = App.requrl(api, "&noticeId=" + app.getEmailDatas().get(current).getId());
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
//                            System.out.println(result);
                            refresh();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {

                    }
                }, false);

    }

    private void refresh() {
        // TODO Auto-generated method stub
        String api = "getNotice";
        String url = App.requrl(api, "");
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
//                            System.out.println(result);
                            GsonBean<List<Email>> json = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<List<Email>>>() {
                                    }.getType());
                            if (!json.getData().isEmpty()) {

                                List<Email> emails = new ArrayList<Email>();
                                for (Email email : json.getData()) {
                                    if (email.getBack_if() == 1) {
                                        emails.add(email);
                                    }
                                }
                                app.setEmailDatas(emails);

                                setvalue();
                                email_list.setSelection(current);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {

                    }
                }, false);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        finish();
        super.onStop();
    }
}
