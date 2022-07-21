package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.malthshop.ActivityShowProduct;
import com.example.malthshop.R;
import com.example.malthshop.SlideShow.Photo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import AdapterPhone.RecyclerViewBrandAdapter;
import AdapterPhone.RecyclerViewElectronicAdapter;
import AdapterPhone.RecyclerViewPhoneAdapter;
import AdapterPhone.RecyclerViewPhoneHighlightsAdapter;
import AutoSlideInPhone.AutoSliderViewPagerAdapter;
import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;
import ModelPhone.Brand;
import me.relex.circleindicator.CircleIndicator;

public class PhoneFragment extends Fragment {


    public static final String KEY_GET_HIGHLIGHT_PHONE = "GET_HIGHTLIGHT_PRODUCT";
    public static final String KEY_GET_PHONE = "GET_PHONE";
    public static final String KEY_GET_ELECTRONIC_COMPONENT = "GET_ELECTRONIC_COMPONENT";

    private ViewPager viewPager;
    private CircleIndicator circle;
    private List<Photo> photoList;
    private AutoSliderViewPagerAdapter adapter;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager.getCurrentItem() == photoList.size() - 1) {
                viewPager.setCurrentItem(0);
            } else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        }
    };

    private RecyclerView rvSanPhamNoiBat;
    private RecyclerView rvThuongHieuNoiBat;
    private RecyclerView rvLinhKien;

    private List<Product> list;
    private List<Brand> brandList;
    private List<Product> electronicList;

    private RecyclerViewPhoneHighlightsAdapter spNoiBatAdapter;
    private RecyclerViewBrandAdapter brandAdapter;
    private RecyclerViewElectronicAdapter electronicAdapter;
    private RecyclerViewPhoneAdapter phoneAdapter;
    private String urlGetDataSell = "https://unemphatic-tailors.000webhostapp.com/Phone/getdataSell.php";
    private String urlGetDataBrand = "https://unemphatic-tailors.000webhostapp.com/Phone/getDataBrand.php";
    private String urlGetDataElectronicComponents = "https://unemphatic-tailors.000webhostapp.com/Phone/getDataElectronicComponents.php";
    private RecyclerView rvSP;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone, container, false);

        mapping(view);
        showProduct();

        return view;
    }
    private void showProduct(){
        list = new ArrayList<>();
        brandList = new ArrayList<>();
        electronicList = new ArrayList<>();
        // rv Sản phẩm nổi bật
        spNoiBatAdapter = new RecyclerViewPhoneHighlightsAdapter(getContext(), list, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(getActivity(), ActivityShowProduct.class);
                intent.putExtra(KEY_GET_HIGHLIGHT_PHONE, product);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });
        rvSanPhamNoiBat.setAdapter(spNoiBatAdapter);
        // rv Thương hiệu nổi bật
        brandAdapter = new RecyclerViewBrandAdapter(getContext(), brandList);
        rvThuongHieuNoiBat.setAdapter(brandAdapter);
        // rv Linh kiện
        electronicAdapter = new RecyclerViewElectronicAdapter(getContext(), electronicList, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(getActivity(), ActivityShowProduct.class);
                intent.putExtra(KEY_GET_ELECTRONIC_COMPONENT, product);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);

            }
        });
        rvLinhKien.setAdapter(electronicAdapter);
        getDataElectronic(urlGetDataElectronicComponents);

        // rv Phone
        phoneAdapter = new RecyclerViewPhoneAdapter(getContext(), list, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(getActivity(), ActivityShowProduct.class);
                intent.putExtra(KEY_GET_PHONE, product);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });

        rvSP.setAdapter(phoneAdapter);
        getDataSell(urlGetDataSell);
        getDataBrand(urlGetDataBrand);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // slide show
        mapping(view);
        slideShow();
    }
    private void slideShow(){
        photoList = getListPhoto();
        adapter = new AutoSliderViewPagerAdapter(photoList);
        viewPager.setAdapter(adapter);
        circle.setViewPager(viewPager);
        handler.postDelayed(runnable, 3000);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getDataElectronic(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        electronicList.add(new Product(
                                object.getInt("Id"),
                                object.getString("ProductName"),
                                object.getString("Brand"),
                                object.getDouble("Price"),
                                object.getInt("Status"),
                                object.getString("Description"),
                                object.getString("Picture"),
                                object.getInt("IdType")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    electronicAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDataBrand(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        brandList.add(new Brand(
                                object.getString("BrandName"),
                                object.getString("BrandPicture"),
                                object.getInt("IdType")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    brandAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDataSell(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        list.add(new Product(
                                object.getInt("Id"),
                                object.getString("ProductName"),
                                object.getString("Brand"),
                                object.getDouble("Price"),
                                object.getInt("Status"),
                                object.getString("Description"),
                                object.getString("Picture"),
                                object.getInt("IdType")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    spNoiBatAdapter.notifyDataSetChanged();
                    phoneAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();

        list.add(new Photo(" ", "", "", "", R.drawable.auto_one));
        list.add(new Photo(" ", "", "", "", R.drawable.auto_two));
        list.add(new Photo(" ", "", "", "", R.drawable.auto_three));
        list.add(new Photo(" ", "", "", "", R.drawable.auto_for));
        list.add(new Photo(" ", "", "", "", R.drawable.auto_five));
        list.add(new Photo(" ", "", "", "", R.drawable.auto_six));
        return list;
    }


    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }
    private void mapping(View view){
        rvSanPhamNoiBat = view.findViewById(R.id.rvSanPhamNoiBat);
        rvThuongHieuNoiBat = view.findViewById(R.id.rvThuongHieuNoiBat);
        rvLinhKien = view.findViewById(R.id.rvLinhKien);
        rvSP = view.findViewById(R.id.rvSP);

        // slide show
        viewPager = view.findViewById(R.id.view_pager);
        circle = view.findViewById(R.id.circle);
    }
}