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

public class ElectronnicActivity extends AppCompatActivity {

    public static final String KEY_GET_ELECTRONIC_COMPONENT = "GET_ELECTRONIC_COMPONENT";
    private RecyclerView rvElectronic;
    private List<Product> list;
    private RecyclerViewPhoneAdapter adapter;
    private String urlGetDataElectronicComponents = "https://unemphatic-tailors.000webhostapp.com/Phone/getDataElectronicComponents.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronnic);
        initView();
        list=new ArrayList<>();
        adapter=new RecyclerViewPhoneAdapter(this, list, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(getApplicationContext(), ActivityShowProduct.class);
                intent.putExtra(KEY_GET_ELECTRONIC_COMPONENT, product);
                startActivity(intent);
                ElectronnicActivity.this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });
        rvElectronic.setAdapter(adapter);
        getDataElectronic(urlGetDataElectronicComponents);
    }

    private void initView() {
        rvElectronic = findViewById(R.id.rv_electronic);
    }
    private void getDataElectronic(String url) {
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
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ElectronnicActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}