package CartManager;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.malthshop.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import SharePreferencesManager.SavePreferences;
import URLServerLink.ServerLink;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    interface OnItemCheckListener{
        void onItemChecked(Cart cart);
        void onItemUnChecked(Cart cart);
    }

    private CartActivity context;
    private List<Cart> mListCart;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    public int index = 0;
    private IEventClickCheckBox onEventClickCheckBox;
    private int check = 0;
    double[] tmp = new double[100];
    boolean isSelected = false;
    private OnItemCheckListener onItemCheckListener;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


    public CartAdapter(CartActivity context, List<Cart> mListCart, IEventClickCheckBox onEventClickCheckBox, OnItemCheckListener onItemCheckListener) {
        this.context = context;
        this.mListCart = mListCart;
        this.onEventClickCheckBox = onEventClickCheckBox;
        this.onItemCheckListener = onItemCheckListener;
    }

    @NonNull
    @NotNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_in_cart, parent, false);
        return new CartViewHolder(view);
    }
    public void selectAll(){
        isSelected = true;
        notifyDataSetChanged();
    }

    public void unSelectedAll(){
        isSelected = false;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartAdapter.CartViewHolder holder, int position) {
        Cart cart = mListCart.get(position);
        if(cart == null){
            return;
        }else{
            viewBinderHelper.setOpenOnlyOne(true);
            viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(cart.getId()));
            viewBinderHelper.closeLayout(String.valueOf(cart.getId()));

            if(isSelected){
                holder.cbxInCart.setChecked(true);
            }else{
                holder.cbxInCart.setChecked(false);
            }
            holder.populate(cart, position);
            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.delete(cart);
                    mListCart.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(mListCart != null) {
            return mListCart.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cbxInCart;
        private ImageView imgProductInCart;
        private TextView tvNameProductInCart, tvDelete;
        private TextView tvTypeProductInCart;
        private TextView tvPriceProductInCart;
        private ImageButton imbDecrease;
        private TextView tvQuantityInCart;
        private ImageButton imbIncrease;
        private SwipeRevealLayout swipeRevealLayout;
        int quantity;

        public CartViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            initView(itemView);

        }

        private void initView(View itemView) {
            cbxInCart = (CheckBox) itemView.findViewById(R.id.cbx_in_cart);
            imgProductInCart = (ImageView) itemView.findViewById(R.id.img_product_in_cart);
            tvNameProductInCart = (TextView) itemView.findViewById(R.id.tv_name_product_in_cart);
            tvTypeProductInCart = (TextView) itemView.findViewById(R.id.tv_type_product_in_cart);
            tvPriceProductInCart = (TextView) itemView.findViewById(R.id.tv_price_product_in_cart);
            imbDecrease = (ImageButton) itemView.findViewById(R.id.imb_decrease);
            tvQuantityInCart = (TextView) itemView.findViewById(R.id.tv_quantity_in_cart);
            imbIncrease = (ImageButton) itemView.findViewById(R.id.imb_increase);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
            swipeRevealLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipeRevealLayout);
        }



        public void populate(Cart cart, int position ){
            Glide.with(context).load(cart.getImage()).into(imgProductInCart);
            tvNameProductInCart.setText(cart.getName());
            tvPriceProductInCart.setText(decimalFormat.format(cart.getPrice())+"đ");
            tvTypeProductInCart.setText("Loại: "+getType(cart));
            tvQuantityInCart.setText(cart.getQuantity()+"");

            quantity = Integer.parseInt(tvQuantityInCart.getText().toString());

            imbDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(quantity > 1){
                        quantity--;
                        tvQuantityInCart.setText(quantity+"");
                        cart.setQuantity(quantity);
                        updateQuantity(cart, quantity, position);
                    }else if(quantity == 1){
                        imbDecrease.setEnabled(false);
                    }
                }
            });

            imbIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imbDecrease.setEnabled(true);
                    quantity++;
                    tvQuantityInCart.setText(quantity+"");
                    cart.setQuantity(quantity);
                    updateQuantity(cart, quantity, position);
                }
            });

            cbxInCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b == true){
                        check = 1;
                        index++;
                        onItemCheckListener.onItemChecked(cart);
                        updateQuantity(cart, quantity, position);

                    }else{
                        check = 2;
                        index--;
                        onItemCheckListener.onItemUnChecked(cart);
                        updateQuantity(cart, quantity, position);
                    }
                }
            });
        }

        private void updateQuantity(Cart cart, int quantity, int position){
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerLink.URL_UPDATE_QUANTITY, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    double totalPrice = 0;
                    cart.setTotalPrice(Double.parseDouble(response));

                    if(check == 2){
                        tmp[position] = 0;
                    }else{
                        tmp[position] = cart.getTotalPrice();
                    }
                    for(int i = 0; i < tmp.length; i++){
                        totalPrice += tmp[i];
                    }
                    onEventClickCheckBox.onClickChangePrice(totalPrice, index);
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
                    params.put("quantity", quantity+"");
                    params.put("username", SavePreferences.getUser(context).getUsername());
                    params.put("idProduct", cart.getId()+"");
                    params.put("priceProduct", cart.getPrice()+"");
                    return params;
                }
            };
            queue.add(stringRequest);
        }

        private String getType(Cart cart){
            String type = "";
            if(cart.getType() == 1){
                type = "Điện thoại";
            }else if(cart.getType() == 2){
                type = "Máy tính";
            }else if(cart.getType() == 3){
                type = "Phụ kiện";
            }else if(cart.getType() == 4){
                type = "Chuột";
            }else if(cart.getType() == 5){
                type = "Tai nghe";
            }
            return type;
        }
    }
}
