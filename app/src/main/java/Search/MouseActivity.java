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

import AdapterLaptop.MouseAdapter;
import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;
import URLServerLink.Sever;

public class MouseActivity extends AppCompatActivity {
    public static final String KEY_GET_MOUSE_LAPTOP = "GET_MOUSE_LAPTOP";
    private RecyclerView rv_mouse;
    private List<Product> list;
    private MouseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouse);
        initView();
        list=new ArrayList<>();
        adapter=new MouseAdapter(this, list, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(getApplicationContext(), ActivityShowProduct.class);
                intent.putExtra(KEY_GET_MOUSE_LAPTOP, product);
                startActivity(intent);
                MouseActivity.this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });
        rv_mouse.setAdapter(adapter);
        GetDataMouse();

    }

    private void initView() {
        rv_mouse = findViewById(R.id.rv_mouse);
    }
    private void GetDataMouse() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.SourceMouse, new Response.Listener<JSONArray>() {
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
                Toast.makeText(MouseActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}