package product.prison.ui.zhinan;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.Info;
import product.prison.bean.IntroduceData;
import product.prison.bean.LiveList;
import product.prison.tools.FragmentTools;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.IntroduceAdapter;
import product.prison.ui.adapter.YiyuanAdapter;

public class IntroduceActivity extends BaseActivity implements IntroduceAdapter.OnItemClickListener {
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


    IntroduceAdapter adapter;
    List<IntroduceData> infos;


    private void setvalue() {
        try {
            infos = (List<IntroduceData>) getIntent().getSerializableExtra("key");


            if (!infos.isEmpty()) {

                adapter = new IntroduceAdapter(this, infos);
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
            IntroduceActivity2 zhinanActivity2 = new IntroduceActivity2();
            Bundle bundle = new Bundle();
            bundle.putSerializable("key", infos.get(position));
            zhinanActivity2.setArguments(bundle);
            FragmentTools.Replace(getFragmentManager(),
                    zhinanActivity2);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        toFram(position);
    }

}
