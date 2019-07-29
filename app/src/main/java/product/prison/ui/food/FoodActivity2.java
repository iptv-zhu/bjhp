package product.prison.ui.food;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.List;

import product.prison.R;
import product.prison.app.App;
import product.prison.bean.Food;
import product.prison.bean.FoodCat;
import product.prison.bean.GsonBean;
import product.prison.request.VolleyListenerInterface;
import product.prison.request.VolleyRequestUtil;
import product.prison.tools.AppTool;
import product.prison.ui.BaseFram;
import product.prison.ui.adapter.FoodGalleryAdapter;
import product.prison.ui.adapter.ShopCartAdapter;
import product.prison.ui.adapter.ShopOrderAdapter;

/**
 * Created by zhu on 2017/9/26.
 */

public class FoodActivity2 extends BaseFram implements OnItemClickListener, View.OnClickListener {

    private App app;
    Context context;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = getActivity();
        app = (App) context.getApplicationContext();
        view = inflater.inflate(R.layout.activity_food, container, false);


        initview();
        setvalue();
        return view;
    }


    Gallery info_gallery;
    TextView info_title;
    TextView info_web;
    ImageView info_img;
    TextView info_time, info_price, sale_num;

    ImageButton shop_cat, shop_order;

    private void initview() {
        info_gallery = view.findViewById(R.id.info_gallery);
        info_title = view.findViewById(R.id.info_title);
        info_web = view.findViewById(R.id.info_web);
        info_img = view.findViewById(R.id.info_img);
        info_time = view.findViewById(R.id.info_time);
        info_price = view.findViewById(R.id.info_price);
        sale_num = view.findViewById(R.id.sale_num);


        info_gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                current = position;
                setdate();
                galleryAdapter.setSelectItem(position);
                galleryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        info_gallery.setOnItemClickListener(this);

        shop_cat = view.findViewById(R.id.shop_cat);
        catico();
        shop_order = view.findViewById(R.id.shop_order);
        shop_cat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (app.getShopCats().size() > 0) {
                    initcart();

                } else {
                    AppTool.toast(context, getString(R.string.shop_cartnull), 0, Gravity.CENTER, 0, 0);
                }
            }
        });
        shop_order.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                getOrder();

//                startActivity(new Intent(context, Order.class));
            }
        });
    }


    FoodCat foodCat;
    int current;
    List<Food> foods;
    FoodGalleryAdapter galleryAdapter;

    private void setvalue() {
        try {
            foodCat = (FoodCat) getArguments().get("key");
            styleId = foodCat.getId();
            getDish();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    菜品详细信息
//    参数 ：mac
//
//
//    返回 List<Dish> dishs
//    http://192.168.2.250:8100/wisdom_hotel/remote/getDish?mac=6c:5a:b5:df:f7:1b&styleId=2
    int styleId;

    private void getDish() {
        String api="getDish";
        String url = App.requrl(api, "&styleId=" + styleId);

        VolleyRequestUtil.RequestGet(context, url, api,
                new VolleyListenerInterface(context, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
//                            System.out.println(result);
                            GsonBean<List<Food>> json = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<List<Food>>>() {
                                    }.getType());
                            if (!json.getData().isEmpty()) {
                                foods = json.getData();

                                galleryAdapter = new FoodGalleryAdapter(context, foods);
                                info_gallery.setAdapter(galleryAdapter);

//                                if (!info_gallery.isFocused()) {
//                                    info_gallery.requestFocus();
//                                }

                                info_gallery.setGravity(Gravity.CENTER);
                                current = galleryAdapter.getCount() / 2;
                                info_gallery.setSelection(current);
                                setdate();

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onMyError(VolleyError error) {

                    }
                }, false);


    }

    private void setdate() {
        info_title.setText(foods.get(current).getName());
        info_web.setText(foods.get(current).getDiscription());
        Picasso.with(context).load(foods.get(current).getIcon()).into(info_img);
        info_time.setText(getString(R.string.shop_time) + foods.get(current).getSupply_time());
        info_price.setText(getString(R.string.shop_price) + foods.get(current).getPrice());
        sale_num.setText(getString(R.string.shop_sale).replace("0", foods.get(current).getSaleNum() + ""));
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        add();
    }

    AlertDialog add_dialog;
    ImageButton ok, cancle;
    ImageButton jia, jian;
    TextView number;
    int count;

    public void add() {
        // TODO Auto-generated method stub
        count = 1;
        add_dialog = new AlertDialog.Builder(context).create();
        if (add_dialog != null && add_dialog.isShowing()) {
            add_dialog.dismiss();
        } else {
            add_dialog.show();
        }

        add_dialog.setContentView(R.layout.dialog_food);
        jia = add_dialog.findViewById(R.id.jia);
        jian = add_dialog.findViewById(R.id.jian);
        number = add_dialog.findViewById(R.id.number);
        jia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                count = Integer.parseInt(number.getText().toString());
                count++;
                number.setText(count + "");
            }
        });

        jian.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                count = Integer.parseInt(number.getText().toString());
                if (count > 1) {
                    count--;
                } else {
                    count = App.NUMMAX;
                }
                number.setText(count + "");

            }
        });

        ok = add_dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                addcat(count);


            }


        });
        cancle = add_dialog.findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                add_dialog.dismiss();
            }
        });

    }

    private void addcat(int c) {
        try {
            if (app.getShopCats().isEmpty()) {
                addnew(c);
            } else {
                int re = -1;
                for (int i = 0; i < app.getShopCats().size(); i++) {
                    if (foods.get(current).getId() == app.getShopCats().get(i).getId())
                        re = i;
                }
                if (re == -1) {
                    addnew(c);
                } else {
                    int tmp = app.getShopCats().get(re).getCount() + c;
                    app.getShopCats().get(re).setCount(tmp);
                }
            }
            AppTool.toast(context, getString(R.string.shop_add_cart_scuess), 0, Gravity.CENTER, 0, 0);
            add_dialog.dismiss();
            catico();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addnew(int c) {
        Food cat = foods.get(current);
        cat.setCount(c);
        app.getShopCats().add(cat);
    }

    public void catico() {
        try {
            if (!app.getShopCats().isEmpty()) {
                shop_cat.setBackgroundResource(R.drawable.shop_cat_h);
            } else {
                shop_cat.setBackgroundResource(R.drawable.shop_cat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void settotal() {
        int allprice = 0;
        for (int i = 0; i < app.getShopCats().size(); i++) {
            allprice += app.getShopCats().get(i)
                    .getCount()
                    * app.getShopCats().get(i)
                    .getPrice();
        }
        shop_allprice.setText(getString(R.string.shop_allprice) + allprice);
    }

    private PopupWindow catpopupWindow;
    private View cat;
    private ListView cart_list;
    private TextView shop_cart_clean, shop_allprice;
    ShopCartAdapter shopCartAdapter;
    TextView shop_cart_ok;

    public void initcart() {
        // TODO Auto-generated method stub
        try {


            cat = LayoutInflater.from(context).inflate(
                    R.layout.shop_cart, null);

            if (catpopupWindow != null) {
                catpopupWindow.dismiss();
            }
            catpopupWindow = new PopupWindow(cat, 643, 493);

            ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
            catpopupWindow.setBackgroundDrawable(dw);
            catpopupWindow.setFocusable(true);
            catpopupWindow.setOutsideTouchable(true);
            catpopupWindow.update();

            cart_list = cat.findViewById(R.id.cart_list);

            shopCartAdapter = new ShopCartAdapter(context,
                    app.getShopCats());

            cart_list.setAdapter(shopCartAdapter);
            shopCartAdapter.setListview(cart_list);

            shop_cart_clean = cat
                    .findViewById(R.id.shop_cart_clean);
            shop_cart_clean.setOnClickListener(this);

            shop_cart_ok = cat.findViewById(R.id.shop_cart_ok);
            shop_cart_ok.setOnClickListener(this);
            shop_allprice = cat
                    .findViewById(R.id.shop_allprice);
            settotal();
            catpopupWindow.showAtLocation(cat, Gravity.LEFT | Gravity.TOP, 19, 101);//*******************
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        if (view == shop_cart_clean) {
            app.getShopCats().clear();
            shopCartAdapter.notifyDataSetChanged();
            settotal();
            catico();
        } else if (view == shop_cart_ok) {
            try {

                if (app.getShopCats().isEmpty()) {
                    AppTool.toast(context, getString(R.string.shop_cartnull), 0, Gravity.CENTER, 0, 0);

                } else {
                    for (int i = 0; i < app.getShopCats().size(); i++) {
                        app.getOrDishs().add(
                                app.getShopCats().get(i));
                    }

                    app.getShopCats().clear();
                    shopCartAdapter.notifyDataSetChanged();
                    catico();
                    settotal();

//                    initorder();
                    orderDish();
                }

            } catch (Exception e) {
                // TODO: handle exception

            }
        }
    }

    TextView shop_order_clean, shop_allprices;
    ListView order_list;
    ImageButton shop_order_ok;
    ShopOrderAdapter shopOrderAdapter;
    PopupWindow orderpopupWindow;
    View order;

    public void initorder() {
        // TODO Auto-generated method stub
        try {

            order = LayoutInflater.from(context).inflate(
                    R.layout.shop_order, null);

            if (orderpopupWindow != null) {
                orderpopupWindow.dismiss();
            }
            orderpopupWindow = new PopupWindow(order, 643, 493);

            ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
            orderpopupWindow.setBackgroundDrawable(dw);
            orderpopupWindow.setFocusable(true);
            orderpopupWindow.setOutsideTouchable(true);
            orderpopupWindow.update();

            order_list = order.findViewById(R.id.order_list);

            shopOrderAdapter = new ShopOrderAdapter(context,
                    app.getOrDishs());
            order_list.setAdapter(shopOrderAdapter);

            shop_order_clean = order
                    .findViewById(R.id.shop_order_clean);
            shop_order_clean.setOnClickListener(this);

            shop_order_ok = order
                    .findViewById(R.id.shop_order_ok);
            shop_order_ok.setOnClickListener(this);

            shop_allprices = order
                    .findViewById(R.id.shop_allprices);
            int allprice = 0;
            for (int i = 0; i < app.getOrDishs().size(); i++) {
                allprice += app.getOrDishs().get(i).getCount() * app.getOrDishs().get(i)
                        .getPrice();
            }
            shop_allprice.setText(getString(R.string.shop_allprice) + allprice);

            orderpopupWindow.showAtLocation(order, Gravity.LEFT | Gravity.TOP, 18, 110);//**************************

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
//    菜品下单
//
//    参数：mac
//    order 菜品id,数量; 菜品id,数量; 菜品id,数量; 菜品id,数量  （点菜格式）
//
//    http://192.168.2.250:8105/wisdom_hotel/remote/orderDish?mac=6c:5a:b5:df:f7:1b&order=2,3;3,1


    private void orderDish() {
        String order = "";
        for (int i = 0; i < app.getOrDishs().size(); i++) {
            order += app.getOrDishs().get(i).getId() + ","
                    + app.getOrDishs().get(i).getCount() + ";";
        }
//        System.out.println(IPConfig.getipaddr(activity) + "orderDish?mac=" + Ini.Mac() + "&order=" + order);
        String api="orderDish";
        String url = App.requrl(api,  "&order=" + order);
        VolleyRequestUtil.RequestGet(context, url, api,
                new VolleyListenerInterface(context, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
//                            System.out.println(result);
                            GsonBean<String> json = App.gson.fromJson(
                                    result, new TypeToken<GsonBean<String>>() {
                                    }.getType());
                            if (json.getCode().equals("200")) {
                                AppTool.toast(context, getString(R.string.shop_orderscu), 0, Gravity.CENTER, 0, 0);
                                catpopupWindow.dismiss();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onMyError(VolleyError error) {

                    }
                }, false);


    }

    //    查询菜品下单
//
//    参数：mac
//
//    返回 List<DishOrder> orders
//    http://192.168.2.250:8105/wisdom_hotel/remote/getOrder?mac=6c:5a:b5:df:f7:1b
//            -------------------------------------------------------------------
    private void getOrder() {
        String api="getOrder";
        String url = App.requrl(api,  "&order=" + order);
        VolleyRequestUtil.RequestGet(context, url, api,
                new VolleyListenerInterface(context, VolleyListenerInterface.mListener,
                        VolleyListenerInterface.mErrorListener) {
                    // Volley请求成功时调用的函数
                    @Override
                    public void onMySuccess(String result) {
                        try {
//                            System.out.println(result);
//                            GsonBean<String> json = Ini.gson.fromJson(
//                                    result, new TypeToken<GsonBean<String>>() {
//                                    }.getType());
//                            if (!json.getCode().equals("200")) {
//                                    AppTool.toast(activity, getString(R.string.shop_orderscu), 0, Gravity.CENTER, 0, 0);
//                                    catpopupWindow.dismiss();
//                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onMyError(VolleyError error) {

                    }
                }, false);


    }
}
