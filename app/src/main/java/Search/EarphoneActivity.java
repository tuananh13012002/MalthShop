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

import AdapterLaptop.EarPhoneAdapter;
import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;
import URLServerLink.Sever;

public class EarphoneActivity extends AppCompatActivity {
    public static final String KEY_GET_EARPHONE_LAPTOP = "GET_EARPHONE_LAPTOP";
    private RecyclerView rv_earphone;
    private List<Product> list;
    private EarPhoneAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earphone);
        initView();
        list=new ArrayList<>();
        adapter=new EarPhoneAdapter(this, list, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(getApplicationContext(), ActivityShowProduct.class);
                intent.putExtra(KEY_GET_EARPHONE_LAPTOP, product);
                startActivity(intent);
                EarphoneActivity.this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });
        rv_earphone.setAdapter(adapter);
        GetDataEarPhone();

    }

    private void initView() {
        rv_earphone = findViewById(R.id.rv_earphone);
    }
    private void GetDataEarPhone() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.SourceEarPhone, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int Id = 0;
                    String Name = "";
                    double price = 0;
                    int status = 0;
                    String description = "";
                    String image = "";

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

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EarphoneActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}