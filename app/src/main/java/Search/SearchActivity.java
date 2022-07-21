package Search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.malthshop.ActivityShowProduct;
import com.example.malthshop.MainActivity;
import com.example.malthshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import AdapterSearch.SearchRecyclerViewAdapter;
import AdapterSearch.TypeRecyclerViewAdapter;
import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;
import ModelLaptop.TypeProduct;

public class SearchActivity extends AppCompatActivity {
    public static final String KEY_GET_FEATURED_PRODUCT = "GET_FEATURED_PRODUCT";
    private List<TypeProduct> list;
    private TypeRecyclerViewAdapter adapter;
    private String urlGetType = "https://unemphatic-tailors.000webhostapp.com/Phone/getType.php";
    private String urlSearch = "https://unemphatic-tailors.000webhostapp.com/Phone/searchProduct.php";

    SearchView searchView = null;
    private Toolbar toobar;
    private RecyclerView rvType;
    private RecyclerView rvProduct;
    private List<Product> productList;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        list = new ArrayList<>();
        productList = new ArrayList<>();
        adapter = new TypeRecyclerViewAdapter(this, list);
        rvType.setAdapter(adapter);
        setSupportActionBar(toobar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        getType(urlGetType);
        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(SearchActivity.this, productList, new OnEventShowProduct() {
            @Override
            public void onClickShowProduct(Product product) {
                Intent intent = new Intent(SearchActivity.this, ActivityShowProduct.class);
                intent.putExtra(KEY_GET_FEATURED_PRODUCT, product);
                startActivity(intent);
                SearchActivity.this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        });

        rvProduct.setLayoutManager(new NoCrashLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvProduct.setAdapter(searchRecyclerViewAdapter);
        getSearch(urlSearch);
    }


    private void getType(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        list.add(new TypeProduct(
                                object.getInt("id"),
                                object.getString("TypeName"),
                                object.getString("Picture")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_nav, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
            searchView.setIconified(false);
            searchView.setQueryHint("Tìm kiếm");
            searchView.setBackgroundColor(Color.WHITE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchRecyclerViewAdapter.getFilter().filter(query);
                    rvProduct.setVisibility(View.VISIBLE);
                    rvType.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    searchRecyclerViewAdapter.getFilter().filter(newText);
                    rvProduct.setVisibility(View.VISIBLE);
                    rvType.setVisibility(View.GONE);
                    return false;
                }
            });
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    rvProduct.setVisibility(View.GONE);
                    rvType.setVisibility(View.VISIBLE);
                    searchView.setBackgroundResource(R.drawable.bg_search);
                    return false;
                }
            });
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchView.setBackgroundColor(Color.WHITE);
                }
            });
        }

        TextView txt = (TextView) menu.findItem(R.id.exit).getActionView();
        txt.setText("  Thoát  ");
        txt.setTextColor(Color.WHITE);
        txt.setTypeface(Typeface.DEFAULT_BOLD);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
                finish();
            }
        });
        return true;
    }

    private void initView() {
        toobar = findViewById(R.id.toobar);
        rvType = findViewById(R.id.rv_type);
        rvProduct = findViewById(R.id.rv_product);
    }

    private void getSearch(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        productList.add(new Product(
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

                    searchRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("AAAA", "Lỗi!\n" + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    // mở rộng LinearLayoutManager và bắt lỗi
    public class NoCrashLinearLayoutManager extends LinearLayoutManager {

        public NoCrashLinearLayoutManager(Context context) {
            super(context);
        }

        public NoCrashLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public NoCrashLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }
}