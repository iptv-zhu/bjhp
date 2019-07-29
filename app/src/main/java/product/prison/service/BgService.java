package product.prison.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.cvte.tv.api.aidl.ITVApiSystemBacklightAidl;
import com.cvte.tv.api.aidl.ITvApiManager;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.Calendar;
import product.prison.bean.GsonBean;
import product.prison.config.What;
import product.prison.download.ZipUtil;
import product.prison.nbean.Cmmond;
import product.prison.nbean.InsertAd;
import product.prison.nbean.Off;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.tools.adb;
import product.prison.ui.ad.MsgWebActivity;
import product.prison.ui.ad.PlanActivity;
import product.prison.ui.ad.ResInsertActivity;
import product.prison.ui.msg.IScrollState;
import product.prison.ui.msg.MarqueeToast;
import product.prison.ui.msg.MsgData;
import product.prison.ui.msg.TextSurfaceView;


/**
 * Created by zhu on 2017/8/24.
 */

public class BgService extends Service implements Runnable, IScrollState {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Timer s1 = new Timer();
    Timer m1 = new Timer();


    App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = (App) getApplication();
        mintask();

        createsocket();

        regtime();
        initweb();
        getSubTitle();
    }

    private WebView webView;

    private void initweb() {
        webView = new WebView(this);
        WebSettings websettings = webView.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setBuiltInZoomControls(true);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.getSettings().setDefaultTextEncodingName("gbk");
    }

    private void regtime() {
        // TODO Auto-generated method stub
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                System.out.println("update time");
                try {
                    adb.screen();
                    sendBroadcast(new Intent().setAction("screen"));

                    getSubTitle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void ClearFiles() {
        try {

            File zip = new File(ZipUtil.zip_file);
            File main = new File(ZipUtil.main_file);
            File res = new File(ZipUtil.res_file);

            for (File zipfiles : zip.listFiles()) {
                zipfiles.delete();
                System.out.println("删除" + zipfiles.getName());
            }
            for (File programfiles : main.listFiles()) {
                if (programfiles.isDirectory()) {
                    DirectoryDelete(programfiles);
                }
            }
            for (File resfile : res.listFiles()) {
                resfile.delete();
                System.out.println("删除" + resfile.getName());

            }


        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void DirectoryDelete(File file) {
        // TODO Auto-generated method stub
        for (File files : file.listFiles()) {
            files.delete();
            System.out.println("删除" + files.getName());
        }
        file.delete();
    }

    private static final int PORT = 9999;
    private ServerSocket server = null;
    private Socket socket = null;

    public void createsocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(PORT);
                    while (true) {
                        socket = server.accept();
                        new SocketThread(socket).start();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void mintask() {
        m1.schedule(new TimerTask() {
            @Override
            public void run() {
                task();
            }
        }, 0, What.MESSAGE_TIME60S);
    }


//    private boolean messagerun = false;

    private void getSubTitle() {
        String api = "getMessageAction_doGetMessage";
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
                            GsonBean<String> json = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<String>>() {
                                    }.getType());

                            messages = json.getData();
                            if (messages != null && !messages.equals("")) {
                                showMessage();
//                                if (!messagerun) {
//                                    messagerun = true;
//                                    handler.post(BgService.this);
//                                }
                            } else {
                                if (toast != null) {
                                    toast.hid();
                                    toast = null;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        return;
                    }
                }, false);

    }

    String head = new SimpleDateFormat("yyyy-MM-dd ").format(new Date(App.internettime));
    Calendar calendar;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private void task() {
//        计划插播
        String api = "getMIPAction_get";
        String url = App.requrl(api, "");
        System.out.println("计划插播" + url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            System.out.println("计划插播------" + result);

                            if (app.isSschabo()) {
                                System.out.println("检测到正在实时插播。");
                                if (app.isInsertadstatus()) {
                                    System.out.println("检测到有即将执行的任务计划插播，任务计划插播正在被终止。");
                                    handler.removeMessages(What.task);
                                    app.setInsertadstatus(false);
                                } else {
                                    System.out.println("未检测到任务计划插播");

                                }
                                return;
                            }
                            GsonBean<InsertAd> task = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<InsertAd>>() {
                                    }.getType());
                            if (task.getData() != null && !task.getData().equals("")) {

                                if (!app.isInsertadstatus()) {
                                    app.setInsertAd(task.getData());
                                    app.setInsertadstatus(true);
                                    if (task.getData().getStart() <= 0) {
                                        System.out
                                                .println("现在开始插播计划插播*************************");
                                        handler.sendEmptyMessage(What.task);
                                    } else {
                                        System.out
                                                .println("计划插播 将在 *************************"
                                                        + (task.getData().getStartTime()
                                                        .getTime() - App.internettime)
                                                        / 1000 + "秒后开始");
                                        handler.sendEmptyMessageDelayed(What.task, task
                                                .getData().getStart());
                                    }
                                    System.out
                                            .println("计划插播 将在  *************************"
                                                    + (task.getData().getEndTime()
                                                    .getTime() - App.internettime) / 1000
                                                    + "秒后结束");
                                }

                            }


                        } catch (Exception e) {
//                            e.printStackTrace();
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        return;
                    }
                }, false);

    }


    private MarqueeToast toast = null;
    //    private TextSurfaceView Text;
    String messages = "";

    public void showMessage() {
        try {
//            System.out.println("@@**********************************");
            if (messages != null && !messages.equals("")) {
//                System.out.println("开始跑马灯");
                if (toast == null) {
                    toast = new MarqueeToast(BgService.this);
                }

//                Text = new TextSurfaceView(BgService.this, BgService.this);


//                Text.setFocusable(false);
//                Text.setOrientation(1);

//                Text.setContent(messages);
//                System.out.println("@@@"+messages);
//                webView.loadUrl("http://baidu.com");
                webView.loadDataWithBaseURL(null, test(messages), "text/html", "utf-8", null);
                toast.setHeight(App.screenHeight / 18);
                toast.setView(webView);
                toast.setGravity(Gravity.TOP | Gravity.LEFT, App.screenWidth, 0, 0);
                toast.show();

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    private String test(String s) {
        return "<marquee style='height:40px;font-size:23px;color:#fff;vertical-align:top;' direction='left'   behavior='scroll' scrollamount='3' scrolldelay='0' loop='-1'>"
                + s +
                "</marquee>";
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    private static final long SHOW_MSG_PERIOD = 1L * 10L * 1000L;

    @Override
    public void stop() {
        // TODO Auto-generated method stub
//        Text.setLoop(false);
//        Looper.prepare();
//        new Handler().postDelayed(this, SHOW_MSG_PERIOD);
//        Looper.loop();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
//        handler.sendEmptyMessage(0);
//        showMessage();
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    showMessage();
                    break;
                case What.task:
                    try {
                        startActivity(new Intent(BgService.this, PlanActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.toString());
                    }
                    break;
                case What.sendmessage:
                    try {
                        System.out.println("MsgWebActivity");
                        startActivity(new Intent(BgService.this,
                                MsgWebActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    break;
            }


        }

    };

    @Override
    public void onDestroy() {
        super.onDestroy();
//        hide();
        unregisterReceiver(receiver);
    }


//    void hide() {
//        try {
//            if (toast != null)
//                toast.hid();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public class SocketThread extends Thread {

        private Socket socket;

        public SocketThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            String msg;
            DataInputStream in = null;
            try {
                in = new DataInputStream(socket.getInputStream());

                while (true) {
                    if ((msg = in.readUTF()) != null) {
                        event(msg);
                    }
                }

            } catch (Exception e) {
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    String cmd = "";

    private void event(String msg) {
        // TODO Auto-generated method stub
        String msgs = "{\"data\":" + msg.substring(1, msg.length() - 1)
                + ",\"msg\":null,\"status\":\"success\"}";
        System.out.println("接收到命令：" + msgs);
        GsonBean<Cmmond> aj = App.gson.fromJson(msgs,
                new TypeToken<GsonBean<Cmmond>>() {
                }.getType());

        exec(aj.getData());

    }


    private void exec(Cmmond cmmond) {
        // TODO Auto-generated method stub
        try {

            app.setCmmond(cmmond);

            if (cmmond.getType() == 1 || cmmond.getType() == 2
                    || cmmond.getType() == 3) {
                switch (cmmond.getCommand()) {
                    case 1:
                        cmd = "";
                        startActivity(new Intent(getApplicationContext(),
                                ResInsertActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 2:
                        cmd = "input keyevent "
                                + KeyEvent.KEYCODE_MEDIA_FAST_FORWARD;
                        sendBroadcast(new Intent(App.FORWARD));
                        break;
                    case 3:
                        cmd = "input keyevent " + KeyEvent.KEYCODE_MEDIA_REWIND;
                        sendBroadcast(new Intent(App.REWIND));
                        break;
                    case 4:
                        sendBroadcast(new Intent(App.Cancle));
                        break;
                    case 5:
                        cmd = "input keyevent " + KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE;
                        sendBroadcast(new Intent(App.PAUSE));
                        break;
                    case 6:
                        cmd = "input keyevent "
                                + KeyEvent.KEYCODE_MEDIA_STOP;
                        sendBroadcast(new Intent(App.STOP));

                        break;

                    default:
                        break;
                }
            }

            if (cmmond.getType() == 5) {// 全屏字幕
                if (cmmond.getCommand() == 1) {
                    System.out.println("启用全屏字幕");

                    if (app.isInsertmsg()) {
                        sendBroadcast(new Intent()
                                .setAction("finishMsgWebActivity"));
                    }
                    sendmessage(cmmond.getId());
                }
                if (cmmond.getCommand() == 6) {
                    sendBroadcast(new Intent()
                            .setAction("finishMsgWebActivity"));
                }
            }
            if (cmmond.getType() == 4) {// 跑马灯
                if (cmmond.getCommand() == 1) {
                    System.out.println("启用跑马灯");
                    getSubTitle();
                }
                if (cmmond.getCommand() == 6) {
                    messages = "";
                    if (toast != null) {
                        toast.hid();
                        toast = null;
                    }
                }
            }
            app.setOldcmmond(cmmond);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    private void sendmessage(int id) {
        String api = "getMessageAction_getNewsMsg";
        String url = App.requrl(api, "&type=2&id=" + id);
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            System.out.println(result);

                            System.out.println("全屏字幕------" + result);
                            GsonBean<List<MsgData>> msgs = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<List<MsgData>>>() {
                                    }.getType());
                            // System.out.println(msgs.getData().size() + "@@@@@@@@");
                            if (msgs.getData() != null && !msgs.getData().equals("")) {
                                System.out
                                        .println("************全屏字幕将在************"
                                                + (msgs.getData().get(0).getStarttime()
                                                .getTime() - App.internettime) / 1000
                                                + "秒后开始");
                                System.out
                                        .println("************全屏字幕将在************"
                                                + (msgs.getData().get(0).getEndtime()
                                                .getTime() - App.internettime) / 1000
                                                + "秒后结束");
                                if (!app.isInsertmsg()) {
                                    app.setInsertmsgs(msgs.getData());
                                    app.setInsertmsg(!app.isInsertmsg());
                                    if ((msgs.getData().get(0).getStarttime().getTime() - App.internettime) / 1000 <= 0) {
                                        handler.sendEmptyMessage(What.sendmessage);
                                    } else {
                                        handler.sendEmptyMessageDelayed(What.sendmessage,
                                                msgs.getData().get(0).getStarttime()
                                                        .getTime()
                                                        - App.internettime);
                                    }
                                } else {

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Volley请求失败时调用的函数
                    @Override
                    public void onMyError(VolleyError error) {
                        return;
                    }
                }, false);

    }

}
