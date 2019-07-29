package product.prison.ui.ad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import product.prison.R;
import product.prison.app.App;
import product.prison.ui.BaseActivity;
import product.prison.ui.msg.MsgData;

public class MsgWebActivity extends BaseActivity {
	App app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (App) getApplication();
		setContentView(R.layout.activity_msgweb);

		initview();
		setvalue();
		isstop();

		reg();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	protected void reg() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("finishMsgWebActivity");
		this.registerReceiver(receiver, filter);
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			app.setInsertmsg(!app.isInsertmsg());
			finish();
		}
	};

	private void isstop() {

		System.out.println("stop after "
				+ (app.getInsertmsgs().get(0).getEndtime().getTime() - App.internettime) / 1000);

		handler.sendEmptyMessageDelayed(0, app.getInsertmsgs().get(0)
				.getEndtime().getTime()
				- App.internettime);

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			try {
				switch (msg.what) {
				case 0:
					app.setInsertmsg(!app.isInsertmsg());
					finish();
					break;

				default:
					break;
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

	MsgData data;

	private void setvalue() {
		// TODO Auto-generated method stub
		data = (MsgData) getIntent().getSerializableExtra("key");
		if (data == null) {
			data = app.getInsertmsgs().get(0);
		}

		msgtitle.setText(data.getTitle());
		msgweb.loadDataWithBaseURL(null, data.getContent(), "text/html",
				"utf-8", null);
	}

	WebView msgweb;
	TextView msgtitle;

	private void initview() {
		// TODO Auto-generated method stub

		msgtitle = (TextView) findViewById(R.id.msgtitle);
		msgweb = (WebView) findViewById(R.id.msgweb);
		WebSettings websettings = msgweb.getSettings();
		websettings.setJavaScriptEnabled(true);
		websettings.setBuiltInZoomControls(true);
		msgweb.setBackgroundColor(Color.TRANSPARENT);
		msgweb.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

}
