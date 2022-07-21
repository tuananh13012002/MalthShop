package Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malthshop.MainActivity;
import com.example.malthshop.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ModelHome.Customer;
import SharePreferencesManager.SavePreferences;

public class LoginActivity extends AppCompatActivity {

    private String url = "https://unemphatic-tailors.000webhostapp.com/Phone/loginCustomer.php";
    private TextInputEditText username;
    private TextInputEditText password;
    private AppCompatButton btnLogin;
    private AppCompatButton btnLoginFb;
    private TextView tvSignUp;
    private TextInputLayout txtUser;
    private TextInputLayout txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (user.equals("") || pass.equals("")) {
                    txtUser.setError("Username empty");
                    txtPass.setError("Password empty");
                } else {
                    SavePreferences.setUser(getApplicationContext(), user, pass);
                    login(url);
                }
            }
        });
    }


    private void login(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Log.d("AAAA", "Lỗi!\n" + response.toString());
                            Toast.makeText(LoginActivity.this, "Tài khoản mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        }
        ) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Username", username.getText().toString().trim());
                params.put("Password", password.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_Login);
        btnLoginFb = findViewById(R.id.btn_loginFb);
        tvSignUp = findViewById(R.id.tv_SignUp);
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_up_login, R.anim.slide_out_up_login);
    }
}