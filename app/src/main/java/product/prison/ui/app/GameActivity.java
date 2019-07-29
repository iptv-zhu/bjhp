package product.prison.ui.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.Game;
import product.prison.bean.GsonBean;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.tools.AppTool;
import product.prison.tools.WebInstaller;
import product.prison.ui.BaseFram;
import product.prison.ui.adapter.GameAdapter;

/**
 * Created by zhu on 2017/9/26.
 */

public class GameActivity extends BaseFram implements AdapterView.OnItemClickListener {
    private App app;
    Context context;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = getActivity();
        app = (App) context.getApplicationContext();
        view = inflater.inflate(R.layout.activity_game, container, false);


        initview();
        setvalue();
        return view;
    }


    private void setvalue() {
        try {
            getApp();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    //    应用信息
//    参数 ：mac
//    返回 List<Apply> applies
//    http://192.168.2.250:8105/wisdom_hotel/remote/getApp?mac=6c:5a:b5:df:f7:1b
    GameAdapter adapter;
    List<Game> game;

    private void getApp() {
        String api="getApp";
        String url = App.requrl(api, "");

        VolleyRequestUtil.RequestGet(context, url, api,
                new VolleyListenerInterface(context, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                        System.out.println(result);
                            GsonBean<List<Game>> json = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<List<Game>>>() {
                                    }.getType());
                            if (!json.getData().isEmpty()) {
                                game = json.getData();
                                adapter = new GameAdapter(context, game);
                                fram_game.setAdapter(adapter);
//                                fram_game.requestFocus();
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

    GridView fram_game;

    private void initview() {
        fram_game = view.findViewById(R.id.fram_game);
        fram_game.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (AppTool.IsInstall(context, game.get(i).getPackage_name())) {
            AppTool.Ostart(context, game.get(i).getPackage_name(), "");
        } else {
            new WebInstaller(context, game.get(i).getPath())
                    .downloadAndInstall(context
                            .getString(R.string.Downloading_game));
        }
    }
}
