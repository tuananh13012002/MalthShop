package Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malthshop.MainActivity;
import com.example.malthshop.R;
import com.example.malthshop.databinding.FragmentAccountBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CartManager.CartActivity;
import Login.LoginActivity;
import Login.RegisterActivity;
import ModelHome.Customer;
import PurchaseOrderManager.PurchasedActivity;
import SharePreferencesManager.SavePreferences;

public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;
    public static String urlGetCustomer="https://unemphatic-tailors.000webhostapp.com/Phone/getCustomer.php";
    private List<Customer> listCustomer;
    TextView tvName;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tvName = binding.tvName;
        listCustomer = new ArrayList<>();
        if (SavePreferences.getUser(getContext()).getUsername().length() != 0) {
            binding.tvCard.setVisibility(View.VISIBLE);
            binding.tvMail.setVisibility(View.VISIBLE);
            binding.btnDangNhap.setVisibility(View.GONE);
            binding.btnDangKy.setVisibility(View.GONE);
        }

        binding.btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RegisterActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });
        binding.btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });
        binding.btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavePreferences.clear(getContext());
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        binding.tvCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });
        binding.viewPurchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SavePreferences.getUser(getContext()).getUsername().length() == 0){
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                }else{
                    startActivity(new Intent(getActivity(), PurchasedActivity.class));
                    getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(SavePreferences.getUser(getContext()).getUsername().length() != 0){
            getCustomer(urlGetCustomer);
            if (SavePreferences.getUser(getContext()).getUsername().length() != 0) {
                binding.tvCard.setVisibility(View.VISIBLE);
                binding.tvMail.setVisibility(View.VISIBLE);
                binding.btnDangNhap.setVisibility(View.GONE);
                binding.btnDangKy.setVisibility(View.GONE);
            }
        }
    }

    private void getCustomer(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0;i < array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                listCustomer.add( new Customer(
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
                        tvName.setText(listCustomer.get(0).getFullname());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        }
        ) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String user=SavePreferences.getUser(getContext()).getUsername();
                params.put("Username", user);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}