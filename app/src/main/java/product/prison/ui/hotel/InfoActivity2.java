package product.prison.ui.hotel;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.Details;
import product.prison.bean.Info;
import product.prison.ui.BaseFram;
import product.prison.ui.adapter.GalleryAdapter;

/**
 * Created by zhu on 2017/9/26.
 */

public class InfoActivity2 extends BaseFram {

    private App app;
    Context context;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = getActivity();
        app = (App) context.getApplicationContext();
        view = inflater.inflate(R.layout.activity_info, container, false);


        initview();
        setvalue();
        return view;
    }

    Gallery info_gallery;
    TextView info_title;
    WebView info_web;
    ImageView info_img;
    LinearLayout info_line2;
    WebView info_otweb;

    private void initview() {
        info_gallery = view.findViewById(R.id.info_gallery);
        info_title = view.findViewById(R.id.info_title);
        info_web = view.findViewById(R.id.info_web);
        info_img = view.findViewById(R.id.info_img);
        WebSettings websettings = info_web.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setBuiltInZoomControls(true);
        info_web.setBackgroundColor(Color.TRANSPARENT);
        info_web.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        info_gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                setdate(position);
                galleryAdapter.setSelectItem(position);
                galleryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        info_line2 = view.findViewById(R.id.info_line2);
        info_otweb = view.findViewById(R.id.info_otweb);
        WebSettings settings2 = info_otweb.getSettings();
        settings2.setJavaScriptEnabled(true);
        settings2.setBuiltInZoomControls(true);
        info_otweb.setBackgroundColor(Color.TRANSPARENT);
        info_otweb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }


    Info info;
    List<Details> details;
    Details detail;
    GalleryAdapter galleryAdapter;

    private void setvalue() {
        try {
            info = (Info) getArguments().get("key");
            details = info.getDetails();
            if (details == null) {
                details = new ArrayList<>();
                detail = new Details();
                detail.setContent(info.getContent());
                detail.setName(info.getName());
                detail.setIcon(info.getPath());
                details.add(detail);
            }
            galleryAdapter = new GalleryAdapter(getActivity(), details);
            info_gallery.setAdapter(galleryAdapter);

            info_gallery.setGravity(Gravity.CENTER);
            info_gallery.setSelection(galleryAdapter.getCount() / 2);

            setdate(galleryAdapter.getCount() / 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setdate(int current) {
        try {
            String name = details.get(current).getName();
//            if (name.equals("航班信息") || name.equals("天气预报")) {
//                if (info_line2.getVisibility() == View.VISIBLE) {
//                    info_otweb.setVisibility(View.VISIBLE);
//                    info_line2.setVisibility(View.GONE);
//                }
//                info_otweb.loadUrl(Ini.lvyou.get(name));
//            } else {
                if (info_line2.getVisibility() == View.GONE) {
                    info_line2.setVisibility(View.VISIBLE);
                    info_otweb.setVisibility(View.GONE);
                }
                info_title.setText(name);
                info_web.loadDataWithBaseURL(null, details.get(current).getContent(),
                        "text/html", "utf-8", null);
                Picasso.with(context).load(details.get(current).getIcon()).error(R.drawable.error_img).into(info_img);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
