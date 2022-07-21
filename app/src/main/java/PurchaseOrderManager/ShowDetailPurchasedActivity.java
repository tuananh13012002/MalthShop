package PurchaseOrderManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.malthshop.R;
import com.example.malthshop.databinding.ActivityShowDetailPurchasedBinding;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import SharePreferencesManager.SavePreferences;
import URLServerLink.ServerLink;

public class ShowDetailPurchasedActivity extends AppCompatActivity {
    private ActivityShowDetailPurchasedBinding binding;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    private Purchased purchased;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDetailPurchasedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        purchased = (Purchased) getIntent().getSerializableExtra(PurchasedActivity.KEY_PURCHASE_PRODUCT);
        Glide.with(this).load(purchased.getImage()).into(binding.imgProductPurchase);
        binding.tvNamePurchaseProduct.setText(purchased.getName());
        binding.tvPriceProduct.setText(decimalFormat.format(purchased.getPrice())+"đ");
        binding.tvQuantityPurchase.setText("x"+purchased.getQuantity());
        binding.tvTotalPrice.setText(decimalFormat.format(purchased.getTotalPrice())+"đ");
        binding.tvTypeProduct.setText("Loại: "+getType(purchased));

        binding.btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePurchase(purchased);
                finish();
            }
        });

    }
    private String getType(Purchased purchased){
        String type = "";
        if(purchased.getType() == 1){
            type = "Điện thoại";
        }else if(purchased.getType() == 2){
            type = "Máy tính";
        }else if(purchased.getType() == 3){
            type = "Phụ kiện";
        }else if(purchased.getType() == 4){
            type = "Chuột";
        }else if(purchased.getType() == 5){
            type = "Tai nghe";
        }
        return type;
    }

    private void deletePurchase(Purchased purchased){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLink.URL_DELETE_PURCHASE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("id", purchased.getId()+"");
                params.put("username", SavePreferences.getUser(ShowDetailPurchasedActivity.this).getUsername());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_in_pay, R.anim.slide_right_out_pay);
    }

}