package AdapterHome;

import android.content.Context;
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

import java.util.List;

import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;

public class FeaturedProductAdapter extends RecyclerView.Adapter<FeaturedProductAdapter.MainProductViewHolder> {
    private Context mContext;
    private List<Product> mListProduct;
    private OnEventShowProduct onEventShowFeaturedProduct;

    public FeaturedProductAdapter(Context mContext, List<Product> mListProduct, OnEventShowProduct onEventShowFeaturedProduct) {
        this.mContext = mContext;
        this.mListProduct = mListProduct;
        this.onEventShowFeaturedProduct = onEventShowFeaturedProduct;
    }

    @NonNull
    @NotNull
    @Override
    public MainProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feature_products, parent, false);
        return new MainProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FeaturedProductAdapter.MainProductViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if(product == null){
            return;
        }else{
            holder.populate(product);
        }
    }

    @Override
    public int getItemCount() {
        return mListProduct == null ? 0 : mListProduct.size();
    }

    public class MainProductViewHolder extends RecyclerView.ViewHolder {

        private CardView cvContainer;
        private ImageView imgFeatureProduct;
        private TextView tvNameProduct;
        private TextView tvStatus;

        public MainProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            cvContainer = (CardView) itemView.findViewById(R.id.cv_container);
            imgFeatureProduct = (ImageView) itemView.findViewById(R.id.img_feature_product);
            tvNameProduct = (TextView) itemView.findViewById(R.id.tv_name_product);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_status);
        }

        public void populate(Product product){
            Glide.with(mContext).load(product.getImgProduct()).into(imgFeatureProduct);
            tvNameProduct.setText(product.getProductName());
            String status = product.getStatus() == 0 ? "Còn hàng" : "Hết hàng";
            tvStatus.setText(status);
            cvContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEventShowFeaturedProduct.onClickShowProduct(product);
                }
            });
        }
    }
}
