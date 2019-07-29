package product.prison.app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.renderscript.ScriptC;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cvte.tv.api.aidl.ITVApiSystemBacklightAidl;
import com.cvte.tv.api.aidl.ITvApiManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import product.prison.bean.Backs;
import product.prison.bean.Email;
import product.prison.bean.Food;
import product.prison.bean.FoodCat;
import product.prison.bean.GsonBean;
import product.prison.bean.Info;
import product.prison.bean.Live;
import product.prison.bean.LiveList;
import product.prison.bean.LogoData;
import product.prison.bean.Menu;
import product.prison.bean.Mings;
import product.prison.bean.Parts;
import product.prison.bean.PowerData;
import product.prison.bean.ShopOrder;
import product.prison.bean.Subs;
import product.prison.bean.User;
import product.prison.bean.VodCat;
import product.prison.bean.VolData;
import product.prison.bean.WelcomeAd;
import product.prison.nbean.Cmmond;
import product.prison.nbean.InsertAd;
import product.prison.service.BgService;
import product.prison.tools.FileType;
import product.prison.tools.LogUtils;
import product.prison.tools.SpUtils;
import product.prison.tools.Utils;
import product.prison.tools.ViewUtil;
import product.prison.tools.VolleyListenerInterface;
import product.prison.tools.VolleyRequestUtil;
import product.prison.tools.adb;
import product.prison.ui.BaseActivity;
import product.prison.ui.msg.MsgData;

/**
 * Created by zhu on 2017/8/28.
 */

public class App extends Application implements ServiceConnection {
    private List<LiveList> liveList;

    public void setLiveList(List<LiveList> liveList) {
        this.liveList = liveList;
    }

    public List<LiveList> getLiveList() {
        return this.liveList;
    }
//    public static String ip = "211.0.101.200:8080";
    //    public static String ip = "182.61.44.59:8080";
//    public static String ip = "74.131.105.200:8080";
    public static String ip = "192.168.2.2:8080";
    public static final String PALY = "PALY";
    public static final String PAUSE = "PAUSE";
    public static final String STOP = "STOP";
    public static final String FORWARD = "FORWARD";
    public static final String REWIND = "REWIND";
    public static final String Cancle = "Cancle";

    public static final String admin = "940512";
    public static final String SetServerIP = "111";
    public static final String SystemSet = "222";

    public static class ScreenType {

        // 横屏
        public static final int HORIZONTAL = 1;
        // 竖屏
        public static final int VERTICAL = 2;
    }

    public static final Gson gson = new GsonBuilder().setDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss").create();


    public static final int NUMMAX = 9;


    public static String Mac() {
        String mac = "testcode";
        try {
            Process pro = Runtime.getRuntime().exec(
                    "cat /sys/class/net/eth0/address");
            InputStreamReader inReader = new InputStreamReader(
                    pro.getInputStream());
            BufferedReader bReader = new BufferedReader(inReader);
            String line = null;
            while ((line = bReader.readLine()) != null) {
                mac = line.trim().replace(":", "").toLowerCase();
            }
            return mac;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return mac;
    }

    List<MsgData> insertmsgs;

    public List<MsgData> getInsertmsgs() {
        return insertmsgs;
    }

    public void setInsertmsgs(List<MsgData> insertmsgs) {
        this.insertmsgs = insertmsgs;
    }

    boolean insertmsg;

    public boolean isInsertmsg() {
        return insertmsg;
    }

    public void setInsertmsg(boolean insertmsg) {
        this.insertmsg = insertmsg;
    }

    public Cmmond getCmmond() {
        return cmmond;
    }

    public void setCmmond(Cmmond cmmond) {
        this.cmmond = cmmond;
    }

    Cmmond cmmond = new Cmmond();
    Cmmond oldcmmond = new Cmmond();


    public Cmmond getOldcmmond() {
        return oldcmmond;
    }

    public void setOldcmmond(Cmmond oldcmmond) {
        this.oldcmmond = oldcmmond;
    }

    private boolean sschabo = false;

    public boolean isSschabo() {
        return sschabo;
    }

    public void setSschabo(boolean sschabo) {
        this.sschabo = sschabo;
    }

    public boolean isInsertadstatus() {
        return insertadstatus;
    }

    public void setInsertadstatus(boolean insertadstatus) {
        this.insertadstatus = insertadstatus;
    }

    boolean insertadstatus;
    InsertAd insertAd = new InsertAd();

    public InsertAd getInsertAd() {
        return insertAd;
    }

    public void setInsertAd(InsertAd insertAd) {
        this.insertAd = insertAd;
    }

    public boolean isWeek() {
        return week;
    }

    public void setWeek(boolean week) {
        this.week = week;
    }

    public boolean week;

    public boolean isMing() {
        return ming;
    }

    public void setMing(boolean ming) {
        this.ming = ming;
    }

    public boolean ming;

    public Mings getMings() {
        return mings;
    }

    public void setMings(Mings mings) {
        this.mings = mings;
    }

    Mings mings = new Mings();


    public boolean isDownloadzip() {
        return downloadzip;
    }

    public void setDownloadzip(boolean downloadzip) {
        this.downloadzip = downloadzip;
    }

    boolean downloadzip;

    public List<WelcomeAd> getWelcomeAds() {
        return welcomeAds;
    }

    public void setWelcomeAds(List<WelcomeAd> welcomeAds) {
        this.welcomeAds = welcomeAds;
    }

    List<WelcomeAd> welcomeAds = new ArrayList<WelcomeAd>();

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    String update;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    int timeout;

    public List<Email> getReademail() {
        return reademail;
    }

    public void setReademail(List<Email> reademail) {
        this.reademail = reademail;
    }

    List<Email> reademail = new ArrayList<Email>();

    List<Email> emailDatas = new ArrayList<Email>();

    public List<Email> getEmailDatas() {
        return emailDatas;
    }

    public void setEmailDatas(List<Email> emailDatas) {
        this.emailDatas = emailDatas;
    }

    List<Food> shopCats = new ArrayList<>();

    public List<Food> getShopCats() {
        return shopCats;
    }

    private List<Food> orDishs = new ArrayList<Food>();

    public List<Food> getOrDishs() {
        return orDishs;
    }

    public void setOrDishs(List<Food> orDishs) {
        this.orDishs = orDishs;
    }

    public void setShopCats(List<Food> shopCats) {
        this.shopCats = shopCats;
    }

    public List<ShopOrder> getShopOrders() {
        return shopOrders;
    }

    public void setShopOrders(List<ShopOrder> shopOrders) {
        this.shopOrders = shopOrders;
    }

    List<ShopOrder> shopOrders = new ArrayList<>();

    public Live getLive() {
        return live;
    }

    public void setLive(Live live) {
        this.live = live;
    }

    Live live;

    public List<Info> getInfos() {
        return infos;
    }

    public void setInfos(List<Info> infos) {
        this.infos = infos;
    }

    List<Info> infos;

    public List<VodCat> getVodCats() {
        return vodCats;
    }

    public void setVodCats(List<VodCat> vodCats) {
        this.vodCats = vodCats;
    }

    List<VodCat> vodCats;

    public List<FoodCat> getFoodCats() {
        return foodCats;
    }

    public void setFoodCats(List<FoodCat> foodCats) {
        this.foodCats = foodCats;
    }

    List<FoodCat> foodCats;

    public List<Subs> getSubses() {
        return subses;
    }

    public void setSubses(List<Subs> subses) {
        this.subses = subses;
    }

    List<Subs> subses;

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    List<Menu> menus = new ArrayList<>();

    public List<Backs> getBacks() {
        return backs;
    }

    public void setBacks(List<Backs> backs) {
        this.backs = backs;
    }

    List<Backs> backs;

    public Bitmap getBg() {
        return bg;
    }

    public void setBg(Bitmap bg) {
        this.bg = bg;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    Bitmap bg;
    Bitmap logo;

    public long getSystemtime() {
        return systemtime;
    }

    public void setSystemtime(long systemtime) {
        this.systemtime = systemtime;
    }

    long systemtime;

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    int language;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    User user;


    public static RequestQueue getQueue() {
        return queue;
    }

    public static void setQueue(RequestQueue queue) {
        queue = queue;
    }

    static RequestQueue queue;
    public static int screenWidth;
    public static int screenHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(getApplicationContext());
        config();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        registerReceiver(receiver, filter);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        try {
            Intent intent = new Intent("com.cvte.tv.api.TV_API_SERVICE");
            intent.setPackage("com.cvte.tv.api.impl");
            bindService(intent, this, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            System.out.println("@@@@" + e.toString());
            e.printStackTrace();
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                userLoginAction_uploadPic();
                keepConnectAction_heartbeat();
            } else if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                //获取ConnectivityManager对象对应的NetworkInfo对象
                // 获取WIFI连接的信息
                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //获取以太网连接的信息
                NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
                if (wifiNetworkInfo.isConnected() || networkInfo.isConnected()) {
                    userLoginAction_getTime();//时间
                    userLoginAction_getLogo();//logo
                    getConfig_getConfig();//音量
                    userLoginAction_changeIp();//修改ip
                    getMessageAction_getPass();//密码
                    getConfig_getControl();//开关机

//                    unregisterReceiver(receiver);
                } else if (!networkInfo.isConnected() && !wifiNetworkInfo.isConnected()) {

                }
            }

        }
    };

    public static String headurl;


    public String getip() {
        String tmp = SpUtils.getString(this, "ip", ip);
        headurl = "http://" + tmp + "/prison/box/";
//            headurl = "http://" + tmp + "/box/";
        LogUtils.d("---headurl---\n" + headurl);
        return tmp;
    }

    public static String requrl(String api, String parm) {
        String url = headurl + api + ".action?mac=" + mac + parm;
        return url;
    }

    private void keepConnectAction_heartbeat() {
        String api = "keepConnectAction_heartbeat";
        String url = requrl("keepConnectAction_heartbeat", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        // ...
                        LogUtils.d(error.toString());
                    }
                }, false);
    }

    private void getConfig_getControl() {//获取开关机时间
        String api = "getConfig_getControl";
        String url = requrl("getConfig_getControl", "");
        LogUtils.d("@@@" + url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
//                            LogUtils.d("@@@"+json);
                            GsonBean<List<PowerData>> data = gson.fromJson(json,
                                    new TypeToken<GsonBean<List<PowerData>>>() {
                                    }.getType());
//                            System.out.println("@@@@"+data.getData().size());
                            if (data.getData() != null) {
                                for (PowerData powerData : data.getData()) {
                                    String s = Utils.creatDate(internettime) + " " + powerData.getTime();
                                    LogUtils.d(s + "---" + powerData.getOperate());
                                    long time = Utils.offtime(s).getTime();
                                    long se = time - internettime;
                                    if (se > 0 && powerData.getOperate() == 1) {//开机
//                                        System.out.println("@@@开机"+se);
                                        handler.sendEmptyMessageDelayed(0, se);
                                    }
                                    if (se > 0 && powerData.getOperate() == 2) {//关机
//                                        System.out.println("@@@关机"+se);
                                        handler.sendEmptyMessageDelayed(1, se);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        // ...
                        LogUtils.d(error.toString());
                    }
                }, false);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://                    adb.InputEvent(KeyEvent.KEYCODE_POWER);
                    if (backlightAidl == null)
                        return;
                    try {
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, defaultvolume, 0);
                        backlightAidl.eventSystemBacklightEnable(true); //打开背光xxe
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                case 1:
                    if (backlightAidl == null)
                        return;
                    try {
                        defaultvolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                        backlightAidl.eventSystemBacklightEnable(false);//关闭背光
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    };
    ITvApiManager iTvApiManager = null;
    ITVApiSystemBacklightAidl backlightAidl = null;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        iTvApiManager = ITvApiManager.Stub.asInterface(service);
        try {
            if (iTvApiManager == null)
                return;
            backlightAidl = iTvApiManager.getTVApiSystemBacklight();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    public static String password = "123456";

    private void getMessageAction_getPass() { //获取终端密码
        String api = "getMessageAction_getPass";
        String url = requrl("getMessageAction_getPass", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                            GsonBean<String> data = gson.fromJson(json,
                                    new TypeToken<GsonBean<String>>() {
                                    }.getType());
                            if (data.getData() != null) {
                                password = data.getData();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        // ...
                        LogUtils.d(error.toString());
                    }
                }, false);
    }

    private void userLoginAction_changeIp() {//修改ip通知服务器
        String api = "userLoginAction_changeIp";
        String url = requrl("userLoginAction_changeIp", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        // ...
                        LogUtils.d(error.toString());
                    }
                }, false);
    }


    private Timer timer = null;
    public static long internettime = System.currentTimeMillis();

    private void userLoginAction_getTime() {//获取服务器时间
        String api = "userLoginAction_getTime";
        String url = requrl("userLoginAction_getTime", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {

                            LogUtils.d(json);
                            GsonBean<Long> data = gson.fromJson(json,
                                    new TypeToken<GsonBean<Long>>() {
                                    }.getType());
                            if (data.getData() != null) {
                                internettime = data.getData();
                                if (timer != null) {
                                    timer.cancel();
                                }
                                timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    public void run() {
                                        internettime += 1000;
                                    }
                                }, 0, 1000);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        // ...
                        LogUtils.d(error.toString());
                    }
                }, false);
    }

    public static LogoData logoData;

    private void userLoginAction_getLogo() {//获取终端logo
        String api = "userLoginAction_getLogo";
        String url = requrl("userLoginAction_getLogo", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                            GsonBean<LogoData> data = gson.fromJson(json,
                                    new TypeToken<GsonBean<LogoData>>() {
                                    }.getType());
                            if (data.getData() != null) {
                                logoData = data.getData();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        // ...
                        LogUtils.d(error.toString());
                    }
                }, false);
    }

    private void getConfig_getConfig() { //获取音量控制大小
        String api = "getConfig_getConfig";
        String url = requrl("getConfig_getConfig", "");
        LogUtils.d(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String json) {
                        try {
                            LogUtils.d(json);
                            GsonBean<List<VolData>> data = gson.fromJson(json,
                                    new TypeToken<GsonBean<List<VolData>>>() {
                                    }.getType());
                            if (!data.getData().isEmpty()) {
                                int percent = Integer.parseInt(data.getData().get(0).getVal());
                                setStreamVolume(percent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        // ...
                        LogUtils.d(error.toString());

                    }
                }, false);
    }

    // 设置音量
    public static void setStreamVolume(int percent) {
        int volume = (int) Math.round((double) maxvolume * percent / 10);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI);
    }

    private boolean fstart;
    public static String mac;
    private static AudioManager am;
    private static int maxvolume;
    private static int defaultvolume;
    private Context context;


    public static String version;

    private void config() {
        try {
            context = this;
            am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            maxvolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            defaultvolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
            LogUtils.d("maxvolume" + maxvolume + " " + "defaultvolume" + defaultvolume);
            SpUtils.putInt(context, "vol", defaultvolume);
            queue = Volley.newRequestQueue(context);
            mac = Utils.getMACAddress();
            fstart = SpUtils.getBoolean(context, "fstart", false);

            if (!fstart) {
                SpUtils.putBoolean(context, "fstart", true);
                SpUtils.putString(context, "ip", ip);
            }

            version = Utils.getVersion(context);
            LogUtils.d("---version---\n" + version);
            getip();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void userLoginAction_uploadPic() {
        try {
            Bitmap bit = ViewUtil.screenShot(BaseActivity.activity);
            final File file = ViewUtil.BitmapToFile(bit);
            if (file.exists()) {
                new Thread(new Runnable() {
                    public void run() {
                        // TODO Auto-generated method stub
                        uploadFile("screenshot", file);
                    }
                }).start();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public String uploadFile(String tagName, File file) {
        String api = "userLoginAction_uploadPic";
        String url = requrl(api, "");

        String result = null;
        PostMethod post = null;
        HttpClient client = null;
        try {
            post = new PostMethod(url);

            NameValuePair[] params = new NameValuePair[1];
            params[0] = new BasicNameValuePair("mac", mac);

            client = new HttpClient();

            Part[] parts = null;
            if (params != null) {
                parts = new Part[params.length + 1];
            }
            if (params != null) {
                int i = 0;
                for (NameValuePair entry : params) {
                    parts[i++] = new StringPart(entry.getName(),
                            (String) entry.getValue());
                }
            }

            FilePart filePart = new FilePart(tagName, file.getName(), file,
                    new FileType().getMIMEType(file), "UTF-8");

            filePart.setTransferEncoding("binary");
            parts[parts.length - 1] = filePart;

            post.setRequestEntity(new MultipartRequestEntity(parts, post
                    .getParams()));

            List<Header> headers = new ArrayList<Header>();

            client.getHostConfiguration().getParams()
                    .setParameter("http.default-headers", headers);

            int status = client.executeMethod(post);

            if (status == HttpStatus.SC_OK) {
                String json = post.getResponseBodyAsString();
                JSONObject data = new JSONObject(json);
                String msgCode = data.getString("status");
                // if (msgCode.equalsIgnoreCase("success")) {
                // result = data.getString("success");
                // }
                LogUtils.d("上传截图成功");
            } else {
                result = "";
                LogUtils.d("上传截图失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
            if (client != null) {
                client = null;
            }

        }
        return result;
    }

    /**
     * 网络请求优化，取消请求
     *
     * @param tag
     */
    public static void cancelRequest(String tag) {
        try {
            queue.cancelAll(tag);
        } catch (Exception e) {

        }
    }
}
