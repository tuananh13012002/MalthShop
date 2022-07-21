package Search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
import java.util.List;

import AdapterLaptop.ProductAdapter;
import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;
import URLServerLink.Sever;

public class LaptopActivity extends AppCompatActivity {

    public static final String KEY_GET_LAPTOP_PRODUCT = "GET_LAPTOP_PRODUCT";
    private RecyclerView rvLaptop;
    private ProductAdapter adapter;
    private List<Product> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        initView();
        list=new ArrayList<>();
        adapter=new ProductAdapter(LaptopActivity.this, list, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(getApplicationContext(), ActivityShowProduct.class);
                intent.putExtra(KEY_GET_LAPTOP_PRODUCT, product);
                startActivity(intent);
                LaptopActivity.this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });
        rvLaptop.setAdapter(adapter);
        GetDataProduct();
    }

    private void initView() {
        rvLaptop = findViewById(R.id.rv_laptop);
    }
    private void GetDataProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                            list.add(new Product(Id, proDuctName, brand, price, status, description, picture, IdType));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LaptopActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}