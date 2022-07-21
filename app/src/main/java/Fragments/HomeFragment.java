package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.malthshop.ActivityShowProduct;
import com.example.malthshop.R;
import com.example.malthshop.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import AdapterHome.FeaturedProductAdapter;
import InterfaceForHomeManager.OnEventShowProduct;
import InterfaceForHomeManager.OnEventShowProductListener;
import ModelHome.Option;
import AdapterHome.OptionAdapter;
import ModelHome.Brand;
import AdapterHome.BrandAdapter;
import AdapterHome.ComponentAdapter;
import ModelHome.Product;
import Slider.Image;
import Slider.ImageAdapter;
import AdapterHome.SProductAdapter;
import ModelHome.SpecialProduct;
import Slider.SubSliderItem;
import Slider.SubSliderItemAdapter;
import URLServerLink.ServerLink;

public class HomeFragment extends Fragment {
    public static final String KEY_GET_SPECIAL_PRODUCT = "GET_SPECIAL_PRODUCT";
    public static final String KEY_GET_COMPONENT_PRODUCT = "GET_COMPONENT_PRODUCT";
    public static final String KEY_GET_FEATURED_PRODUCT = "GET_FEATURED_PRODUCT";
    public static final String KEY_GET_FEATURED_COMPONENT= "GET_COMPONENT_FEATURED";
    private FragmentHomeBinding binding;
    private List<Image> mListImage;
    private List<Option> mListOption;
    private List<SpecialProduct> mListSProduct;
    private List<Brand> mListBrand;
    private List<Product> mListProduct;
    private List<Product> mListComponent;
    private FeaturedProductAdapter mFeaturedAdapter;
    private ImageAdapter mImgAdapter;
    private BrandAdapter brandAdapter;
    private SProductAdapter sProductAdapter;
    private ComponentAdapter mComponentAdapter;
    private List<SubSliderItem> items;
    private Timer mTimer;
    private ViewPager viewPagerSlide;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewPagerSlide = binding.viewpagerSlide;
        mListProduct = new ArrayList<>();
        // Auto slide
        mListImage = getListImg();
        mImgAdapter = new ImageAdapter(getContext(), mListImage);
        viewPagerSlide.setAdapter(mImgAdapter);
        binding.circle.setViewPager(binding.viewpagerSlide);
        mImgAdapter.registerDataSetObserver(binding.circle.getDataSetObserver());
        autoSlide();
        // End auto slide

        // Option main
        mListOption = new ArrayList<>();
        getListOption();
        binding.rvOption.setLayoutManager(new GridLayoutManager(getContext(),2, GridLayoutManager.HORIZONTAL, false));
        binding.rvOption.setAdapter(new OptionAdapter(getContext(), mListOption));
        imgUnderOption();

        // Special product
        mListSProduct = new ArrayList<>();

        sProductAdapter= new SProductAdapter(getContext(), mListSProduct, new OnEventShowProductListener() {
            @Override
            public void onClickShowSProductListener(SpecialProduct specialProduct) {
                Intent intent = new Intent(getContext(), ActivityShowProduct.class);
                intent.putExtra(KEY_GET_SPECIAL_PRODUCT, specialProduct);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });
        binding.rvSpecialProduct.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvSpecialProduct.setAdapter(sProductAdapter);
        getSpecialProduct();

        // Brand
        mListBrand = new ArrayList<>();
        brandAdapter = new BrandAdapter(getContext(), mListBrand);
        binding.rvBrandProduct.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvBrandProduct.setAdapter(brandAdapter);
        getBrand();

        // ComponentElectric
        mListComponent = new ArrayList<>();
        mComponentAdapter = new ComponentAdapter(getContext(), mListComponent, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(getContext(), ActivityShowProduct.class);
                intent.putExtra(KEY_GET_COMPONENT_PRODUCT, product);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });
        binding.rvComponentProduct.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvComponentProduct.setAdapter(mComponentAdapter);
        getElectricComponent();
        // Sub slide
        items = getListSubSlide();
        binding.viewpagerSubSlide.setAdapter(new SubSliderItemAdapter(getContext(),binding.viewpagerSubSlide, items));
        binding.viewpagerSubSlide.setClipToPadding(false);
        binding.viewpagerSubSlide.setClipChildren(false);
        binding.viewpagerSubSlide.setOffscreenPageLimit(3);
        binding.viewpagerSubSlide.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull @NotNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);

            }
        });
        binding.viewpagerSubSlide.setPageTransformer(compositePageTransformer);
        binding.viewpagerSubSlide.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,3000);
            }
        });

        // Featured Product
        mFeaturedAdapter = new FeaturedProductAdapter(getContext(), mListProduct, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(getContext(), ActivityShowProduct.class);
                intent.putExtra(KEY_GET_FEATURED_PRODUCT, product);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });
        binding.rvFeaturedProduct.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.rvFeaturedProduct.setAdapter(mFeaturedAdapter);
        getFeatureProduct();
        return root;
    }
    private Handler sliderHandler = new Handler();
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            binding.viewpagerSubSlide.setCurrentItem(binding.viewpagerSlide.getCurrentItem() + 1);
        }
    };

    // Auto Slide
    private List<Image> getListImg(){
        List<Image> images = new ArrayList<>();
        images.add(new Image("https://intphcm.com/data/upload/banner-la-gi.jpg"));
        images.add(new Image("https://hoanghamobile.com/tin-tuc/wp-content/uploads/2017/11/T12-POSM-Banner-Event-SS-Fb-Ads.png"));
        images.add(new Image("https://thienthoi.com.vn/wp-content/uploads/2020/09/nhung-luu-y-khi-thiet-ke-banner-cho-website-1.jpg"));
        images.add(new Image("https://bizweb.dktcdn.net/100/407/435/themes/793944/assets/banner_2.jpg?1609922774943"));
        images.add(new Image("https://i.ytimg.com/vi/vMVwdSp489E/maxresdefault.jpg"));
        images.add(new Image("https://static.unica.vn/media/imagesck/1602820670_banner-quang-cao-dep-3.jpg?v=16028206703"));

        return images;
    }

    private void autoSlide(){
        if (mImgAdapter == null || mListImage == null || mListImage.isEmpty()){
            return;
        }
        if(mTimer == null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                          int itemShowing = viewPagerSlide.getCurrentItem();
                        int totalItem = mListImage.size() - 1;
                        if(itemShowing < totalItem){
                            itemShowing++;
                            viewPagerSlide.setCurrentItem(itemShowing);
                        }else{
                            viewPagerSlide.setCurrentItem(0);
                        }
                    }
                });
            }
        },1000,3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }
    // End

    // Option
    private void getListOption(){
        mListOption = new ArrayList<>();
        mListOption.add(new Option(R.drawable.icon_flash, "Khung giờ săn sale"));
        mListOption.add(new Option(R.drawable.icon_text, "Hạng hiệu -50%"));
        mListOption.add(new Option(R.drawable.icon_device, "Malth Market - Siêu thị điện tử"));
        mListOption.add(new Option(R.drawable.icon_exchange, "Hoàn trả xu"));
        mListOption.add(new Option(R.drawable.icon_phone_rang, "Nạp thẻ, thanh toán hóa đơn"));
        mListOption.add(new Option(R.drawable.icon_free, "Deal gần bạn - Chỉ từ 0Đ"));
        mListOption.add(new Option(R.drawable.icon_earth, "Hạng quốc tế"));
        mListOption.add(new Option(R.drawable.icon_timeline, "Nghiện săn sale"));
        mListOption.add(new Option(R.drawable.icon_bonus, "Giải thưởng Malth"));
        mListOption.add(new Option(R.drawable.icon_bag, "Matlh Mall"));
        mListOption.add(new Option(R.drawable.icon_coins, "Săn xu mỗi ngày"));
        mListOption.add(new Option(R.drawable.icon_sale, "Mã giảm giá"));
        mListOption.add(new Option(R.drawable.hand_shake, "Ưu đãi đối tác"));

    }
    // End option

    private void imgUnderOption(){
        Glide.with(getContext()).load("https://www.matichon.co.th/wp-content/uploads/2019/05/S10-x-Lisa-Blackpink-1.jpg").into(binding.imgStartBanner);
        Glide.with(getContext()).load("https://cdn.tgdd.vn/Files/2019/09/13/1197906/tim-hieu-cong-nghe-den-ban-phim-tren-laptop-.jpg").into(binding.imgCenterBanner);
        Glide.with(getContext()).load("https://bloganchoi.com/wp-content/uploads/2020/02/jennie-red2.jpg").into(binding.imgEndBanner);
    }

    //Special Product
    private void getSpecialProduct(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerLink.URL_SPECIAL_PRODUCT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        mListSProduct.add(new SpecialProduct(
                                jsonObject.getInt("Id"),
                                jsonObject.getString("Picture"),
                                jsonObject.getDouble("PercentSale"),
                                jsonObject.getDouble("Price"),
                                jsonObject.getInt("Status"),
                                jsonObject.getString("ProductName"),
                                jsonObject.getString("Description"),
                                jsonObject.getInt("Type")
                                ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                sProductAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ErrorGetSpecialProduct", error.toString());
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void getBrand(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerLink.URL_BRAND_PRODUCT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        mListBrand.add(new Brand(jsonObject.getString("BrandPicture"),
                                jsonObject.getString("BrandName")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ErrorGetBrand", error.toString());
            }
        });
        brandAdapter.notifyDataSetChanged();
        queue.add(jsonArrayRequest);
    }

    private void getElectricComponent(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerLink.URL_GET_COMPONENT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        mListComponent.add(new Product(
                                jsonObject.getInt("IdProduct"),
                                jsonObject.getString("ProductName"),
                                jsonObject.getString("Brand"),
                                jsonObject.getDouble("Price"),
                                jsonObject.getInt("Status"),
                                jsonObject.getString("Description"),
                                jsonObject.getString("Picture"),
                                jsonObject.getInt("IdType")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mComponentAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ErrorGetElectricComponent", error.toString());
            }
        });
        queue.add(jsonArrayRequest);
    }

    private List<SubSliderItem> getListSubSlide(){
        List<SubSliderItem> items = new ArrayList<>();
        items.add(new SubSliderItem("https://sachhaynendoc.net/wp-content/uploads/2019/10/Thi%E1%BA%BFt-b%E1%BB%8B-c%C3%B4ng-ngh%E1%BB%87.jpg"));
        items.add(new SubSliderItem("https://cdn.tgdd.vn/Files/2017/07/07/1000678/banner_1200x628-800-resize.jpg"));
        items.add(new SubSliderItem("https://www.xtmobile.vn/vnt_upload/news/04_2019/20/tuan-le-vang-ron-rang-khuyen-mai-57-quang-trung-xtmobile.jpg"));
        items.add(new SubSliderItem("https://www.xtmobile.vn/vnt_upload/news/04_2018/22/chuong-trinh-khuyen-mung-le-lon-giam-gia-lon.jpg"));
        items.add(new SubSliderItem("https://cdn.tgdd.vn/Files/2019/12/04/1224717/samsung_sale_800x450.jpg"));
        return items;
    }

    private void getFeatureProduct(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerLink.URL_GET_PRODUCT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        mListProduct.add(new Product(
                                jsonObject.getInt("IdProduct"),
                                jsonObject.getString("ProductName"),
                                jsonObject.getString("Brand"),
                                jsonObject.getDouble("Price"),
                                jsonObject.getInt("Status"),
                                jsonObject.getString("Description"),
                                jsonObject.getString("Picture"),
                                jsonObject.getInt("IdType")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mFeaturedAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ErrorGetFeaturedProduct", error.toString());
            }
        });
        queue.add(jsonArrayRequest);
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }
    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }
}