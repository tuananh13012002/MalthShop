package AdapterHome;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.malthshop.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

import InterfaceForHomeManager.OnEventShowProductListener;
import ModelHome.SpecialProduct;

public class SProductAdapter extends RecyclerView.Adapter<SProductAdapter.SProductViewHolder> {
    private Context mContext;
    private List<SpecialProduct> mListSProduct;
    private OnEventShowProductListener onEventShowProductListener;

    public SProductAdapter(Context mContext, List<SpecialProduct> mListSProduct, OnEventShowProductListener onEventShowProductListener) {
        this.mContext = mContext;
        this.mListSProduct = mListSProduct;
        this.onEventShowProductListener = onEventShowProductListener;
    }

    @NonNull
    @NotNull
    @Override
    public SProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special_product, parent, false);
        return new SProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SProductViewHolder holder, int position) {
        SpecialProduct specialProduct = mListSProduct.get(position);
        if(specialProduct == null){
            return;
        }else{
            if(mListSProduct.get(holder.getAdapterPosition()).getStatus() == 0){
                holder.tvStatus.setTextColor(Color.GREEN);
            }else{
                holder.tvStatus.setTextColor(Color.YELLOW);
            }
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            String status = specialProduct.getStatus() == 0 ? "Còn hàng" : "Hết hàng";
            Glide.with(mContext).load(specialProduct.getImg()).into(holder.imgSproduct);
            holder.tvPrice.setText(decimalFormat.format(specialProduct.getPrice() -(specialProduct.getPrice() * specialProduct.getPercentSale() / 100))+" VNĐ");
            holder.tvPercent.setText(specialProduct.getSale()+"%");
            holder.tvStatus.setText(status);
            holder.cvContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEventShowProductListener.onClickShowSProductListener(specialProduct);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
    return mListSProduct != null ? mListSProduct.size() : 0;
    }

    public class SProductViewHolder extends RecyclerView.ViewHolder {

        private CardView cvContainer;
        private ImageView imgSproduct;
        private TextView tvPercent;
        private TextView tvPrice;
        private TextView tvStatus;

        public SProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            cvContainer = (CardView) itemView.findViewById(R.id.cv_container);
            imgSproduct = (ImageView) itemView.findViewById(R.id.img_sproduct);
            tvPercent = (TextView) itemView.findViewById(R.id.tv_percent);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_status);
        }
    }
}
