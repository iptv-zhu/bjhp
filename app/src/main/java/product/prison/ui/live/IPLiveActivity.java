package product.prison.ui.live;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.LiveList;
import product.prison.config.What;
import product.prison.nbean.Live;
import product.prison.tools.AppTool;
import product.prison.tools.FULL;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.LiveListAdapter;

/**
 * Created by zhu on 2017/9/21.
 */

public class IPLiveActivity extends BaseActivity implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        try {
            config();
            initview();
            setvalue();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    App app;
    SharedPreferences live_share;
    AudioManager audioManager;
    int maxVolume;
    int currentVolume;


    private void config() {
        try {
            app = (App) getApplication();

            livelist = (List<LiveList>) getIntent().getSerializableExtra("key");

            live_share = getSharedPreferences("live", Context.MODE_PRIVATE);
            // TODO Auto-generated method stub
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            currentVolume = audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    RelativeLayout live_tips;
    TextView live_no_s, live_no_name;
    TextView live_no_b;
    VideoView live_player;

    private void initview() {
        live_tips = $(R.id.live_tips);
        live_no_s = $(R.id.live_no_s);
        live_no_name = $(R.id.live_no_name);
        live_no_b = $(R.id.live_no_b);
        live_player = $(R.id.live_player);
        FULL.star(live_player);
        live_player.setOnPreparedListener(this);
        live_player.setOnCompletionListener(this);
        live_player.setOnErrorListener(this);
    }

    //    List<Singles> livelist = new ArrayList<>();
    List<LiveList> livelist = new ArrayList<>();
    private int historyno = 0;

    private void setvalue() {

        try {
            historyno = live_share.getInt("no", 0);
            play();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }


    String testurl = "http://192.168.31.250:8105/wisdom_hotel/upload/1.ts";

    private void play() {
        // TODO Auto-generated method stub
        try {

            System.out.println(livelist.get(historyno).getLivemanagementaddress());
            if (livelist.isEmpty())
                return;
            live_player.setVideoPath(livelist.get(historyno).getLivemanagementaddress());
            showchanleinfo();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private ListView live_list;
    private TextView live_count;
    private PopupWindow popupWindow;
    private View view;

    private void show() {
        // TODO Auto-generated method stub
        try {
            view = getLayoutInflater().inflate(R.layout.pop_live, null);
            popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);

            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    popupWindow.dismiss();
                }
            });
            live_list = view.findViewById(R.id.live_list);
            live_count = view
                    .findViewById(R.id.live_count);
            live_list.setAdapter(new LiveListAdapter(this, livelist));
            live_count.setText(getString(R.string.live_count).replace("X", livelist.size() + ""));
            live_list.setSelectionFromTop(historyno, 220);
            popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            live_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> av, View v,
                                        int position, long id) {
                    historyno = position;
                    play();
                }
            });
            handler.removeMessages(What.HideLiveList);
            handler.sendEmptyMessageDelayed(What.HideLiveList, What.HideLiveInfo_TIME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case What.HideLiveList:
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        break;
                    case What.HideLiveInfo:
                        live_tips.setVisibility(View.GONE);
                        live_no_b.setText("");
                        break;
                    case What.SwitchNo:
                        try {
                            int tmp = Integer.parseInt(cutno) - 1;
                            cutno = "";
                            if (tmp >= 0 && tmp < livelist.size()) {
                                historyno = tmp;
                                play();

                            } else {
                                AppTool.toast(IPLiveActivity.this, getString(R.string.live_none), 0, Gravity.CENTER, 0, 0);
                            }

                        } catch (Exception e) {
                            // TODO: handle exception
                            cutno = "";
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    String cutno = "";

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        try {
            System.out.println("@@@@@" + keyCode);
            if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
                cutno += keyCode - 7;
                live_no_b.setText(cutno);

                handler.removeMessages(What.SwitchNo);
                handler.sendEmptyMessageDelayed(What.SwitchNo, 2 * 1000);

            } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER
                    || keyCode == KeyEvent.KEYCODE_ENTER) {
                show();
            }
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    downvol();
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    upvol();
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    upchanle();
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    downchanle();
                    break;
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    pause();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.setLooping(true);
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        AppTool.toast(this, getString(R.string.play_error), 0, Gravity.CENTER, 0, 0);
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    private void showchanleinfo() {
        try {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    live_tips.setVisibility(View.VISIBLE);
                    live_no_s.setText(historyno + 1 + "");
                    live_no_name.setText(livelist.get(historyno).getLivemanagementname());

                    live_no_b.setText(historyno + 1 + "");
                }
            });


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    live_tips.setVisibility(View.GONE);
                    live_no_b.setText("");
                }
            }, What.HideLiveInfo_TIME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void savechanle() {
        // TODO Auto-generated method stub
        try {
            live_share.edit().putInt("no", historyno).commit();
            if (live_player.isPlaying()) {
                live_player.stopPlayback();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        savechanle();
        super.onStop();
    }

    private void upchanle() {
        // TODO Auto-generated method stub
        try {
            if (historyno < livelist.size() - 1) {
                historyno += 1;
            } else {
                historyno = 0;
            }
            play();


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void downchanle() {
        // TODO Auto-generated method stub
        try {
            if (historyno > 0) {
                historyno -= 1;
            } else {
                historyno = livelist.size() - 1;
            }
            play();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void upvol() {
        // TODO Auto-generated method stub
//        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
////                audioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND
////                        | AudioManager.FLAG_SHOW_UI);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                audioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
    }

    private void downvol() {
        // TODO Auto-generated method stub
//        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
//                audioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND
//                        | AudioManager.FLAG_SHOW_UI);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                audioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
    }

    private void pause() {
        // TODO Auto-generated method stub
        try {
            if (live_player == null)
                return;
            if (live_player.isPlaying()) {
                live_player.pause();
            } else {
                live_player.start();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onTouchEvent(event);
    }


}
