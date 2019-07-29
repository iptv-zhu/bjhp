package product.prison.ui.hotel;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.Info;
import product.prison.tools.FragmentTools;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.InfoAdapter;

/**
 * Created by zhu on 2017/9/26.
 */

public class InfoActivity extends BaseActivity implements InfoAdapter.OnItemClickListener {
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        app = (App) getApplication();
        initview();
        setvalue();
    }


    RecyclerView left_list;
    LinearLayoutManager layoutmanager;


    private void initview() {
        left_list = $(R.id.left_list);

        layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        left_list.setLayoutManager(layoutmanager);

    }


    InfoAdapter adapter;
    List<Info> infos;

    private void setvalue() {
        try {
            infos = app.getInfos();

            Info info = new Info();

//            info.setId(app.getMenus().get(0).getSubs().get(0).getId());
//            info.setName(app.getMenus().get(0).getSubs().get(0).getName());
//            info.setPath(app.getMenus().get(0).getSubs().get(0).getIcon());

            infos.add(info);

            if (!infos.isEmpty()) {

                adapter = new InfoAdapter(this, infos);
                left_list.setAdapter(adapter);
                adapter.setOnItemClickListener(this);
                toFram(0);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void toFram(int position) {

        try {
            if (infos.get(position).getId() == 21) {
                FragmentTools.Replace(getFragmentManager(),
                        new SatisfiedActivity());
            } else {
                InfoActivity2 infoActivity2 = new InfoActivity2();
                Bundle bundle = new Bundle();
                bundle.putSerializable("key", infos.get(position));
                infoActivity2.setArguments(bundle);
                FragmentTools.Replace(getFragmentManager(),
                        infoActivity2);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        toFram(position);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_DPAD_LEFT||keyCode==KeyEvent.KEYCODE_DPAD_RIGHT){
            left_list.setFocusable(false);
        }else{
            left_list.setFocusable(true);
        }
        return super.onKeyDown(keyCode, event);
    }
}
