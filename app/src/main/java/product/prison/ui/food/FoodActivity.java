package product.prison.ui.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.FoodCat;
import product.prison.tools.FragmentTools;
import product.prison.ui.BaseActivity;
import product.prison.ui.adapter.FoodAdapter;
import product.prison.ui.app.UsefulActivity;

/**
 * Created by zhu on 2017/9/26.
 */

public class FoodActivity extends BaseActivity implements FoodAdapter.OnItemClickListener {
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


    FoodAdapter adapter;
    List<FoodCat> foodCats;

    private void setvalue() {
        foodCats = app.getFoodCats();

        if (!foodCats.isEmpty()) {

            adapter = new FoodAdapter(this, foodCats);
            left_list.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            toFram(0);
        }

    }


    private void toFram(int position) {
        FoodActivity2 foodActivity2 = new FoodActivity2();
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", foodCats.get(position));
        foodActivity2.setArguments(bundle);

        FragmentTools.Replace(getFragmentManager(),
                foodActivity2);
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
