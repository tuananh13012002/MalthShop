package SubFragmentLaptop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.malthshop.ActivityShowProduct;
import com.example.malthshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AdapterLaptop.ProductAdapter;
import Fragments.LaptopFragment;
import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;
import URLServerLink.Sever;


public class LaptopTablayoutFragment extends Fragment {
    ArrayList<Product> mListProduct;
    private RecyclerView rv;
    private ProductAdapter productAdapter;

    public LaptopTablayoutFragment() {

    }

    public static LaptopTablayoutFragment newInstance(String param1, String param2) {
        LaptopTablayoutFragment fragment = new LaptopTablayoutFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_laptop_tablayout,container,false);
        mListProduct = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(), mListProduct, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(getActivity(), ActivityShowProduct.class);
                intent.putExtra(LaptopFragment.KEY_GET_LAPTOP_PRODUCT, product);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });

        rv = v.findViewById(R.id.rvLaptopTablayout);
        rv.setAdapter(productAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        rv.setLayoutManager(gridLayoutManager);
        GetDataProduct();
        return v;
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
}