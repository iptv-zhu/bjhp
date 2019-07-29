package product.prison.ui.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.GsonBean;
import product.prison.bean.SatisfiedDetails;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.tools.AppTool;

public class SatisfiedAdapter extends BaseAdapter {
    private Context context;
    private List<SatisfiedDetails> list = new ArrayList<SatisfiedDetails>();

    public SatisfiedAdapter(Context context, List<SatisfiedDetails> list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void post() {
        // TODO Auto-generated method stub
        exam_answer();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private TextView satisfied_title;
    private View view;
    private EditText satisfied_no;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(context).inflate(R.layout.adapter_satisfied,
                null);

        satisfied_title = view.findViewById(R.id.satisfied_title);
        satisfied_no = view.findViewById(R.id.satisfied_no);
        try {
            satisfied_title.setText((position + 1) + "、" + list.get(position).getName());
        } catch (Exception e) {
            // TODO: handle exception
        }

        satisfied_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 将editText中改变的值设置的HashMap中
                hashMap.put(position, s.toString());
            }
        });

        // // 如果hashMap不为空，就设置的editText
        // if (hashMap.get(position) != null) {
        // satisfied_no.setText(hashMap.get(position));
        // }

        return view;
    }

    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();


    //2.提交答案
//    参数  : mac：mac地址       answer ：   所有答案拼接字符串    1：满意  2：基本满意  3：不满意  用,号隔开     如 ：answer=1,2,3,2,1,3,2,1,2
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
//    http://192.168.2.250:8105/wisdom_hotel/remote/exam_answer?mac=6c:5a:b5:df:f7:1b
    private void exam_answer() {
        String answer = "";

        for (int i = 0; i < hashMap.size(); i++) {
            answer += hashMap.get(i) + ",";
        }

        String api = "exam_answer";
        String url = App.requrl(api,  "&answer=" + answer);
        System.out.println(url);
        VolleyRequestUtil.RequestGet(context, url, api,
                new VolleyListenerInterface(context, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            System.out.println(result);
                            GsonBean<String> json = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<String>>() {
                                    }.getType());
                            if (json.getCode().equals("200")) {
                                AppTool.toast(context, json.getMsg(), 0, Gravity.CENTER, 0, 0);;
//                                satisfied_no.setText("");
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

}
