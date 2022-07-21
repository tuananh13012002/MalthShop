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

import ModelHome.Brand;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
    private Context mContext;
    private List<Brand> mListBrand;

    public BrandAdapter(Context mContext, List<Brand> mListBrand) {
        this.mContext = mContext;
        this.mListBrand = mListBrand;
    }

    @NonNull
    @NotNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand_product, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BrandViewHolder holder, int position) {
        Brand brand = mListBrand.get(position);
        if(brand == null){
            return;
        }else{
            Glide.with(mContext).load(brand.getImg()).into(holder.imgBrand);
            holder.tvBrand.setText(brand.getBrandName());
            holder.cvContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListBrand != null ? mListBrand.size()-9 : 0;
    }

    public class BrandViewHolder extends RecyclerView.ViewHolder {

        private CardView cvContainer;
        private ImageView imgBrand;
        private TextView tvBrand;

        public BrandViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            cvContainer = (CardView) view.findViewById(R.id.cv_container);
            imgBrand = (ImageView) view.findViewById(R.id.img_brand);
            tvBrand = (TextView) view.findViewById(R.id.tv_brand);
        }
    }
}
