package PayManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malthshop.ActivityShowProduct;
import com.example.malthshop.MainActivity;
import com.example.malthshop.R;
import com.example.malthshop.databinding.ActivityPayBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CartManager.Cart;
import CartManager.CartActivity;
import Fragments.AccountFragment;
import ModelHome.Customer;
import PurchaseOrderManager.PurchasedActivity;
import SharePreferencesManager.SavePreferences;
import URLServerLink.ServerLink;

public class PayActivity extends AppCompatActivity {
    private ActivityPayBinding binding;
    private List<PayProduct> mListPay;
    private PaymentAdapter paymentAdapter;
    private List<Cart> mListCart;
    private List<Customer> mListCusomer;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    private Button btnHome;
    private Button btnPurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mListPay = new ArrayList<>();
        mListCart = new ArrayList<>();
        mListCusomer = new ArrayList<>();
        getCustomer(AccountFragment.urlGetCustomer);
        // Get Product Buy Now
        int id = getIntent().getIntExtra(ActivityShowProduct.KEY_ID_PRODUCT, -1);
        int quantity = getIntent().getIntExtra(ActivityShowProduct.KEY_QUANTITY_PRODUCT, -1);
        String orderDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime());
        getMainProduct(orderDate, quantity, id);

        // Get product in cart
        mListCart = (List<Cart>) getIntent().getSerializableExtra(CartActivity.KEY_GET_LIST_PRODUCT_IN_CART);
        binding.rvPayProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        paymentAdapter = new PaymentAdapter(this, mListPay, mListCart);
        binding.rvPayProduct.setAdapter(paymentAdapter);

        if (mListCart != null) {
            double totalPrice = 0;
            for (int i = 0; i < mListCart.size(); i++) {
                totalPrice += mListCart.get(i).getTotalPrice();
            }
            binding.tvTotalProduct.setText(decimalFormat.format(totalPrice) + "đ");
            double totalAll = totalPrice + 30000;
            binding.tvTotalShip.setText(decimalFormat.format(30000) + "đ");
            binding.tvTotalAll.setText(decimalFormat.format(totalAll) + "đ");
            binding.tvTotalAllProduct.setText(decimalFormat.format(totalAll) + "đ");
        }

        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListCart != null) {
                    for (int i = 0; i < mListCart.size(); i++) {
                        insertOrder(mListCart.get(i));
                        deleteInCart(mListCart.get(i));
                        showDialogAfterBought(Gravity.CENTER);
                    }
                } else {
                    insertOrderPayNow(quantity, id);
                    showDialogAfterBought(Gravity.CENTER);
                }
            }
        });
    }

    private void insertOrder(Cart cart) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, ServerLink.URL_ADD_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idProduct", cart.getId() + "");
                params.put("username", SavePreferences.getUser(PayActivity.this).getUsername());
                params.put("quantity", cart.getQuantity() + "");
                return params;
            }
        };
        queue.add(request);
    }

    private void insertOrderPayNow(int quantity, int idProduct) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, ServerLink.URL_ADD_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idProduct", idProduct + "");
                params.put("username", SavePreferences.getUser(PayActivity.this).getUsername());
                params.put("quantity", quantity + "");
                return params;
            }
        };
        queue.add(request);
    }

    private void getMainProduct(String orderDate, int quantity, int id) {
        RequestQueue queue = Volley.newRequestQueue(PayActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLink.URL_GET_PRODUCT_TO_PAY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        mListPay.add(new PayProduct(
                                jsonObject.getString("ProductName"),
                                jsonObject.getString("Picture"),
                                orderDate,
                                quantity,
                                jsonObject.getDouble("Price"),
                                jsonObject.getInt("IdType")
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                paymentAdapter.notifyDataSetChanged();
                setValuePrice();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idProduct", id + "");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void setValuePrice() {
        if (mListPay.size() > 0) {
            double totalPayNow = 0;
            for (int i = 0; i < mListPay.size(); i++) {
                totalPayNow += mListPay.get(i).getTotalPrice();
            }
            binding.tvTotalProduct.setText(decimalFormat.format(totalPayNow) + "đ");
            double totalInPayNow = totalPayNow + 30000;
            binding.tvTotalShip.setText(decimalFormat.format(30000) + "đ");
            binding.tvTotalAll.setText(decimalFormat.format(totalInPayNow) + "đ");
            binding.tvTotalAllProduct.setText(decimalFormat.format(totalInPayNow) + "đ");
        }
    }

    public void deleteInCart(Cart cart) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, ServerLink.URL_DELETE_PRODUCT_IN_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idProduct", String.valueOf(cart.getId()));
                params.put("username", SavePreferences.getUser(PayActivity.this).getUsername());
                return params;
            }
        };
        queue.add(request);
    }

    private void showDialogAfterBought(int gravity) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_show_bought);

        Window window = dialog.getWindow();

        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        initView(dialog);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PayActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                dialog.dismiss();
                finish();
            }
        });
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PayActivity.this, PurchasedActivity.class));
                dialog.dismiss();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_in_pay, R.anim.slide_right_out_pay);
    }

    private void initView(Dialog dialog) {
        btnHome = (Button) dialog.findViewById(R.id.btn_home);
        btnPurchase = (Button) dialog.findViewById(R.id.btn_purchase);
    }
    private void getCustomer(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0;i < array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                mListCusomer.add( new Customer(
                                        object.getString("Username"),
                                        object.getString("Password"),
                                        object.getString("FullName"),
                                        object.getInt("Age"),
                                        object.getString("Address")
                                ));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        binding.tvAddressPay.setText(mListCusomer.get(0).getAddress());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PayActivity.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        }
        ) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String user=SavePreferences.getUser(PayActivity.this).getUsername();
                params.put("Username", user);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}