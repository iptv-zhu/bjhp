package product.prison.ui.set;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import product.prison.R;
import product.prison.tools.AppTool;
import product.prison.ui.BaseFram;
import product.prison.ui.set.usb.FileListAdapter;
import product.prison.ui.set.usb.MyUtil;

public class USBFr extends BaseFram implements OnClickListener {
    private int filestype = 0;
    // private String rootPath = "/mnt/external_sd/";
    // private String rootPath = "/mnt/usb_storage/";
    private String rootPath = "/mnt/usbhost/Storage01/";

    Context context;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = getActivity();
        view = inflater.inflate(R.layout.activity_usb, container, false);
        try {
            initview();
            setvalue();

            // reg();

        } catch (Exception e) {
            // TODO: handle exception
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        try {
            context.unregisterReceiver(usbreceiver);
        } catch (Exception e) {
            // TODO: handle exception
        }
        super.onDestroyView();
    }

    private void reg() {
        // TODO Auto-generated method stub
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_MEDIA_EJECT);
        intentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        intentFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
        intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        intentFilter.addDataScheme("file");
        context.registerReceiver(usbreceiver, intentFilter);
    }

    private BroadcastReceiver usbreceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            try {
                String action = intent.getAction();
                if (action.equals(Intent.ACTION_MEDIA_EJECT)) {
//					MyToast.makeshow(context,
//							context.getString(R.string.usb_out),
//							Toast.LENGTH_LONG);
                } else if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
//					MyToast.makeshow(context,
//							context.getString(R.string.usb_in),
//							Toast.LENGTH_LONG);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }

    };

    private List<String> items = new ArrayList<String>();
    private List<String> video = new ArrayList<String>();

    private List<String> music = new ArrayList<String>();

    private List<String> image = new ArrayList<String>();

    private List<String> txt = new ArrayList<String>();

    private List<String> other = new ArrayList<String>();


    private List<String> paths = new ArrayList<String>();
//    private List<String> sizes = new ArrayList<String>();

    private GridView other_usb_grid;
    private String currentpath;
    private ListView other_usb_list;
    private String[] filetype;

    private int[] fileico = {R.drawable.usb_1bg, R.drawable.usb_2bg,
            R.drawable.usb_3bg, R.drawable.usb_4bg, R.drawable.usb_5bg};

    LinearLayout usb_1, usb_2, usb_3, usb_4, usb_5;

    private void initview() {
        // TODO Auto-generated method stub
        usb_1 = (LinearLayout) view.findViewById(R.id.usb_1);
        usb_2 = (LinearLayout) view.findViewById(R.id.usb_2);
        usb_3 = (LinearLayout) view.findViewById(R.id.usb_3);
        usb_4 = (LinearLayout) view.findViewById(R.id.usb_4);
        usb_5 = (LinearLayout) view.findViewById(R.id.usb_5);

        usb_1.setOnClickListener(this);
        usb_2.setOnClickListener(this);
        usb_3.setOnClickListener(this);
        usb_4.setOnClickListener(this);
        usb_5.setOnClickListener(this);

        other_usb_grid = (GridView) view.findViewById(R.id.other_usb_grid);

        other_usb_grid.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                File file = new File(paths.get(position));
                // fileOrDirHandle(file, "short");
                openfiles(file);
            }

        });
    }

    private void setvalue() {
        // TODO Auto-generated method stub

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getFileDir(rootPath, filestype);

                    handler.sendEmptyMessage(1);
                }
            }).start();
            showProgressDialog();
        } catch (Exception e) {
            // TODO: handle exception
            AppTool.toast(context, context.getString(R.string.usb_error), 0, Gravity.CENTER, 0, 0);
        }
    }

    private void openfiles(File file) {
        // TODO Auto-generated method stub

        if (file.isDirectory()) {
            getFileDir(file.getPath(), filestype);
        } else {
            openFile(file);
        }
    }

    private void openFile(File f) {
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            String type = "*/*";
            type = MyUtil.getMIMEType(f, true);
            intent.setDataAndType(Uri.fromFile(f), type);
            startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void getFileDir(String filePath, int type) {
        try {
            currentpath = filePath;
            File f = new File(filePath);
            File[] files = f.listFiles();

            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        try {
                            getFileDir(files[i].getAbsolutePath(), type);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }

                    }
                }

                for (int i = 0; i < files.length; i++) {

                    if (files[i].isFile()) {
                        String fName = files[i].getName();
                        String end = fName.substring(
                                fName.lastIndexOf(".") + 1, fName.length())
                                .toLowerCase();
                        switch (type) {
                            case 0:
                                if (end.equals("3gp") || end.equals("mp4") || end.equals("mkv")) {
                                    items.add(files[i].getName());
                                    paths.add(files[i].getPath());
//                                    sizes.add(MyUtil.fileSizeMsg(files[i]));
                                }

                                break;
                            case 1:
                                if (end.equals("m4a") || end.equals("mp3")
                                        || end.equals("mid") || end.equals("xmf")
                                        || end.equals("ogg") || end.equals("wav")) {
                                    items.add(files[i].getName());
                                    paths.add(files[i].getPath());
//                                    sizes.add(MyUtil.fileSizeMsg(files[i]));
                                }
                                break;
                            case 2:
                                if (end.equals("jpg") || end.equals("gif")
                                        || end.equals("png") || end.equals("jpeg")
                                        || end.equals("bmp")) {
                                    items.add(files[i].getName());
                                    paths.add(files[i].getPath());
//                                    sizes.add(MyUtil.fileSizeMsg(files[i]));
                                }
                                break;
                            case 3:
                                if (end.equals("txt") || end.equals("text")) {
                                    items.add(files[i].getName());
                                    paths.add(files[i].getPath());
//                                    sizes.add(MyUtil.fileSizeMsg(files[i]));
                                }
                                break;
                            case 4:
                                if (!end.equals("3gp") && !end.equals("mp4")
                                        && !end.equals("m4a") && !end.equals("mp3")
                                        && !end.equals("mid") && !end.equals("xmf")
                                        && !end.equals("ogg") && !end.equals("wav")
                                        && !end.equals("jpg") && !end.equals("gif")
                                        && !end.equals("png")
                                        && !end.equals("jpeg")
                                        && !end.equals("bmp") && !end.equals("txt")
                                        && !end.equals("text")) {

                                    items.add(files[i].getName());
                                    paths.add(files[i].getPath());
//                                    sizes.add(MyUtil.fileSizeMsg(files[i]));
                                }
                                break;

                            default:
                                break;
                        }

                    }

                }
//                switch (type) {
//                    case 0:
//                        video = new ArrayList<>(items);
//                        break;
//                    case 1:
//                        music = new ArrayList<>(items);
//                        break;
//                    case 2:
//                        image = new ArrayList<>(items);
//                        break;
//                    case 3:
//                        txt = new ArrayList<>(items);
//                    case 4:
//                        other =new ArrayList<>(items);
//                        break;
//                }
            } else {
//                rootPath = "/mnt/usbhost/Storage02/";
//                handler.sendEmptyMessage(0);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 0:
                    if (!rootPath.equals("")) {
                        setvalue();
                    }
                    rootPath = "";
                    break;
                case 1:
                    System.out.println("文件數量：" + items.size());
//                    other_usb_grid.setAdapter(new FileListAdapter(context, items,
//                            paths, sizes, 2));
                    other_usb_grid.setAdapter(new FileListAdapter(context, items, paths));
                    AppTool.toast(context, "检测到文件數量：" + items.size(), 0, Gravity.CENTER, 0, 0);
                    hideProgressDialog();
                    break;
            }


            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        showProgressDialog();

        switch (v.getId()) {
            case R.id.usb_1:
                filestype = 0;
//                if (!video.isEmpty()) {
//                    items = new ArrayList<>(video);
//                    handler.sendEmptyMessage(1);
//                    return;
//                }
                break;
            case R.id.usb_2:
                filestype = 1;
//                if (!music.isEmpty()) {
//                    items = new ArrayList<>(music);
//                    handler.sendEmptyMessage(1);
//                    return;
//                }
                break;
            case R.id.usb_3:
                filestype = 2;
//                if (!image.isEmpty()) {
//                    items = new ArrayList<>(image);
//                    handler.sendEmptyMessage(1);
//                    return;
//                }
                break;
            case R.id.usb_4:
                filestype = 3;
//                if (!txt.isEmpty()) {
//                    items = new ArrayList<>(txt);
//                    handler.sendEmptyMessage(1);
//                    return;
//                }
                break;
            case R.id.usb_5:
                filestype = 4;
//                if (!other.isEmpty()) {
//                    items = new ArrayList<>(other);
//                    handler.sendEmptyMessage(1);
//                    return;
//                }
                break;

            default:
                break;
        }
//        System.out.println("第一次刷新");
        clean();
        setvalue();
    }

    private void clean() {
        // TODO Auto-generated method stub
        if (!items.isEmpty()) {
            items.clear();
        }
        if (!paths.isEmpty()) {
            paths.clear();
        }
//        if (!sizes.isEmpty()) {
//            sizes.clear();
//        }
    }

    ProgressDialog progressDialog;

    public void showProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        }

        progressDialog = ProgressDialog.show(getActivity(), "正在全盘扫描U盘",
                "拼命加载中，请稍后...", true, false);
        progressDialog.show();

    }

    /*
     * 隐藏提示加载
     */
    public void hideProgressDialog() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

}

