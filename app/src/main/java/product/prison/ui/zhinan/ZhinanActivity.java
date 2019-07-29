package product.prison.ui.zhinan;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.Info;
import product.prison.tools.FragmentTools;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.YiyuanAdapter;

public class ZhinanActivity extends BaseActivity implements YiyuanAdapter.OnItemClickListener {
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
            infos = app.getInfos().get(1);

//            Details details = new Details();
//            details.setId(app.getMenus().get(0).getSubs().get(0).getId());
//            details.setName(app.getMenus().get(0).getSubs().get(0).getName());
//            details.setIcon(app.getMenus().get(0).getSubs().get(0).getIcon());
//            infos.getDetails().add(details);

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
//            if (infos.get(position).getId() == 21) {
//                FragmentTools.Replace(getFragmentManager(),
//                        new SatisfiedActivity());
//            } else {
            ZhinanActivity2 zhinanActivity2 = new ZhinanActivity2();
            Bundle bundle = new Bundle();
            bundle.putSerializable("key", infos.getDetails().get(position));
            zhinanActivity2.setArguments(bundle);
            FragmentTools.Replace(getFragmentManager(),
                    zhinanActivity2);
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        toFram(position);
    }

}
