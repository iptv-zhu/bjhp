package product.prison.ui.ad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import product.prison.R;
import product.prison.app.App;
import product.prison.nbean.Cmmond;
import product.prison.nbean.MipTrDetial;
import product.prison.nbean.MipdList;
import product.prison.nbean.SourceDetials;
import product.prison.tools.AppTool;
import product.prison.tools.FULL;
import product.prison.tools.RsType;
import product.prison.ui.BaseActivity;
import product.prison.ui.live.IPLiveActivity;
import product.prison.ui.main.MainActicity;

public class PlanActivity extends BaseActivity {
    private App app;
    private ImageView chaboimg;
    private VideoView chabovod;
    private Timer timer;
    private int localvideo;
    public static final String PALY = "PALY";
    public static final String PAUSE = "PAUSE";
    public static final String STOP = "STOP";
    public static final String FORWARD = "FORWARD";
    public static final String REWIND = "REWIND";
    public static final String Cancle = "Cancle";

    Timer timerv;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String com = intent.getAction();
            System.out.println(com);
            if (com.equals(PALY)) {
            } else if (com.equals(PAUSE)) {
                try {
                    if (chabovod.isPlaying()) {
                        chabovod.pause();
                    } else {
                        chabovod.start();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } else if (com.equals(STOP)) {
                System.out.println("*****" + !app.isInsertadstatus());

                if (app.isInsertadstatus()) {
                    app.setInsertadstatus(!app.isInsertadstatus());
                    finish();
                }

            } else if (com.equals(FORWARD)) {

                try {
                    if (timerv != null) {
                        timerv.cancel();
                    }
                    timerv = new Timer();
                    if (cmmond.getType() == 2) {
                        timerv.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                handle.sendEmptyMessage(1);

                            }
                        }, 0, 1 * 1000);

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println(e.toString());
                }

            } else if (com.equals(REWIND)) {
                try {
                    if (timerv != null) {
                        timerv.cancel();
                    }
                    timerv = new Timer();
                    if (cmmond.getType() == 2) {
                        timerv.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                handle.sendEmptyMessage(2);
                            }
                        }, 0, 1 * 1000);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println(e.toString());
                }
            } else if (com.equals(Cancle)) {
                if (timerv != null) {
                    timerv.cancel();
                }

            }
        }
    };

    Cmmond cmmond;
    Handler handle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 1:
                    chabovod.seekTo((int) (chabovod.getCurrentPosition() + chabovod
                            .getDuration() * 0.05));
                    break;
                case 2:
                    chabovod.seekTo((int) (chabovod.getCurrentPosition() - chabovod
                            .getDuration() * 0.05));
                    break;
                case 3:
                    setvalue();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        try {
            sendBroadcast(new Intent("STOPTOLIVE"));
            setContentView(R.layout.activity_ad);
            app = (App) getApplication();
            System.out.println("开始插播广告...");

            initview();
            setvalue();
            isstop();
            reg();
        } catch (Exception e) {
        }
//		new Req(this, null).addplay(What.addplay, 5, "", app.getInsertAd()
//				.getId());

    }

    private void isstop() {
        // TODO Auto-generated method stub

        System.out.println((app.getInsertAd().getEnd() - app.getInsertAd()
                .getStart()) / 1000 + "秒后停止-----------------------");

        handler.sendEmptyMessageDelayed(0, app.getInsertAd().getEnd()
                - app.getInsertAd().getStart());
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStoponStoponStoponStop-----------------------");
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        try {
            System.out.println("onDestroyonDestroyonDestroy-----------------------");
            chabovod.stopPlayback();
            mediaPlayer.release();
            mediaPlayer.stop();
            unregisterReceiver(receiver);

        } catch (Exception e) {
            // TODO: handle exception
        }

        super.onDestroy();
    }

    int local;
    int local2;
    List<MipdList> ad;
    List<MipdList> mipdLists;

    List<MipTrDetial> mipTrDetials;
    List<SourceDetials> sourceDetials;
    String liveAdd;

    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            System.out.println("setvalue***********类型"
                    + app.getInsertAd().getType() + "**********当前位置" + local);

            switch (app.getInsertAd().getType()) {

                case 1:// 文件
                    sourceDetials = app.getInsertAd().getSourceDetials();
                    if (!sourceDetials.isEmpty()) {
                        if (local > sourceDetials.size() - 1) {
                            local = 0;
                        }
                        handler.sendEmptyMessage(88);

                    }

                    break;
                case 2:// 用户上传
                    mipdLists = app.getInsertAd().getMipdList();
                    if (local > mipdLists.size() - 1) {
                        local = 0;
                    }
                    handler.sendEmptyMessage(88);
                    break;
                case 3:// 直播
                    liveAdd = app.getInsertAd().getLiveAdd();
                    handler.sendEmptyMessage(88);
                    break;
                case 4:// 录播资源
                    mipTrDetials = app.getInsertAd().getMipTrDetials();
                    if (local > mipTrDetials.size() - 1) {
                        local = 0;
                    }
                    handler.sendEmptyMessage(88);

                    break;
                default:
                    break;
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            handle.sendEmptyMessage(0);
        }
    }

    private void oncreatelive(String livepath) {
        // TODO Auto-generated method stub
        chaboimg.setVisibility(View.GONE);
        chaboweb.setVisibility(View.GONE);
        chabovod.setVisibility(View.VISIBLE);
        chabovod.setVideoURI(Uri.parse(livepath));
        if (chabovod.isPlaying()) {
            chabovod.stopPlayback();
        }

    }

    private void playmusic() {
        // TODO Auto-generated method stub
        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(getApplicationContext(),
                    Uri.parse(localurl));
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void playtxt() {
        // TODO Auto-generated method stub
        chaboimg.setVisibility(View.GONE);
        chabovod.setVisibility(View.GONE);
        chaboweb.setVisibility(View.VISIBLE);
        chaboweb.loadUrl(localurl);
        chaboweb.getSettings().setDefaultTextEncodingName("GBK");
        handler.sendEmptyMessageDelayed(99, jiange);
    }

    private void playvideo() {
        // TODO Auto-generated method stub
        chaboimg.setVisibility(View.GONE);
        chaboweb.setVisibility(View.GONE);
        chabovod.setVisibility(View.VISIBLE);
        chabovod.setVideoURI(Uri.parse(localurl));
        chabovod.start();
    }

    WebView chaboweb;

    private void initview() {
        // TODO Auto-generated method stub
        chaboimg = (ImageView) findViewById(R.id.chaboimg);
        chabovod = (VideoView) findViewById(R.id.chabovod);
        chaboweb = (WebView) findViewById(R.id.chaboweb);
        WebSettings websettings = chaboweb.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setBuiltInZoomControls(true);
        chaboweb.setBackgroundColor(Color.TRANSPARENT);
        chaboweb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        FULL.star(chabovod);
        // chabovod.setOnPreparedListener(new OnPreparedListener() {
        //
        // @Override
        // public void onPrepared(MediaPlayer mp) {
        // // TODO Auto-generated method stub
        // mp.start();
        // }
        // });
        chabovod.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // TODO Auto-generated method stub
                AppTool.toast(PlanActivity.this, getString(R.string.play_error), 0, Gravity.CENTER, 0, 0);
                handle.sendEmptyMessageDelayed(3, 5 * 1000);
                return true;
            }
        });
        chabovod.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                setvalue();
            }
        });
        setMediaListene();
    }

    int type;
    String localurl;
    int jiange = 15 * 1000;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            try {
                switch (msg.what) {

                    case 0:
                        try {
                            System.out.println("STOP----------STOP");
                            app.setInsertadstatus(false);
                            finish();
                            timer.cancel();
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        break;
                    case 1:
                        chabovod.setVisibility(View.GONE);
                        chaboweb.setVisibility(View.GONE);
                        chaboimg.setVisibility(View.VISIBLE);
                        System.out.println(localurl);

                        Picasso.with(PlanActivity.this).load(localurl)
                                .into(chaboimg);
                        handler.sendEmptyMessageDelayed(99, jiange);
                        break;

                    case 88:
                        switch (app.getInsertAd().getType()) {
                            case 1:
//						System.out.println(local + "----------");
                                localurl = sourceDetials.get(local).getPath();

                                if (localurl == null) {
                                    localurl = sourceDetials.get(local).getSubFiles()
                                            .get(local2);
                                }
                                if (sourceDetials.get(local).getSubFiles() != null) {
                                    // ppt、pdf
                                    local2++;
                                    System.out
                                            .println(local2
                                                    + "-----"
                                                    + sourceDetials.get(local)
                                                    .getSubFiles().size());
                                    if (local2 <= sourceDetials.get(local).getSubFiles()
                                            .size() - 1) {
                                        return;
                                    }
                                    local2 = 0;
                                }
                                break;
                            case 2:
                                localurl = mipdLists.get(local).getPath();
                                break;
                            case 3:
                                localurl = app.getInsertAd().getLiveAdd();
                                break;
                            case 4:
                                localurl = mipTrDetials.get(local).getPath();
                                break;
                            default:
                                break;
                        }

                        if (app.getInsertAd().getType() != 3) {
                            String temp = localurl.substring(localurl
                                    .lastIndexOf("."));
                            type = RsType.type.get(temp.toLowerCase());

                            System.out.println(localurl + "地址-----------" + temp
                                    + "类型" + type);
                            switch (type) {
                                case 1:
                                    handler.sendEmptyMessage(1);
                                    break;
                                case 2:
                                    playmusic();
                                    break;
                                case 3:
                                    playvideo();
                                    break;
                                case 4:
                                    playtxt();
                                    break;
                            }
                        } else {
                            playvideo();
                        }


                        local++;
                        break;
                    case 99:
                        setvalue();
                        break;
                    default:
                        break;
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                // System.out.println(e.toString());
            }
        }

    };

    private void reg() {
        // TODO Auto-generated method stub
        IntentFilter filter = new IntentFilter();
        filter.addAction(PAUSE);
        filter.addAction(STOP);
        filter.addAction(FORWARD);
        filter.addAction(REWIND);
        filter.addAction(Cancle);
        registerReceiver(receiver, filter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return true;
    }

    MediaPlayer mediaPlayer;

    private void setMediaListene() {
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    mediaPlayer.release();
                    mediaPlayer.stop();
                    setvalue();

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });
        mediaPlayer.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // TODO Auto-generated method stub
//                AppTool.toast(PlanActivity.this, getString(R.string.play_error), 0, Gravity.CENTER, 0, 0);
                return true;
            }
        });
    }


    int currentplay;

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        try {
            chabovod.pause();
            // mediaPlayer.pause();
            currentplay = chabovod.getCurrentPosition();

        } catch (Exception e) {
            // TODO: handle exception
        }
        super.onPause();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        try {
            chabovod.seekTo(currentplay);
            chabovod.start();
        } catch (Exception e) {
            // TODO: handle exception
        }

        super.onRestart();
    }

}
