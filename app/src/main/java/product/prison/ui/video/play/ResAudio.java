package product.prison.ui.video.play;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.GsonBean;
import product.prison.nbean.Res;
import product.prison.nbean.ResData;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.tools.FULL;
import product.prison.ui.BaseActivity;


public class ResAudio extends BaseActivity implements OnPreparedListener,
        OnErrorListener, OnCompletionListener {
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.res_audio);

        app = (App) getApplication();

        initview();

        setvalue();
    }

    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            ResData resData = (ResData) getIntent().getExtras()
                    .getSerializable("key");
            res_audio_title.setText(resData.getName());
            res_audio.setVideoURI(Uri.parse(resData.getPath()));
            System.out.println(resData.getPath());
            req(resData.getId());
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private void req(int id) {
        String api = "userLoginAction_addVisitCount";
        String url = App.requrl(api, "&id=" + id);
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            System.out.println(result);
                            GsonBean<Res> json = App.gson.fromJson(result,
                                    new TypeToken<GsonBean<Res>>() {
                                    }.getType());
                            if (json.getData() != null) {

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

    VideoView res_audio;
    TextView res_audio_title;

    private void initview() {
        // TODO Auto-generated method stub
        res_audio = (VideoView) findViewById(R.id.res_audio);
        FULL.star(res_audio);
        MediaController controller = new MediaController(this);
        res_audio.setMediaController(controller);
        res_audio_title = (TextView) findViewById(R.id.res_audio_title);
        res_audio.setOnPreparedListener(this);
        res_audio.setOnCompletionListener(this);
        res_audio.setOnErrorListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // TODO Auto-generated method stub
        finish();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        // TODO Auto-generated method stub
        mp.start();
    }

}
