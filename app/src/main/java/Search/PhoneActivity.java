package Search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
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

import AdapterPhone.RecyclerViewPhoneAdapter;
import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;

public class PhoneActivity extends AppCompatActivity {
    public static final String KEY_GET_PHONE = "GET_PHONE";
    private RecyclerView rvPhone;
    private String urlGetDataSell = "https://unemphatic-tailors.000webhostapp.com/Phone/getdataSell.php";
    private RecyclerViewPhoneAdapter phoneAdapter;
    private List<Product> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initView();
        list=new ArrayList<>();
        phoneAdapter = new RecyclerViewPhoneAdapter(this, list, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(getApplicationContext(), ActivityShowProduct.class);
                intent.putExtra(KEY_GET_PHONE, product);
                startActivity(intent);
                PhoneActivity.this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });

        rvPhone.setAdapter(phoneAdapter);
        getDataSell(urlGetDataSell);
    }

    private void initView() {
        rvPhone = findViewById(R.id.rv_phone);
    }
    private void getDataSell(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                    phoneAdapter.notifyDataSetChanged();
                    phoneAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PhoneActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}