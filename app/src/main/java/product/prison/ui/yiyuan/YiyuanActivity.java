package product.prison.ui.yiyuan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.Details;
import product.prison.bean.Info;
import product.prison.tools.FragmentTools;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.InfoAdapter;
import product.prison.ui.adapter.YiyuanAdapter;
import product.prison.ui.hotel.InfoActivity2;
import product.prison.ui.hotel.SatisfiedActivity;

public class YiyuanActivity extends BaseActivity implements YiyuanAdapter.OnItemClickListener {
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


    YiyuanAdapter adapter;
    Info infos;


    private void setvalue() {
        try {
            infos = app.getInfos().get(0);

            Details details = new Details();
            details.setId(app.getSubses().get(0).getId());
            details.setName(app.getSubses().get(0).getName());
            details.setIcon(app.getSubses().get(0).getIcon());
            infos.getDetails().add(details);

            if (!infos.getDetails().isEmpty()) {

                adapter = new YiyuanAdapter(this, infos.getDetails());
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
            if (infos.getDetails().get(position).getId() == 21) {
                FragmentTools.Replace(getFragmentManager(),
                        new SatisfiedActivity());
            } else {
                YiyuanActivity2 yiyuanActivity2 = new YiyuanActivity2();
                Bundle bundle = new Bundle();
                bundle.putSerializable("key", infos.getDetails().get(position));
            yiyuanActivity2.setArguments(bundle);
                FragmentTools.Replace(getFragmentManager(),
                        yiyuanActivity2);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        toFram(position);
    }

}
