package PurchaseOrderManager;

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

import PayManager.PayProduct;

public class PurchasedAdapter extends RecyclerView.Adapter<PurchasedAdapter.PurchaseViewHolder> {

    interface OnClickItem{
        void onClick(Purchased purchased);
    }

    private Context context;
    private List<Purchased> mListPurchased;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    private OnClickItem onClickItem;

    public PurchasedAdapter(Context context, List<Purchased> mListPurchased, OnClickItem onClickItem) {
        this.context = context;
        this.mListPurchased = mListPurchased;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @NotNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchased, parent, false);
        return new PurchaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PurchasedAdapter.PurchaseViewHolder holder, int position) {
        Purchased purchased = mListPurchased.get(position);
        if(purchased == null) {
            return;
        }else{
            holder.populate(purchased);
        }
    }


    @Override
    public int getItemCount() {
        return mListPurchased != null ? mListPurchased.size() : 0;
    }

    public class PurchaseViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProductPurchase;
        private TextView tvNamePurchaseProduct;
        private TextView tvTypeProduct;
        private TextView tvQuantityPurchase;
        private TextView tvPriceProduct;
        private TextView tvTotalQuantity;
        private TextView tvTotalPrice;
        private View itemView;
        public PurchaseViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            initView(itemView);
            this.itemView = itemView;
        }

        private void initView(View itemView) {
            imgProductPurchase = (ImageView) itemView.findViewById(R.id.img_product_purchase);
            tvNamePurchaseProduct = (TextView) itemView.findViewById(R.id.tv_name_purchase_product);
            tvTypeProduct = (TextView) itemView.findViewById(R.id.tv_type_product);
            tvQuantityPurchase = (TextView) itemView.findViewById(R.id.tv_quantity_purchase);
            tvPriceProduct = (TextView) itemView.findViewById(R.id.tv_price_product);
            tvTotalQuantity = (TextView) itemView.findViewById(R.id.tv_total_quantity);
            tvTotalPrice = (TextView) itemView.findViewById(R.id.tv_total_price);
        }

        public void populate(Purchased purchased){
            Glide.with(context).load(purchased.getImage()).into(imgProductPurchase);
            tvNamePurchaseProduct.setText(purchased.getName());
            tvPriceProduct.setText(decimalFormat.format(purchased.getPrice())+"đ");
            tvQuantityPurchase.setText("x"+purchased.getQuantity());
            tvTotalPrice.setText(decimalFormat.format(purchased.getTotalPrice())+"đ");
            tvTotalQuantity.setText(purchased.getQuantity()+" sản phẩm");
            tvTypeProduct.setText("Loại: "+getType(purchased));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickItem.onClick(purchased);
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
    }
}
