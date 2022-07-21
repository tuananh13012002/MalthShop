package PayManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.malthshop.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

import CartManager.Cart;

public class PaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int VIEW_PAY_PRODUCT = 0;
    private int VIEW_PRODUCT_IN_CART = 1;

    private Context context;
    private List<PayProduct> mListPay;
    private List<Cart> mListCart;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

    public PaymentAdapter(Context context, List<PayProduct> mListPay, List<Cart> mListCart) {
        this.context = context;
        this.mListPay = mListPay;
        this.mListCart = mListCart;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_PAY_PRODUCT){
            return new BuyNowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay_product, parent, false));
        }
        if(viewType == VIEW_PRODUCT_IN_CART){
            return new CartCheckOutViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay_product, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BuyNowViewHolder){
            ((BuyNowViewHolder) holder).populate(mListPay.get(position));
        }
        if(holder instanceof CartCheckOutViewHolder){
            ((CartCheckOutViewHolder) holder).populate(mListCart.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(mListCart != null){
            return mListPay.size() + mListCart.size();
        }
        return mListPay.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position < mListPay.size()){
            return VIEW_PAY_PRODUCT;
        }
        if((position - mListPay.size()) < mListCart.size()){
            return VIEW_PRODUCT_IN_CART;
        }
        return -1;
    }

    public class BuyNowViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProductPay;
        private TextView tvNamePayProduct;
        private TextView tvTypeProduct;
        private TextView tvPriceProduct;
        private TextView tvQuantityPay;
        private TextView tvPayTotalQuantity;
        private TextView tvTotalPrice;

        public BuyNowViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            imgProductPay = (ImageView) itemView.findViewById(R.id.img_product_pay);
            tvNamePayProduct = (TextView) itemView.findViewById(R.id.tv_name_pay_product);
            tvTypeProduct = (TextView) itemView.findViewById(R.id.tv_type_product);
            tvPriceProduct = (TextView) itemView.findViewById(R.id.tv_price_product);
            tvQuantityPay = (TextView) itemView.findViewById(R.id.tv_quantity_pay);
            tvPayTotalQuantity = (TextView) itemView.findViewById(R.id.tv_pay_total_quantity);
            tvTotalPrice = (TextView) itemView.findViewById(R.id.tv_total_price);
        }
        public void populate(PayProduct payProduct){
            Glide.with(context).load(payProduct.getImgProduct()).into(imgProductPay);
            tvNamePayProduct.setText(payProduct.getNameProduct());
            tvPriceProduct.setText(decimalFormat.format(payProduct.getPrice())+"đ");
            tvTypeProduct.setText("Phân loại: "+getType(payProduct));
            tvQuantityPay.setText("x"+payProduct.getQuantity());
            tvPayTotalQuantity.setText("Tổng số tiền ("+payProduct.getQuantity()+" sản phẩm)");
            tvTotalPrice.setText(decimalFormat.format(payProduct.getTotalPrice())+"đ");
        }

        private String getType(PayProduct payProduct){
            String type = "";
            if(payProduct.getType() == 1){
                type = "Điện thoại";
            }else if(payProduct.getType() == 2){
                type = "Máy tính";
            }else if(payProduct.getType() == 3){
                type = "Phụ kiện";
            }else if(payProduct.getType() == 4){
                type = "Chuột";
            }else if(payProduct.getType() == 5){
                type = "Tai nghe";
            }
            return type;
        }

    }
    public class CartCheckOutViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProductPay;
        private TextView tvNamePayProduct;
        private TextView tvTypeProduct;
        private TextView tvPriceProduct;
        private TextView tvQuantityPay;
        private TextView tvPayTotalQuantity;
        private TextView tvTotalPrice;

        public CartCheckOutViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            imgProductPay = (ImageView) itemView.findViewById(R.id.img_product_pay);
            tvNamePayProduct = (TextView) itemView.findViewById(R.id.tv_name_pay_product);
            tvTypeProduct = (TextView) itemView.findViewById(R.id.tv_type_product);
            tvPriceProduct = (TextView) itemView.findViewById(R.id.tv_price_product);
            tvQuantityPay = (TextView) itemView.findViewById(R.id.tv_quantity_pay);
            tvPayTotalQuantity = (TextView) itemView.findViewById(R.id.tv_pay_total_quantity);
            tvTotalPrice = (TextView) itemView.findViewById(R.id.tv_total_price);
        }

        public void populate(Cart cart){
            Glide.with(context).load(cart.getImage()).into(imgProductPay);
            tvNamePayProduct.setText(cart.getName());
            tvPriceProduct.setText(decimalFormat.format(cart.getPrice()) + "đ");
            tvTypeProduct.setText("Phân loại: "+getType(cart));
            tvQuantityPay.setText("x"+cart.getQuantity());
            tvPayTotalQuantity.setText("Tổng số tiền ("+cart.getQuantity()+" sản phẩm)");
            tvTotalPrice.setText(decimalFormat.format(cart.getTotalPrice())+"đ");
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
