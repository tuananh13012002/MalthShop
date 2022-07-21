package Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.malthshop.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    private TextInputEditText username;
    private TextInputEditText password;
    private TextInputEditText fullname;
    private TextInputEditText age;
    private TextInputEditText address;
    private AppCompatButton btnSignUp;
    String urlInsertCustomer = "https://unemphatic-tailors.000webhostapp.com/Phone/insertCustomer.php";
    private TextInputLayout txtUser;
    private TextInputLayout txtPass;
    private TextInputLayout txtFull;
    private TextInputLayout txtAge;
    private TextInputLayout txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String fullName = fullname.getText().toString().trim();
                String Age = age.getText().toString().trim();
                String addRess = address.getText().toString().trim();
                if (user.equals("") || pass.equals("") || fullName.equals("") || Age.equals("") || addRess.equals("")) {
                    txtUser.setError("Username empty");
                    txtPass.setError("Password empty");
                    txtFull.setError("FullName empty");
                    txtAge.setError("Age empty");
                    txtAddress.setError("Address empty");
                } else if (user.length() < 8 && user.length() > 16 && pass.length() < 5) {
                    txtUser.setError("Username greater than 8 and less than 16");
                    txtPass.setError("Password greater than 5");
                    txtFull.setError("");
                    txtAge.setError("");
                    txtAddress.setError("");
                } else {
                    txtUser.setError("");
                    txtPass.setError("");
                    txtFull.setError("");
                    txtAge.setError("");
                    txtAddress.setError("");
                    insertCustomer(urlInsertCustomer);
                }
            }
        });
    }

    private void insertCustomer(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else if (response.trim().equals("username")) {
                            txtUser.setError("Username already exists");
                            Log.d("AAAA", "Lỗi!\n" + response.toString());
                        } else {
                            txtUser.setError("");
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            Log.d("AAAA", "Lỗi!\n" + response.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        }
        ) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("usernameCus", username.getText().toString().trim());
                params.put("passwordCus", password.getText().toString().trim());
                params.put("fullNameCus", fullname.getText().toString().trim());
                params.put("ageCus", age.getText().toString().trim());
                params.put("addressCus", address.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        fullname = findViewById(R.id.fullname);
        age = findViewById(R.id.age);
        address = findViewById(R.id.address);
        btnSignUp = findViewById(R.id.btn_signUp);
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        txtFull = findViewById(R.id.txtFull);
        txtAge = findViewById(R.id.txtAge);
        txtAddress = findViewById(R.id.txtAddress);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}