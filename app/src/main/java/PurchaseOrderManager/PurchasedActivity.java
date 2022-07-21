package PurchaseOrderManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malthshop.R;
import com.example.malthshop.databinding.ActivityPurchasedBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SharePreferencesManager.SavePreferences;
import URLServerLink.ServerLink;

public class PurchasedActivity extends AppCompatActivity {
    public static final String KEY_PURCHASE_PRODUCT = "GET_PURCHASE_PRODUCT";
    private ActivityPurchasedBinding binding;
    private List<Purchased> mListPurchase;
    private PurchasedAdapter purchasedAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchasedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.rvPurchase.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
    public void getPurchaseOrder(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, ServerLink.URL_GET_PURCHASE_PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        mListPurchase.add(new Purchased(
                                jsonObject.getInt("Id"),
                                jsonObject.getString("ProductName"),
                                jsonObject.getString("Picture"),
                                jsonObject.getInt("Quantity"),
                                jsonObject.getInt("Type"),
                                jsonObject.getDouble("Price"),
                                jsonObject.getDouble("totalPrice")
                        ));
                    }
                    purchasedAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", SavePreferences.getUser(PurchasedActivity.this).getUsername());
                return params;
            }
        };
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_in_pay, R.anim.slide_right_out_pay);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListPurchase = new ArrayList<>();

        purchasedAdapter = new PurchasedAdapter(PurchasedActivity.this, mListPurchase, new PurchasedAdapter.OnClickItem() {
            @Override
            public void onClick(Purchased purchased) {
                Intent intent = new Intent(PurchasedActivity.this, ShowDetailPurchasedActivity.class);
                intent.putExtra(KEY_PURCHASE_PRODUCT, purchased);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });
        getPurchaseOrder();
        binding.rvPurchase.setAdapter(purchasedAdapter);
    }
}