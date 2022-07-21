package CartManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malthshop.R;
import com.example.malthshop.databinding.ActivityCartBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import PayManager.PayActivity;
import SharePreferencesManager.SavePreferences;
import URLServerLink.ServerLink;

public class CartActivity extends AppCompatActivity {
    public static final String KEY_GET_LIST_PRODUCT_IN_CART = "GET LIST PRODUCT IN CART";
    private ActivityCartBinding binding;
    private List<Cart> mListCart;
    private CartAdapter cartAdapter;
    private DecimalFormat decimalFormat = new DecimalFormat();
    private List<Cart> mListItemSelected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListItemSelected.size() > 0){
                    Intent intent = new Intent(CartActivity.this, PayActivity.class);
                    intent.putExtra(KEY_GET_LIST_PRODUCT_IN_CART, (Serializable) mListItemSelected);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                }else{
                    Toast toast = new Toast(CartActivity.this);
                    toast.setView(getLayoutInflater().inflate(R.layout.custom_toast_none_product, null, false));
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    public void getDataInCart(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLink.URL_GET_DATA_IN_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        mListCart.add(new Cart(
                                jsonObject.getInt("Id"),
                                jsonObject.getString("ProductName"),
                                jsonObject.getString("Picture"),
                                jsonObject.getInt("Quantity"),
                                jsonObject.getDouble("totalPrice"),
                                jsonObject.getDouble("Price"),
                                jsonObject.getInt("IdType"),
                                jsonObject.getInt("IdBill")
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cartAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR IN CART", error.toString());
            }
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", SavePreferences.getUser(CartActivity.this).getUsername());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void delete(Cart cart){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, ServerLink.URL_DELETE_PRODUCT_IN_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){

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
                params.put("idProduct", String.valueOf(cart.getId()));
                params.put("username", SavePreferences.getUser(CartActivity.this).getUsername());
                return params;
            }
        };
        queue.add(request);
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.btnBuy.setText("Mua NGAY (0)");
        binding.tvTotalPriceCart.setText("0đ");
        mListCart = new ArrayList<>();

        cartAdapter = new CartAdapter(this, mListCart, new IEventClickCheckBox() {
            @Override
            public void onClickChangePrice(double totalPrice, int index) {
                binding.tvTotalPriceCart.setText(decimalFormat.format(totalPrice) + "đ");
                binding.btnBuy.setText("MUA NGAY (" + index + ")");
            }
        }, new CartAdapter.OnItemCheckListener() {
            @Override
            public void onItemChecked(Cart cart) {
                mListItemSelected.add(cart);
            }

            @Override
            public void onItemUnChecked(Cart cart) {
                mListItemSelected.remove(cart);
            }
        });
        binding.cbxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    cartAdapter.selectAll();
                }else{
                    cartAdapter.unSelectedAll();
                }
            }
        });
        binding.rvCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvCart.setAdapter(cartAdapter);
        getDataInCart();
    }
}