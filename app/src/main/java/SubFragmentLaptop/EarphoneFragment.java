package SubFragmentLaptop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

import AdapterLaptop.EarPhoneAdapter;
import AdapterLaptop.MouseAdapter;
import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;
import URLServerLink.Sever;


public class EarphoneFragment extends Fragment {
    public static final String KEY_GET_EARPHONE_LAPTOP = "GET_EARPHONE_LAPTOP";
    ArrayList<Product> mListEarPhone;
    private RecyclerView rv;
    private EarPhoneAdapter earPhoneAdapter;

    public EarphoneFragment() {

    }

    public static EarphoneFragment newInstance(String param1, String param2) {
        EarphoneFragment fragment = new EarphoneFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_earphone, container, false);
        mListEarPhone = new ArrayList<>();
        rv = v.findViewById(R.id.rvFragmentEarPhone);
        earPhoneAdapter = new EarPhoneAdapter(getContext(), mListEarPhone, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(getContext(), ActivityShowProduct.class);
                intent.putExtra(KEY_GET_EARPHONE_LAPTOP, product);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        rv.setAdapter(earPhoneAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rv.setLayoutManager(gridLayoutManager);
        GetDataEarPhone();
        return v;
    }

    // get du lieu san pham
    private void GetDataEarPhone() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                            mListEarPhone.add(new Product(
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
                        earPhoneAdapter.notifyDataSetChanged();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}