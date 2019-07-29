package product.prison.ui.res;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.GsonBean;
import product.prison.config.What;
import product.prison.nbean.Dir;
import product.prison.nbean.Res;
import product.prison.nbean.ResData;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.tools.AppTool;
import product.prison.tools.LogUtils;
import product.prison.tools.RsType;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.DirListAdapter;
import product.prison.ui.adapter.ResAdapter2;
import product.prison.ui.video.play.ResAudio;
import product.prison.ui.video.play.ResImage;
import product.prison.ui.video.play.ResTxt;
import product.prison.ui.video.play.ResVideo;

/**
 * Created by zhu on 2017/9/26.
 */

public class ResActivity2 extends BaseActivity implements AdapterView.OnItemClickListener, DirListAdapter.OnItemClickListener {
    int pageNo = 1;
    int type;
    int pageSize = 99999;
    int maxpageSize;

    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res2);
        app = (App) getApplication();

        initview();
        setvalue();
    }


    private void setvalue() {
        // TODO Auto-generated method stub
        try {
            dir = (Dir) getIntent().getSerializableExtra("key");
            if (dir != null) {
                dirid1 = dir.getId();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        getdirlist();
                    }
                });

            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    List<Dir> dirs;
    DirListAdapter adapter;
    String filedir;

    private void getdirlist() {
        if (dirid1 > 0) {
            filedir = "&file.dir=" + dirid1;
        }
        String api = "getFileAction_getDir";
        String url = App.requrl(api, filedir);
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                VolleyListenerInterface.mErrorListener) {
            // Volley请求成功时调用的函数
            @Override
            public void onMySuccess(String result) {
                try {
                    System.out.println(result);
                    GsonBean<List<Dir>> json = App.gson.fromJson(result,
                            new TypeToken<GsonBean<List<Dir>>>() {
                            }.getType());
                    if (!json.getData().isEmpty()) {
                        dirs = json.getData();

                        adapter = new DirListAdapter(ResActivity2.this, dirs);
                        left_list.setAdapter(adapter);
                        adapter.setOnItemClickListener(ResActivity2.this);

                        dirid2 = dirs.get(0).getId();
                        getDirD();

                        if (res_line.getVisibility() == View.GONE) {
                            res_line.setVisibility(View.VISIBLE);
                        }
                    } else {
//                                        AppTool.toast(ResActivity2.this, getString(R.string.nodate), 0, Gravity.CENTER, 0, 0);
                        if (res_line.getVisibility() == View.VISIBLE) {
                            res_line.setVisibility(View.GONE);
                        }
                        getDirF();
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

    @Override
    public void onItemClick(View view, int position) {
        try {
            System.out.println("-----xxxxxx" + (view == left_list));
            if (!griddir.isEmpty()) {
                griddir.clear();
            }
            dirid2 = dirs.get(position).getId();
            getDirD();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    //    List<Dir> dirs;
//    DirListAdapter adapter;
    List<ResData> griddir = new ArrayList<ResData>();

    private void getDirD() {
        if (dirid2 > 0) {
            filedir = "&file.dir=" + dirid2;
        }
        String api = "getFileAction_getDir";
        String url = App.requrl(api, filedir);
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
                            System.out.println(result);
                            GsonBean<List<Dir>> json = App.gson.fromJson(result,
                                    new TypeToken<GsonBean<List<Dir>>>() {
                                    }.getType());
                            if (!json.getData().isEmpty()) {
                                try {
                                    for (int i = 0; i < json.getData().size(); i++) {
                                        ResData resData = new ResData();
                                        resData.setId(json.getData().get(i).getId());
                                        resData.setName(json.getData().get(i).getName());
                                        resData.setPath("");
                                        resData.setFileType(0);
                                        griddir.add(resData);
                                    }

                                } catch (Exception e) {
                                    // TODO: handle exception
                                }
                            }
                            getDirF1();
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

    private void getDirF1() {
        if (dirid2 > 0) {
            filedir = "&file.dir=" + dirid1;
        }
        String api = "getFileAction_getSourceFile";
        String url = App.requrl(api,  filedir
                + "&pageNo=" + pageNo + "&pageSize=" + pageSize + "&sub=true");
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new  VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                                VolleyListenerInterface.mErrorListener) {
                            // Volley请求成功时调用的函数
                            @Override
                            public void onMySuccess(String result) {
                                try {
                                    System.out.println("#######" + result);
                                    GsonBean<Res> json = App.gson.fromJson(result,
                                            new TypeToken<GsonBean<Res>>() {
                                            }.getType());
                                    if (json.getData() != null) {
                                        try {
                                            for (int i = 0; i < json.getData().getData().size(); i++) {
                                                ResData resData = new ResData();
                                                resData.setId(json.getData().getData().get(i).getId());
                                                resData.setName(json.getData().getData().get(i).getName());
                                                resData.setPath(json.getData().getData().get(i).getPath());
                                                resData.setFileType(1);
                                                griddir.add(resData);
                                            }

                                            res2_grid.setAdapter(new ResAdapter2(ResActivity2.this,
                                                    griddir));
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                        }
                                    } else {
                                        AppTool.toast(ResActivity2.this, getString(R.string.nodate), 0, Gravity.CENTER, 0, 0);
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

    private void getDirF() {
        if (dirid2 > 0) {
            filedir = "&file.dir=" + dirid2;
        }
        String api = "getFileAction_getSourceFile";
        String url = App.requrl(api,  filedir
                + "&pageNo=" + pageNo + "&pageSize=" + pageSize + "&sub=true");
        System.out.println(url);
        VolleyRequestUtil.RequestGet(this, url, api,
                new   VolleyListenerInterface(this, VolleyListenerInterface.mListener,
                                VolleyListenerInterface.mErrorListener) {
                            // Volley请求成功时调用的函数
                            @Override
                            public void onMySuccess(String result) {
                                try {
                                    System.out.println(result);
                                    LogUtils.d(result);
                                    GsonBean<Res> json = App.gson.fromJson(result,
                                            new TypeToken<GsonBean<Res>>() {
                                            }.getType());
                                    if (json.getData() != null) {
                                        try {
                                            for (int i = 0; i < json.getData().getData().size(); i++) {
                                                ResData resData = new ResData();
                                                resData.setId(json.getData().getData().get(i).getId());
                                                resData.setName(json.getData().getData().get(i).getName());
                                                resData.setPath(json.getData().getData().get(i).getPath());
                                                resData.setFileType(1);
                                                griddir.add(resData);
                                            }

                                            res2_grid.setAdapter(new ResAdapter2(ResActivity2.this,
                                                    griddir));
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                        }
                                    } else {
                                        AppTool.toast(ResActivity2.this, getString(R.string.nodate), 0, Gravity.CENTER, 0, 0);
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

    private Dir dir;
    private int dirid1;
    private int dirid2;
    RecyclerView left_list;
    LinearLayoutManager layoutmanager;
    GridView res2_grid;

    LinearLayout res_line;

    private void initview() {
        left_list = $(R.id.left_list);
        res_line = $(R.id.res_line);
        layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        left_list.setLayoutManager(layoutmanager);
        res2_grid = $(R.id.res2_grid);
        res2_grid.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub

        if (parent == res2_grid) {
            if (griddir.get(position).getFileType() == 0) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("key", griddir.get(position));
                startActivity(new Intent(this, ResActivity3.class)
                        .putExtras(bundle));
            } else {
                try {
                    String path = griddir.get(position).getPath();
                    String temp = path.substring(path.lastIndexOf("."));
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 1);
                    bundle.putSerializable("key", griddir.get(position));
                    System.out.println(path + "*************" + temp);
                    switch (RsType.type.get(temp.toLowerCase())) {
                        case 1:
                            intent.setClass(ResActivity2.this, ResImage.class);
                            break;
                        case 2:
                            intent.setClass(ResActivity2.this, ResAudio.class);
                            break;
                        case 3:
                            intent.setClass(ResActivity2.this, ResVideo.class);
                            break;
                        case 4:
                            intent.setClass(ResActivity2.this, ResTxt.class);
                            break;

                        default:
                            break;
                    }
                    startActivity(intent.putExtras(bundle));
                } catch (Exception e) {
                    // TODO: handle exception
                    AppTool.toast(ResActivity2.this, getString(R.string.play_error), 0, Gravity.CENTER, 0, 0);
                }
            }

        }

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
