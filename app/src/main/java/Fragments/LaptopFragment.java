package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.malthshop.ActivityShowProduct;
import com.example.malthshop.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import AdapterLaptop.Brand2Adapter;
import AdapterLaptop.BrandAdapter;
import AdapterLaptop.PhotoAdapter;
import AdapterLaptop.ProductAdapter;
import AdapterLaptop.TabLayoutFragmentLaptopAdapter;
import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;
import ModelLaptop.Brand;
import ModelLaptop.Brand2;
import ModelLaptop.Photo;
import URLServerLink.Sever;
import me.relex.circleindicator.CircleIndicator;

public class LaptopFragment extends Fragment {
    public static final String KEY_GET_LAPTOP_PRODUCT = "GET_LAPTOP_PRODUCT";
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> mListPhoto;
    private Timer timer;
    private RecyclerView recyclerViewProductLaptop, recyclerViewThuongHieu, recyclerViewThuonghieu2;
    ArrayList<Product> mListProduct;
    ProductAdapter productAdapter;
    ArrayList<Brand> mListBrand;
    ArrayList<Brand2> mListBrand2;
    private Brand2Adapter brand2Adapter;
    private BrandAdapter brandAdapter;
    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    private TabLayoutFragmentLaptopAdapter tabLayoutFragmentLaptopAdapter;

    public LaptopFragment() {

    }

    public static LaptopFragment newInstance(String param1, String param2) {
        LaptopFragment fragment = new LaptopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_laptop, container, false);
        viewPager = v.findViewById(R.id.viewPager_slide_laptop);
        photoAdapter = new PhotoAdapter(getContext(), getListPhoto());
        autoSlideImage();
        GetDataProduct();
        GetBrand();
        GetBrand2();
        circleIndicator = v.findViewById(R.id.circleIndicator);
        viewPager.setAdapter(photoAdapter);
        mListPhoto = getListPhoto();
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        // khoi tao mang
        mListProduct = new ArrayList<>();
        mListBrand = new ArrayList<>();
        mListBrand2 = new ArrayList<>();
        // khoi tao adapter
        brand2Adapter = new Brand2Adapter(getContext(), mListBrand2);
        brandAdapter = new BrandAdapter(getContext(), mListBrand);

        productAdapter = new ProductAdapter(getContext(), mListProduct, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(getActivity(), ActivityShowProduct.class);
                intent.putExtra(KEY_GET_LAPTOP_PRODUCT, product);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });

        // rv set adapter
        recyclerViewThuongHieu = v.findViewById(R.id.rvThuongHieu);
        recyclerViewProductLaptop = v.findViewById(R.id.rvLaptop);
        recyclerViewProductLaptop.setAdapter(productAdapter);
        recyclerViewThuongHieu.setAdapter(brandAdapter);
        recyclerViewThuonghieu2 = v.findViewById(R.id.rvThuongHieu2);
        recyclerViewThuonghieu2.setAdapter(brand2Adapter);

        //
        mTableLayout = v.findViewById(R.id.tabLayoutFragmentLaptop);
        mViewPager = v.findViewById(R.id.viewPagerLaptopFragment);
        //
        tabLayoutFragmentLaptopAdapter = new TabLayoutFragmentLaptopAdapter(getActivity().getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(tabLayoutFragmentLaptopAdapter);
        mTableLayout.setupWithViewPager(mViewPager);
        // set icon tablayout
        setUpTabIcons();

        return v;

    }
    // set icon tablayout
    private int[] tabIcons = {
            R.drawable.ic_laptop,
            R.drawable.ic_account,
            R.drawable.ic_mouse,
            R.drawable.ic_headphone


    };

    private void setUpTabIcons() {
        for (int i = 0; i < 4; i++) {
            mTableLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }
    //
    private List<Photo> getListPhoto() {
        List<Photo> mList = new ArrayList<>();
        mList.add(new Photo(R.drawable.img_banner_laptop1));
        mList.add(new Photo(R.drawable.img_banner2));
        mList.add(new Photo(R.drawable.img_banner3));
        mList.add(new Photo(R.drawable.img_banner4));

        return mList;

    }

    private void autoSlideImage() {
//        if (mListPhoto != null || mListPhoto.isEmpty() || viewPager == null) {
//            return;
//        }
        // khoi tao time;
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // index cua image
                        int currentImage = viewPager.getCurrentItem();
                        // tong slide
                        int totalImage = mListPhoto.size() - 1;
                        if (currentImage < totalImage) {
                            currentImage++;
                            viewPager.setCurrentItem(currentImage);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });

            }
        }, 500, 3000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;

        }
    }

    // get du lieu san pham
    private void GetDataProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.SourceProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int Id = 0;
                    String proDuctName = "";
                    String brand = "";
                    double price = 0;
                    int status = 0;
                    String description = "";
                    String picture = "";
                    int IdType = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Id = jsonObject.getInt("Id");
                            proDuctName = jsonObject.getString("ProductName");
                            brand = jsonObject.getString("Brand");
                            price = jsonObject.getDouble("Price");
                            status = jsonObject.getInt("Status");
                            description = jsonObject.getString("Description");
                            picture = jsonObject.getString("Picture");
                            IdType = jsonObject.getInt("IdType");
                            mListProduct.add(new Product(Id, proDuctName, brand, price, status, description, picture, IdType));
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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

    // lay du lieu thuong hieu
    private void GetBrand() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.SourceBrand, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {

                    String brandName = "";
                    String brandPicture = "";
                    int idType = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            brandName = jsonObject.getString("BrandName");
                            brandPicture = jsonObject.getString("BrandPicture");
                            idType = jsonObject.getInt("IdType");

                            mListBrand.add(new Brand(brandName, brandPicture, idType));
                            brandAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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

    private void GetBrand2() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.SourceBrand2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {

                    String brandName = "";
                    String brandPicture = "";
                    int idType = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            brandName = jsonObject.getString("BrandName");
                            brandPicture = jsonObject.getString("BrandPicture");
                            idType = jsonObject.getInt("IdType");

                            mListBrand2.add(new Brand2(brandName, brandPicture, idType));
                            brand2Adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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
}