package product.prison.request;

import android.content.Context;
import android.view.Gravity;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import product.prison.R;
import product.prison.tools.AppTool;

/**
 * Created by zhu on 2017/9/18.
 */

public abstract class VolleyListenerInterface {
    public Context mContext;
    public static Response.Listener<String> mListener;
    public static Response.ErrorListener mErrorListener;

    public VolleyListenerInterface(Context context, Response.Listener<String> listener,
                                   Response.ErrorListener errorListener) {
        this.mContext = context;
        this.mErrorListener = errorListener;
        this.mListener = listener;
    }

    // 请求成功时的回调函数
    public abstract void onMySuccess(String result);

    // 请求失败时的回调函数
    public abstract void onMyError(VolleyError error);

    // 创建请求的事件监听
    public Response.Listener<String> responseListener() {
        mListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
//                Log.e("Volley Response", "response == " + s);
                onMySuccess(s);
            }
        };
        return mListener;
    }

    // 创建请求失败的事件监听
    public Response.ErrorListener errorListener() {
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onMyError(volleyError);
//                AppTool.toast(mContext, mContext.getString(R.string.server_error), 0, Gravity.CENTER, 0, 0);

            }
        };
        return mErrorListener;
    }
}
