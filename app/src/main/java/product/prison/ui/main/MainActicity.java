package product.prison.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.AddeList;
import product.prison.bean.FoodCat;
import product.prison.bean.GsonBean;
import product.prison.bean.IntroduceData;
import product.prison.bean.LiveData;
import product.prison.bean.Menu;
import product.prison.bean.WelcomData;
import product.prison.config.What;
import product.prison.nbean.Record;
import product.prison.nbean.Themes;
import product.prison.nbean.UpdateData;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.service.BgService;
import product.prison.tools.FULL;
import product.prison.tools.Update;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.MenuAdapter;
import product.prison.ui.food.FoodActivity;
import product.prison.ui.live.IPLiveActivity;
import product.prison.ui.live.RecordActivity;
import product.prison.ui.news.NewsActivity;
import product.prison.ui.res.ResActivity;
import product.prison.ui.set.SetActivity;
import product.prison.ui.zhinan.IntroduceActivity;

/**
 * Created by Administrator on 2017/9/15 0015.
 */

public class MainActicity extends BaseActivity implements MenuAdapter.OnItemClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            app = (App) getApplication();

            initView();

            setMediaListene();

            setvalue();

            startService(new Intent(getApplicationContext(), BgService.class));
            IntentFilter filter = new IntentFilter();
            filter.addAction("STOPTOLIVE");
            registerReceiver(receiver, filter);
            handler.sendEmptyMessageDelayed(What.tolive, 5 * 1000);
        } catch (Exception e) {
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            try {
                String com = intent.getAction();
                if (com.equals("STOPTOLIVE")) {
                    handler.removeMessages(What.tolive);
                }
            } catch (Exception e) {
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            if (!app.isInsertadstatus()) {
                handler.sendEmptyMessageDelayed(What.tolive, 5 * 1000);
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStop() {
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();
    }


    RecyclerView mainrecyle;
    LinearLayoutManager layoutmanager;

    TextView main_time, main_date, main_week;
    ImageView weather_img;
    TextView weather_status;
    TextView weather_status1;
    TextView info1, info2, info3;
    ImageView main_image;
    VideoView main_video;

    private void initView() {
        main_image = $(R.id.main_image);
        main_video = $(R.id.main_video);
        FULL.star(main_video);
        main_video.setOnPreparedListener(this);
        main_video.setOnErrorListener(this);

        mainrecyle = $(R.id.mainrecyle);
        mainrecyle.requestFocus();

        main_time = $(R.id.main_time);
        main_date = $(R.id.main_date);
        main_week = $(R.id.main_week);
        weather_img = $(R.id.weather_img);
        weather_status = $(R.id.weather_status);
        weather_status1 = $(R.id.weather_status1);

        info1 = $(R.id.info1);
        info2 = $(R.id.info2);
        info3 = $(R.id.info3);

        layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mainrecyle.setLayoutManager(layoutmanager);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case What.PlayWelcomead:
                    try {
                        switch (ad.get(cutad).getType()) {
                            case 1:
                                playimg();
                                playmusic();
                                break;
                            case 2:
                                if (mediaPlayer.isPlaying()) {
                                    mediaPlayer.stop();
                                    mediaPlayer.reset();
                                }
                                playvideo();
                                break;
                            default:
                                break;
                        }

//                        System.out.println(ad.getAddeList().get(cutad).getInter() + "***********************" + cutad);
                        if (ad.size() > 1) {
                            handler.sendEmptyMessageDelayed(What.PlayWelcomead, ad.get(cutad).getInter() * 1000);
                            if (cutad < ad.size() - 1) {
                                cutad++;
                            } else {
                                cutad = 0;
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case What.tolive:
                    getlive();
                    break;
            }
        }
    };

    private void playimg() {
        if (main_video.getVisibility() == View.VISIBLE) {
            main_video.setVisibility(View.GONE);
            if (main_video.isPlaying()) {
                main_video.stopPlayback();
            }
        }
        if (main_image.getVisibility() == View.GONE) {
            main_image.setVisibility(View.VISIBLE);
        }
        Picasso.with(this).load(ad.get(cutad).getPath()).into(main_image);
    }

    private MediaPlayer mediaPlayer;

    private void setMediaListene() {
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playmusic();
            }
        });

    }

    private void playmusic() {
        // TODO Auto-generated method stub

        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(MainActicity.this,
                    Uri.parse(ad.get(cutad).getBgMusic()));
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private void playvideo() {
        if (main_image.getVisibility() == View.VISIBLE) {
            main_image.setVisibility(View.GONE);
        }
        if (main_video.getVisibility() == View.GONE) {
            main_video.setVisibility(View.VISIBLE);
        }
        System.out.println(ad.get(cutad).getPath());
        main_video.setVideoURI(Uri.parse(ad.get(cutad).getPath()));

    }

    private void setvalue() {
        tmenu();
        update();
        getWelComeAd();
        getLogo();
    }


    //    List<Welcomad> welcomeAds;
    int cutad;
    List<AddeList> ad;

    private void getWelComeAd() {
        String api = "adDetailAction_getAllAdDetail";
        String url = App.requrl(api, "");
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            System.out.println(result);
                            GsonBean<List<WelcomData>> data = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<List<WelcomData>>>() {
                                    }.getType());
                            if (data.getData() != null) {
                                ad = data.getData().get(0).getAddeList();
                                handler.sendEmptyMessage(What.PlayWelcomead);
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


    Themes logoBg;

    private void getLogo() {
        String api = "userLoginAction_getLogo";
        String url = App.requrl(api, "");
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            final GsonBean<Themes> json = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<Themes>>() {
                                    }.getType());
                            if (json.getData() != null) {
                                logoBg = json.getData();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            app.setLogo(Picasso.with(MainActicity.this).load(logoBg.getLogoPath()).get());

                                            app.setBg(Picasso.with(MainActicity.this).load(logoBg.getBgPath()).get());
                                        } catch (Exception e) {
//                                            e.printStackTrace();
                                        }
                                    }
                                }).start();


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

    //    http://192.168.2.250:8105/wisdom_hotel/remote/tmenu?mac=6c:5a:b5:df:f7:1b
    List<Menu> menus = new ArrayList<Menu>();

    private void tmenu() {
        String api = "userLoginAction_getModels";
        String url = App.requrl(api, "");
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            GsonBean<List<Menu>> json = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<List<Menu>>>() {
                                    }.getType());
                            if (json.getData() != null) {
                                menus = json.getData();

                                MenuAdapter adapter = new MenuAdapter(MainActicity.this, menus);
                                mainrecyle.setAdapter(adapter);
                                adapter.setOnItemClickListener(MainActicity.this);

                                app.setMenus(menus);
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
    public void onItemClick(View view, int position) {

        int tmp = menus.get(position).getId();
        System.out.println(position);
        switch (tmp) {
            case 1://监所信息
                getIntroduce();
                break;
            case 2://直播
                getlive();
                break;
            case 3:// 点播
                startActivity(new Intent(MainActicity.this, ResActivity.class));
                break;
            case 4://点播回放
                getrecord();
                break;
            case 5://文化长廊
                startActivity(new Intent(MainActicity.this, NewsActivity.class));

                break;
            case 6://设置功能
                startActivity(new Intent(MainActicity.this, SetActivity.class));
                break;
        }
    }

    //    http://106.12.200.32:8080/prison/box/getFuyuan_getIntroduce.action?mac=testcode
    private void getIntroduce() {
        String api = "getFuyuan_getIntroduce";
        String url = App.requrl(api, "");
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
//                        System.out.println(result);
                            GsonBean<List<IntroduceData>> json = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<List<IntroduceData>>>() {
                                    }.getType());
                            if (!json.getData().isEmpty()) {
                                startActivity(new Intent(MainActicity.this, IntroduceActivity.class)
                                        .putExtra("key", (Serializable) json.getData()));
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

    private void getlive() {
        String api = "userLoginAction_getUserProductInfo";
        String url = App.requrl(api, "");
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            System.out.println(result);

                            GsonBean<LiveData> json = App.gson.fromJson(result,
                                    new TypeToken<GsonBean<LiveData>>() {
                                    }.getType());
                            if (json.getData() != null) {
                                app.setLiveList(json.getData().getLiveList());
                                startActivity(new Intent(MainActicity.this, IPLiveActivity.class)
                                        .putExtra("key", (Serializable) json.getData().getLiveList()));
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

    private void getrecord() {
        String api = "getFileAction_getTrFile";
        String url = App.requrl(api, "&pageNo=" + 1
                + "&pageSize=" + 9999999);
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            System.out.println(result);

                            GsonBean<Record> json = App.gson.fromJson(result,
                                    new TypeToken<GsonBean<Record>>() {
                                    }.getType());

                            if (json.getData() != null) {
                                startActivity(new Intent(MainActicity.this, RecordActivity.class).putExtra("key", (Serializable) json.getData().getData()));
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


    //    菜品分类信息
//    参数 ：mac
//    返回 List<DishStyle> styles
//    http://192.168.2.250:8105/wisdom_hotel/remote/getDishStyle?mac=6c:5a:b5:df:f7:1b
    private void getDishStyle() {
        String api = "getDishStyle";
        String url = App.requrl(api, "");
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
//                        System.out.println(result);
                            GsonBean<List<FoodCat>> json = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<List<FoodCat>>>() {
                                    }.getType());
                            if (!json.getData().isEmpty()) {
                                app.setFoodCats(json.getData());
                                startActivity(new Intent(getApplicationContext(), FoodActivity.class));
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
    public void onBackPressed() {
//        super.onBackPressed();
    }

    //    查看升级
    private void update() {
        String api = "updateJobAction_checkUpdate";
        String url = App.requrl(api, "" + "&version=" + App.version);
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            System.out.println(result);
                            GsonBean<UpdateData> json = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<UpdateData>>() {
                                    }.getType());
                            if (json.getData() != null) {
                                String apkurl = json.getData().getApkUrl();
                                if (apkurl != null && !apkurl.equals("")) {

                                    new Update(MainActicity.this, apkurl).downloadAndInstall();
                                }
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
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        handler.removeMessages(What.tolive);
        return super.onKeyDown(keyCode, event);
    }
}
